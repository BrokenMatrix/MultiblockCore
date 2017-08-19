package com.brokenmatrix.multiblockcore;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class MBCColorableBlock implements IMultiblockComponent
{
	private Material block;
	private byte color;
	
	public MBCColorableBlock(Material block, byte color)
	{
		if (!block.isBlock())
		{
			System.err.println("A MBC block attempted to initilize with a non block material!");
		}
		
		this.block = block;
		this.color = color;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isComplete(int[] loc, World world)
	{
		Block block = world.getBlockAt(loc[0], loc[1], loc[2]);
		
		return block.getType() == this.block && block.getData() == color;
	}
}