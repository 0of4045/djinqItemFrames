package dev.djinq.djinqitemframes;

import java.util.Objects;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DjinqItemFrames extends JavaPlugin {
    private MessageManager messages;
    private int targetDistance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ResourceMigration.mergeMissingDefaults(this, "config.yml");
        ResourceMigration.mergeMissingDefaults(this, "messages.yml");
        messages = new MessageManager(this);
        if (!reloadPlugin()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        ItemFrameCommand command = new ItemFrameCommand(this);
        PluginCommand itemFrame = Objects.requireNonNull(getCommand("itemframe"));
        itemFrame.setExecutor(command);
        itemFrame.setTabCompleter(command);

        getServer().getConsoleSender().sendMessage(TextRenderer.render(
                "<#B3E342>djinqItemFrames <#CACACA>made by <#B3E342>djinq "
                        + "<#CACACA>| <#B3E342>djinq.dev <#CACACA>| <#B3E342>Loaded"));
    }

    public boolean reloadPlugin() {
        try {
            reloadConfig();
            int nextTargetDistance = Math.clamp(getConfig().getInt("target-distance", 5), 1, 16);
            if (!messages.reload()) {
                return false;
            }

            targetDistance = nextTargetDistance;
            return true;
        } catch (RuntimeException exception) {
            getLogger().severe("Could not reload djinqItemFrames configuration: " + exception.getMessage());
            return false;
        }
    }

    public int targetDistance() {
        return targetDistance;
    }

    public MessageManager messages() {
        return messages;
    }
}
