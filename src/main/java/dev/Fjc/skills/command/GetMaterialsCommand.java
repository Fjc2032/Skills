package dev.Fjc.skills.command;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Debug command
 */
public class GetMaterialsCommand implements CommandExecutor {

    YMLDataStorage storage;

    public GetMaterialsCommand(Skills plugin) {
        this.storage = plugin.getStorage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You are lacking proper permissions to use this.");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(storage.getMaterialScores().toString());
            return true;
        }
        return false;
    }
}
