/************************************************************************
 * This file is part of AdminCmd.									
 *																		
 * AdminCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by	
 * the Free Software Foundation, either version 3 of the License, or		
 * (at your option) any later version.									
 *																		
 * AdminCmd is distributed in the hope that it will be useful,	
 * but WITHOUT ANY WARRANTY; without even the implied warranty of		
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the			
 * GNU General Public License for more details.							
 *																		
 * You should have received a copy of the GNU General Public License
 * along with AdminCmd.  If not, see <http://www.gnu.org/licenses/>.
 ************************************************************************/
package be.Balor.Manager;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import be.Balor.Manager.Exceptions.CommandAlreadyExist;
import be.Balor.Manager.Exceptions.CommandNotFound;
import be.Balor.Manager.Permissions.PermissionManager;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public abstract class ACCommand {
	protected String permNode = null;
	protected String cmdName = null;
	protected Permission bukkitPerm = null;
	protected PermissionDefault bukkitDefault = PermissionDefault.OP;
	protected boolean other = false;
	protected PluginCommand pluginCommand;

	/**
	 * 
	 */
	public ACCommand() {
		permNode = "";
		cmdName = "";
	}

	/**
	 * Execute the command
	 * 
	 * @param args
	 */
	public abstract void execute(CommandSender sender, String... args);

	/**
	 * Check if the command can be executed
	 * 
	 * @param args
	 * @return
	 */
	public abstract boolean argsCheck(String... args);

	/**
	 * Check if the sender have the permission for the command.
	 * 
	 * @param sender
	 * @return
	 */
	public boolean permissionCheck(CommandSender sender) {
		return PermissionManager.hasPerm(sender, bukkitPerm);
	}

	/**
	 * @return the cmdName
	 */
	public String getCmdName() {
		return cmdName;
	}

	/**
	 * @return the permNode
	 */
	public String getPermNode() {
		return permNode;
	}

	/**
	 * Register the bukkit Permission
	 */
	public void registerBukkitPerm() {
		bukkitPerm = PermissionManager.getInstance().addPermChild(permNode, bukkitDefault);
		if (other)
			PermissionManager.getInstance().addPermChild(permNode + ".other", bukkitDefault);
	}

	/**
	 * Check if the command is usable on other people than the CommandSender
	 * 
	 * @return
	 */
	public boolean toOther() {
		return other;
	}

	/**
	 * Initialize the bukkit plugin command
	 * 
	 * @param plugin
	 * @throws CommandNotFound
	 * @throws CommandAlreadyExist
	 */
	public void initializeCommand(JavaPlugin plugin) throws CommandNotFound, CommandAlreadyExist {
		if ((pluginCommand = plugin.getCommand(cmdName)) == null)
			throw new CommandNotFound(cmdName + " is not loaded in bukkit. Command deactivated");

		if (pluginCommand.getAliases().isEmpty())
			throw new CommandAlreadyExist(cmdName
					+ " has all his alias already registered. Command deactivated");
	}

	/**
	 * @return the pluginCommand
	 */
	public PluginCommand getPluginCommand() {
		return pluginCommand;
	}

}
