package adunaphel.morebooze;

import adunaphel.morebooze.blocks.BlockRye;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BoozeBlocks 
{
	public final static Block cropRye = new BlockRye();
	
	public static void init() 
	{
		GameRegistry.registerBlock(cropRye, "rye_crop");
	}
}
