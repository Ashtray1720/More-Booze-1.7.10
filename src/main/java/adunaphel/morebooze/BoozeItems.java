package adunaphel.morebooze;


import adunaphel.morebooze.items.ItemRye;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.Item;

public class BoozeItems
{
	//seeds
	public final static Item rye = new ItemRye();
	
	//drinks
	public static Item mugWhiskey;
	
	public static void init()
	{
		GameRegistry.registerItem(rye, "rye");
		
		mugWhiskey = (new LOTRItemMug(0.3F)).setDrinkStats(3, 0.3F).setUnlocalizedName("mugWhiskey");
		GameRegistry.registerItem(mugWhiskey, "mugWhiskey");
	}
}
