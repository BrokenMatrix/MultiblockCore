package com.brokenmatrix.multiblockcore;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;

public final class MultiblockDataStorage
{
	private static HashMap<Location, MultiblockStructure> Multiblocks;
	
	static
	{
		Multiblocks = new HashMap<Location, MultiblockStructure>();
	}
	
	public static void Purge()
	{
		Multiblocks = new HashMap<Location, MultiblockStructure>();
	}
	
	public static void Store(MultiblockStructure structure)
	{
		List<Location> locations = structure.getLocations();
		
		for (Location location : locations)
		{
			Multiblocks.put(location, structure);
		}
	}
	
	public static void Remove(MultiblockStructure structure)
	{
		List<Location> locations = structure.getLocations();
		
		for (Location location : locations)
		{
			Multiblocks.remove(location);
		}
	}
	
	public static MultiblockStructure GetStructure(Location location)
	{
		return Multiblocks.get(location);
	}
}
