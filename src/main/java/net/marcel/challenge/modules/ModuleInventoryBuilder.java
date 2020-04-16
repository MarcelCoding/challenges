package net.marcel.challenge.modules;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.marcel.challenge.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ModuleInventoryBuilder {

    private static final ItemStack DARK_PLACEHOLDER_ITEM;
    private static final ItemStack LIGHT_PLACEHOLDER_ITEM;

    private static final ItemStack ARROW_LEFT;
    private static final ItemStack ARROW_RIGHT;
    private static final ItemStack BLANK;

    static {
        final ItemStack darkItemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        final ItemMeta darkItemMeta = darkItemStack.getItemMeta();
        darkItemMeta.setDisplayName(" ");
        darkItemStack.setItemMeta(darkItemMeta);
        DARK_PLACEHOLDER_ITEM = darkItemStack;

        final ItemStack lightItemStack = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        final ItemMeta lightItemMeta = lightItemStack.getItemMeta();
        lightItemMeta.setDisplayName(" ");
        lightItemStack.setItemMeta(lightItemMeta);
        LIGHT_PLACEHOLDER_ITEM = lightItemStack;

        final ItemStack arrowLeftItemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        final SkullMeta arrowLeftSkullMeta = (SkullMeta) arrowLeftItemStack.getItemMeta();
        setSkullMetaTexture(arrowLeftSkullMeta, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=");
        arrowLeftSkullMeta.setDisplayName(Color.SECONDARY + "Previous Page");
        arrowLeftItemStack.setItemMeta(arrowLeftSkullMeta);
        ARROW_LEFT = arrowLeftItemStack;

        final ItemStack arrowRightItemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        final SkullMeta arrowRightSkullMeta = (SkullMeta) arrowRightItemStack.getItemMeta();
        setSkullMetaTexture(arrowRightSkullMeta, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19");
        arrowRightSkullMeta.setDisplayName(Color.SECONDARY + "Next Page");
        arrowRightItemStack.setItemMeta(arrowRightSkullMeta);
        ARROW_RIGHT = arrowRightItemStack;

        final ItemStack blankItemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        final SkullMeta blankSkullMeta = (SkullMeta) blankItemStack.getItemMeta();
        setSkullMetaTexture(blankSkullMeta, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ0ZjE4N2Y0MWNhZTY0MTU1OGY4Nzg3YmYxZTdiZTcyYTZkNzI5MTFiMjFjOTdkOTE2ZjBhN2ZhYWYyOGY3In19fQ==");
        blankSkullMeta.setDisplayName(Color.SECONDARY + "No further Pages");
        blankItemStack.setItemMeta(blankSkullMeta);
        BLANK = blankItemStack;
    }

    private ModuleInventoryBuilder() {
    }

    private static void setSkullMetaTexture(final SkullMeta skullMeta, final String texture) {
        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", texture));

        try {
            final Field profile = skullMeta.getClass().getDeclaredField("profile");
            profile.setAccessible(true);
            profile.set(skullMeta, gameProfile);
            profile.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static ItemStack buildModuleItem(final Module module) {
        final ItemStack itemStack = new ItemStack(module.getSymbol(), 1);

        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Color.PRIMARY + module.getName());

        final List<String> lore = new ArrayList<>();
        lore.add(module.isEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled");
        lore.add("");
        for (final String line : module.getDescription().split("\n")) lore.add(Color.SECONDARY + line);
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static Inventory buildInventory(final int currentPage, final Set<Module> modules) {
        final List<ItemStack> moduleItems = modules.stream().filter(module -> !module.isHide()).map(ModuleInventoryBuilder::buildModuleItem).collect(Collectors.toList());

        final int width = 9;
        final int height = 6;

        final int size = width * height;

        final int pages = (int) Math.ceil(((double) moduleItems.size()) / (width * (height - 1)));

        final Inventory inventory = Bukkit.createInventory(null, size, "Modules [Page " + currentPage + "/" + pages + "]");

        int moduleIndex = 0;
        for (int i = 0; i < size; i++) {
            final int x = i % width;
            final int y = i / width;

            if (x == 0 && y == height - 1) {
                inventory.setItem(i, currentPage == 1 ? BLANK : ARROW_LEFT);
            } else if (x == width - 1 && y == height - 1) {
                inventory.setItem(i, currentPage == pages ? BLANK : ARROW_RIGHT);
            } else if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                inventory.setItem(i, DARK_PLACEHOLDER_ITEM);
            } else if (moduleItems.size() > moduleIndex) {
                inventory.setItem(i, moduleItems.get(moduleIndex));
                moduleIndex++;
            } else {
                inventory.setItem(i, LIGHT_PLACEHOLDER_ITEM);
            }
        }

        return inventory;
    }
}
