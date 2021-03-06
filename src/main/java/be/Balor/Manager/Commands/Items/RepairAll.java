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
package be.Balor.Manager.Commands.Items;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import be.Balor.Manager.ACCommand;
import be.Balor.Tools.Utils;
import be.Balor.bukkit.AdminCmd.ACHelper;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class RepairAll extends ACCommand {

	/**
	 * 
	 */
	public RepairAll() {
		permNode = "admincmd.item.repair";
		cmdName = "bal_repairall";
		other = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * be.Balor.Manager.ACCommands#execute(org.bukkit.command.CommandSender,
	 * java.lang.String[])
	 */
	@Override
	public void execute(CommandSender sender, String... args) {
		Player player = Utils.getUser(sender, args, permNode);
		if (player == null)
			return;
		for (ItemStack item : player.getInventory().getContents())
			if (item != null && ACHelper.getInstance().reparable(item.getTypeId()))
				item.setDurability((short) 0);
		for (ItemStack item : player.getInventory().getArmorContents())
			if (item != null)
				item.setDurability((short) 0);
		HashMap<String, String> replace = new HashMap<String, String>();
		replace.put("player", player.getName());
		if (!sender.equals(player))
			Utils.sI18n(sender, "repairAll", replace);
		Utils.sI18n(player, "repairAllTarget");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see be.Balor.Manager.ACCommands#argsCheck(java.lang.String[])
	 */
	@Override
	public boolean argsCheck(String... args) {
		return args != null;
	}

}
