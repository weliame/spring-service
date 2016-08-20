/**
 *
 */
package com.mytest.app.common;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * @author yqwu
 *
 */
public class JsonUtil {

	// public static String merge(String jsonStr1, String jsonStr2) {
	// Gson gson = new Gson();
	// JsonElement jsonElement = new JsonParser().parse(jsonStr1);
	// jsonElement.getAsJsonObject().a
	// return gson.toJson(jsonElement);
	// }

	public static void merge(ObjectNode primary, ObjectNode backup) {
		Iterator<String> fieldNames = backup.fieldNames();
		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			JsonNode primaryValue = primary.get(fieldName).deepCopy();
			if (primaryValue == null) {
				JsonNode backupValue = backup.get(fieldName);
				primary.set(fieldName, backupValue);
			} else if (primaryValue.isObject()) {
				JsonNode backupValue = backup.get(fieldName);
				if (backupValue.isObject()) {
					merge(	(ObjectNode) primaryValue,
							(ObjectNode) backupValue.deepCopy());
				}
			}
		}
	}
}
