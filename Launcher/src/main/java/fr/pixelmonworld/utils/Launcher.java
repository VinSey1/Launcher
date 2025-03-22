package fr.pixelmonworld.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import fr.pixelmonworld.MainFrame;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.launcher.LauncherPanel;
import fr.pixelmonworld.launcher.news_panel.NewsPanel;
import fr.pixelmonworld.launcher.top_panel.TopPanel;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.*;

/**
 * Coeur technique de l'application.
 */
public class Launcher {

    // Version de Minecraft
    private static final String MINECRAFT_VERSION = "1.16.5";

    // Version de Forge
    private static final String FORGE_VERSION = "36.2.39";

    // Path à partir duquel récupérer la bonne version des mods
    public static final String MODPACKS_URL = "https://nacou.pixelmonworld.fr/launcher/modpack";

    // IP du serveur
    private static final String SERVER_NAME = "play.pixelmonworld.fr";

    // Port du serveur
    private static final String SERVER_PORT = "25564";

    // ID de l'application Discord
    public static final String DISCORD_APPLICATION_ID = "1297976065121325076";

    // Informations globales sur Minecraft
    private static GameInfos gameInfos = new GameInfos(
            "PixelmonWorld",
            new GameVersion(MINECRAFT_VERSION, GameType.V1_13_HIGHER_FORGE),
            new GameTweak[]{GameTweak.FORGE}
    );

    // Path de l'application (%APPDATA%/.PixelmonWorld/)
    private static Path path = gameInfos.getGameDir();

    // Dossier de crash de l'application
    private static File crashFile = new File(String.valueOf(path), "crashs");

    // Fichier d'options de Minecraft
    private static File optionsFile = new File(String.valueOf(path), "options.txt");

    // Dossier des assets de l'application
    private static File assetsDir = new File(String.valueOf(path), "assets/");

    private static File rendersDir = new File(String.valueOf(assetsDir), "renders/");

    // Objet permettant de log les erreurs
    private static CrashReporter reporter = new LauncherCrashReporter(String.valueOf(crashFile), crashFile.toPath());

    // Auth Microsoft
    private static AuthInfos authInfos;

    // Le launcher de l'application
    private static LauncherPanel launcherPanel;

    // La barre du haut de l'application
    private static TopPanel topPanel;

    // Le panneau des actualités de l'application
    private static NewsPanel newsPanel;

    // La ram allouée à Minecraft via le RamPanel
    private static Integer ram;

    // Background du panneau de connexion
    private static BufferedImage connexionPanel;

    // Icône du serveur
    private static BufferedImage icon;

    // Renders du serveur
    private static ArrayList<BufferedImage> renders = new ArrayList<>();;

    // Fichiers à garder lors de la suppression de l'ensemble des données du launcher
    private static List<String> filesToKeep = Arrays.asList("user.stock", "options.txt.old", "servers.dat", "resourcepacks", "PixelmonWorld.zip", "crashs", ".PixelmonWorld");

