package mods.eln.item

import mods.eln.generic.GenericItemUsingDamage
import mods.eln.i18n.I18N.tr
import mods.eln.wiki.Data
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

// Consumable blade item. Turbines need one installed to run, durability drains based on fuel temperature and corrosiveness.
class TurbineBladeDescriptor(
    name: String,
    val maxDurability: Int,
    val temperatureResistance: Double,
    val corrosionResistance: Double,
    val tierDescription: String
) : GenericItemUsingDamageDescriptorUpgrade(name) {

    override fun setParent(item: Item?, damage: Int) {
        super.setParent(item, damage)
        Data.addUpgrade(newItemStack())
    }

    // NBT helpers
    override fun getDefaultNBT(): NBTTagCompound {
        val nbt = NBTTagCompound()
        nbt.setInteger("bladeDurability", maxDurability)
        return nbt
    }

    fun getDurability(stack: ItemStack): Int {
        val tag = stack.tagCompound ?: return maxDurability
        return tag.getInteger("bladeDurability")
    }

    fun setDurability(stack: ItemStack, durability: Int) {
        if (stack.tagCompound == null) stack.tagCompound = NBTTagCompound()
        stack.tagCompound.setInteger("bladeDurability", durability.coerceAtLeast(0))
    }

    fun isBroken(stack: ItemStack): Boolean = getDurability(stack) <= 0

    // Tooltip
    override fun addInformation(
        stack: ItemStack?,
        player: EntityPlayer?,
        list: MutableList<String>,
        par4: Boolean
    ) {
        super.addInformation(stack, player, list, par4)
        if (stack != null) {
            list.add(tr("Durability: %1$", TurbineBladeDescriptor.bladeDurabilityPct(getDurability(stack), maxDurability)))
            list.add(tr(tierDescription))
        }
        list.add(tr("Install in a turbine's blade slot to enable power generation."))
    }

    // Companion
    companion object {
        fun getDescriptor(stack: ItemStack?): TurbineBladeDescriptor? {
            if (stack == null) return null
            val item = stack.item as? GenericItemUsingDamage<*> ?: return null
            return item.getDescriptor(stack) as? TurbineBladeDescriptor
        }

        // Returns "<1%" instead of "0%" when there's still a sliver of durability left.
        fun bladeDurabilityPct(current: Int, max: Int): String {
            if (max <= 0) return "0%"
            val pct = (current * 100) / max
            return when {
                current > 0 && pct == 0 -> "<1%"
                else -> "$pct%"
            }
        }
    }
}
