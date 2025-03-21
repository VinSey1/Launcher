package fr.pixelmonworld;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.prelauncher.PreLauncherPanel;
import fr.pixelmonworld.utils.LauncherCrashReporter;
import fr.pixelmonworld.utils.SiteUtils;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.explorer.FileList;
import org.pushingpixels.radiance.animation.api.Timeline;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class Prelauncher extends JFrame {

    // Version de Minecraft
    private static final String MINECRAFT_VERSION = "1.16.5";

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

    private static File launcherFile = new File(String.valueOf(path), "launchers");

    // Fichier d'options de Minecraft
    private static File optionsFile = new File(String.valueOf(path), "options.txt");

    // Objet permettant de log les erreurs
    private static CrashReporter reporter = new LauncherCrashReporter(String.valueOf(crashFile), crashFile.toPath());

    // Instance de l'application (singleton)
    private static Prelauncher instance;

    // Fichier de sauvegarde des options (ram + token d'authentification Microsoft)
    private static File saverFile = new File(String.valueOf(path), "user.stock");

    // Objet permettant de sauvegarder les options dans le fichier comme les news ou le token d'authentification Microsoft
    private static Saver saver = new Saver(saverFile);

    private static PreLauncherPanel preLauncherPanel;

    /**
     * Constructeur par défaut.
     */
    public Prelauncher() {
        instance = this;

        // Définit les éléments basique de l'application (taille, titre, icône...)
        this.setTitle("Launcher PixelmonWorld");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setUndecorated(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setLocationRelativeTo(null);

        // Permet d'ajouter le panneau principal à l'application
        this.setContentPane(preLauncherPanel = new PreLauncherPanel(this, 770, 420));

        // Permet de définir l'opacité de l'application
        this.setOpacity(0);

        // Affiche l'application
        this.setVisible(true);

        // Permet de faire apparaître l'application avec un fadeIn
        Timeline.builder(this)
                .addPropertyToInterpolate("opacity", 0.0f, 1.0f)
                .setDuration(70)
                .play();
    }

    public static void doUpdate() throws Exception {
        FlowUpdater flowUpdater = new FlowUpdater.FlowUpdaterBuilder().withExternalFiles(ExternalFile.getExternalFilesFromJson("")).build();
        flowUpdater.update(Paths.get(path + "/launchers"));
    }
n b
    public static void launch() throws LaunchException {
        ClasspathConstructor classpathConstructor = new ClasspathConstructor();

        FileList fileList = new FileList();
        fileList.add(new File(launcherFile, "PixelmonWorld.jar").toPath());
        classpathConstructor.add(fileList);

        ExternalLaunchProfile profile = new ExternalLaunchProfile("fr.pixelmonworld.launcher.LauncherFrame", classpathConstructor.make());
        ExternalLauncher launcher = new ExternalLauncher(profile);

        launcher.launch();
    }

    /**
     * Permet de récupérer l'instance de l'application.
     * @return L'instance de l'application.
     */
    public static Prelauncher getInstance() {
        return instance;
    }

    /**
     * Permet de récupérer l'objet de sauvegarde des options.
     * @return L'objet de sauvegarde des options.
     */
    public static Saver getSaver() {
        return saver;
    }

    public static void main(String[] args) throws Exception {
        try {

            launcherFile.mkdirs();
            // Permet de créer le fichier de sauvegarde s'il n'existe pas
            if (!saverFile.exists()) {
                saverFile.createNewFile();
            }

            // Permet de créer le dossier %APPDATA%/.PixelmonWorld/ s'il n'existe pas
            crashFile.mkdirs();

            instance = new Prelauncher();

            init();
            doUpdate();
            launch();
        } catch (Exception e) {
            erreurInterne(e);
        }

    }

    public static void init() {
        try {
            preLauncherPanel.updateText("Vérification de la version du launcher...");
//            JsonObject launcherFromSite = null;
//            try {
//                launcherFromSite = SiteUtils.getFileFromSiteAsJsonObject("PixelmonWorld");
//            } catch (Exception e) {
//                erreurInterne(new Exception("Impossible de récupérer la version du launcher. Veuillez vérifier votre connexion internet."));
//            }
//            String versionFromSite = launcherFromSite.get("name").getAsString().split("-")[1].replace(".exe", "");
//            if (!versionFromSite.equals(LAUNCHER_VERSION)) {
//                clearDirectory();
//                Desktop.getDesktop().browse(URI.create(launcherFromSite.get("url").getAsString()));
//                erreurInterne(new Exception("La version du launcher n'est pas à jour (" + LAUNCHER_VERSION + "). Veuillez installer la version " + versionFromSite + "."));
//            }
//            preLauncherPanel.updateText("Récupération du logo...");
//            connexionPanel = SiteUtils.getAssetFromSite("connexion_panel");
//            if (connexionPanel == null) {
//                erreurInterne(new Exception("Impossible de récupérer le logo du serveur."));
//            }
//            preLauncherPanel.updateText("Récupération de l'icône...");
//            icon = SiteUtils.getAssetFromSite("icon");
//            if (icon == null) {
//                erreurInterne(new Exception("Impossible de récupérer l'icône du serveur."));
//            }
//            this.setIconImage(icon);
//            preLauncherPanel.updateText("Récupération des renders...");
//            renders = SiteUtils.getRendersFromSite();
//            if (renders.isEmpty()) {
//                erreurInterne(new Exception("Impossible de récupérer les renders du serveur."));
//            }
            preLauncherPanel.updateText("Vérification des news...");
            File newsSaved = new File(String.valueOf(path), "news");
            Collection<News> newsFromSite = SiteUtils.getNewsFromSite();
            if (!newsSaved.exists()) {
                preLauncherPanel.updateText("Récupération des news...");
                FileOutputStream newsFile = new FileOutputStream(newsSaved);
                ObjectOutputStream oos = new ObjectOutputStream(newsFile);
                oos.writeObject(newsFromSite);
                oos.close();
                saver.set("news", "true");
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
                    saver.set("news", "true");
                } else if (saver.get("news") == null || !saver.get("news").equals("true")) {
                    saver.set("news", "false");
                }
            }
            preLauncherPanel.updateText("Lancement du launcher...");
            preLauncherPanel.setVisible(false);
        } catch (IOException | ClassNotFoundException e) {
            erreurInterne(e);
        }
    }

    /**
     * Permet de throw une erreur interne.
     * @param e L'exception à renvoyer.
     */
    public static void erreurInterne(Exception e) {
        reporter.catchError(e, "Erreur interne du launcher.");
    }
}