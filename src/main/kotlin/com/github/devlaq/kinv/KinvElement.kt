package com.github.devlaq.kinv

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

sealed class KinvElement(val itemStack: ItemStack)

class KinvEmptyElement: KinvElement(ItemStack(Material.AIR))

class KinvFixedElement(itemStack: ItemStack): KinvElement(itemStack)

class KinvButtonElement(itemStack: ItemStack, val clickHandler: (KinvButtonElementClickContext) -> Unit): KinvElement(itemStack)

class KinvMovableElement(itemStack: ItemStack = ItemStack(Material.AIR), val changeHandler: (KinvMovableElementChangeContext) -> Unit): KinvElement(itemStack)
