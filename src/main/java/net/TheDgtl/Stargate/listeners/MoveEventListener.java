package net.TheDgtl.Stargate.listeners;

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.jetbrains.annotations.NotNull;

import net.TheDgtl.Stargate.Stargate;
import net.TheDgtl.Stargate.portal.GateStructure;
import net.TheDgtl.Stargate.portal.Network;
import net.TheDgtl.Stargate.portal.Network.Portal;
import net.TheDgtl.Stargate.portal.SGLocation;

public class MoveEventListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerMove(@NotNull PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();
		
		//Check if player really moved
		if (to == null || from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY()
				&& from.getBlockZ() == to.getBlockZ()) {
			return;
		}
		Stargate.log(Level.FINEST, "checking if in portal");
		Portal portal = Network.getPortal(new SGLocation(to), GateStructure.Type.IRIS);

		if (portal == null || !portal.isOpen())
			return;
		Player player = event.getPlayer();
		if(!portal.isOpenFor(player)) {
			//TODO send deny message, teleport to this portals exit
		}
		
		// check perm's
		Portal desti = portal.getDestination();
		Location exit = desti.getExit();
		Stargate.log(Level.FINEST, "Teleporting " + player.getName() + " to " + exit.toString());
		player.teleport(exit);
		desti.close();
		portal.close();
	}
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent event) {
		//same thing as for player
		//low priority implementation
	}
}
