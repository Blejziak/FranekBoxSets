package dev.franek.boxsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetGiveCommand implements CommandExecutor, TabCompleter {

    private final FranekBoxSets plugin;
    private final SetManager setManager;

    public SetGiveCommand(FranekBoxSets plugin, SetManager setManager) {
        this.plugin = plugin;
        this.setManager = setManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("franek.boxsets.give")) {
            sender.sendMessage(ChatColor.RED + "Nie masz uprawnien do tej komendy.");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.YELLOW + "Uzycie: /setgive <gracz> <poziom> [przedmiot|all] " + ChatColor.DARK_GRAY + "(FranekBoxSets by " + FranekBoxSets.AUTHOR + ")");
            return true;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Nie znaleziono gracza: " + args[0]);
            return true;
        }
        int level;
        try {
            level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Poziom musi byc liczba.");
            return true;
        }
        if (!this.setManager.levelExists(level)) {
            sender.sendMessage(ChatColor.RED + "Poziom " + level + " nie istnieje w konfiguracji.");
            return true;
        }
        String piece = args.length >= 3 ? args[2].toLowerCase() : "all";
        if (piece.equals("all")) {
            Map<String, ItemStack> fullSet = this.setManager.buildFullSet(level);
            for (ItemStack item : fullSet.values()) {
                Map<Integer, ItemStack> leftover = target.getInventory().addItem(item);
                if (!leftover.isEmpty()) {
                    leftover.values().forEach(i -> target.getWorld().dropItem(target.getLocation(), i));
                }
            }
            sender.sendMessage(ChatColor.GREEN + "Wydano graczowi " + target.getName() + " pelny set (poziom " + level + ").");
        } else {
            ItemStack item = this.setManager.buildPiece(level, piece);
            if (item == null) {
                sender.sendMessage(ChatColor.RED + "Nieznany przedmiot: " + piece + ". Dostepne: " + String.join(", ", SetManager.PIECES) + ", all");
                return true;
            }
            Map<Integer, ItemStack> leftover = target.getInventory().addItem(item);
            if (!leftover.isEmpty()) {
                leftover.values().forEach(i -> target.getWorld().dropItem(target.getLocation(), i));
            }
            sender.sendMessage(ChatColor.GREEN + "Wydano graczowi " + target.getName() + " przedmiot '" + piece + "' (poziom " + level + ").");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> options = new ArrayList<>();
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        if (args.length == 2) {
            for (int i = 1; i <= this.plugin.getThemeManager().getMaxLevel(); ++i) {
                options.add(String.valueOf(i));
            }
            return options;
        }
        if (args.length == 3) {
            options.add("all");
            options.addAll(List.of(SetManager.PIECES));
            return options;
        }
        return options;
    }
}
