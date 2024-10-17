package fr.pixelmonworld;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
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

public class Launcher {

    private static final String MINECRAFT_VERSION = "1.16.5";
    private static final String FORGE_VERSION = "36.2.39";

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
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result = authenticator.loginWithWebview();
        authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId());
    }

    public static void update() throws Exception {
        VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder().withName(MINECRAFT_VERSION).build();
        UpdaterOptions updaterOptions = new UpdaterOptions.UpdaterOptionsBuilder().build();

        Collection<CurseFileInfo> curseFileInfos = new ArrayList<>();
        // Biomes O' Plenty 1.16.5-13.1.0.482
        curseFileInfos.add(new CurseFileInfo(220318, 3407189));
        // CustomNPCs-1.16.5.20220515
        curseFileInfos.add(new CurseFileInfo(221826, 3791072));
        // journeymap-1.16.5-5.8.5p6
        curseFileInfos.add(new CurseFileInfo(32274, 4012858));
        // NBTEdit-0.10.0
        curseFileInfos.add(new CurseFileInfo(247580, 3062106));
        // Pixelmon-1.16.5-9.1.3-universal
        curseFileInfos.add(new CurseFileInfo(389487, 4395992));

        AbstractForgeVersion modLoaderVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                .withCurseMods(curseFileInfos)
                .withForgeVersion(FORGE_VERSION)
                .build();

        FlowUpdater flowUpdater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanillaVersion)
                .withUpdaterOptions(updaterOptions)
                .withModLoaderVersion(modLoaderVersion)
                .build();

        flowUpdater.update(path);
    }

    public static void launch() throws Exception {
        NoFramework noFramework = new NoFramework(path, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.launch(MINECRAFT_VERSION, FORGE_VERSION, NoFramework.ModLoader.FORGE);
    }

    public static CrashReporter getReporter() {
        return reporter;
    }

    public static File getCrashFile() {
        return crashFile;
    }
}
