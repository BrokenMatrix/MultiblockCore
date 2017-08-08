package com.brokenmatrix.multiblockcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.MathHelper;

public class MultiblockConfiguration
{
	private HashMap<String, IMultiblockComponent> components;
	private HashMap<String, IMultiblockComponent> quickChecks;
	private Material centre;
	private String ID;
	
	public MultiblockConfiguration(String ID, Material centre)
	{
		this.ID = ID;
		
		if (!centre.isBlock())
		{
			System.err.println("Multiblock configuration " + ID + " attempted to initlize with a non block core!");
			return;
		}
		
		components = new HashMap<String, IMultiblockComponent>();
		quickChecks = new HashMap<String, IMultiblockComponent>();
	}
	
	public String getID()
	{
		return ID;
	}
	
	public Material getCentreMaterial()
	{
		return centre;
	}
	
	public void onInteract(Player player, Location centre, int rotation) {}
	
	public List<Location> getLocations(Location loc, int rotation)
	{
		World world = loc.getWorld();
		List<Location> locations = new ArrayList<Location>();
		
		for (String s : components.keySet())
		{
			String[] parts = s.split(" ");
			int[] pos = new int[parts.length];
			
			pos[0] = Integer.parseInt(parts[0]);
			pos[1] = Integer.parseInt(parts[1]);
			pos[2] = Integer.parseInt(parts[2]);
			
			float rotr = (float) Math.toRadians(rotation);
			rotate(pos, rotr);
			
			pos[0] += loc.getBlockX();
			pos[1] += loc.getBlockY();
			pos[2] += loc.getBlockZ();
			
			locations.add(world.getBlockAt(pos[0], pos[1], pos[2]).getLocation());
		}
		
		return locations;
	}
	
	public boolean isCanidate(Location loc)
	{
		if (loc.getBlock().getType() != centre)
		{
			return false;
		}
		
		if (check(loc.getWorld(), loc, quickChecks, 0))
		{
			return true;
		}
		
		if (check(loc.getWorld(), loc, quickChecks, 90))
		{
			return true;
		}
		
		if (check(loc.getWorld(), loc, quickChecks, 180))
		{
			return true;
		}
		
		if (check(loc.getWorld(), loc, quickChecks, 270))
		{
			return true;
		}
		
		return false;
	}
	
	public int fits(Location loc)
	{
		if (loc.getBlock().getType() != centre)
		{
			return -1;
		}
		
		if (check(loc.getWorld(), loc, components, 0))
		{
			return 0;
		}
		
		if (check(loc.getWorld(), loc, components, 90))
		{
			return 90;
		}
		
		if (check(loc.getWorld(), loc, components, 180))
		{
			return 180;
		}
		
		if (check(loc.getWorld(), loc, components, 270))
		{
			return 270;
		}
		
		return -1;
	}
	
	public MultiblockConfiguration addComponent(String loc, IMultiblockComponent component)
	{
		components.put(loc, component);
		
		return this;
	}

	public MultiblockConfiguration addComponent4(String sloc, IMultiblockComponent component)
	{
		String[] parts = sloc.split(" ");
		int[] loc = new int[parts.length];
		for (int i = 0; i < parts.length; i++)
			loc[i] = Integer.parseInt(parts[i]);
		
		components.put(loc[0] + " " + loc[1] + " " + loc[2], component);
		components.put(-loc[0] + " " + loc[1] + " " + -loc[2], component);
		components.put(loc[2] + " " + loc[1] + " " + loc[2], component);
		components.put(-loc[2] + " " + loc[1] + " " + -loc[2], component);
		
		return this;
	}
	
	public MultiblockConfiguration finish()
	{
		int count = 0;
		for (Entry<String, IMultiblockComponent> component : components.entrySet())
		{
			quickChecks.put(component.getKey(), component.getValue());
			
			count++;
			if (count == 3)
			{
				break;
			}
		}
		
		return this;
	}
	
	private boolean check(World world, Location centre, HashMap<String, IMultiblockComponent> components, float rotd)
	{
		for (Entry<String, IMultiblockComponent> component : components.entrySet())
		{
			String[] parts = component.getKey().split(" ");
			int[] loc = new int[parts.length];
			
			loc[0] = Integer.parseInt(parts[0]) + centre.getBlockX();
			loc[1] = Integer.parseInt(parts[1]) + centre.getBlockY();
			loc[2] = Integer.parseInt(parts[2]) + centre.getBlockZ();
			
			float rotr = (float) Math.toRadians(rotd);
			rotate(loc, rotr);
			
			if (MultiblockDataStorage.GetStructure(world.getBlockAt(loc[0], loc[1], loc[2]).getLocation()) != null || !component.getValue().isComplete(loc, world))
			{
				return false;
			}
		}
		
		return true;
	}
	
	private void rotate(int[] pos, float rotr)
	{
		pos[0] = Math.round((pos[0] * MathHelper.cos(rotr)) - (pos[2] * MathHelper.sin(rotr)));
		pos[2] = Math.round((pos[2] * MathHelper.cos(rotr)) + (pos[0] * MathHelper.sin(rotr)));
	}
}
