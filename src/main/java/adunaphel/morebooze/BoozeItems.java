package adunaphel.morebooze;


import adunaphel.morebooze.items.ItemAgave;
import adunaphel.morebooze.items.ItemRye;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class BoozeItems
{
	//seeds
	public final static Item rye = new ItemRye();
	public final static Item agaveSeeds = new ItemAgave();
	public final static Item agaveLeaf = new Item().setUnlocalizedName("agaveLeaf").setTextureName(MoreBooze.MODID + ":agaveLeaf").setCreativeTab(CreativeTabs.tabMaterials);
	
	//drinks
	public static Item mugWhiskey;
	public static Item orcLiquor;
	public static Item goldOrcDraught;
	public static Item mugShet;
	public static Item mugPhuck;
	public static Item mugElvenTonic;
	public static Item trollSnot;
	public static Item pureMithril;
	public static Item mugGoblinMead;
	public static Item mugCheerwine;
	public static Item mugHarde;
	public static Item mugYeale;
	public static Item mugTequila;
	public static Item wargPiss;
	public static Item ceaseEnDecist;
	public static Item dietMtnDew;
	
	//mixed drinks
	public static Item bloodyMary;
	
	public static void init()
	{
		GameRegistry.registerItem(rye, "rye");
		GameRegistry.registerItem(agaveSeeds, "agaveSeeds");
		GameRegistry.registerItem(agaveLeaf, "agaveLeaf");
		
		////////////////////////////////////////////////////////////////////////////
		//                                                                        //
		//                               Test Brews                               //
		//                                                                        //
		////////////////////////////////////////////////////////////////////////////
		
		/*
		 * can use .addPotionEffect(); to add effects when the brew is drunk, this is how strength and other effects are added
		 */
		mugWhiskey = new LOTRItemMug(0.4f).setDrinkStats(3, 0.3f).setUnlocalizedName("lotr:mugWhiskey").setTextureName("lotr:mugWhiskey");
        GameRegistry.registerItem((Item)mugWhiskey, (String)"mugWhiskey");
        BoozeReflection.setbrewrecipe(new ItemStack(mugWhiskey, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[]{Items.sugar, LOTRMod.cherry, Items.sugar, BoozeItems.rye, BoozeItems.rye, BoozeItems.rye });
        orcLiquor = new LOTRItemMug(0.0f).setDrinkStats(7, 7.0f).addPotionEffect(Potion.damageBoost.id, 30).addPotionEffect(Potion.fireResistance.id, 30).addPotionEffect(Potion.moveSpeed.id, 15).setDamageAmount(4).setUnlocalizedName("lotr:orcLiquor").setTextureName("lotr:orcLiquor");
        GameRegistry.registerItem((Item)orcLiquor, (String)"orcLiquor");
        BoozeReflection.setbrewrecipe(new ItemStack(orcLiquor, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[]{LOTRMod.orcBone, LOTRMod.elfBone, Items.sugar, LOTRMod.orcBone, LOTRMod.elfBone, Items.sugar });
        goldOrcDraught = new LOTRItemMug(0.0f).setDrinkStats(3, 0.4f).addPotionEffect(Potion.damageBoost.id, 80).addPotionEffect(Potion.moveSpeed.id, 60).setDamageAmount(2).setUnlocalizedName("lotr:goldOrcDraught").setTextureName("lotr:goldOrcDraught");
        GameRegistry.registerItem((Item)goldOrcDraught, (String)"goldOrcDraught");
        BoozeReflection.setbrewrecipe(new ItemStack(goldOrcDraught, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[]{LOTRMod.orcBone, LOTRMod.elfBone, LOTRMod.hobbitBone, LOTRMod.dwarfBone, Items.glowstone_dust, Items.bone });	
        mugShet = new LOTRItemMug(0.6f).setDrinkStats(4, 0.4f).setUnlocalizedName("lotr:mugShet").setTextureName("lotr:mugShet");
        GameRegistry.registerItem((Item)mugShet, (String)"mugShet");
        BoozeReflection.setbrewrecipe(new ItemStack(mugShet, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[]{Items.sugar, LOTRMod.appleGreen, Items.sugar, LOTRMod.cornBread, BoozeItems.rye, LOTRMod.cornBread });	
        mugPhuck = new LOTRItemMug(0.5f).setDrinkStats(5, 0.35f).setUnlocalizedName("lotr:mugPhuck").setTextureName("lotr:mugPhuck");
        GameRegistry.registerItem((Item)mugPhuck, (String)"mugPhuck");
        BoozeReflection.setbrewrecipe(new ItemStack(mugPhuck, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[]{Items.sugar, Items.sugar, Items.sugar, Items.sugar, BoozeItems.rye, Items.sugar});
        mugElvenTonic = new LOTRItemMug(0.0f).setDrinkStats(3, 0.4f).addPotionEffect(Potion.damageBoost.id, 75).addPotionEffect(Potion.regeneration.id, 75).setUnlocalizedName("lotr:mugElvenTonic").setTextureName("lotr:mugElvenTonic");
        GameRegistry.registerItem((Item)mugElvenTonic, (String)"mugElvenTonic");
        BoozeReflection.setbrewrecipe(new ItemStack(mugElvenTonic, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[]{LOTRMod.amber, Items.sugar, LOTRMod.amber, BoozeItems.rye, Items.sugar, BoozeItems.rye });
        trollSnot = new LOTRItemMug(0.3f).setDrinkStats(2, 0.25f).addPotionEffect(Potion.resistance.id, 60).addPotionEffect(Potion.moveSlowdown.id, 15).setUnlocalizedName("lotr:trollSnot").setTextureName("lotr:trollSnot");
        GameRegistry.registerItem((Item)trollSnot, (String)"trollSnot");
        BoozeReflection.setbrewrecipe(new ItemStack(trollSnot, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[] {Items.slime_ball, Items.slime_ball, Items.slime_ball, Items.slime_ball, Items.bone, Items.slime_ball});
        pureMithril = new LOTRItemMug(0.4f).setDrinkStats(0, 0f).addPotionEffect(Potion.poison.id, 200).addPotionEffect(Potion.damageBoost.id, 250).setUnlocalizedName("lotr:pureMithril").setTextureName("lotr:pureMithril");
        GameRegistry.registerItem((Item)pureMithril, (String)"pureMithril");
        BoozeReflection.setbrewrecipe(new ItemStack(pureMithril, 6), new Object[] {LOTRMod.mithrilNugget, LOTRMod.mithrilNugget, LOTRMod.mithrilNugget, LOTRMod.mithrilNugget, LOTRMod.mithrilNugget, LOTRMod.mithrilNugget});
        mugGoblinMead = new LOTRItemMug(0.5f).setDrinkStats(3, 0.3f).setUnlocalizedName("lotr:mugGoblinMead").setTextureName("lotr:mugGoblinMead");
        GameRegistry.registerItem((Item)mugGoblinMead, (String)"mugGoblinMead");
        BoozeReflection.setbrewrecipe(new ItemStack(mugGoblinMead, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[] {BoozeItems.rye, "bone", BoozeItems.rye, BoozeItems.rye, BoozeItems.rye, BoozeItems.rye});
        mugCheerwine = new LOTRItemMug(0.0f).setDrinkStats(3, 0.2f).addPotionEffect(Potion.moveSpeed.id, 75).setUnlocalizedName("lotr:mugCheerwine").setTextureName("lotr:mugCheerwine");
        GameRegistry.registerItem((Item)mugCheerwine, (String)"mugCheerwine");
        BoozeReflection.setbrewrecipe(new ItemStack(mugCheerwine, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[] {LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, Items.sugar, LOTRMod.cherry});
        mugHarde = new LOTRItemMug(1.0f).setDrinkStats(4, 0.5f).setUnlocalizedName("lotr:mugHarde").setTextureName("lotr:mugHarde");
        GameRegistry.registerItem((Item)mugHarde, (String)"mugHarde");
        BoozeReflection.setbrewrecipe(new ItemStack(mugHarde, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[] {LOTRMod.elderberry, LOTRMod.blackberry, LOTRMod.elderberry, LOTRMod.elderberry, LOTRMod.cranberry, LOTRMod.elderberry});
        mugYeale = new LOTRItemMug(0.8f).setDrinkStats(4, 0.4f).setUnlocalizedName("lotr:mugYeale").setTextureName("lotr:mugYeale");
        GameRegistry.registerItem((Item)mugYeale, (String)"mugYeale");
        BoozeReflection.setbrewrecipe(new ItemStack(mugYeale, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[] {LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, BoozeItems.rye, BoozeItems.rye, BoozeItems.rye});
        mugTequila = new LOTRItemMug(1.2f).setDrinkStats(5, 0.6f).setUnlocalizedName("lotr:mugTequila").setTextureName("lotr:mugTequila");
        GameRegistry.registerItem((Item)mugTequila, (String)"mugTequila");
        BoozeReflection.setbrewrecipe(new ItemStack(mugTequila, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[] {BoozeItems.agaveLeaf, BoozeItems.agaveLeaf, BoozeItems.agaveLeaf, BoozeItems.agaveLeaf, BoozeItems.agaveLeaf, BoozeItems.agaveLeaf});
        wargPiss = new LOTRItemMug(0.0f).setDrinkStats(4, 0.35f).addPotionEffect(Potion.hunger.id, 60).addPotionEffect(Potion.moveSpeed.id, 60).setUnlocalizedName("lotr:wargPiss").setTextureName("lotr:wargPiss");
        GameRegistry.registerItem((Item)wargPiss, (String)"wargPiss");
        BoozeReflection.setbrewrecipe(new ItemStack(wargPiss, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[] {LOTRMod.wargBone, LOTRMod.fur, LOTRMod.wargBone, LOTRMod.fur, LOTRMod.wargBone, LOTRMod.fur});
        dietMtnDew = new LOTRItemMug(0.2f).setDrinkStats(4, 0.4f).addPotionEffect(Potion.moveSpeed.id, 75).addPotionEffect(Potion.damageBoost.id, 80).addPotionEffect(Potion.resistance.id, 30).setUnlocalizedName("lotr:dietMtnDew").setTextureName("lotr:dietMtnDew");
        GameRegistry.registerItem((Item)dietMtnDew, (String)"dietMtnDew");
        BoozeReflection.setbrewrecipe(new ItemStack(dietMtnDew, LOTRBrewingRecipes.BARREL_CAPACITY), new Object[] {LOTRMod.lemon, LOTRMod.athelas, LOTRMod.lemon, LOTRMod.lime, Items.sugar, LOTRMod.lime});
        
        ////////////////////////////////////////////////////////////////////////////
        //                                                                        //
        //                             Mixed Drinks                               //
        //                                                                        //
        ////////////////////////////////////////////////////////////////////////////
        
        bloodyMary = new LOTRItemMug(1.0f).setDrinkStats(5, 0.5f).setUnlocalizedName("lotr:bloodyMary").setTextureName("lotr:bloodyMary");
        GameRegistry.registerItem((Item)bloodyMary, (String)"bloodyMary");
	}
}