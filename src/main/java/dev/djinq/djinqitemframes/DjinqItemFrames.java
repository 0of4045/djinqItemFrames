package dev.djinq.djinqitemframes;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class DjinqItemFrames extends JavaPlugin {
    private MessageManager messages;
    private int targetDistance;

    @Override
    public void onEnable() {
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
            YamlConfiguration config = new YamlConfiguration();
            config.load(new File(getDataFolder(), "config.yml"));
            int nextTargetDistance = Math.clamp(config.getInt("target-distance", 5), 1, 16);
            if (!messages.reload()) {
                return false;
            }

            targetDistance = nextTargetDistance;
            return true;
        } catch (IOException | InvalidConfigurationException | RuntimeException exception) {
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
