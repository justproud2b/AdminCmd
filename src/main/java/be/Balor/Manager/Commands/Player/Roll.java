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
import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import be.Balor.Manager.ACCommand;
import be.Balor.Tools.Utils;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class Roll extends ACCommand {

	/**
	 * 
	 */
	public Roll() {
		permNode = "admincmd.player.roll";
		cmdName = "bal_roll";
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
		int dice = 6;
		if (args.length >= 1) {
			try {
				dice = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
			}
		}
		Random rand = new Random();
		HashMap<String, String> replace = new HashMap<String, String>();
		replace.put("face", String.valueOf(dice));
		if (Utils.isPlayer(sender, false))
			replace.put("player", ((Player) sender).getDisplayName());
		else
			replace.put("player", "Server Admin");
		replace.put("result", String.valueOf(rand.nextInt(dice) + 1));
		sender.getServer().broadcastMessage(Utils.I18n("roll", replace));

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
