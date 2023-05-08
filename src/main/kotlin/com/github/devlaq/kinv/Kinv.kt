package com.github.devlaq.kinv

import com.github.devlaq.kinv.event.InventoryEventListener
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.plugin.Plugin

class Kinv(
    val title: String,
    val type: KinvType,
    val contents: Array<Array<KinvElement>>,

    val openListener: (InventoryView) -> Unit = {},
    val closeListener: (InventoryView) -> Unit = {}
) {

    companion object {
        fun registerEvents(plugin: Plugin) {
            Bukkit.getPluginManager().registerEvents(InventoryEventListener(), plugin)
        }

        fun build(builder: KinvBuilder.() -> Unit): Kinv {
            return KinvBuilder().apply(builder).build()
        }

        fun show(player: Player, builder: KinvBuilder.() -> Unit) = build(builder).show(player)
    }

    private fun createInventory(): Inventory {
        val inventory = type.inventoryProvider(title)

        for(i in contents.indices) {
            for (j in contents[i].indices) {
                inventory.setItem(i * 9 + j, contents[i][j].itemStack)
            }
        }

        return inventory
    }

    fun show(player: HumanEntity) {
        val view = player.openInventory(createInventory())

        view?.let {
            KinvRegistry.views[it] = this
            openListener(it)
        }
    }

}
