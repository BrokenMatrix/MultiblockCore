package com.brokenmatrix.multiblockcore;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener
{
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if (!e.isCancelled())
		{
			MultiblockStructure structure = MultiblockDataStorage.GetStructure(e.getBlock().getLocation());
			
			if (structure != null)
			{
				MultiblockDataStorage.Remove(structure);
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		if (!e.isCancelled())
		{
			MultiblockStructure structure = MultiblockDataStorage.GetStructure(e.getBlockPlaced().getLocation());
			
			if (structure != null)
			{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if (!e.isCancelled() && e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Location loc = e.getClickedBlock().getLocation();
			MultiblockStructure structure = MultiblockDataStorage.GetStructure(loc);
			
			if (structure == null)
			{
				structure = MultiblockConfigurations.GetStructure(loc);
				
				if (structure == null)
				{
					return;
				}
				
				MultiblockDataStorage.Store(structure);
			}
			
			if (structure.getCentre().equals(loc))
			{
				structure.onInteract(e.getPlayer());
			}
		}
	}
}
