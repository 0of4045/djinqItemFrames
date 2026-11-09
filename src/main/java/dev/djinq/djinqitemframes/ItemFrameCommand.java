package dev.djinq.djinqitemframes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Location;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public final class ItemFrameCommand implements CommandExecutor {
    private final DjinqItemFrames plugin;

    public ItemFrameCommand(DjinqItemFrames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
