package adunaphel.morebooze.recipes;

import java.util.ArrayList;

import adunaphel.morebooze.BoozeItems;
import lotr.common.LOTRMod;
import lotr.common.recipe.LOTRBrewingRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BoozeBrewingRecipes extends LOTRBrewingRecipes 
{
	private static ArrayList<ShapelessOreRecipe> recipes = new ArrayList<ShapelessOreRecipe>();
	public static void createBoozeRecipes()
	{
		addBrewingRecipe(new ItemStack(BoozeItems.mugWhiskey, BARREL_CAPACITY), new Object[] { BoozeItems.rye, BoozeItems.rye, BoozeItems.rye, Items.sugar, LOTRMod.cherry, Items.sugar });
	}
	
	private static void addBrewingRecipe(ItemStack result, Object... ingredients)
	{
	    if (ingredients.length != 6)
	    {
	    	throw new IllegalArgumentException("Brewing recipes must contain exactly 6 items"); 
	    }
	    recipes.add(new ShapelessOreRecipe(result, ingredients));
	  }
}
