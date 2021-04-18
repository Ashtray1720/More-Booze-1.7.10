package adunaphel.morebooze;

import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;

public class EventListener 
{
	public void pickup(ItemPickupEvent event)
	{
		if(event.pickedUp.getEntityItem().getItem() == BoozeItems.pureMithril) 
		{ 
			event.player.addStat(MoreBooze.mithril, 1);
		}
	}
}
