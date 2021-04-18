package adunaphel.morebooze.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BoozeItemSeed extends Item implements IPlantable
{
	private final Block theBlockPlant;
	private final Block soilId;
	
	public BoozeItemSeed(Block parBlockPlant, Block parSoilBlock)
	{
		theBlockPlant = parBlockPlant;
		soilId = parSoilBlock;
	}
	
	@Override
	public boolean onItemUse(ItemStack parItemStack, EntityPlayer parPlayer, World parWorld, int parX, int parY, int parZ, int par7, float par8, float par9, float par10)
	{
		if (par7 != 1)
		{
			return false;
		}
		else if (parPlayer.canPlayerEdit(parX, parY+1, parZ, par7, parItemStack))
		{
			if (parWorld.getBlock(parX, parY, parZ).canSustainPlant(parWorld, parX, parY, parZ, ForgeDirection.UP, this) && parWorld.isAirBlock(parX, parY+1, parZ))
			{
				parWorld.setBlock(parX, parY+1, parZ, theBlockPlant);
				--parItemStack.stackSize;
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
	{
		return EnumPlantType.Plains;
	}
	
	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z)
	{
		return theBlockPlant;
	}
	
	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
	{
		return 0;
	}
	
	public Block getSoilId()
	{
		return soilId;
	}
}
