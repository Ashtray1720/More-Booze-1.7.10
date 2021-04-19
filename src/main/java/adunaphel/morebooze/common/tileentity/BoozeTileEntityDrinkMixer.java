package adunaphel.morebooze.common.tileentity;

import java.util.ArrayList;
import java.util.List;

import adunaphel.morebooze.recipes.BoozeMixingRecipes;
import lotr.common.item.LOTRItemMug;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
 
public class BoozeTileEntityDrinkMixer extends TileEntity implements IInventory
{
    public static final int EMPTY = 0;
    public static final int MIXING = 1;
    public static final int FULL = 2;
    public static final int mixTime = 12000;
    public static final int mixAnimTime = 32;
    private ItemStack[] inventory = new ItemStack[10];
    public int mixerMode;
    public int mixingTime;
    public int mixingAnim;
    public int mixingAnimPrev;
    private String specialMixerName;
    public List<EntityPlayer> players = new ArrayList<EntityPlayer>();
    public static final int MIXER_SLOT = 9;
 
    public ItemStack getMixedDrink()
    {
        if(this.mixerMode == 2 && this.inventory[9] != null)
        {
            ItemStack itemstack = this.inventory[9].copy();
            return itemstack;
        }
        return null;
    }
 
    public void consumeMugRefill()
    {
        if(this.mixerMode == 2 && this.inventory[9] != null)
        {
            --this.inventory[9].stackSize;
            if(this.inventory[9].stackSize <= 0)
            {
                this.inventory[9] = null;
                this.mixerMode = 0;
            }
        }
    }
 
    private void updateMixingRecipe()
    {
        if(this.mixerMode == 0)
        {
            this.inventory[9] = BoozeMixingRecipes.findMatchingRecipe(this);
        }
    }
 
    public void handleMixingButtonPress()
    {
        if(this.mixerMode == 0 && this.inventory[9] != null)
        {
            int i;
            this.mixerMode = 1;
            for(i = 0; i < 9; ++i)
            {
                if(this.inventory[i] == null) continue;
                ItemStack containerItem = null;
                if(this.inventory[i].getItem().hasContainerItem(this.inventory[i]) && (containerItem = this.inventory[i].getItem().getContainerItem(this.inventory[i])).isItemStackDamageable() && containerItem.getItemDamage() > containerItem.getMaxDamage())
                {
                    containerItem = null;
                }
                --this.inventory[i].stackSize;
                if(this.inventory[i].stackSize > 0) continue;
                this.inventory[i] = null;
                if(containerItem == null) continue;
                this.inventory[i] = containerItem;
            }
            if(!this.worldObj.isRemote)
            {
                for(i = 0; i < this.players.size(); ++i)
                {
                    EntityPlayerMP entityplayer = (EntityPlayerMP) this.players.get(i);
                    entityplayer.openContainer.detectAndSendChanges();
                    entityplayer.sendContainerToPlayer(entityplayer.openContainer);
                }
            }
        }
        else if(this.mixerMode == 1 && this.inventory[9] != null && this.inventory[9].getItemDamage() > 0)
        {
            this.mixerMode = 2;
            this.mixingTime = 0;
            ItemStack itemstack = this.inventory[9].copy();
            itemstack.setItemDamage(itemstack.getItemDamage() - 1);
            this.inventory[9] = itemstack;
        }
    }
 
    public boolean canPoisonMixer() {
        if(this.mixerMode != 0 && this.inventory[9] != null)
        {
            ItemStack itemstack = this.inventory[9];
            return LOTRPoisonedDrinks.canPoison(itemstack) && !LOTRPoisonedDrinks.isDrinkPoisoned(itemstack);
        }
        return false;
    }
 
    public void poisonMixer(EntityPlayer entityplayer)
    {
        ItemStack itemstack = this.inventory[9];
        LOTRPoisonedDrinks.setDrinkPoisoned(itemstack, true);
        LOTRPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
    }
 
    @Override
    public int getSizeInventory()
    {
        return this.inventory.length;
    }
 
