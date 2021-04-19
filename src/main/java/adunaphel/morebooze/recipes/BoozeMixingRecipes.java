package adunaphel.morebooze.recipes;

import java.util.ArrayList;
import java.util.Iterator;

import adunaphel.morebooze.BoozeItems;
import adunaphel.morebooze.common.tileentity.BoozeTileEntityDrinkMixer;
import lotr.common.LOTRMod;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BoozeMixingRecipes 
{
	private static ArrayList<ShapelessOreRecipe> recipes = new ArrayList<ShapelessOreRecipe>();
	
	public static int MIXER_CAPACITY = 16;
	
	public static void createMixingRecipes()
	{
		addMixingRecipe(new ItemStack(BoozeItems.bloodyMary, MIXER_CAPACITY), new Object[] { LOTRMod.reeds, LOTRMod.salt, LOTRMod.olive, LOTRMod.mugVodka, LOTRMod.mugLimeLiqueur, LOTRMod.mugLemonLiqueur });
	}
	
	private static void addMixingRecipe(ItemStack result, Object... ingredients)
	{
		if (ingredients.length != 6)
			throw new IllegalArgumentException("Mixing recipes must contain exactly 6 items");
		recipes.add(new ShapelessOreRecipe(result, ingredients));
	}
	
	public static boolean isFuelSource(ItemStack itemstack)
	{
		return (itemstack != null && itemstack.getItem() == Items.coal);
	}
	
	public static ItemStack findMatchingRecipe(BoozeTileEntityDrinkMixer mixer)
	{
		for (int i = 6; i < 9; i++)
		{
			ItemStack itemstack = mixer.getStackInSlot(i);
			if (!isFuelSource(itemstack))
				return null;
		}
	label47: for (ShapelessOreRecipe recipe : recipes)
	{
		ArrayList<Object> ingredients = new ArrayList<Object>(recipe.getInput());
		for (int j = 0; j < 6; j++)
		{
			ItemStack itemstack = mixer.getStackInSlot(j);
			if (itemstack != null)
			{
				boolean inRecipe = false;
				Iterator<Object> it = ingredients.iterator();
				while (it.hasNext())
				{
					boolean match = false;
					Object next = it.next();
					if (next instanceof ItemStack)
					{
						match = LOTRRecipes.checkItemEquals((ItemStack)next, itemstack);
					} else if (next instanceof ArrayList)
					{
						for (ItemStack item : next)
							match = (match || LOTRRecipes.checkItemEquals(item, itemstack));
					}
					if (match)
					{
						inRecipe = true;
						ingredients.remove(next);
						break;
					}
				}
				if (!inRecipe)
					continue label47;
			}
		}
		if (ingredients.isEmpty())
			return recipe.getRecipeOutput().copy();
	}
	return null;
}
}
