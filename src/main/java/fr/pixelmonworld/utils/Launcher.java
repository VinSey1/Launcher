package fr.pixelmonworld.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
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
import fr.pixelmonworld.MainPanel;
import fr.pixelmonworld.crash.LauncherCrashReporter;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.launcher.LauncherPanel;
import fr.pixelmonworld.launcher.news_panel.NewsPanel;
import fr.pixelmonworld.launcher.top_panel.TopPanel;
import fr.pixelmonworld.prelauncher.PreLauncherPanel;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

/**
 * Coeur technique de l'application.
 */
public class Launcher {

    // Version du launcher
    private static final String LAUNCHER_VERSION = "0.0.1";
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
    public static File getCrashFile() {
        return crashFile;
    }

    // Fichier de crash de l'application
    private static File crashFile = new File(String.valueOf(path), "crashs");
    private static File optionsFile = new File(String.valueOf(path), "options.txt");
    // Objet permettant de log les erreurs
    private static CrashReporter reporter = new LauncherCrashReporter(String.valueOf(crashFile), crashFile.toPath());
    // Auth Microsoft
    private static AuthInfos authInfos;
    // Fenêtre principale de l'application permettant de définir les éléments graphiques
    private static MainFrame mainFrame;
    // Le prélauncher de l'application
    private static PreLauncherPanel preLauncherPanel;
    // Le panneau principal de l'application
    private static LauncherPanel launcherPanel;
    private static TopPanel topPanel;
    private static NewsPanel newsPanel;
    // La ram allouée à Minecraft via le RamPanel
    private static Integer ram;
    // Fichier contenant la liste des serveurs
    private static File serversFile =  new File(String.valueOf(path), "servers.dat");
    // Zip du texturepack du serveur
    private static File resourcepackFile =  new File(String.valueOf(path), File.separator + "resourcepacks" + File.separator + "PixelmonWorld.zip");
    // Logo du serveur
    private static BufferedImage connexionPanel;
    // Icône du serveur
    private static BufferedImage icon;
    // Renders du serveur
    private static ArrayList<BufferedImage> renders;

    public static void setMainPanel(MainPanel mainPanel) {
        Launcher.mainPanel = mainPanel;
    }

    private static MainPanel mainPanel;

    private static List<String> filesToKeep = Arrays.asList("user.stock", "options.txt.old", "servers.dat", "resourcepacks", "PixelmonWorld.zip", "crashs", ".PixelmonWorld");

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

    public static void init() {
        Launcher.getFilesFromSite();
    }