    @Override
    public ItemStack getStackInSlot(int i)
    {
        return this.inventory[i];
    }
 
    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if(this.inventory[i] != null) {
            if(this.inventory[i].stackSize <= j)
            {
                ItemStack itemstack = this.inventory[i];
                this.inventory[i] = null;
                if(i != 9) {
                    this.updateMixingRecipe();
                }
                return itemstack;
            }
            ItemStack itemstack = this.inventory[i].splitStack(j);
            if(this.inventory[i].stackSize == 0)
            {
                this.inventory[i] = null;
            }
            if(i != 9) {
                this.updateMixingRecipe();
            }
            return itemstack;
        }
        return null;
    }
 
    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        if(this.inventory[i] != null)
        {
            ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        }
        return null;
    }
 
    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        this.inventory[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        if(i != 9) {
            this.updateMixingRecipe();
        }
    }
 
    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.specialMixerName : StatCollector.translateToLocal("container.morebooze.mixer");
    }
 
    public String getInvSubtitle()
    {
        ItemStack mixingItem = this.getStackInSlot(9);
        if(this.mixerMode == 0)
        {
            return StatCollector.translateToLocal("container.morebooze.mixer.empty");
        }
        if(this.mixerMode == 1 && mixingItem != null)
        {
            return StatCollector.translateToLocalFormatted("container.morebooze.mixer.mixing", mixingItem.getDisplayName(), LOTRItemMug.getStrengthSubtitle(mixingItem));
        }
        if(this.mixerMode == 2 && mixingItem != null)
        {
            return StatCollector.translateToLocalFormatted("container.morebooze.mixer.full", mixingItem.getDisplayName(), LOTRItemMug.getStrengthSubtitle(mixingItem), mixingItem.stackSize);
        }
        return "";
    }
 
    @Override
    public boolean hasCustomInventoryName()
    {
        return this.specialMixerName != null && this.specialMixerName.length() > 0;
    }
 
    public void setBarrelName(String s)
    {
        this.specialMixerName = s;
    }
 
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.readMixerFromNBT(nbt);
    }
 
    public void readMixerFromNBT(NBTTagCompound nbt)
    {
        NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for(int i = 0; i < items.tagCount(); ++i)
        {
            NBTTagCompound itemData = items.getCompoundTagAt(i);
            byte slot = itemData.getByte("Slot");
            if(slot < 0 || slot >= this.inventory.length) continue;
            this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
        }
        this.mixerMode = nbt.getByte("MixerMode");
        this.mixingTime = nbt.getInteger("MixingTime");
        if(nbt.hasKey("CustomName"))
        {
            this.specialMixerName = nbt.getString("CustomName");
        }
    }
 
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        this.writeMixerToNBT(nbt);
    }
 
    public void writeMixerToNBT(NBTTagCompound nbt)
    {
        NBTTagList items = new NBTTagList();
        for(int i = 0; i < this.inventory.length; ++i)
        {
            if(this.inventory[i] == null) continue;
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.setByte("Slot", (byte) i);
            this.inventory[i].writeToNBT(itemData);
            items.appendTag(itemData);
        }
        nbt.setTag("Items", items);
        nbt.setByte("MixerMode", (byte) this.mixerMode);
        nbt.setInteger("MixingTime", this.mixingTime);
        if(this.hasCustomInventoryName()) {
            nbt.setString("CustomName", this.specialMixerName);
        }
    }
 
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
 
    public int getMixProgressScaled(int i)
    {
        return this.mixingTime * i / 12000;
    }
 
    public int getMixAnimationProgressScaled(int i)
    {
        return this.mixingAnim * i / 32;
    }
 
    public float getMixAnimationProgressScaledF(int i, float f)
    {
        float f1 = (float) this.mixingAnimPrev * (float) i / 32.0f;
        float f2 = (float) this.mixingAnim * (float) i / 32.0f;
        return f1 + (f2 - f1) * f;
    }
 
    public int getMixerFullAmountScaled(int i)
    {
        return this.inventory[9] == null ? 0 : this.inventory[9].stackSize * i / BoozeMixingRecipes.MIXER_CAPACITY;
    }
 
    @Override
    public void updateEntity()
    {
        boolean needUpdate = false;
        if(!this.worldObj.isRemote)
        {
            if(this.mixerMode == 1)
            {
                if(this.inventory[9] != null)
                {
                    ++this.mixingTime;
                    if(this.mixingTime >= 12000)
                    {
                        this.mixingTime = 0;
                        if(this.inventory[9].getItemDamage() < 4)
                        {
                            this.inventory[9].setItemDamage(this.inventory[9].getItemDamage() + 1);
                            needUpdate = true;
                        }
                        else
                        {
                            this.mixerMode = 2;
                        }
                    }
                }
                else
                {
                    this.mixerMode = 0;
                }
            }
            else 
            {
                this.mixingTime = 0;
            }
            if(this.mixerMode == 2 && this.inventory[9] == null)
            {
                this.mixerMode = 0;
            }
        }
        else 
        {
            this.mixingAnimPrev = this.mixingAnim++;
            if(this.mixerMode == 1)
            {
                if(this.mixingAnim >= 32)
                {
                    this.mixingAnimPrev = this.mixingAnim = 0;
                }
            }
            else 
            {
                this.mixingAnimPrev = this.mixingAnim = 0;
            }
        }
        if(needUpdate)
        {
            this.markDirty();
        }
    }
 
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }
 
    @Override
    public void openInventory()
    {
    	
    }
 
    @Override
    public void closeInventory() 
    {
    	
    }
 
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
        return false;
    }
 
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound data = new NBTTagCompound();
        this.writeMixerToNBT(data);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }
 
    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet)
    {
        NBTTagCompound data = packet.func_148857_g();
        this.readMixerFromNBT(data);
    }
}