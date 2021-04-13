package adunaphel.morebooze.blocks;

import java.util.Random;

import adunaphel.morebooze.BoozeItems;
import adunaphel.morebooze.MoreBooze;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockRye extends BoozeCrops
{
	
	public BlockRye()
	{
		setBlockName("rye_crop");
		setBlockTextureName(MoreBooze.MODID + ":" + "rye_crop");
		
	}
	
	/*
	 * Returns the quantity of items to drop when block is destroyed
	 */
	@Override
	public int quantityDropped(int parMetadata, int parFourtune, Random parRand)
	{
		return (parMetadata/1);
	}
	
	@Override
	public Item getItemDropped(int parMetadata, Random parRand, int parFortune)
	{
		//DEBUG
		System.out.println("BlockRye getItemDropped()");
			return (BoozeItems.rye);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister parIIconRegister)
	{
		iIcon = new IIcon[maxGrowthStage+1];
		
		iIcon[0] = parIIconRegister.registerIcon(textureName + "_stage_0");
		iIcon[1] = parIIconRegister.registerIcon(textureName + "_stage_0");
		iIcon[2] = parIIconRegister.registerIcon(textureName + "_stage_1");
		iIcon[3] = parIIconRegister.registerIcon(textureName + "_stage_1");
		iIcon[4] = parIIconRegister.registerIcon(textureName + "_stage_2");
		iIcon[5] = parIIconRegister.registerIcon(textureName + "_stage_2");
		iIcon[6] = parIIconRegister.registerIcon(textureName + "_stage_3");
		iIcon[7] = parIIconRegister.registerIcon(textureName + "_stage_3");
	}
}
