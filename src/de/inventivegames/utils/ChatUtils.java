package de.inventivegames.utils;

import org.bukkit.entity.Player;

public class ChatUtils {

	private IGUtils			utils;

	private static Class<?>	nmsChatSerializer		= Reflection.getNMSClass("ChatSerializer");
	private static Class<?>	nmsPacketPlayOutChat	= Reflection.getNMSClass("PacketPlayOutChat");

	public ChatUtils(IGUtils utils) {
		this.utils = utils;
	}

	public void sendRawMessage(Player player, String message) {
		try {
			Object handle = Reflection.getHandle(player);
			Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
			Object serialized = Reflection.getMethod(nmsChatSerializer, "a", String.class).invoke(null, message);
			Object packet = nmsPacketPlayOutChat.getConstructor(Reflection.getNMSClass("IChatBaseComponent")).newInstance(serialized);
			Reflection.getMethod(connection.getClass(), "sendPacket").invoke(connection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
