package dev.djinq.djinqitemframes;

import java.util.List;
import java.util.Locale;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public final class ItemFrameCommand implements CommandExecutor, TabCompleter {
    private final DjinqItemFrames plugin;

    public ItemFrameCommand(DjinqItemFrames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("djinqitemframes.reload")) {
                plugin.messages().send(sender, "no-permission");
                return true;
            }

            plugin.messages().send(sender, plugin.reloadPlugin() ? "reload-success" : "reload-failed");
            return true;
        }
        if (args.length > 0) {
            plugin.messages().send(sender, "usage");
            return true;
        }
        if (!(sender instanceof Player player)) {
            plugin.messages().send(sender, "player-only");
            return true;
        }
        if (!player.hasPermission("djinqitemframes.use")) {
            plugin.messages().send(player, "no-permission");
            return true;
        }

        ItemFrame frame = targetFrame(player);
        if (frame == null) {
            plugin.messages().send(player, "target-frame");
            return true;
        }

        boolean visible = !frame.isVisible();
        frame.setVisible(visible);
        plugin.messages().send(player, visible ? "visible" : "invisible");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1 && sender.hasPermission("djinqitemframes.reload")
                && "reload".startsWith(args[0].toLowerCase(Locale.ROOT))) {
            return List.of("reload");
        }
        return List.of();
    }

    private ItemFrame targetFrame(Player player) {
        Location eye = player.getEyeLocation();
        RayTraceResult entityTrace = player.getWorld().rayTraceEntities(
                eye, eye.getDirection(), plugin.targetDistance(), 0.1,
                entity -> entity instanceof ItemFrame);
        if (entityTrace == null || !(entityTrace.getHitEntity() instanceof ItemFrame frame)) {
            return null;
        }

        RayTraceResult blockTrace = player.getWorld().rayTraceBlocks(
                eye, eye.getDirection(), plugin.targetDistance());
        if (blockTrace != null && blockTrace.getHitPosition().distanceSquared(eye.toVector())
                < entityTrace.getHitPosition().distanceSquared(eye.toVector())) {
            return null;
        }

        return frame;
    }
}
