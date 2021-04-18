package adunaphel.morebooze.items;

import adunaphel.morebooze.BoozeBlocks;
import adunaphel.morebooze.MoreBooze;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class ItemAgave extends BoozeItemSeed
{
	
	public ItemAgave()
	{
		super(BoozeBlocks.blockAgave, Blocks.grass);
		setUnlocalizedName("agaveSeeds");
		setTextureName(MoreBooze.MODID + ":" + "agaveSeeds");
		setCreativeTab(CreativeTabs.tabMaterials);
	}

}
