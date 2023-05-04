package util

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

fun Inventory.title(title: String): Inventory {
    return Bukkit.createInventory(null, size, title)
}
