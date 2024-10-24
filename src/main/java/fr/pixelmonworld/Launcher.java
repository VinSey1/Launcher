package fr.pixelmonworld;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.flowupdater.versions.forge.ForgeVersion;
import fr.flowarg.flowupdater.versions.forge.ForgeVersionBuilder;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.pixelmonworld.panels.main.MainPanel;
import fr.pixelmonworld.utils.LauncherLogger;
import fr.pixelmonworld.utils.SiteUtils;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Coeur technique de l'application.
 */
public class Launcher {

    // Version de Minecraft
    private static final String MINECRAFT_VERSION = "1.16.5";
    // Version de Forge
    private static final String FORGE_VERSION = "36.2.39";
    // Path à partir duquel récupérer la bonne version des mods
    public static final String MODPACKS_URL = "https://nacou.pixelmonworld.fr/modpack";
    // IP du serveur
    private static final String SERVER_NAME = "play.pixelmonworld.fr";
    // Port du serveur
    private static final String SERVER_PORT = "25564";
    public static final String DISCORD_APPLICATION_ID = "1297976065121325076";
    // Informations globales sur Minecraft
    private static GameInfos gameInfos = new GameInfos(
            "PixelmonWorld",
            new GameVersion(MINECRAFT_VERSION, GameType.V1_13_HIGHER_FORGE),
            new GameTweak[]{GameTweak.FORGE}
    );
    // Path de l'application (%APPDATA%/.PixelmonWorld/)
    private static Path path = gameInfos.getGameDir();
    // Fichier de crash de l'application
    private static File crashFile = new File(String.valueOf(path), "crashed");
    // Objet permettant de log les erreurs
    private static CrashReporter reporter = new CrashReporter(String.valueOf(crashFile), path);
    // Auth Microsoft
    private static AuthInfos authInfos;
    // Le panneau principal de l'application
    private static MainPanel mainPanel;

    // La ram allouée à Minecraft via le RamPanel
    private static Integer ram;
    // Fichier contenant la liste des serveurs
    private static File serversFile =  new File(String.valueOf(path), "servers.dat");
    // Zip du texturepack du serveur
    private static File resourcepackFile =  new File(String.valueOf(path), "\\resourcepacks\\PixelmonWorld.zip");

    /**
     * Permet d'ajouter de la ram jusqu'à 16 et la sauvegarder.
     */
    public static void addRam() {
        if (ram < 16) {
            ram++;
            MainFrame.getSaver().set("ram", String.valueOf(ram));
        }
    }

    /**
     * Permet d'enlever de la ram jusqu'à 2 et la sauvegarder.
     */
    public static void removeRam() {
        if (ram > 2) {
            ram--;
            MainFrame.getSaver().set("ram", String.valueOf(ram));
        }
    }

    /**
     * Permet de récupérer la ram actuelle. Si jamais il n'y en a pas de définie, la met à 2 et la sauvegarde.
     * @return La ram actuelle allouée à Minecraft.
     */
    public static int getRam() {
        if (ram == null) {
            String savedRam = MainFrame.getSaver().get("ram");
            if (savedRam != null && !savedRam.isEmpty()) {
                ram = Integer.valueOf(savedRam);
            } else {
                ram = 2;
            }
        }
        return ram;
    }

    /**
     * Permet de récupérer les arguments de ram pour Minecraft.
     * @return La liste des arguments permettant de définir la ram.
     */
    private static Collection<String> getRamVmArg() {
        Collection<String> vmArgs = new ArrayList<>();
        vmArgs.add("-Xmx" + ram * 1024 + "M");
        vmArgs.add("-Xms" + ram * 1024 + "M");
        return vmArgs;
    }

    /**
     * Permet de se connecter automatiquement à Microsoft sans action utilisateur avec le token sauvegardé.
     * @return Vrai si l'utilisateur est connecté, faux sinon.
     */
    public static boolean defaultAuth() {
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result;

        String refresh_token = MainFrame.getSaver().get("refresh_token");
        // Si jamais la connexion a déjà été faite via le launcher et que c'est sauvegardé
        if (refresh_token != null && !refresh_token.isEmpty()) {
            try {
                result = microsoftAuthenticator.loginWithRefreshToken(refresh_token);
                authInfos = new AuthInfos(
                        result.getProfile().getName(),
                        result.getAccessToken(),
                        result.getProfile().getId()
                );
            } catch (Exception e) {
                MainFrame.getSaver().set("refresh_token", "");
                getReporter().catchError(e, "Impossible de se connecter à Microsoft automatiquement. Relancez le launcher.");
            }
            return true;
        }
        return false;
    }

