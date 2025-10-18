package dev.Fjc.skills.command;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {

    YMLDataStorage storage;

    public Reload(Skills plugin) {
        this.storage = plugin.getStorage();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You are lacking permissions for this.");
            return false;
        }

        if (args.length == 0) {
            storage.reload();
            sender.sendMessage("Success.");
            return true;
        }

        return false;
    }
}
