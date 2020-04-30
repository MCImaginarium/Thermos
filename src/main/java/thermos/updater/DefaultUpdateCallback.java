package thermos.updater;

import thermos.Thermos;
import thermos.ThermosCommand;
import thermos.updater.TVersionRetriever.IVersionCheckCallback;
import net.minecraft.server.MinecraftServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class DefaultUpdateCallback implements IVersionCheckCallback {
    public static DefaultUpdateCallback INSTANCE;

    static {
        INSTANCE = new DefaultUpdateCallback();
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (hasPermission(player)) {
            if (mHasUpdate) {
                sendUpdate(player);
            }
        }
    }

    private boolean hasPermission(CommandSender player) {
        return player.hasPermission(ThermosCommand.UPDATE) || player.isOp();
    }

    private void sendUpdate(CommandSender player) {
        CommandSenderUpdateCallback.newVersion(player, mCurrentVersion, mNewVersion);
    }

    private boolean mHasUpdate;
    private String mCurrentVersion;
	private String mCurrentRevision;
	private String mNewVersion;

    private DefaultUpdateCallback() {
    }

    @Override
    public void upToDate() {
        mHasUpdate = false;
        mCurrentVersion = Thermos.getCurrentVersion();
        mNewVersion = null;
    }

    @Override
    public void newVersion(String newVersion) {
        mCurrentVersion = Thermos.getCurrentVersion();
		mCurrentRevision = Thermos.getCurrentRevision();
        mNewVersion = newVersion;
		
        if (!mHasUpdate) {
			Bukkit.getConsoleSender().sendMessage("==== Current Server Information ====");
			Bukkit.getConsoleSender().sendMessage("= Version: " + mCurrentVersion);
			Bukkit.getConsoleSender().sendMessage("= Build: " + mCurrentRevision);
			Bukkit.getConsoleSender().sendMessage("=====================================");
            Bukkit.getConsoleSender().sendMessage("A new build " + newVersion + " is available: ");
            Bukkit.getConsoleSender().sendMessage("Download at: https://github.com/MCImaginarium/Thermos/releases/tag/" + newVersion + "/");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (hasPermission(player)) {
                    sendUpdate(player);
                }
            }
        }
        mHasUpdate = true;
    }

    @Override
    public void error(Throwable t) {

    }
}
