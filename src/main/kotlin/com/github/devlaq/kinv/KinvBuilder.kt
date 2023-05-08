package com.github.devlaq.kinv

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class KinvBuilder {

    class Body(row: Int) {

        internal val contents: Array<Array<KinvElement>> = Array(row) { Array(9) { KinvEmptyElement() } }

        fun selector() = KinvEmptySelector()

        fun selector(position: Pair<Int, Int>) = selector().include(position)

        fun item(material: Material, displayName: String? = null, vararg lore: String): ItemStack {
            val itemStack = ItemStack(material)
            itemStack.itemMeta = itemStack.itemMeta?.apply {
                setDisplayName(displayName)
                setLore(lore.toList())
            }

            return itemStack
        }

        fun fixed(selector: KinvSelector, itemStack: ItemStack) {
            selector.list.forEach { contents[it.row][it.col] = KinvFixedElement(itemStack) }
        }

        fun button(selector: KinvSelector, itemStack: ItemStack, clickHandler: KinvButtonElementClickContext.() -> Unit = {}) {
            selector.list.forEach { contents[it.row][it.col] = KinvButtonElement(itemStack, clickHandler) }
        }

        fun movable(selector: KinvSelector, itemStack: ItemStack = ItemStack(Material.AIR), changeHandler: KinvMovableElementChangeContext.() -> Unit = {}) {
            selector.list.forEach { contents[it.row][it.col] = KinvMovableElement(itemStack, changeHandler) }
        }
    }

    lateinit var title: String
    lateinit var type: KinvType

    private var openListener: (InventoryView) -> Unit = {}
    private var closeListener: (InventoryView) -> Unit = {}
    private lateinit var body: Array<Array<KinvElement>>

    internal fun build(): Kinv {
        return Kinv(
            title = title,
            type = type,
            contents = body,

            openListener = openListener,
            closeListener = closeListener
        )
    }

    fun open(listener: (InventoryView) -> Unit) {
        openListener = listener
    }

    fun close(listener: (InventoryView) -> Unit) {
        closeListener = listener
    }

    fun body(builder: Body.() -> Unit) {
        body = Body(type.inventoryProvider(title).size / 9).apply(builder).contents
    }
}
