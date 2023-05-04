import org.bukkit.Material
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class KinvBuilder {

    class BodyBuilder {

        internal val contents: Array<Array<KinvElement>> = Array(6) { Array(9) { KinvEmptyElement() } }

        fun selector() = KinvEmptySelector()

        fun selector(position: Pair<Int, Int>) = selector().include(position)

        fun fixed(selector: KinvSelector, itemStack: ItemStack) {
            selector.list.forEach { contents[it.first-1][it.second-1] = KinvFixedElement(itemStack) }
        }

        fun button(selector: KinvSelector, itemStack: ItemStack, clickHandler: (InventoryView) -> Unit) {
            selector.list.forEach { contents[it.first-1][it.second-1] = KinvButtonElement(itemStack, clickHandler) }
        }

        fun movable(selector: KinvSelector, itemStack: ItemStack = ItemStack(Material.AIR)) {
            selector.list.forEach { contents[it.first-1][it.second-1] = KinvMovableElement(itemStack) }
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

    fun body(builder: BodyBuilder.() -> Unit) {
        body = BodyBuilder().apply(builder).contents
    }
}
