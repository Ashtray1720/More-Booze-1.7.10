package adunaphel.morebooze;

import adunaphel.morebooze.recipes.BoozeBrewingRecipes;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.stats.Achievement;


@Mod(modid = MoreBooze.MODID, name = MoreBooze.NAME, version = MoreBooze.VERSION, dependencies = "required-after:lotr")
public class MoreBooze
{
	public static final String MODID = "morebooze";
	public static final String NAME = "More Booze";
	public static final String VERSION = "BETA+1.0";
	
	@Mod.Instance(MoreBooze.MODID)
	public static MoreBooze instance;
	
	public static Achievement mithril;
	
	public MoreBooze()
	{
		instance = this;
	}
	
	//Block & Item init
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		BoozeBlocks.init();
		BoozeItems.init();
	}
	
	//Proxy, tile entities, entities, GUI & packet registering
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		BoozeBrewingRecipes.createBoozeRecipes();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
