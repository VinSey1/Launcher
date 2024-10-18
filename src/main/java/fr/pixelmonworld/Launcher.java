package fr.pixelmonworld;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.flowarg.flowupdater.download.json.MCP;
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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public static void auth() throws MicrosoftAuthenticationException {
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result;

        String refresh_token = Frame.getSaver().get("refresh_token");
        if (refresh_token != null && !refresh_token.isEmpty()) {
            result = microsoftAuthenticator.loginWithRefreshToken(refresh_token);
        } else {
            result = microsoftAuthenticator.loginWithWebview();
            Frame.getSaver().set("refresh_token", result.getRefreshToken());
        }
        authInfos = new AuthInfos(
                result.getProfile().getName(),
                result.getAccessToken(),
                result.getProfile().getId()
        );
    }

    public static void update() throws Exception {
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

        flowUpdater.update(path);
    }

    public static void launch() throws Exception {
        NoFramework noFramework = new NoFramework(path, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalVmArgs().addAll(List.of(Frame.getInstance().getPanel().getRamSelector().getRamArguments()));
        noFramework.launch(MINECRAFT_VERSION, FORGE_VERSION, NoFramework.ModLoader.FORGE);
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
}
