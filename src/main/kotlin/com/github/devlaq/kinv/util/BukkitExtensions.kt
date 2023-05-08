package com.github.devlaq.kinv.util

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

fun Inventory.title(title: String): Inventory {
    return Bukkit.createInventory(null, size, title)
}

fun InventoryAction.isPlace(): Boolean {
    return this == InventoryAction.PLACE_ALL
            || this == InventoryAction.PLACE_ONE
            || this == InventoryAction.PLACE_SOME
}

fun InventoryAction.isPickup(): Boolean {
    return this == InventoryAction.PICKUP_ONE
            || this == InventoryAction.PICKUP_SOME
            || this == InventoryAction.PICKUP_HALF
            || this == InventoryAction.PICKUP_ALL
}
