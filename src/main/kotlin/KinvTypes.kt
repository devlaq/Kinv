import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

sealed class KinvType(val inventoryProvider: (String) -> Inventory) {
    object Type9x1: KinvType({ Bukkit.createInventory(null, 9 * 1, it) })
    object Type9x2: KinvType({ Bukkit.createInventory(null, 9 * 2, it) })
    object Type9x3: KinvType({ Bukkit.createInventory(null, 9 * 3, it) })
    object Type9x4: KinvType({ Bukkit.createInventory(null, 9 * 4, it) })
    object Type9x5: KinvType({ Bukkit.createInventory(null, 9 * 5, it) })
    object Type9x6: KinvType({ Bukkit.createInventory(null, 9 * 6, it) })
}
