package com.brokenmatrix.multiblockcore;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
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
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		System.out.println("INTERACT");
		if (!e.isCancelled() && e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			System.out.println("SUCESS");
			Location loc = e.getClickedBlock().getLocation();
			MultiblockStructure structure = MultiblockDataStorage.GetStructure(loc);
			
			if (structure == null)
			{
				System.out.println("NO STRUCTURE");
				structure = MultiblockConfigurations.GetStructure(loc);
				
				if (structure == null)
				{
					System.out.println("NON VALID STRUCTURE");
					return;
				}
				
				System.out.println("VALID STRUCTURE");
				MultiblockDataStorage.Store(structure);
			}
			
			if (structure.getCentre() == loc)
			{
				System.out.println("INTERACT");
				structure.onInteract(e.getPlayer());
			}
		}
	}
}
