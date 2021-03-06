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
package be.Balor.Manager.Terminal;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.util.config.Configuration;

import be.Balor.Manager.Exceptions.CommandNotFound;
import be.Balor.Manager.Permissions.PermissionManager;
import be.Balor.Manager.Terminal.Commands.UnixTerminalCommand;
import be.Balor.Manager.Terminal.Commands.WindowsTerminalCommand;
import be.Balor.Tools.FilesManager;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class TerminalCommandManager {
	HashMap<String, TerminalCommand> commands = new HashMap<String, TerminalCommand>();
	private static TerminalCommandManager instance = null;

	/**
	 * 
	 */
	private TerminalCommandManager() {
		File scripts = FilesManager.getInstance().getInnerFile("scripts.yml", "scripts");
		File workingDir = scripts.getParentFile();
		Configuration conf = new Configuration(scripts);
		conf.load();
		TerminalCommand toAdd;
		if (System.getProperty("os.name").contains("Windows"))
			for (String cmdName : conf.getKeys()) {
				toAdd = new WindowsTerminalCommand(cmdName, conf.getString(cmdName + ".exec"),
						conf.getString(cmdName + ".args"), workingDir);
				toAdd.setBukkitPerm(PermissionManager.getInstance().addPermChild(
						"admincmd.server.exec." + cmdName));
				commands.put(cmdName, toAdd);

			}

		else

			for (String cmdName : conf.getKeys()) {
				toAdd = new UnixTerminalCommand(cmdName, conf.getString(cmdName + ".exec"),
						conf.getString(cmdName + ".args"), workingDir);
				toAdd.setBukkitPerm(PermissionManager.getInstance().addPermChild(
						"admincmd.server.exec." + cmdName));
				commands.put(cmdName, toAdd);
			}

	}

	/**
	 * @return the instance
	 */
	public static TerminalCommandManager getInstance() {
		if (instance == null)
			instance = new TerminalCommandManager();
		return instance;
	}

	public boolean checkCommand(String cmdName, CommandSender sender) {
		TerminalCommand cmd = commands.get(cmdName);
		if (cmd == null)
			return false;

		return cmd.permCheck(sender, false);
	}

	public void reloadScripts() {
		File scripts = FilesManager.getInstance().getInnerFile("scripts.yml", "scripts");
		File workingDir = scripts.getParentFile();
		Configuration conf = new Configuration(scripts);
		conf.load();
		commands.clear();
		TerminalCommand toAdd;
		if (System.getProperty("os.name").contains("Windows"))
			for (String cmdName : conf.getKeys()) {
				toAdd = new WindowsTerminalCommand(cmdName, conf.getString(cmdName + ".exec"),
						conf.getString(cmdName + ".args"), workingDir);
				toAdd.setBukkitPerm(PermissionManager.getInstance().addOnTheFly(
						"admincmd.server.exec." + cmdName, "admincmd.server.exec.*"));
				commands.put(cmdName, toAdd);

			}

		else

			for (String cmdName : conf.getKeys()) {
				toAdd = new UnixTerminalCommand(cmdName, conf.getString(cmdName + ".exec"),
						conf.getString(cmdName + ".args"), workingDir);
				toAdd.setBukkitPerm(PermissionManager.getInstance().addOnTheFly(
						"admincmd.server.exec." + cmdName, "admincmd.server.exec.*"));
				commands.put(cmdName, toAdd);
			}
	}

	/**
	 * Execute the script
	 * 
	 * @param sender
	 * @param cmdName
	 * @return
	 * @throws CommandNotFound
	 */
	public boolean execute(CommandSender sender, String cmdName, boolean reload)
			throws CommandNotFound {
		TerminalCommand cmd = commands.get(cmdName);
		if (cmd == null || reload) {
			File scripts = FilesManager.getInstance().getInnerFile("scripts.yml", "scripts");
			File workingDir = scripts.getParentFile();
			Configuration conf = new Configuration(scripts);
			conf.load();
			if (conf.getProperty(cmdName) == null)
				throw new CommandNotFound(cmdName + " is not registered");
			if (System.getProperty("os.name").contains("Windows")) {
				commands.put(
						cmdName,
						new WindowsTerminalCommand(cmdName, conf.getString(cmdName + ".exec"), conf
								.getString(cmdName + ".args"), workingDir));
				cmd = commands.get(cmdName);
				cmd.setBukkitPerm(PermissionManager.getInstance().addOnTheFly(
						"admincmd.server.exec." + cmdName, "admincmd.server.exec.*"));
			} else {
				commands.put(
						cmdName,
						new UnixTerminalCommand(cmdName, conf.getString(cmdName + ".exec"), conf
								.getString(cmdName + ".args"), workingDir));
				cmd = commands.get(cmdName);
				cmd.setBukkitPerm(PermissionManager.getInstance().addOnTheFly(
						"admincmd.server.exec." + cmdName, "admincmd.server.exec.*"));
			}
		}
		if (!cmd.permCheck(sender, true))
			return false;
		cmd.execute(sender);
		return true;
	}

	public final Set<String> getCommandList() {
		return commands.keySet();
	}

}
