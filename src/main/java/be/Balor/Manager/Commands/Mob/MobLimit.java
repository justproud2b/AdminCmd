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
package be.Balor.Manager.Commands.Mob;

import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import be.Balor.Manager.ACCommand;
import be.Balor.Tools.Type;
import be.Balor.Tools.Utils;
import be.Balor.bukkit.AdminCmd.ACHelper;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class MobLimit extends ACCommand {

	/**
	 * 
	 */
	public MobLimit() {
		permNode = "admincmd.mob.limit";
		cmdName = "bal_moblimit";
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
		World world = sender.getServer().getWorld(args[0]);
		if (world != null) {
			int limit;
			try {
				HashMap<String, String> replace = new HashMap<String, String>();
				limit = Integer.parseInt(args[1]);
				ACHelper.getInstance().addValue(Type.MOB_LIMIT, world.getName(), limit);
				replace.put("number", args[0]);
				replace.put("world", args[1]);
				Utils.sI18n(sender, "mobLimit", replace);
			} catch (NumberFormatException e) {
				if (args[1].equals("none")) {
					ACHelper.getInstance().removeValue(Type.MOB_LIMIT, world.getName());
					Utils.sI18n(sender, "mobLimitRemoved", "world", world.getName());
				} else
					Utils.sI18n(sender, "NaN", "number", args[1]);
			}

		} else
			Utils.sI18n(sender, "worldNotFound", "world", args[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see be.Balor.Manager.ACCommands#argsCheck(java.lang.String[])
	 */
	@Override
	public boolean argsCheck(String... args) {
		return args != null && args.length >= 2;
	}
}
