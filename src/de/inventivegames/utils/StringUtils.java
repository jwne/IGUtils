package de.inventivegames.utils;

public class StringUtils {

	private IGUtils	utils;

	public StringUtils(IGUtils utils) {
		this.utils = utils;
	}

	public boolean validateLength(String s, int length) {
		if (s.length() <= length)
			return true;
		return false;
	}

	public String addS(String s) {
		return ((s.endsWith("s")) ? (s + "\'") : (s + "\'s"));
	}

	public boolean containsIgnoreCase(String s, String s1) {
		return (s.toLowerCase().contains(s1.toLowerCase()));
	}

	public boolean isNumber(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
