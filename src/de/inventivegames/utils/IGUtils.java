package de.inventivegames.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.inventivegames.utils.debug.Debug;
import de.inventivegames.utils.event.PlayerEvent;

public class IGUtils {

	public Plugin				plugin;
	public double				JAVA_VERSION;
	public String				CURRENT_DATE;

	public File					configFile;
	public YamlConfiguration	config;

	public static IGUtils		utils;

	public IGUtils(JavaPlugin plugin) {

		utils = this;
		utils.plugin = plugin;
		utils.JAVA_VERSION = getJavaVersion();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		utils.CURRENT_DATE = dateFormat.format(cal.getTime());

		this.configFile = new File("plugins/IGUtils/config.yml");
		if (!(configFile.exists())) {
			try {
				(new File("plugins/IGUtils/")).mkdirs();
				configFile.createNewFile();
				System.out.println("Creating new File: " + this.configFile);

				this.config = YamlConfiguration.loadConfiguration(configFile);

				this.config.addDefault("PasteBin_APIKey", "PASTE YOUR API KEY HERE.");
				this.config.addDefault("broadcastJoin", true);
				this.config.options().copyDefaults(true);

				this.config.save(this.configFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.config = YamlConfiguration.loadConfiguration(configFile);

		setupBukkit();
	}

	public void setupBukkit() {
		this.plugin.getServer().getPluginManager().registerEvents(new PlayerEvent(this), this.plugin);
	}

	static double getJavaVersion() {
		String version = System.getProperty("java.version");
		int pos = 0, count = 0;
		for (; pos < version.length() && count < 2; pos++) {
			if (version.charAt(pos) == '.') {
				count++;
			}
		}

		--pos; //EVALUATE double

		double dversion = Double.parseDouble(version.substring(0, pos));
		return dversion;

	}

	public Debug getDebug() {
		return new Debug(this);
	}
	
	public PlayerUtils getPlayerUtils() {
		return new PlayerUtils(this);
	}
	
	public ChatUtils getChatUtils() {
		return new ChatUtils(this);
	}

	//	public XPTimer getXPTimer() {
	//		return (new XPTimer(this));
	//	}

	public String getAPIKey() {
		return this.config.getString("PasteBin_APIKey");
	}

}
