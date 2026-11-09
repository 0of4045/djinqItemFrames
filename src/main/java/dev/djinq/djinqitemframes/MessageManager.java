package dev.djinq.djinqitemframes;

import java.io.File;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public final class MessageManager {
    private final YamlConfiguration messages;
    private final String prefix;

    public MessageManager(DjinqItemFrames plugin) {
        messages = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
        prefix = messages.getString("prefix", "[Frames] ");
    }

    public void send(CommandSender sender, String key) {
        String message = messages.getString("messages." + key, "<#E34242>Missing message.");
        sender.sendMessage(TextRenderer.render(prefix + message));
    }
}
