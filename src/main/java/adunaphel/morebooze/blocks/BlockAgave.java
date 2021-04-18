package adunaphel.morebooze.blocks;

import java.util.Random;

import adunaphel.morebooze.BoozeItems;
import adunaphel.morebooze.MoreBooze;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockAgave extends BoozeBush
{
	
	public BlockAgave()
	{
		setBlockName("agave");
		setBlockTextureName(MoreBooze.MODID + ":" + "agave");
	}
	
	@Override
	public int quantityDropped(int parMetadata, int parFortune, Random parRand)
	{
		return (parMetadata/2);
	}
	
	@Override
	public Item getItemDropped(int parMetadata, Random parRand, int parFortune)
	{
		//DEBUG
		System.out.println("BlockAgave getItemDropped()");
		return (BoozeItems.agaveLeaf);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister parIIconRegister)
	{
		iIcon = new IIcon[maxGrowthStage+1];
		iIcon[0] = parIIconRegister.registerIcon(textureName + "_stage_0");
		iIcon[1] = parIIconRegister.registerIcon(textureName + "_stage_1");
		iIcon[2] = parIIconRegister.registerIcon(textureName + "_stage_2");
	}
	
	protected boolean canPlaceBlockOn(Block block)
	{
		return (block.getMaterial() == Material.grass || block.getMaterial() == Material.sand);
	}
}
