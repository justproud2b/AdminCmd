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
package be.Balor.Manager.Commands.Tp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import be.Balor.Manager.ACCommand;
import be.Balor.Tools.Type;
import be.Balor.Tools.Utils;
/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class TpPlayerToPlayer extends ACCommand {

	/**
	 * 
	 */
	public TpPlayerToPlayer() {
		permNode = "admincmd.tp.players";
		cmdName = "bal_tp2p";
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
		Player from = sender.getServer().getPlayer(args[0]);
		Player to = sender.getServer().getPlayer(args[1]);
		if (from != null && from.equals(sender))
			Utils.tpP2P(sender, args[0], args[1], Type.Tp.TP_TO);
		else if (to != null && to.equals(sender))
			Utils.tpP2P(sender, args[0], args[1], Type.Tp.TP_HERE);
		else
			Utils.tpP2P(sender, args[0], args[1], Type.Tp.TP_PLAYERS);
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
