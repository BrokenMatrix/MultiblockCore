package com.brokenmatrix.multiblockcore;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MultiblockStructure
{
	private MultiblockConfiguration type;
	private Location centre;
	private int rotation;
	
	public MultiblockStructure(Location centre, int rotation, MultiblockConfiguration type)
	{
		this.centre = centre;
		this.type = type;
	}
	
	public void onInteract(Player player)
	{
		type.onInteract(player, centre, rotation);
	}
	
	public List<Location> getLocations()
	{
		return type.getLocations(centre, rotation);
	}
	
	public boolean isCentre(Location loc)
	{
		return loc == centre;
	}
	
	public Location getCentre()
	{
		return centre;
	}
	
	public MultiblockConfiguration getType()
	{
		return type;
	}
}
