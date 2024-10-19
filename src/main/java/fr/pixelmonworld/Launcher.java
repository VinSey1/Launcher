package fr.pixelmonworld;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Launcher {

    private static final String MINECRAFT_VERSION = "1.16.5";
    private static final String FORGE_VERSION = "36.2.39";
    public static final String MODPACKS_URL = "https://nacou.pixelmonworld.fr/modpack";

    private static GameInfos gameInfos = new GameInfos(
            "PixelmonWorld",
            new GameVersion(MINECRAFT_VERSION, GameType.V1_13_HIGHER_FORGE),
            new GameTweak[]{GameTweak.FORGE}
    );

    private static Path path = gameInfos.getGameDir();
    private static File crashFile = new File(String.valueOf(path), "crashed");

    private static CrashReporter reporter = new CrashReporter(String.valueOf(crashFile), path);
    private static AuthInfos authInfos;

    public static boolean defaultAuth() {
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result;

        String refresh_token = MainFrame.getSaver().get("refresh_token");
        if (refresh_token != null && !refresh_token.isEmpty()) {
            try {
                result = microsoftAuthenticator.loginWithRefreshToken(refresh_token);
                authInfos = new AuthInfos(
                        result.getProfile().getName(),
                        result.getAccessToken(),
                        result.getProfile().getId()
                );
            } catch (MicrosoftAuthenticationException e) {
                MainFrame.getSaver().set("refresh_token", null);
                getReporter().catchError(e, "Impossible de se connecter à Microsoft automatiquement. Relancez le launcher.");
            }
            return true;
        }
        return false;
    }

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

    public static void update() {
        VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                .withName(MINECRAFT_VERSION)
                .build();

        UpdaterOptions updaterOptions = new UpdaterOptions.UpdaterOptionsBuilder().build();

        AbstractForgeVersion modLoaderVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                .withMods(MODPACKS_URL)
                .withFileDeleter(new ModFileDeleter(true))
                .withForgeVersion(FORGE_VERSION)
                .build();

        FlowUpdater flowUpdater = new FlowUpdater.FlowUpdaterBuilder()
                .withVanillaVersion(vanillaVersion)
                .withUpdaterOptions(updaterOptions)
                .withModLoaderVersion(modLoaderVersion)
                .build();

        try {
            flowUpdater.update(path);
        } catch (Exception e) {
            try {
                Files.delete(path);
            } catch (IOException e1) {
                getReporter().catchError(e1, "Impossible de supprimer le dossier " + path + ".");
            }
            getReporter().catchError(e, "Impossible de mettre à jour Minecraft.");
        }
    }

    public static void launch() {
        NoFramework noFramework = new NoFramework(path, authInfos, GameFolder.FLOW_UPDATER);
//        noFramework.getAdditionalVmArgs().addAll(List.of(MainFrame.getInstance().getPanel().getRamSelector().getRamArguments()));
        try {
            noFramework.launch(MINECRAFT_VERSION, FORGE_VERSION, NoFramework.ModLoader.FORGE);
        } catch (Exception e) {
            getReporter().catchError(e, "Impossible de lancer Minecraft.");
        }
    }

    public static CrashReporter getReporter() {
        return reporter;
    }

    public static File getCrashFile() {
        return crashFile;
    }

    public static Path getPath() {
        return path;
    }

    public static void erreurInterne(Exception e) {
        getReporter().catchError(e, "Erreur interne du launcher.");
    }
}
