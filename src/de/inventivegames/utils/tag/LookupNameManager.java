package de.inventivegames.utils.tag;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.events.ListenerPriority;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

public class LookupNameManager extends NameManager {
	// Observing Client -> Entity -> Name
	private Table<String, String, String>	clientName	= HashBasedTable.create();
	// Fallback name in case the per-client name is missing
	private Map<String, String>				globalName	= Maps.newHashMap();

	// Remove outdated entries
	protected boolean						autoCleanup	= true;
	protected Listener						bukkitListener;

	/**
	 * Construct a new lookup handler.
	 * 
	 * @param manager - the name manager.
	 */
	public LookupNameManager(Plugin plugin) {
		super(plugin);
	}

	/**
	 * Start the handler and the associated name mananger.
	 * 
	 * @param priority - the listener priority.
	 */
	@Override
	public void start(ListenerPriority priority) {
		super.start(priority);
		registerBukkit();
	}

	private void registerBukkit() {
		Plugin owner = getPlugin();
		owner.getServer().getPluginManager().registerEvents(bukkitListener = new Listener() {
			@EventHandler
			public void onPlayerQuit(PlayerQuitEvent e) {
				if (!autoCleanup)
					return;

				// Clean up table
				String removed = e.getPlayer().getName();
				clientName.rowKeySet().remove(removed);
				clientName.columnKeySet().remove(removed);
				globalName.remove(removed);
			}
		}, owner);
	}

	/**
	 * Invoked when we're intercepting the name of an observed entity.
	 * 
	 * @param client - the client that is receiving the name.
	 * @param observedEntity - the entity whose name we are sending.
	 * @param observedName - the current name of the entity.
	 * @return The new name of the entity. Use NULL for no change.
	 */
	@Override
	public final String handleName(Player client, Player observedEntity, String observedName) {
		String changed = clientName.get(client.getName(), observedEntity.getName());

		// Fall back to the global name change
		if (changed == null) {
			changed = globalName.get(observedEntity.getName());
		}
		return changed;
	}

	/**
	 * Set the global name of a given entity.
	 * <p>
	 * This will be overridden by {@link #setClientName(Player, Player, String)} for individual clients.
	 * 
	 * @param observedEntity - the player entity to rename.
	 * @param observedName - the new name of this entity.
	 * @return The previous altered name, or NULL.
	 */
	public String setGlobalName(Player observedEntity, String observedName) {
		checkGlobal(observedEntity);
		String original = globalName.put(observedEntity.getName(), observedName);

		// See if we need to update the entity
		if (!Objects.equal(original, observedName)) {
			updateEntity(null, observedEntity);
		}
		return original;
	}

	/**
	 * Set the visible name of a player entity for a given client.
	 * 
	 * @param client - the client that will see the name change.
	 * @param observedEntity - the player entity to rename.
	 * @param observedName - the new name of this entity.
	 * @return The previous altered name, or NULL.
	 */
	public String setClientName(Player client, Player observedEntity, String observedName) {
		checkClient(client, observedEntity);
		String original = clientName.put(client.getName(), observedEntity.getName(), observedName);

		// See if we need to update the entity
		if (!Objects.equal(original, observedName)) {
			updateEntity(client, observedEntity);
		}
		return original;
	}

	/**
	 * Reset the visible name of an entity to the default globally.
	 * 
	 * @param observedEntity - the entity whose name will be reset.
	 * @return The removed custom name, or NULL.
	 */
	public String resetGlobalName(Player observedEntity) {
		checkGlobal(observedEntity);
		String removed = globalName.remove(observedEntity.getName());

		// Trigger a name update
		if (removed != null) {
			updateEntity(null, observedEntity);
		}
		return removed;
	}

	/**
	 * Reset the visible name of an entity to the default for a client.
	 * 
	 * @param client - the client we're resetting.
	 * @param observedEntity - the entity whose name will be reset.
	 * @return The removed custom name, or NULL.
	 */
	public String resetClientName(Player client, Player observedEntity) {
		checkClient(client, observedEntity);
		String removed = clientName.remove(client.getName(), observedEntity.getName());

		// Trigger a name update
		if (removed != null) {
			updateEntity(client, observedEntity);
		}
		return removed;
	}

	// Throw on NULL
	private void checkGlobal(Player observedEntity) {
		if (!isStarted())
			throw new IllegalStateException("Name mananger hasn't started yet.");
		Preconditions.checkNotNull(observedEntity, "observedEntity cannot be NULL");
	}

	// Here too
	private void checkClient(Player client, Player observedEntity) {
		Preconditions.checkNotNull(client, "client cannot be NULL");
		checkGlobal(observedEntity);
	}

	/**
	 * Determine if this handler has started.
	 * 
	 * @return TRUE if it has, FALSE otherwise.
	 */
	@Override
	public boolean isStarted() {
		return bukkitListener != null && super.isStarted();
	}

	/**
	 * Determine if we automatically remove logged out entires.
	 * 
	 * @return TRUE if we do, FALSE otherwise.
	 */
	public boolean isAutoCleanup() {
		return autoCleanup;
	}

	/**
	 * Set whether or not we automatically remove entries of logged out players.
	 * 
	 * @param autoCleanup - TRUE to auto-clean, FALSE otherwise.
	 */
	public void setAutoCleanup(boolean autoCleanup) {
		this.autoCleanup = autoCleanup;
	}

	/**
	 * Close the current lookup handler and the associated manager.
	 */
	@Override
	public void close() {
		if (isStarted()) {
			HandlerList.unregisterAll(bukkitListener);
			super.close();
			bukkitListener = null;
		}
	}
}