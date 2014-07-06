package de.inventivegames.utils.debug;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.nrubin29.pastebinapi.CreatePaste;
import me.nrubin29.pastebinapi.ExpireDate;
import me.nrubin29.pastebinapi.Format;
import me.nrubin29.pastebinapi.PastebinAPI;
import me.nrubin29.pastebinapi.PastebinException;
import me.nrubin29.pastebinapi.PrivacyLevel;

import org.bukkit.plugin.Plugin;

import de.inventivegames.utils.IGUtils;

public class Debug {

	private Map<String, String>	values	= new HashMap<String, String>();

	private String				apiKey	= "";

	private IGUtils				utils;

	private String				pasteURL;
	
	private String pasteID;

	public Debug(IGUtils utils) {
		this.utils = utils;
		values.put("PLUGIN", utils.plugin.getName());
		values.put("PLUGIN_VERSION", utils.plugin.getDescription().getVersion());
		values.put("SYSTEM_JAVA_VERSION", "" + utils.JAVA_VERSION);
		values.put("SERVER_VERSION", utils.plugin.getServer().getVersion());
		values.put("SERVER_BUKKIT_VERSION", utils.plugin.getServer().getBukkitVersion());

		String plugins = "";
		for (Plugin pl : utils.plugin.getServer().getPluginManager().getPlugins()) {
			plugins += pl.getName() + "|";
		}
		values.put("INSTALLED_PLUGINS", plugins);

	}

	
	
	public String send() {
		
		this.apiKey = IGUtils.utils.getAPIKey();

		if((this.apiKey == null) || (this.apiKey.isEmpty()) || (this.apiKey.contains("PASTE YOUR API KEY HERE"))) {
			return "§cMissing Pastebin API Key! Please provide one in the configuration file at plugins/IGUtils/config.yml";
		}
		
		PastebinAPI api = new PastebinAPI(this.apiKey);
		//
		//		ArrayList<String> line1List = new ArrayList<String>(values.keySet());
		//		ArrayList<String> line2List = new ArrayList<String>(values.values());

		String lines = "";

		//		for(int i = 0; i < line1List.size(); i++) {
		//			lines += line1List.get(i) + "=" + line2List.get(i) + "\n";
		//		}

		for (Map.Entry<String, String> entry : values.entrySet()) {
			lines += entry.getKey() + " = " + entry.getValue() + "\n";
		}

		CreatePaste paste = api.createPaste()
				.withExpireDate(ExpireDate.ONE_MONTH)
				.withFormat(Format.None)
				.withPrivacyLevel(PrivacyLevel.UNLISTED)
				.withName("IGUtils_Debug_" + utils.CURRENT_DATE)
				.withText(lines);
		String url = "NULL";
		try {
			url = paste.post();
		} catch (PastebinException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.pasteID = url.replace("http://pastebin.com/", "");
		this.pasteURL = "http://www.inventivegames.de/quickreport?id=" + pasteID;
		
		return "§2Successfully sent Report.";
	}
	
	public String getURL() {
		return this.pasteURL;
	}
	
	public String getReportURL() {
		return "http://www.inventivegames.de/report?pl=" + values.get("PLUGIN") + "&qr=" + this.pasteURL;
	}

}
