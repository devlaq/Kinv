package event

import KinvBuilder
import KinvButtonElement
import KinvEmptyElement
import KinvFixedElement
import KinvMovableElement
import KinvRegistry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

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
            if(event.clickedInventory == event.inventory) {
                when(val element = kinv.contents.flatten()[event.slot]) {
                    is KinvFixedElement, is KinvEmptyElement -> event.isCancelled = true
                    is KinvButtonElement -> {
                        event.isCancelled = true
                        element.clickHandler(event.view)
                    }
                    is KinvMovableElement -> {}
                }
            }
        }
    }

}
