package com.github.devlaq.kinv

import org.bukkit.inventory.InventoryView

object KinvRegistry {

    internal val views = mutableMapOf<InventoryView, Kinv>()

}
