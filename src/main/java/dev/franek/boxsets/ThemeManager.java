package dev.franek.boxsets;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ThemeManager {

    private final Map<Integer, LevelTheme> levels = new LinkedHashMap<>();
    private final Map<String, String> pieceNames = new LinkedHashMap<>();
    private int enchantMin = 1;
    private int enchantMax = 10;
    private int maxLevel = 14;
    private int fireAspectFromLevel = 10;
    private int fireAspectMin = 1;
    private int fireAspectMax = 2;

    public void load(FileConfiguration config) {
        this.levels.clear();
        this.pieceNames.clear();
        this.maxLevel = config.getInt("max-level", 14);
        this.enchantMin = config.getInt("enchants.enchant-min", 1);
        this.enchantMax = config.getInt("enchants.enchant-max", 10);
        this.fireAspectFromLevel = config.getInt("sword.fire-aspect-from-level", 10);
        this.fireAspectMin = config.getInt("sword.fire-aspect-min", 1);
        this.fireAspectMax = config.getInt("sword.fire-aspect-max", 2);

        ConfigurationSection piecesSection = config.getConfigurationSection("pieces");
        if (piecesSection != null) {
            for (String key : piecesSection.getKeys(false)) {
                this.pieceNames.put(key.toLowerCase(), piecesSection.getString(key, key));
            }
        }

        ConfigurationSection levelsSection = config.getConfigurationSection("levels");
        if (levelsSection != null) {
            for (String key : levelsSection.getKeys(false)) {
                int lvl;
                try {
                    lvl = Integer.parseInt(key);
                } catch (NumberFormatException e) {
                    continue;
                }
                ConfigurationSection lvlSection = levelsSection.getConfigurationSection(key);
                if (lvlSection == null) {
                    continue;
                }
                String theme = lvlSection.getString("theme", "Poziom " + lvl);
                String hex = lvlSection.getString("hex", "#FFFFFF");
                LevelTheme.Tier tier = LevelTheme.Tier.fromString(lvlSection.getString("material-tier", "IRON"));
                this.levels.put(lvl, new LevelTheme(lvl, theme, hex, tier));
            }
        }
    }

    public LevelTheme getLevel(int level) {
        return this.levels.get(level);
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public int getEnchantMin() {
        return this.enchantMin;
    }

    public int getEnchantMax() {
        return this.enchantMax;
    }

    public String getPieceName(String piece) {
        return this.pieceNames.getOrDefault(piece.toLowerCase(), piece);
    }

    public Map<String, String> getPieceNames() {
        return this.pieceNames;
    }

    public int scaledEnchant(int level) {
        if (this.maxLevel <= 1) {
            return this.enchantMax;
        }
        double ratio = (double) (level - 1) / (double) (this.maxLevel - 1);
        int value = (int) Math.round((double) this.enchantMin + ratio * (double) (this.enchantMax - this.enchantMin));
        return Math.max(this.enchantMin, Math.min(this.enchantMax, value));
    }

    public int getFireAspectFromLevel() {
        return this.fireAspectFromLevel;
    }

    public Integer scaledFireAspect(int level) {
        if (level < this.fireAspectFromLevel) {
            return null;
        }
        if (this.maxLevel <= this.fireAspectFromLevel) {
            return this.fireAspectMax;
        }
        double ratio = (double) (level - this.fireAspectFromLevel) / (double) (this.maxLevel - this.fireAspectFromLevel);
        int value = (int) Math.round((double) this.fireAspectMin + ratio * (double) (this.fireAspectMax - this.fireAspectMin));
        return Math.max(this.fireAspectMin, Math.min(this.fireAspectMax, value));
    }
}
