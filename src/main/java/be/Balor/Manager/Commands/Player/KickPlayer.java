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
package be.Balor.Manager.Commands.Player;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import be.Balor.Manager.ACCommand;
import be.Balor.Tools.Utils;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class KickPlayer extends ACCommand {

	/**
	 * 
	 */
	public KickPlayer() {
		permNode = "admincmd.player.kick";
		cmdName = "bal_kick";
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
		Player toKick = sender.getServer().getPlayer(args[0]);
		HashMap<String, String> replace = new HashMap<String, String>();
		String message = "";
		if (args.length >= 2)
			for (int i = 1; i < args.length; i++)
				message += args[i] + " ";
		else {
			message = "You have been kick by ";
			if (!Utils.isPlayer(sender, false))
				message += "Server Admin";
			else
				message += ((Player) sender).getName();
		}
		message = message.trim();
		if (toKick != null) {
			replace.put("player", toKick.getName());
			toKick.kickPlayer(message);
		} else
			Utils.sI18n(sender, "playerNotFound", "player", args[0]);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see be.Balor.Manager.ACCommands#argsCheck(java.lang.String[])
	 */
	@Override
	public boolean argsCheck(String... args) {
		return args != null && args.length >= 1;
	}

}
