package com.lucas.specterdrops.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {
	@SuppressWarnings("rawtypes")
	public static void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay,
				fadeOut);
		Sender.sendPacket((Packet) packet, p);
		if (title != null) {
			packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
					IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
			Sender.sendPacket((Packet) packet, p);
		}
		if (subtitle != null) {
			packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
					IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"));
			Sender.sendPacket((Packet) packet, p);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void sendTitle(Player p, String title, int fadeIn, int stay, int fadeOut) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay,
				fadeOut);
		Sender.sendPacket((Packet) packet, p);
		if (title != null) {
			packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
					IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
			Sender.sendPacket((Packet) packet, p);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void sendTitle(Player p, String title, String subtitle) {
		if (title != null)
			Sender.sendPacket((Packet) new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
					IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}")), p);
		if (subtitle != null)
			Sender.sendPacket((Packet) new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
					IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}")), p);

	}

	@SuppressWarnings("rawtypes")
	public static void sendTitleTime(Player p, int fadeIn, int stay, int fadeOut) {
		Sender.sendPacket(
				(Packet) new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut),
				p);
	}

	@SuppressWarnings("rawtypes")
	public static void resetTitle(Player p) {
		Sender.sendPacket((Packet) new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null), p);
	}

	@SuppressWarnings("rawtypes")
	public static void ActionBar(String text, Player p) {
		PacketPlayOutChat packet = new PacketPlayOutChat(
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
		(((CraftPlayer) p).getHandle()).playerConnection.sendPacket((Packet) packet);
	}
}
