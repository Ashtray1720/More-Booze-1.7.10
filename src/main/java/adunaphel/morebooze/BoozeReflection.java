package adunaphel.morebooze;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.ReflectionHelper;
import lotr.common.recipe.LOTRBrewingRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BoozeReflection
{	
	@SuppressWarnings("unchecked")
	public static void setbrewrecipe(ItemStack result, Object... ingredients) {
	        LOTRBrewingRecipes brew = null;
			ArrayList<ShapelessOreRecipe> list = (ArrayList<ShapelessOreRecipe>)ReflectionHelper.getPrivateValue(LOTRBrewingRecipes.class, brew, (String[])new String[] { "recipes" });
	        list.add(new ShapelessOreRecipe(result, ingredients));
	    }
}
