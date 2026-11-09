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
        saveResourceIfAbsent("messages.yml");
        reloadSettings();

        ItemFrameCommand command = new ItemFrameCommand(this);
        PluginCommand itemFrame = Objects.requireNonNull(getCommand("itemframe"));
        itemFrame.setExecutor(command);

        getServer().getConsoleSender().sendMessage(TextRenderer.render(
                "<#B3E342>djinqItemFrames <#CACACA>made by <#B3E342>djinq "
                        + "<#CACACA>| <#B3E342>djinq.dev <#CACACA>| <#B3E342>Loaded"));
    }

    private void reloadSettings() {
        targetDistance = Math.clamp(getConfig().getInt("target-distance", 5), 1, 16);
        messages = new MessageManager(this);
    }

    public int targetDistance() {
        return targetDistance;
    }

    public MessageManager messages() {
        return messages;
    }

    private void saveResourceIfAbsent(String resourceName) {
        if (!getDataFolder().toPath().resolve(resourceName).toFile().isFile()) {
            saveResource(resourceName, false);
        }
    }
}
