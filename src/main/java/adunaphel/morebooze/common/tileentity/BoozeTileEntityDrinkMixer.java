package adunaphel.morebooze.common.tileentity;

import java.util.ArrayList;
import java.util.List;

import adunaphel.morebooze.recipes.BoozeBrewingRecipes;
import adunaphel.morebooze.recipes.BoozeMixingRecipes;
import lotr.common.item.LOTRItemMug;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class BoozeTileEntityDrinkMixer extends TileEntity implements ISidedInventory
{
	public static final int EMPTY = 0;
	public static final int MIXING = 1;
	public static final int FINISHED = 2;
	public static final int mixTime = 12000;
	public static final int mixAnimTime = 32;
	
	private ItemStack[] inventory = new ItemStack[10];
	
	private static final int[] DRINK_SLOTS = new int[] { 0, 1, 2, 3, 4, 5 };
	private static final int[] FUEL_SLOTS = new int[] { 6, 7, 8 };
	
	public static final int BARREL_SLOT = 9;
	
	public int mixerMode;
	public int mixingTime;
	public int mixingAnim;
	public int mixingAnimPrev;
	
	private String specialMixerName;
	
	public List players = new ArrayList();
	
	public ItemStack getMixedDrink()
	{
		if (this.mixerMode == 2 && this.inventory[9] != null)
		{
			ItemStack itemstack = this.inventory[9].copy();
			return itemstack;
		}
		return null;
	}
	
	public void consumeMugRefill()
	{
		if (this.mixerMode == 2 && this.inventory[9] != null)
		{
			(this.inventory[9]).stackSize--;
			if ((this.inventory[9]).stackSize <= 0)
			{
				this.inventory[9] = null;
				this.mixerMode = 0;
			}
		}
	}
	
	private void updateMixerRecipe()
	{
		if (this.mixerMode == 0)
			this.inventory[9] = BoozeMixingRecipes.findMatchingRecipe(this);
	}
	
	public void handleMixingButtonPress()
	{
		if (this.mixerMode == 0 && this.inventory[9] != null)
		{
			this.mixerMode = 1;
			int i;
			for (i = 0; i < 9; i++)
			{
				if (this.inventory[i] != null)
				{
					ItemStack containerItem = null;
					if (this.inventory[i].getItem().hasContainerItem(this.inventory[i]))
					{
						containerItem = this.inventory[i].getItem().getContainerItem(this.inventory[i]);
						if (containerItem.isItemStackDamageable() && containerItem.getItemDamage() > containerItem.getMaxDamage())
							containerItem = null;
					}
					(this.inventory[i]).stackSize--;
					if ((this.inventory[i]).stackSize <= 0)
					{
						this.inventory[i] = null;
						if (containerItem != null)
							this.inventory[i] = containerItem;
					}
				}
			}
			if (!this.worldObj.isRemote)
				for (i = 0; i < this.players.size(); i++)
				{
					EntityPlayerMP entityplayer = this.players.get(i);
					entityplayer.openContainer.detectAndSendChanges();
					entityplayer.sendContainerToPlayer(entityplayer.openContainer);
				}
		}
		else if (this.mixerMode == 1 && this.inventory[9] != null && this.inventory[9].getItemDamage() > 0)
		{
			this.mixerMode = 2;
			this.mixingTime = 0;
			ItemStack itemstack = this.inventory[9].copy();
			itemstack.setItemDamage(itemstack.getItemDamage() - 1);
			this.inventory[9] = itemstack;
		}
	}
	
	public boolean canPoisonBarrel()
	{
		if (this.mixerMode != 0 && this.inventory[9] != null)
		{
			ItemStack itemstack = this.inventory[9];
			return (LOTRPoisonedDrinks.canPoison(itemstack) && !LOTRPoisonedDrinks.isDrinkPoisoned(itemstack));
		}
		return false;
	}
	
	public void poisonBarrel(EntityPlayer entityplayer)
	{
		ItemStack itemstack = this.inventory[9];
		LOTRPoisonedDrinks.setDrinkPoisoned(itemstack,  true);
		LOTRPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
	}
	
	public int getSizeInventory()
	{
		return this.inventory.length;
	}
	
	public ItemStack getStackInSlot(int i)
	{
		return this.inventory[i];
	}
	
	  public ItemStack decrStackSize(int i, int j)
	  {
		    if (this.inventory[i] != null)
		    {
		      if ((this.inventory[i]).stackSize <= j)
		      {
		        ItemStack itemStack = this.inventory[i];
		        this.inventory[i] = null;
		        if (i != 9)
		          updateMixerRecipe(); 
		        return itemStack;
		      } 
		      ItemStack itemstack = this.inventory[i].splitStack(j);
		      if ((this.inventory[i]).stackSize == 0)
		        this.inventory[i] = null; 
		      if (i != 9)
		        updateMixerRecipe(); 
		      return itemstack;
		    } 
		    return null;
	  }
	  
	  public ItemStack getStackInSlotOnClosing(int i) 
	  {
		    if (this.inventory[i] != null)
		    {
		      ItemStack itemstack = this.inventory[i];
		      this.inventory[i] = null;
		      return itemstack;
		    } 
		    return null;
	  }
	  
	  public void setInventorySlotContents(int i, ItemStack itemstack)
	  {
		    this.inventory[i] = itemstack;
		    if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		      itemstack.stackSize = getInventoryStackLimit(); 
		    if (i != 9)
		      updateMixerRecipe(); 
	  }
	  
	  public String getInventoryName() 
	  {
		    return hasCustomInventoryName() ? this.specialMixerName : StatCollector.translateToLocal("container.lotr.barrel");
	  }
	  
	  public String getInvSubtitle() 
	  {
		    ItemStack brewingItem = getStackInSlot(9);
		    if (this.mixerMode == 0)
		      return StatCollector.translateToLocal("container.lotr.barrel.empty"); 
		    if (this.mixerMode == 1 && brewingItem != null)
		      return StatCollector.translateToLocalFormatted("container.lotr.barrel.brewing", new Object[] { brewingItem.getDisplayName(), LOTRItemMug.getStrengthSubtitle(brewingItem) }); 
		    if (this.mixerMode == 2 && brewingItem != null)
		      return StatCollector.translateToLocalFormatted("container.lotr.barrel.full", new Object[] { brewingItem.getDisplayName(), LOTRItemMug.getStrengthSubtitle(brewingItem), Integer.valueOf(brewingItem.stackSize) }); 
		    return "";
	  }
	  
	  public boolean hasCustomInventoryName()
	  {
		    return (this.specialMixerName != null && this.specialMixerName.length() > 0);
	  }
	  
	  public void setBarrelName(String s) {
		    this.specialMixerName = s;
		  }
		  
		  public void readFromNBT(NBTTagCompound nbt) {
		    super.readFromNBT(nbt);
		    readBarrelFromNBT(nbt);
		  }
		  
		  public void readBarrelFromNBT(NBTTagCompound nbt) {
		    NBTTagList items = nbt.getTagList("Items", 10);
		    this.inventory = new ItemStack[getSizeInventory()];
		    for (int i = 0; i < items.tagCount(); i++) {
		      NBTTagCompound itemData = items.getCompoundTagAt(i);
		      int slot = itemData.getByte("Slot");
		      if (slot >= 0 && slot < this.inventory.length)
		        this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemData); 
		    } 
		    this.mixerMode = nbt.getByte("BarrelMode");
		    this.mixingTime = nbt.getInteger("BrewingTime");
		    if (nbt.hasKey("CustomName"))
		      this.specialMixerName = nbt.getString("CustomName"); 
		  }
		  
		  public void writeToNBT(NBTTagCompound nbt) {
		    super.writeToNBT(nbt);
		    writeBarrelToNBT(nbt);
		  }
		  
		  public void writeBarrelToNBT(NBTTagCompound nbt) {
		    NBTTagList items = new NBTTagList();
		    for (int i = 0; i < this.inventory.length; i++) {
		      if (this.inventory[i] != null) {
		        NBTTagCompound itemData = new NBTTagCompound();
		        itemData.setByte("Slot", (byte)i);
		        this.inventory[i].writeToNBT(itemData);
		        items.appendTag((NBTBase)itemData);
		      } 
		    } 
		    nbt.setTag("Items", (NBTBase)items);
		    nbt.setByte("BarrelMode", (byte)this.mixerMode);
		    nbt.setInteger("BrewingTime", this.mixingTime);
		    if (hasCustomInventoryName())
		      nbt.setString("CustomName", this.specialMixerName); 
		  }
		  
		  public int getInventoryStackLimit() {
		    return 64;
		  }
		  
		  public int getBrewProgressScaled(int i) {
		    return this.mixingTime * i / 12000;
		  }
		  
		  public int getBrewAnimationProgressScaled(int i) {
		    return this.mixingAnim * i / 32;
		  }
		  
		  public float getBrewAnimationProgressScaledF(int i, float f) {
		    float f1 = this.mixingAnimPrev * i / 32.0F;
		    float f2 = this.mixingAnim * i / 32.0F;
		    return f1 + (f2 - f1) * f;
		  }
		  
		  public int getBarrelFullAmountScaled(int i) {
		    return (this.inventory[9] == null) ? 0 : ((this.inventory[9]).stackSize * i / BoozeMixingRecipes.MIXER_CAPACITY);
		  }
		  
		  public void updateEntity() {
		    boolean needUpdate = false;
		    if (!this.worldObj.isRemote) {
		      if (this.mixerMode == 1) {
		        if (this.inventory[9] != null) {
		          this.mixingTime++;
		          if (this.mixingTime >= 12000) {
		            this.mixingTime = 0;
		            if (this.inventory[9].getItemDamage() < 4) {
		              this.inventory[9].setItemDamage(this.inventory[9].getItemDamage() + 1);
		              needUpdate = true;
		            } else {
		              this.mixerMode = 2;
		            } 
		          } 
		        } else {
		          this.mixerMode = 0;
		        } 
		      } else {
		        this.mixingTime = 0;
		      } 
		      if (this.mixerMode == 2 && this.inventory[9] == null)
		        this.mixerMode = 0; 
		    } else {
		      this.mixingAnimPrev = this.mixingAnim;
		      if (this.mixerMode == 1) {
		        this.mixingAnim++;
		        if (this.mixingAnim >= 32) {
		          this.mixingAnim = 0;
		          this.mixingAnimPrev = this.mixingAnim;
		        } 
		      } else {
		        this.mixingAnim = 0;
		        this.mixingAnimPrev = this.mixingAnim;
		      } 
		    } 
		    if (needUpdate)
		      markDirty(); 
		  }
		  
		  public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		    return (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityplayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D);
		  }
		  
		  public void openInventory() {}
		  
		  public void closeInventory() {}
		  
		  public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		    if (ArrayUtils.contains(INGREDIENT_SLOTS, slot))
		      return true; 
		    if (ArrayUtils.contains(BUCKET_SLOTS, slot))
		      return BoozeMixingRecipes.isFuelSource(itemstack); 
		    return false;
		  }
		  
		  public int[] getAccessibleSlotsFromSide(int side) {
		    if (side == 0)
		      return BUCKET_SLOTS; 
		    if (side == 1) {
		      List<LOTRSlotStackSize> slotsWithStackSize = new ArrayList<LOTRSlotStackSize>();
		      for (int slot : INGREDIENT_SLOTS) {
		        int size = (getStackInSlot(slot) == null) ? 0 : (getStackInSlot(slot)).stackSize;
		        slotsWithStackSize.add(new LOTRSlotStackSize(slot, size));
		      } 
		      Collections.sort(slotsWithStackSize);
		      int[] sortedSlots = new int[INGREDIENT_SLOTS.length];
		      for (int i = 0; i < sortedSlots.length; i++) {
		        LOTRSlotStackSize slotAndStack = slotsWithStackSize.get(i);
		        sortedSlots[i] = slotAndStack.slot;
		      } 
		      return sortedSlots;
		    } 
		    return BUCKET_SLOTS;
		  }
		  
		  public boolean canInsertItem(int slot, ItemStack insertItem, int side) {
		    return isItemValidForSlot(slot, insertItem);
		  }
		  
		  public boolean canExtractItem(int slot, ItemStack extractItem, int side) {
		    if (ArrayUtils.contains(BUCKET_SLOTS, slot))
		      return !isItemValidForSlot(slot, extractItem); 
		    return false;
		  }
		  
		  public Packet getDescriptionPacket() {
		    NBTTagCompound data = new NBTTagCompound();
		    writeBarrelToNBT(data);
		    return (Packet)new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
		  }
		  
		  public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		    NBTTagCompound data = packet.func_148857_g();
		    readBarrelFromNBT(data);
		  }
		}
}
