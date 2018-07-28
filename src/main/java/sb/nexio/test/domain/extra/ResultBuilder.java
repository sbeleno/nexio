package sb.nexio.test.domain.extra;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class ResultBuilder {
	
	private Map<String, Object> playload = new HashMap<>();

	public ResultBuilder(String key, String value) {
		playload.put(key, value);
	}

	public ResultBuilder put(String key, Object value) {
		this.playload.put(key, value);
		return this;
	}

	public String getPlayload() {
		try {
			return new Gson().toJson(playload);
		} catch (Exception e) {
			return "";
		}
	}


}
