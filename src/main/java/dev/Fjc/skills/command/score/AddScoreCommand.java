package dev.Fjc.skills.command.score;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.command.Data;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddScoreCommand extends YMLDataStorage implements CommandExecutor {

    private final YMLDataStorage storage;

    public AddScoreCommand(Skills plugin) {
        super(plugin);
        this.storage = plugin.getStorage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage(Data.noPermission);
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return false;
        }

        if (args.length == 1) {
            sender.sendMessage("You are missing one or more arguments.");
            return false;
        }
        if (args.length == 2) {
            SkillSet skill = SkillSet.parseSkill(args[0]);
            double value = Double.parseDouble(args[1]);

            if (skill != null && !Double.isNaN(value)) {
                storage.incrementSkillScore(player, skill, value);
                player.sendMessage("Successfully increased the score of your skill " + skill + " by " + value);
                return true;
            } else {
                player.sendMessage("One of the parameters provided is not valid. Make sure you typed everything correctly.");
                return false;
            }
        }

        if (args.length == 3) {
            Player target = Bukkit.getPlayer(args[0]);
            SkillSet skill = SkillSet.parseSkill(args[1]);
            double value = Double.parseDouble(args[2]);

            if (target != null && skill != null && !Double.isNaN(value)) {
                storage.incrementSkillScore(target, skill, value);
                player.sendMessage("Successfully increased the score of " + skill + " on player " + target + " by " + value);
                return true;
            } else {
                player.sendMessage("One of the parameters provided is not valid. Make sure you typed everything correctly.");
                return false;
            }
        }
        return false;
    }
}
