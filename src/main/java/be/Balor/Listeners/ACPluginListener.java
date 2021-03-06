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
package be.Balor.Listeners;

import info.somethingodd.bukkit.OddItem.OddItem;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import be.Balor.Manager.Permissions.PermissionManager;
import be.Balor.Tools.Utils;
import be.Balor.bukkit.AdminCmd.AdminCmd;

import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class ACPluginListener extends ServerListener {

	@Override
	public void onPluginEnable(PluginEnableEvent event) {
		if (!PermissionManager.isPermissionsExSet()) {
			Plugin Permissions = AdminCmd.getBukkitServer().getPluginManager()
					.getPlugin("PermissionsEx");
			if (Permissions != null) {
				if (Permissions.isEnabled())
					PermissionManager.setPEX(PermissionsEx.getPermissionManager());
			}
		}
		if (!PermissionManager.isYetiPermissionsSet()) {
			Plugin Permissions = AdminCmd.getBukkitServer().getPluginManager()
					.getPlugin("Permissions");
			if (Permissions != null) {
				if (Permissions.isEnabled())
					PermissionManager.setYetiPermissions(((Permissions) Permissions).getHandler());
			}
		}
		if (Utils.oddItem == null) {
			Plugin items = AdminCmd.getBukkitServer().getPluginManager().getPlugin("OddItem");
			if (items != null && items.isEnabled()) {
				Utils.oddItem = (OddItem) items;
				System.out.print("[AdminCmd] Successfully linked to OddItem");
			}
		}
	}
}