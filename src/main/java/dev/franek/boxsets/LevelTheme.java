package dev.franek.boxsets;

import org.bukkit.Material;

public class LevelTheme {

    public final int level;
    public final String theme;
    public final String hex;
    public final Tier tier;

    public LevelTheme(int level, String theme, String hex, Tier tier) {
        this.level = level;
        this.theme = theme;
        this.hex = hex;
        this.tier = tier;
    }

    public Material armorMaterial(String piece) {
        String prefix = switch (this.tier) {
            case LEATHER -> "LEATHER";
            case IRON -> "IRON";
            case DIAMOND -> "DIAMOND";
            case NETHERITE -> "NETHERITE";
        };
        return Material.valueOf(prefix + "_" + piece.toUpperCase());
    }

    public Material toolMaterial(String tool) {
        String prefix = switch (this.tier) {
            case LEATHER -> "STONE";
            case IRON -> "IRON";
            case DIAMOND -> "DIAMOND";
            case NETHERITE -> "NETHERITE";
        };
        return Material.valueOf(prefix + "_" + tool.toUpperCase());
    }

    public enum Tier {
        LEATHER,
        IRON,
        DIAMOND,
        NETHERITE;

        public static Tier fromString(String s) {
            try {
                return Tier.valueOf(s.trim().toUpperCase());
            } catch (Exception e) {
                return IRON;
            }
        }
    }
}
