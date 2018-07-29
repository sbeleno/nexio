package sb.nexio.test.domain.extra;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Outil qui permet de créer une réponse json 
 * à partir des clés et des valeurs.
 * @author Shirley Beleno
 *
 */
public class ResultBuilder {
	
	private Map<String, Object> playload = new HashMap<>();

	public ResultBuilder(String key, String value) {
		playload.put(key, value);
	}

	public ResultBuilder put(String key, Object value) {
		this.playload.put(key, value);
		return this;
	}

	/**
	 * Permet de convertir le map des clés et 
	 * des valeurs à Json au moyen de l'objet Gson
	 * @return 
	 */
	public String getPlayload() {
		try {
			return new Gson().toJson(playload);
		} catch (Exception e) {
			return "";
		}
	}


}
