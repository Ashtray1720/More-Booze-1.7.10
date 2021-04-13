package adunaphel.morebooze.items;

import adunaphel.morebooze.BoozeBlocks;
import adunaphel.morebooze.MoreBooze;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class ItemRye extends BoozeItemSeedFood 
{
	
	public ItemRye()
	{
		super(1, 0.3F, BoozeBlocks.cropRye, Blocks.farmland);
		setUnlocalizedName("rye");
		setTextureName(MoreBooze.MODID + ":" + "rye");
		setCreativeTab(CreativeTabs.tabFood);
	}
}