    /**
     * Permet d'initialiser les différents fichiers qui proviennent des ressources et qui seront utilisés dans le launcher.
     */
    // TODO Ne marche pas en .exe avec la récupération via les ressources, voir pour récupérer depuis le site
    public static void initFiles() {
        try {
            showLoadingScreen();
            // Permet de récupérer la liste des serveurs pour l'afficher dans Minecraft
            if (!serversFile.exists()) {
                serversFile.createNewFile();
            }
            File serversFileFromSite = SiteUtils.getFileFromSite(serversFile);
//            LauncherFileUtils.copyFile(serversFileFromSite, serversFile);
            // Permet de récupérer le texture pack du serveur s'il n'est pas présent dans les fichiers du jeu
            if (!resourcepackFile.exists()) {
                resourcepackFile.getParentFile().mkdirs();
                resourcepackFile.createNewFile();
            }
            File resourcepackFromSite = SiteUtils.getFileFromSite(resourcepackFile);
//            LauncherFileUtils.copyFile(resourcepackFromSite, resourcepackFile);
            closeLoadingScreen();
        } catch (IOException e) {
            erreurInterne(e);
        }
    }

    public static void init() {
        crashFile.mkdirs();
        Launcher.initDiscord();
        Launcher.getFilesFromSite();
    }

    private static void getFilesFromSite() {
        MainFrame.getSaver().set("news", "true");
    }

    /**
     * Permet d'initialiser Discord Rich Presence.
     */
    public static void initDiscord() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder(SERVER_NAME);
            DiscordRPC.discordUpdatePresence(presence.build());
        }).build();
        DiscordRPC.discordInitialize(DISCORD_APPLICATION_ID, handlers, false);
        DiscordRPC.discordRegister(DISCORD_APPLICATION_ID, "");
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordRPC.discordRunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    /**
     * Permet de se connecter à Microsoft via une fenêtre et de sauvegarder le token.
     */
    public static void auth() {
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result;

        try {
            result = microsoftAuthenticator.loginWithWebview();
            MainFrame.getSaver().set("refresh_token", result.getRefreshToken());
            authInfos = new AuthInfos(
                    result.getProfile().getName(),
                    result.getAccessToken(),
                    result.getProfile().getId()
            );
        } catch (MicrosoftAuthenticationException e) {
            getReporter().catchError(e, "Impossible de se connecter à Microsoft.");
        }
    }

    /**
     * Permet de mettre à jour Minecraft.
     */
    public static void update() {
        try {
            showLoadingScreen();
        } catch (IOException e) {
            erreurInterne(e);
        }
        VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                .withName(MINECRAFT_VERSION)
                .build();

        UpdaterOptions updaterOptions = new UpdaterOptions.UpdaterOptionsBuilder().build();

        ForgeVersion modLoaderVersion = new ForgeVersionBuilder()
                .withMods(MODPACKS_URL)
                .withFileDeleter(new ModFileDeleter(true))
                .withForgeVersion(MINECRAFT_VERSION + "-" + FORGE_VERSION)
                .build();

        LauncherLogger logger = new LauncherLogger(mainPanel);

        FlowUpdater flowUpdater = new FlowUpdater.FlowUpdaterBuilder()
                .withVanillaVersion(vanillaVersion)
                .withUpdaterOptions(updaterOptions)
                .withModLoaderVersion(modLoaderVersion)
                .withLogger(logger)
                .build();

        try {
            flowUpdater.update(path);
        } catch (Exception e) {
            try {
                FileUtils.cleanDirectory(path.toFile());
            } catch (IOException e1) {
                getReporter().catchError(e1, "Impossible de supprimer le dossier " + path + ".");
            }
            getReporter().catchError(e, "Impossible de mettre à jour Minecraft. Relancez le Launcher.");
        }
        closeLoadingScreen();
    }

    /**
     * Permet de lancer Minecraft et de se connecter automatiquement au serveur.
     */
    public static void launch() {
        NoFramework noFramework = new NoFramework(path, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalVmArgs().addAll(getRamVmArg());
        noFramework.getAdditionalArgs().addAll(List.of("--server", SERVER_NAME));
        noFramework.getAdditionalArgs().addAll(List.of("--port", SERVER_PORT));
        try {
            noFramework.launch(MINECRAFT_VERSION, FORGE_VERSION, NoFramework.ModLoader.FORGE);
        } catch (Exception e) {
            getReporter().catchError(e, "Impossible de lancer Minecraft.");
        }
    }

    /**
     * Permet de throw une erreur interne.
     * @param e L'exception à renvoyer.
     */
    public static void erreurInterne(Exception e) {
        getReporter().catchError(e, "Erreur interne du launcher.");
    }

    /**
     * Permet d'afficher le pop-up d'information avec le message correspondant.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public static void showLoadingScreen() throws IOException {
//        mainPanel.add(infosPanel = new InfosPanel(mainPanel, 600, 400, mainPanel.getWidth() / 2, mainPanel.getHeight() / 2, message), 0);
        mainPanel.setLoading(true);
        mainPanel.repaint();
    }

    /**
     * Permet de fermer le pop-up d'information.
     */
    private static void closeLoadingScreen() {
//        mainPanel.remove(infosPanel);
        mainPanel.setLoading(false);
        mainPanel.repaint();
    }

    public static void removeNewsAlert() {
        mainPanel.getNewsAlert().setVisible(false);
        MainFrame.getSaver().set("news", "false");
        mainPanel.repaint();
    }

    public static CrashReporter getReporter() {
        return reporter;
    }

    public static Path getPath() {
        return path;
    }

    public static void setLauncherPanel(MainPanel mainPanel) {
        Launcher.mainPanel = mainPanel;
    }
}
