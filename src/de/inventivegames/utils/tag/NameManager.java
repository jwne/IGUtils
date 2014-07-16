package de.inventivegames.utils.tag;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Preconditions;

/**
 * Represents a name manager.
 * 
 * @author Kristian
 */
public abstract class NameManager {
	protected ProtocolManager	manager;

	// Listeners
	protected PacketAdapter		packetListener;

	// The parent plugin
	protected final Plugin		plugin;

	/**
	 * Construct a new player renamer.
	 * 
	 * @param plugin - the plugin.
	 */
	public NameManager(Plugin plugin) {
		this.plugin = Preconditions.checkNotNull(plugin, "plugin cannot be NULL.");
	}

	/**
	 * Start the player renamer component.
	 * 
	 * @param priority - the priority of our player modification.
	 */
	public void start(ListenerPriority priority) {
		if (isStarted())
			throw new IllegalStateException("Cannot start renamer twice.");
		registerProtocolLib(priority);
	}

	private void registerProtocolLib(ListenerPriority priority) {
		this.manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(packetListener = new PacketAdapter(plugin, priority, PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
			@Override
			public void onPacketSending(PacketEvent event) {
				Player observed = (Player) event.getPacket().getEntityModifier(event).read(0);

				if (observed == null)
					return;

				if (observed.getName().startsWith(" "))
					return;

				// Determine if we are
				if (MinecraftReflection.isUsingNetty()) {
					handleNetty(event, observed);
				} else {
					handleLegacy(event, observed);
				}
			}
		});
	}

	/**
	 * Process a spawn player packet in version 1.7.2 and above.
	 * 
	 * @param event - the packet event.
	 * @param observed - the observed player entity.
	 */
	private void handleNetty(PacketEvent event, Player observed) {
		WrappedGameProfile profile = event.getPacket().getGameProfiles().read(0);
		String name = handleName(event.getPlayer(), observed, profile.getName());

		//		profile.withId("bcd2033c-63ec-4bf8-8aca-680b22461340");

		// Update the displayed name
		if (name != null) {
			//			System.out.println(event.getPacket().getGameProfiles());
			event.getPacket().getGameProfiles().write(0, profile.withName(StringUtils.abbreviate(name, 16))/*.withId("bcd2033c-63ec-4bf8-8aca-680b22461340")*/);
		}
	}

	// As above, only for 1.6.4 and below
	private void handleLegacy(PacketEvent event, Player observed) {
		String name = handleName(event.getPlayer(), observed, event.getPacket().getStrings().read(0));

		// As above
		if (name != null) {
			event.getPacket().getStrings().write(0, StringUtils.abbreviate(name, 16));
		}
	}

	/**
	 * Trigger an entity update.
	 * 
	 * @param client - the client that will recieve the update.
	 * @param observerEntity - the observed entity to update.
	 */
	protected void updateEntity(Player client, Player observerEntity) {
		// A list of the players we will update
		List<Player> trackers = client != null ? Arrays.asList(client) : manager.getEntityTrackers(observerEntity);

		manager.updateEntity(observerEntity, trackers);
	}

	/**
	 * Invoked when we're intercepting the name of an observed entity.
	 * 
	 * @param client - the client that is receiving the name.
	 * @param observedEntity - the entity whose name we are sending.
	 * @param observedName - the current name of the entity.
	 * @return The new name of the entity. Use NULL for no change.
	 */
	public abstract String handleName(Player client, Player observedEntity, String observedName);

	/**
	 * Retrieve the owner plugin.
	 * 
	 * @return The owner.
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * Determine if we're currently renaming players.
	 * 
	 * @return TRUE if we are, FALSE otherwise.
	 */
	public boolean isStarted() {
		return packetListener != null;
	}

	public void close() {
		if (packetListener != null) {
			manager.removePacketListener(packetListener);
			packetListener = null;
			packetListener = null;
		}
	}
}