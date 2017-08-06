package com.brokenmatrix.mutliblockcore;

import org.bukkit.World;

import com.brokenmatrix.modcore.blocks.CustomBlock;
import com.brokenmatrix.modcore.tools.DataStorage;

public class MBCCustomBlock implements IMultiblockComponent
{
	private CustomBlock block;
	
	public MBCCustomBlock(CustomBlock block)
	{
		this.block = block;
	}
	
	@Override
	public boolean isComplete(int[] loc, World world)
	{
		return block.getID() == DataStorage.getBlock(world.getBlockAt(loc[0], loc[1], loc[2]).getLocation());
	}
}
