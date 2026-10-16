package dev.franek.boxsets;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SetManager {

    private final FranekBoxSets plugin;
    private final ThemeManager themeManager;
    public static final String[] PIECES = new String[]{"helmet", "chestplate", "leggings", "boots", "sword", "pickaxe", "shears", "elytra"};

    public SetManager(FranekBoxSets plugin, ThemeManager themeManager) {
        this.plugin = plugin;
        this.themeManager = themeManager;
    }

    public boolean levelExists(int level) {
        return this.themeManager.getLevel(level) != null;
    }

    public ItemStack buildPiece(int level, String piece) {
        Material material;
        LevelTheme theme = this.themeManager.getLevel(level);
        if (theme == null) {
            return null;
        }
        piece = piece.toLowerCase();
        String label = this.themeManager.getPieceName(piece);
        int enchantLvl = this.themeManager.scaledEnchant(level);
        Map<Enchantment, Integer> enchants = new LinkedHashMap<>();

        switch (piece) {
            case "helmet" -> {
                material = theme.armorMaterial("HELMET");
                enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, enchantLvl);
                enchants.put(Enchantment.DURABILITY, enchantLvl);
            }
            case "chestplate" -> {
                material = theme.armorMaterial("CHESTPLATE");
                enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, enchantLvl);
                enchants.put(Enchantment.DURABILITY, enchantLvl);
            }
            case "leggings" -> {
                material = theme.armorMaterial("LEGGINGS");
                enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, enchantLvl);
                enchants.put(Enchantment.DURABILITY, enchantLvl);
            }
            case "boots" -> {
                material = theme.armorMaterial("BOOTS");
                enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, enchantLvl);
                enchants.put(Enchantment.DURABILITY, enchantLvl);
            }
            case "sword" -> {
                material = theme.toolMaterial("SWORD");
                enchants.put(Enchantment.DAMAGE_ALL, enchantLvl);
                enchants.put(Enchantment.DURABILITY, enchantLvl);
                Integer fireAspect = this.themeManager.scaledFireAspect(level);
                if (fireAspect != null) {
                    enchants.put(Enchantment.FIRE_ASPECT, fireAspect);
                }
            }
            case "pickaxe" -> {
                material = theme.toolMaterial("PICKAXE");
                enchants.put(Enchantment.DIG_SPEED, enchantLvl);
                enchants.put(Enchantment.DURABILITY, enchantLvl);
            }
            case "shears" -> {
                material = Material.SHEARS;
                enchants.put(Enchantment.DIG_SPEED, enchantLvl);
                enchants.put(Enchantment.DURABILITY, enchantLvl);
            }
            case "elytra" -> {
                material = Material.ELYTRA;
                enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, enchantLvl);
                enchants.put(Enchantment.DURABILITY, enchantLvl);
            }
            default -> {
                return null;
            }
        }
        return ItemBuilder.build(this.plugin, material, theme, label, enchants);
    }

    public Map<String, ItemStack> buildFullSet(int level) {
        Map<String, ItemStack> set = new LinkedHashMap<>();
        for (String piece : PIECES) {
            ItemStack item = buildPiece(level, piece);
            if (item != null) {
                set.put(piece, item);
            }
        }
        return set;
    }
}
