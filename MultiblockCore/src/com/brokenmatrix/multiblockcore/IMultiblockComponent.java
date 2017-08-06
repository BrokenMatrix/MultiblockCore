package com.brokenmatrix.multiblockcore;

import org.bukkit.World;

public interface IMultiblockComponent
{
	public boolean isComplete(int[] location, World world);
}
