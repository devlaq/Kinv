package com.github.devlaq.kinv

import com.github.devlaq.kinv.util.KinvPosition
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

data class KinvMovableElementChangeContext(
    val position: KinvPosition,
    val itemStack: ItemStack,
    val event: InventoryClickEvent
)

data class KinvButtonElementClickContext(
    val position: KinvPosition,
    val event: InventoryClickEvent
)
