package dev.Fjc.skills.command;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Debug command
 */
public class GetMaterialsCommand implements CommandExecutor {

    private final YMLDataStorage storage;

    public GetMaterialsCommand(Skills plugin) {
        this.storage = plugin.getStorage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage(Data.noPermission);
            return false;
        }

        if (args.length == 0) {
            Map<Material, Double> map = storage.getMaterialScores().isEmpty() || storage.getMaterialScores().containsKey(null) || storage.getMaterialScores().containsValue(null)
                    ? storage.blockMap()
                    : storage.getMaterialScores();
            sender.sendMessage(map.toString());
            return true;
        }
        return false;
    }
}
