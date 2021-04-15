package adunaphel.morebooze;


import adunaphel.morebooze.items.ItemRye;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BoozeItems
{
	//seeds
	public final static Item rye = new ItemRye();
	
	//drinks
	public static Item mugWhiskey;
	
	public static void init()
	{
		GameRegistry.registerItem(rye, "rye");
		
		/*
		 * can use .addPotionEffect(); to add effects when the brew is drunk, this is how strength and other effects are added
		 */
		mugWhiskey = new LOTRItemMug(0.4f).setDrinkStats(3, 0.3f).setUnlocalizedName("morebooze:mugWhiskey").setTextureName("morebooze:mugWhiskey");
        GameRegistry.registerItem((Item)mugWhiskey, (String)"mugWhiskey");
        BoozeReflection.setbrewrecipe(new ItemStack(mugWhiskey, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[]{Items.sugar, LOTRMod.cherry, Items.sugar, BoozeItems.rye, BoozeItems.rye, BoozeItems.rye });
	}
}
