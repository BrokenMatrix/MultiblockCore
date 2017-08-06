package com.brokenmatrix.multiblockcore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.brokenmatrix.modcore.tools.Helper;

import net.md_5.bungee.api.ChatColor;

public class CommandMultiblockCore implements CommandExecutor
{
	private MultiblockCore plugin;
	
	public CommandMultiblockCore(MultiblockCore plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String Label, String[] args)
	{
		String message = null;
		
		if (args.length > 0)
		{
			if (args[0].equals("help"))
			{
				message = getHelpMessage();
			}
			else if (args[0].equals("info"))
			{
				message = getInformationMessage();
			}
			else if (args[0].equals("dismantleall"))
			{
				if (sender.hasPermission("mutliblockcore.dismantleall"))
				{
					MultiblockDataStorage.Purge();
					
					message = ChatColor.GREEN + "Dismantled all multiblocks!";
				}
				else
				{
					message = ChatColor.RED + "You can not do this!";
				}
			}
			else if (args[0].equals("dismantle"))
			{
				if (sender.hasPermission("mutliblockcore.dismantle"))
				{
					if (args.length == 4)
					{
						int x = Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						int z = Integer.parseInt(args[3]);
						
						Location loc = Bukkit.getPlayer(sender.getName()).getWorld().getBlockAt(x, y, z).getLocation();
						MultiblockStructure structure = MultiblockDataStorage.GetStructure(loc);
						if (structure != null)
						{
							MultiblockDataStorage.Remove(structure);
							
							message = ChatColor.GREEN + "Dismantled multiblock!";
						}
						else
						{
							message = ChatColor.RED + "No multiblock to dismantle!";
						}
					}
					else
					{
						message = ChatColor.RED + "You need to prove a position!";
					}
				}
				else
				{
					message = ChatColor.RED + "You can not do this!";
				}
			}
			else if (args[0].equals("view"))
			{
				if (sender.hasPermission("mutliblockcore.view"))
				{
					if (args.length == 4)
					{
						int x = Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						int z = Integer.parseInt(args[3]);
						
						Location loc = Bukkit.getPlayer(sender.getName()).getWorld().getBlockAt(x, y, z).getLocation();
						MultiblockStructure structure = MultiblockDataStorage.GetStructure(loc);
						if (structure != null)
						{
							message = ChatColor.GREEN + "This is a " + structure.getType().getID() + "!";
						}
						else
						{
							message = ChatColor.RED + "No multiblock to view!";
						}
					}
					else
					{
						message = ChatColor.RED + "You need to prove a position!";
					}
				}
				else
				{
					message = ChatColor.RED + "You can not do this!";
				}
			}
		}
		
		if (message == null)
		{
			message = ChatColor.RED + "Provided arguments are not valid\n" + getHelpMessage();
		}
		
		Helper.sendMessage(sender, message);
		
		return true;
	}
	
	private String getHelpMessage()
	{
		StringBuilder message = new StringBuilder();
		
		message.append(ChatColor.YELLOW + "MultiblockCore Help\n");
		message.append("   /mbc help - Displays the help page\n");
		message.append("   /mbc info - Displays information about the information.\n");
		message.append("   /mbc view <x> <y> <z> - Displays info about a Multiblock.\n");
		message.append("   /mbc dismantle <x> <y> <z> - Resets stored multiblock info.\n");
		message.append("   /mbc dismantleall - Resets all stored multiblock info.\n");
		
		return message.toString();
	}
	
	private String getInformationMessage()
	{
		StringBuilder message = new StringBuilder();
		
		message.append(ChatColor.YELLOW + "MultiblockCore Information\n");
		message.append("   Version:" + plugin.getDescription().getVersion() + "\n");
		message.append("   Authors:\n");
		for (String author : plugin.getDescription().getAuthors())
		{
			message.append("      " + author + "\n");
		}
		
		return message.toString();
	}
}
