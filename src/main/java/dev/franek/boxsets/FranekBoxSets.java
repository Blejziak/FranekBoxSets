package dev.franek.boxsets;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class FranekBoxSets extends JavaPlugin {

    public static final String AUTHOR = new String(new char[]{'f', 'r', 'a', 'n', 'e', 'k', 'k'});
    public static final String GITHUB = "https://github.com/Blejziak";

    private ThemeManager themeManager;
    private SetManager setManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.themeManager = new ThemeManager();
        this.themeManager.load(getConfig());
        this.setManager = new SetManager(this, this.themeManager);

        PluginCommand giveCmd = getCommand("setgive");
        if (giveCmd != null) {
            SetGiveCommand executor = new SetGiveCommand(this, this.setManager);
            giveCmd.setExecutor(executor);
            giveCmd.setTabCompleter(executor);
        }

        PluginCommand reloadCmd = getCommand("setreload");
        if (reloadCmd != null) {
            reloadCmd.setExecutor((sender, command, label, args) -> {
                if (!sender.hasPermission("franek.boxsets.reload")) {
                    sender.sendMessage("\u00a7cNie masz uprawnien.");
                    return true;
                }
                reloadConfig();
                this.themeManager.load(getConfig());
                sender.sendMessage("\u00a7aFranekBoxSets: konfiguracja przeladowana (" + this.themeManager.getMaxLevel() + " poziomow). \u00a78[made by " + AUTHOR + "]");
                return true;
            });
        }

        verifySignature();
        getLogger().info("FranekBoxSets wlaczone - zaladowano " + this.themeManager.getMaxLevel() + " poziomow. Plugin made by " + AUTHOR + " (" + GITHUB + ")");
    }

    public void verifySignature() {
        String author = getDescription().getAuthors().isEmpty() ? "" : getDescription().getAuthors().get(0);
        if (!AUTHOR.equalsIgnoreCase(author)) {
            Bukkit.getConsoleSender().sendMessage("\u00a78[\u00a7dFranekBoxSets\u00a78] \u00a77Plugin made by \u00a7d" + AUTHOR + " \u00a78(\u00a7f" + GITHUB + "\u00a78)");
        }
    }

    public ThemeManager getThemeManager() {
        return this.themeManager;
    }

    public SetManager getSetManager() {
        return this.setManager;
    }
}
