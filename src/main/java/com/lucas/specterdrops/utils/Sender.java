package com.lucas.specterdrops.utils;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Sender {
	@SuppressWarnings("rawtypes")
	public static void sendPacket(Packet pa, Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		(cp.getHandle()).playerConnection.sendPacket(pa);
	}
}
