package de.inventivegames.utils;

import java.util.Map;
import java.util.Map.Entry;

public class CMapUtils {

	private IGUtils	utils;

	public CMapUtils(IGUtils utils) {
		this.utils = utils;
	}

	@SuppressWarnings("rawtypes")
	public Object getValueByKey(Map map, Object key) {
		return map.get(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getKeyByValue(Map map, Object value) {
		//		if (utils.JAVA_VERSION == 1.8)
		//			return getKeysByValue8(map, value);
		//		else
		return getKeyByValue7(map, value);
	}

	private <T, E> T getKeyByValue7(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue()))
				return entry.getKey();
		}
		return null;
	}

	//	private <T, E> Set<T> getKeysByValue8(Map<T, E> map, E value) {
	//		return map.entrySet().stream().filter(entry -> entry.getValue().equals(value)).map(entry -> entry.getKey()).collect(Collectors.toSet());
	//	}
}
