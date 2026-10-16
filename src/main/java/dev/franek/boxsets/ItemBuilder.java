package dev.franek.boxsets;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemBuilder {

    public static final String NBT_KEY = "franek_set_level";
    public static final String AUTHOR_KEY = "franek_author";
    private static final String STAR_COLOR = "#FFD700";
    private static final String LEVEL_LABEL_COLOR = "#808080";

    public static String hex(String hexColor) {
        return ChatColor.of(hexColor).toString();
    }

    public static ItemStack build(FranekBoxSets plugin, Material material, LevelTheme theme, String pieceLabel, Map<Enchantment, Integer> enchants) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }
        String color = hex(theme.hex);
        String rawName = pieceLabel + " " + theme.theme;
        meta.setDisplayName(color + ChatColor.BOLD + rawName.toUpperCase() + " " + hex(STAR_COLOR) + "\u2605" + hex(LEVEL_LABEL_COLOR) + theme.level);

        List<String> lore = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> e : enchants.entrySet()) {
            EnchantStyle style = styleFor(e.getKey());
            lore.add(hex(style.color) + " [" + style.icon + "] " + capitalize(style.name) + " " + e.getValue());
        }
        meta.setLore(lore);

        for (Map.Entry<Enchantment, Integer> e : enchants.entrySet()) {
            meta.addEnchant(e.getKey(), e.getValue(), true);
        }

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DYE);
        meta.setUnbreakable(true);

        NamespacedKey key = new NamespacedKey(plugin, NBT_KEY);
        NamespacedKey authorKey = new NamespacedKey(plugin, AUTHOR_KEY);
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, theme.level);
        meta.getPersistentDataContainer().set(authorKey, PersistentDataType.STRING, FranekBoxSets.AUTHOR);

        item.setItemMeta(meta);
        return item;
    }

    private static String capitalize(String s) {
        if (s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    private static EnchantStyle styleFor(Enchantment e) {
        Map<String, EnchantStyle> styles = new LinkedHashMap<>();
        styles.put("protection", new EnchantStyle("\u1d0f\u1d04\u029c\u0280\u1d0f\u0274\u1d00", "\u2764", "#55FF55"));
        styles.put("unbreaking", new EnchantStyle("\u1d21\u028f\u1d1b\u0280\u1d22\u028f\u1d0d\u1d00\u029f\u1d0f\ua731\u1d04", "\ud83d\udd27", "#55FFFF"));
        styles.put("sharpness", new EnchantStyle("\u1d0f\ua731\u1d1b\u0280\u1d0f\ua731\u1d04", "\u2694", "#FF5555"));
        styles.put("efficiency", new EnchantStyle("\u1d21\u028f\u1d05\u1d00\u1d0a\u0274\u1d0f\ua731\u1d04", "\u26cf", "#FFFF55"));
        styles.put("fire_aspect", new EnchantStyle("\u1d0f\u0262\u026a\u1d07\u0274", "\ud83d\udd25", "#FFAA00"));
        String key = e.getKey().getKey();
        return styles.getOrDefault(key, new EnchantStyle(key.replace("_", " "), "\u2726", "#AAAAAA"));
    }

    public static String toRoman(int number) {
        int[] values = new int[]{10, 9, 5, 4, 1};
        String[] symbols = new String[]{"X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        int n = number;
        for (int i = 0; i < values.length; ++i) {
            while (n >= values[i]) {
                n -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }

    private static class EnchantStyle {
        final String name;
        final String icon;
        final String color;

        EnchantStyle(String name, String icon, String color) {
            this.name = name;
            this.icon = icon;
            this.color = color;
        }
    }
}
