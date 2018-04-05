package com.zx.tjmarketmobile.http;

import java.util.HashMap;
import java.util.Map;

public class HttpParam extends BaseHttpParams<Object> {
	private Map<String, Object> map = new HashMap<String, Object>();

	@Override
	public Map<String, Object> getParams(int id) {
		return map;
	}

	public void putParams(String key, Object value) {
		map.put(key, value);
	}
	public void clear(){
		map.clear();
	}
}
