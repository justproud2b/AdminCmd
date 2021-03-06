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

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import be.Balor.Manager.ACCommand;
import be.Balor.Manager.Permissions.PermissionManager;
import be.Balor.Tools.Type;
import be.Balor.Tools.Utils;
import be.Balor.bukkit.AdminCmd.ACHelper;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class PrivateMessage extends ACCommand {

	/**
	 * 
	 */
	public PrivateMessage() {
		permNode = "admincmd.player.msg";
		cmdName = "bal_playermsg";
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
		if (Utils.isPlayer(sender, false)
				&& ACHelper.getInstance().isValueSet(Type.MUTED, ((Player) sender).getName())
				&& (Boolean) ACHelper.getInstance().getConfValue("mutedPlayerCantPm")) {
			Utils.sI18n(sender, "muteEnabled");
			return;
		}
		Player buddy = sender.getServer().getPlayer(args[0]);
		if (buddy != null) {
			String senderPm = "";
			String msgPrefix = "[" + ChatColor.RED + "private" + ChatColor.WHITE + "] ";
			String msg = "";
			String senderName = "Server Admin";
			if (Utils.isPlayer(sender, false)) {
				Player pSender = (Player) sender;
				senderName = pSender.getName();
				if (PermissionManager.hasInfoNode()) {
					String name = pSender.getName();
					String prefixstring;
					String world = "";
					world = pSender.getWorld().getName();

					prefixstring = PermissionManager.getPrefix(world, name);

					if (prefixstring != null && prefixstring.length() > 1) {
						String result = Utils.colorParser(prefixstring);
						if (result == null)
							senderPm = prefixstring + name + ChatColor.WHITE + " - ";
						else
							senderPm = result + name + ChatColor.WHITE + " - ";

					} else
						senderPm = name + " - ";
				} else
					senderPm = pSender.getName() + " - ";
			} else
				senderPm = "Server Admin" + " - ";

			for (int i = 1; i < args.length; ++i)
				msg += args[i] + " ";
			msg = msg.trim();
			String parsed = Utils.colorParser(msg);
			if (parsed == null)
				parsed = msg;
			buddy.sendMessage(msgPrefix + senderPm + parsed);
			sender.sendMessage(msgPrefix + senderPm + parsed);
			for (Player p : ACHelper.getInstance().getAllPowerUserOf(Type.SPYMSG))
				if (p != null && !p.getName().equals(senderName)
						&& !p.getName().equals(buddy.getName()))
					p.sendMessage("[" + ChatColor.GREEN + "SpyMsg" + ChatColor.WHITE + "] "
							+ senderName + "-" + buddy.getName() + ": " + parsed);
		} else
			sender.sendMessage(ChatColor.RED + "Player " + ChatColor.WHITE + args[0]
					+ ChatColor.RED + " not found!");

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
