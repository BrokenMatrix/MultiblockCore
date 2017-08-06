package com.brokenmatrix.mutliblockcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.brokenmatrix.modcore.EventListener;
import com.brokenmatrix.modcore.ModCore;

public class MultiblockCore extends JavaPlugin
{
	@Override
	public void onEnable()
	{	
		this.getCommand("multiblockcore").setExecutor(new CommandMultiblockCore(this));
		
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		
		((ModCore) Bukkit.getPluginManager().getPlugin("ModCore")).notifyOfMod(this);
	}
}
