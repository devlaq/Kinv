package com.github.devlaq.kinv.event

import com.github.devlaq.kinv.*
import com.github.devlaq.kinv.util.slotToKinvAccessPosition
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

class InventoryEventListener: Listener {

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        val kinv = KinvRegistry.views[event.view]
        kinv?.let {
            it.closeListener(event.view)
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val kinv = KinvRegistry.views[event.view]

        kinv?.let {
            val pos = event.slot.slotToKinvAccessPosition()

            val element = kinv.contents[pos.row][pos.col]

            if(event.clickedInventory == event.inventory) {
                when(element) {
                    is KinvFixedElement, is KinvEmptyElement -> event.isCancelled = true
                    is KinvButtonElement -> {
                        event.isCancelled = true
                        element.clickHandler(KinvButtonElementClickContext(
                            position = pos.toPosition(),
                            event = event
                        ))
                    }
                    is KinvMovableElement -> {
                        event.whoClicked.sendMessage("${event.action} ${event.cursor?.type}")
                        element.changeHandler(KinvMovableElementChangeContext(
                            position = pos.toPosition(),
                            itemStack = event.cursor ?: ItemStack(Material.AIR),
                            event = event
                        ))
                    }
                }
            } else {
                if(event.action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    val slot = event.inventory.toList().indexOfFirst {
                        it == null || it.type == Material.AIR || (it.type == event.currentItem?.type && it.amount < 64)
                    }

                    event.whoClicked.sendMessage("$slot, $element, current${event.currentItem}, cursor${event.cursor}")

                    when(element) {
                        is KinvFixedElement, is KinvEmptyElement, is KinvButtonElement -> event.isCancelled = true
                        is KinvMovableElement -> {
                            val itemStack = event.inventory.getItem(slot) ?: ItemStack(Material.AIR)

                            if(itemStack.type == Material.AIR) {
                                itemStack.type = event.currentItem!!.type
                                itemStack.amount = event.currentItem!!.amount
                            }
                            else itemStack.amount += event.currentItem!!.amount

                            if(itemStack.amount > 64) itemStack.amount = 64

                            element.changeHandler(KinvMovableElementChangeContext(
                                position = pos.toPosition(),
                                itemStack = itemStack,
                                event = event
                            ))
                        }
                    }
                }
            }
        }
    }

}
