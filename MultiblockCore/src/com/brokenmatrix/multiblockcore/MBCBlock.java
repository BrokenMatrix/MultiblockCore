package com.brokenmatrix.multiblockcore;

import org.bukkit.Material;
import org.bukkit.World;

public class MBCBlock implements IMultiblockComponent
{
	private Material block;
	
	public MBCBlock(Material block)
	{
		if (!block.isBlock())
		{
			System.err.println("A MBC block attempted to initilize with a non block material!");
		}
	}
	
	@Override
	public boolean isComplete(int[] loc, World world)
	{
		return world.getBlockAt(loc[0], loc[1], loc[2]).getType() == block;
	}
}