    /**
     * Permet de récupérer les fichiers associés au lancement du launcher depuis le site (logo, icon, renders, news).
     */
    private static void getFilesFromSite() {
        try {
            preLauncherPanel.updateText("Vérification de la version du launcher...");
            JsonObject launcherFromSite = null;
            try {
                launcherFromSite = SiteUtils.getFileFromSiteAsJsonObject("PixelmonWorld");
            } catch (Exception e) {
                erreurInterne(new Exception("Impossible de récupérer la version du launcher. Veuillez vérifier votre connexion internet."));
            }
            String versionFromSite = launcherFromSite.get("name").getAsString().split("-")[1].replace(".exe", "");
            if (!versionFromSite.equals(LAUNCHER_VERSION)) {
                clearDirectory();
                Desktop.getDesktop().browse(URI.create(launcherFromSite.get("url").getAsString()));
                erreurInterne(new Exception("La version du launcher n'est pas à jour (" + LAUNCHER_VERSION + "). Veuillez installer la version " + versionFromSite + "."));
            }
            preLauncherPanel.updateText("Récupération du logo...");
            connexionPanel = SiteUtils.getAssetFromSite("connexion_panel");
            if (connexionPanel == null) {
                erreurInterne(new Exception("Impossible de récupérer le logo du serveur."));
            }
            preLauncherPanel.updateText("Récupération de l'icône...");
            icon = SiteUtils.getAssetFromSite("icon");
            if (icon == null) {
                erreurInterne(new Exception("Impossible de récupérer l'icône du serveur."));
            }
            mainFrame.setIconImage(Launcher.getIcon());
            preLauncherPanel.updateText("Récupération des renders...");
            renders = SiteUtils.getRendersFromSite();
            if (renders.isEmpty()) {
                erreurInterne(new Exception("Impossible de récupérer les renders du serveur."));
            }
            preLauncherPanel.updateText("Vérification des news...");
            File newsSaved = new File(String.valueOf(path), "news");
            Collection<News> newsFromSite = SiteUtils.getNewsFromSite();
            if (!newsSaved.exists()) {
                preLauncherPanel.updateText("Récupération des news...");
                FileOutputStream newsFile = new FileOutputStream(newsSaved);
                ObjectOutputStream oos = new ObjectOutputStream(newsFile);
                oos.writeObject(newsFromSite);
                oos.close();
                MainFrame.getSaver().set("news", "true");
            } else {
                FileInputStream newsFile = new FileInputStream(newsSaved);
                ObjectInputStream ois = new ObjectInputStream(newsFile);
                Collection<News> newsFromAssets = (Collection<News>) ois.readObject();
                ois.close();
                if (!newsFromAssets.equals(newsFromSite)) {
                    FileOutputStream newsFileUpdated = new FileOutputStream(newsSaved);
                    ObjectOutputStream oos = new ObjectOutputStream(newsFileUpdated);
                    oos.writeObject(newsFromSite);
                    oos.close();
                    MainFrame.getSaver().set("news", "true");
                } else if (MainFrame.getSaver().get("news") == null || !MainFrame.getSaver().get("news").equals("true")) {
                    MainFrame.getSaver().set("news", "false");
                }
            }
            preLauncherPanel.updateText("Lancement du launcher...");
            mainPanel.initLauncher();
            preLauncherPanel.setVisible(false);
        } catch (IOException | ClassNotFoundException e) {
            erreurInterne(e);
        }
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

            launcherPanel.updateLog("Récupération de la liste des serveurs...", 0);
            JsonObject serversFileFromSite = SiteUtils.getFileFromSiteAsJsonObject("servers.dat");
            if (!serversFile.exists() || !LauncherFileUtils.areFilesIdentical(serversFile, serversFileFromSite.get("sha1").getAsString())) {
                launcherPanel.updateLog("Téléchargement de la liste des serveurs...", 25);
                FileUtils.copyURLToFile(new URI(serversFileFromSite.get("url").getAsString()).toURL(), serversFile);
            }
            launcherPanel.updateLog("Récupération du pack de textures...", 50);
            JsonObject resourcepackFileFromSite = SiteUtils.getFileFromSiteAsJsonObject("resourcepack.zip");
            if (!resourcepackFile.exists() || !LauncherFileUtils.areFilesIdentical(resourcepackFile, resourcepackFileFromSite.get("sha1").getAsString())) {
                resourcepackFile.mkdirs();
                launcherPanel.updateLog("Téléchargement du pack de textures...", 75);
                FileUtils.copyURLToFile(new URI(resourcepackFileFromSite.get("url").getAsString()).toURL(), resourcepackFile);
            }
//            throw new IOException("");
            optionsModifier();
        } catch (Exception e) {
            clearDirectory();
            getReporter().catchError(e, "Impossible de mettre à jour Minecraft. Relancez le Launcher.");
        }
        launcherPanel.setLoading(false);
    }

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
        } catch (IOException e1) {
            getReporter().catchError(e1, "Impossible de supprimer le dossier " + path + ".");
        }
    }

    private static void optionsModifier() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> defaultOptions = mapper.readValue(
            ResourcesUtils.getResource("other/default_options.json"),
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
     * Permet de récupérer la ram actuelle. Si jamais il n'y en a pas de définie, la met à 2 et la sauvegarde.
     * @return La ram actuelle allouée à Minecraft.
     */
    public static int getRam() {
        if (ram == null) {
            String savedRam = MainFrame.getSaver().get("ram_panel");
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
            MainFrame.getSaver().set("ram_panel", String.valueOf(ram));
        }
    }

    /**
     * Permet d'enlever de la ram jusqu'à 2 et la sauvegarder.
     */
    public static void removeRam() {
        if (ram > 2) {
            ram--;
            MainFrame.getSaver().set("ram_panel", String.valueOf(ram));
        }
    }

    public static CrashReporter getReporter() {
        return reporter;
    }

    public static Path getPath() {
        return path;
    }

    public static void setLauncherPanel(LauncherPanel launcherPanel) {
        Launcher.launcherPanel = launcherPanel;
    }

    public static void setTopPanel(TopPanel topPanel) {
        Launcher.topPanel = topPanel;
    }

    public static void setPreLauncherPanel(PreLauncherPanel preLauncherPanel) {
        Launcher.preLauncherPanel = preLauncherPanel;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        Launcher.mainFrame = mainFrame;
    }

    public static BufferedImage getConnexionPanel() {
        return connexionPanel;
    }

    public static BufferedImage getIcon() {
        return icon;
    }

    public static ArrayList<BufferedImage> getRenders() {
        return renders;
    }

    public static void showNews() {
        newsPanel.setVisible(!newsPanel.isVisible());
    }

    public static void setNewsPanel(NewsPanel newsPanel) {
        Launcher.newsPanel = newsPanel;
    }
}
