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
package be.Balor.Manager.Commands.Weather;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import be.Balor.Manager.ACCommand;
import be.Balor.Tools.Type;
import be.Balor.Tools.Utils;
import be.Balor.bukkit.AdminCmd.ACHelper;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class Thor extends ACCommand {

	/**
	 * 
	 */
	public Thor() {
		permNode = "admincmd.weather.thor";
		cmdName = "bal_thor";
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
		if (player != null) {
			HashMap<String, String> replace = new HashMap<String, String>();
			replace.put("player", player.getName());
			if (ACHelper.getInstance().isValueSet(Type.THOR, player.getName())) {
				ACHelper.getInstance().removeValue(Type.THOR, player);
				Utils.sI18n(player, "thorDisabled");
				if (!player.equals(sender))
					Utils.sI18n(sender, "thorDisabledTarget", replace);
			} else {
				ACHelper.getInstance().addValue(Type.THOR, player);
				Utils.sI18n(player, "thorEnabled");
				if (!player.equals(sender))
					Utils.sI18n(sender, "thorEnabledTarget", replace);
			}
		} 
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
