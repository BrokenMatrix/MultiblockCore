package com.brokenmatrix.multiblockcore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public final class MultiblockConfigurations
{
	private static List<MultiblockConfiguration> Configs;
	private static List<Material> ValidCentres;
	
	static
	{
		Configs = new ArrayList<MultiblockConfiguration>();
	}
	
	public static void Register(MultiblockConfiguration config)
	{
		Configs.add(config);
		ValidCentres.add(config.getCentreMaterial());
	}
	
	public static MultiblockStructure GetStructure(Location centre)
	{
		if (!ValidCentres.contains(centre.getBlock().getType()))
		{
			return null;
		}
		
		List<MultiblockConfiguration> canidates = new ArrayList<MultiblockConfiguration>();
		for (MultiblockConfiguration config : Configs)
		{
			if (config.isCanidate(centre))
			{
				canidates.add(config);
			}
		}
		
		for (MultiblockConfiguration canidate : canidates)
		{
			int rotation = canidate.fits(centre);
			if (rotation > -1)
			{
				return new MultiblockStructure(centre, rotation, canidate);
			}
		}
		
		return null;
	}
}