    private static Collection<News> news = new ArrayList<>();

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
     * Permet d'initialiser le launcher.
     */
    public static void init() {
        try {
            connexionPanel = ImageIO.read(new File(String.valueOf(assetsDir), "/logo.png"));
            if (connexionPanel == null) {
                erreurInterne(new Exception("Impossible de récupérer le logo du serveur."));
            }
            icon = ImageIO.read(new File(String.valueOf(assetsDir), "/icon.png"));
            if (icon == null) {
                erreurInterne(new Exception("Impossible de récupérer l'icône du serveur."));
            }
            for (File file : rendersDir.listFiles()) {
                renders.add(ImageIO.read(file));
            }
            if (renders.isEmpty()) {
                erreurInterne(new Exception("Impossible de récupérer les renders du serveur."));
            }
            FileInputStream newsFile = new FileInputStream(new File(String.valueOf(path), "news"));
            ObjectInputStream ois = new ObjectInputStream(newsFile);
            news = (Collection<News>) ois.readObject();
            ois.close();
            initDiscord();
        } catch (IOException | ClassNotFoundException e) {
            erreurInterne(e);
        }
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
     * Permet de se déconnecter de Microsoft et de supprimer le token associé.
     */
    public static void disconnect() {
        MainFrame.getSaver().set("refresh_token", "");
        authInfos = null;
    }

    /**
     * Permet de mettre à jour Minecraft et de récupérer les fichiers associées au lancement (serveurs.dat, resourcepack.zip).
     */
    public static void update() {
        launcherPanel.setLoading(true);
        VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                .withName(MINECRAFT_VERSION)
                .build();

        UpdaterOptions updaterOptions = new UpdaterOptions.UpdaterOptionsBuilder().build();

        ForgeVersion modLoaderVersion = new ForgeVersionBuilder()
                .withMods(MODPACKS_URL)
                .withFileDeleter(new ModFileDeleter(true))
                .withForgeVersion(MINECRAFT_VERSION + "-" + FORGE_VERSION)
                .build();

        LauncherLogger logger = new LauncherLogger(launcherPanel);

        FlowUpdater flowUpdater = new FlowUpdater.FlowUpdaterBuilder()
                .withVanillaVersion(vanillaVersion)
                .withUpdaterOptions(updaterOptions)
                .withModLoaderVersion(modLoaderVersion)
                .withLogger(logger)
                .build();

        try {
            flowUpdater.update(path);

            optionsModifier();
        } catch (Exception e) {
            clearDirectory();
            getReporter().catchError(e, "Impossible de mettre à jour Minecraft. Relancez le Launcher.");
        }
        launcherPanel.setLoading(false);
        MainFrame.getSaver().set("mods_installed", "true");
        launcherPanel.updateMaxLogs();
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
     * Permet de supprimer l'ensemble des données du launcher sauf certains fichiers spécifiques.
     */
    private static void clearDirectory() {
        try {
            if (optionsFile.exists()) {
                FileUtils.copyFile(optionsFile, new File(String.valueOf(path), "options.txt.old"));
            }
            for (File file : FileUtils.listFilesAndDirs(path.toFile(), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
                if (!filesToKeep.contains(file.getName())) {
                    FileUtils.delete(file);
                }
            }
            MainFrame.getSaver().set("mods_installed", "false");

        } catch (IOException e1) {
            getReporter().catchError(e1, "Impossible de supprimer le dossier " + path + ".");
        }
    }

    /**
     * Permet de modifier les options de Minecraft pour forcer certains paramètres.
     * @throws IOException Si jamais il y a une erreur lors de la lecture des options.
     */
    private static void optionsModifier() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> defaultOptions = mapper.readValue(
            ResourcesUtils.getResource("utils/default_options.json"),
            new TypeReference<>() {}
        );

        List<String> optionsModified = new ArrayList<>();

        File oldOptionsFile = new File(String.valueOf(path), "options.txt.old");
        File optionsFile = new File(String.valueOf(path), "options.txt");
        if (oldOptionsFile.exists()) {
            List<String> options = FileUtils.readLines(oldOptionsFile);
            options.forEach(o -> {
                String[] option = o.split(":", 2);
                if (!defaultOptions.get(option[0]).equals(option[1])) {
                    // Endroit pour FORCER les options des utilisateurs, même après leur save
                    if (option[0].equals("resourcePacks")) {
                        option[1] = "[\"vanilla\",\"mod_resources\",\"file/PixelmonWorld.zip\"]";
                    }
                    if (option[0].equals("lastServer")) {
                        option[1] = SERVER_NAME + ":" + SERVER_PORT;
                    }
                }
                optionsModified.add(String.join(":", option));
            });
        } else {
            defaultOptions.forEach((k, e) -> optionsModified.add(e != null ? k + ":" + e : k));
        }
        FileUtils.writeLines(optionsFile, optionsModified);
    }

    /**
     * Permet de lancer Minecraft et de se connecter automatiquement au serveur.
     */
    public static void launch() {
        NoFramework noFramework = new NoFramework(path, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalVmArgs().addAll(List.of("-Xmx" + ram * 1024 + "M", "-Xms" + ram * 1024 + "M"));
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
     * Permet de définir les news comme étant lues et de supprimer l'alerte graphique associée.
     */
    public static void removeNewsAlert() {
        topPanel.getNewsAlert().setVisible(false);
        MainFrame.getSaver().set("news", "clicked");
    }

    /**
     * Permet de montrer ou cacher les news.
     */
    public static void showNews() {
        newsPanel.setVisible(!newsPanel.isVisible());
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
     * Permet de définir le launcher de l'application.
     * @param launcherPanel
     */
    public static void setLauncherPanel(LauncherPanel launcherPanel) {
        Launcher.launcherPanel = launcherPanel;
    }

    /**
     * Permet de définir la barre du haut de l'application.
     * @param topPanel La barre du haut de l'application.
     */
    public static void setTopPanel(TopPanel topPanel) {
        Launcher.topPanel = topPanel;
    }

    /**
     * Permet de définir le panneau des actualités de l'application.
     * @param newsPanel Le panneau des actualités de l'application.
     */
    public static void setNewsPanel(NewsPanel newsPanel) {
        Launcher.newsPanel = newsPanel;
    }

    /**
     * Permet de récupérer le panneau de connexion.
     * @return Le panneau de connexion.
     */
    public static BufferedImage getConnexionPanel() {
        return connexionPanel;
    }

    /**
     * Permet de récupérer l'icône de l'application.
     * @return L'icône de l'application.
     */
    public static BufferedImage getIcon() {
        return icon;
    }

    /**
     * Permet de récupérer les renders du serveur.
     * @return Les renders du serveur.
     */
    public static ArrayList<BufferedImage> getRenders() {
        return renders;
    }

    /**
     * Permet de récupérer le fichier de crashs de l'application.
     * @return Le fichier de crashs de l'application.
     */
    public static File getCrashFile() {
        return crashFile;
    }

    /**
     * Permet de récupérer le reporter de l'application.
     * @return Le reporter de l'application.
     */
    public static CrashReporter getReporter() {
        return reporter;
    }

    /**
     * Permet de récupérer le path de l'application.
     * @return Le path de l'application.
     */
    public static Path getPath() {
        return path;
    }

    public static Collection<News> getNews() {
        return news;
    }
}
