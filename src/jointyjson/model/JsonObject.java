
/**
 *  Copyright 2017 Salvatore Giampà
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 **/

package jointyjson.model;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Defines a JSON object, a structure containing name-value pairs.
 * @author Salvatore Giampa'
 *
 */
public class JsonObject implements JsonElement {
	
	//name-value map
	private Map<JsonString, JsonElement> elements = new TreeMap<>();
	
	/**
	 * Put a generic JsonElement into this JsonObject
	 * @param name
	 * @param element
	 * @return
	 */
	public JsonElement put(String name, JsonElement element) {
		if(name.contains("'") && name.contains("\""))
			throw new IllegalArgumentException("A json name string can contain characters \" or characters ' alternatively");
		return elements.put(JsonString.get(name), element);
	}
	
	public JsonElement putJsonObject(String name, JsonObject object) {
		if(name.contains("'") && name.contains("\""))
			throw new IllegalArgumentException("A json name string can contain characters \" or characters ' alternatively");
		return elements.put(JsonString.get(name), object);
	}
	
	public JsonElement putJsonArray(String name, JsonArray array) {
		if(name.contains("'") && name.contains("\""))
			throw new IllegalArgumentException("A json name string can contain characters \" or characters ' alternatively");
		return elements.put(JsonString.get(name), array);
	}
	
	public JsonElement putJsonString(String name, String string) {
		if(name.contains("'") && name.contains("\""))
			throw new IllegalArgumentException("A json name string can contain characters \" or characters ' alternatively");
		return elements.put(JsonString.get(name), JsonString.get(string));
	}
	
	public JsonElement putJsonNumber(String name, Double number) {
		if(name.contains("'") && name.contains("\""))
			throw new IllegalArgumentException("A json name string can contain characters \" or characters ' alternatively");
		return elements.put(JsonString.get(name), new JsonNumber(number));
	}
	
	public JsonElement putJsonNull(String name) {
		if(name.contains("'") && name.contains("\""))
			throw new IllegalArgumentException("A json name string can contain characters \" or characters ' alternatively");
		return elements.put(JsonString.get(name), JsonNull.getInstance());
	}
	
	public JsonElement putJsonBoolean(String name, boolean value) {
		if(name.contains("'") && name.contains("\""))
			throw new IllegalArgumentException("A json name string can contain characters \" or characters ' alternatively");
		return elements.put(JsonString.get(name), JsonBoolean.getInstance(value));
	}
	
	public JsonElement get(String name) {
		return elements.get(JsonString.get(name));
	}

	public JsonObject getJsonObject(String name) {
		JsonString nm = JsonString.get(name);
		JsonElement elem = elements.get(nm);
		if(!(elem instanceof JsonObject))
			return null;
		return (JsonObject) elements.get(nm);
	}
	
	public JsonArray getJsonArray(String name) {
		JsonString nm = JsonString.get(name);
		JsonElement elem = elements.get(nm);
		if(!(elem instanceof JsonArray))
			return null;
		return (JsonArray) elements.get(nm);
	}
	
	public JsonString getJsonString(String name) {
		JsonString nm = JsonString.get(name);
		JsonElement elem = elements.get(nm);
		if(!(elem instanceof JsonString))
			return null;
		return (JsonString) elements.get(nm);
	}
	
	public JsonNumber getJsonNumber(String name) {
		JsonString nm = JsonString.get(name);
		JsonElement elem = elements.get(nm);
		if(!(elem instanceof JsonNumber))
			return null;
		return (JsonNumber) elements.get(nm);
	}
	
	public JsonNull getJsonNull(String name) {
		JsonString nm = JsonString.get(name);
		JsonElement elem = elements.get(nm);
		if(!(elem instanceof JsonNumber))
			return null;
		return (JsonNull) elements.get(nm);
	}
	
	public JsonBoolean getJsonBoolean(String name) {
		JsonString nm = JsonString.get(name);
		JsonElement elem = elements.get(nm);
		if(!(elem instanceof JsonNumber))
			return null;
		return (JsonBoolean) elements.get(nm);
	}
	
	public JsonType getType(String name) {
		JsonString nm = JsonString.get(name);
		JsonElement elem = elements.get(nm);
		if(elem == null)
			return null;
		if(elem instanceof JsonObject)
			return JsonType.OBJECT;
		if(elem instanceof JsonArray)
			return JsonType.ARRAY;
		if(elem instanceof JsonString)
			return JsonType.STRING;
		if(elem instanceof JsonNumber)
			return JsonType.NUMBER;
		if(elem instanceof JsonBoolean)
			return JsonType.BOOLEAN;
		if(elem instanceof JsonNull)
			return JsonType.NULL;
		return JsonType.UNKNOWN;
	}
	
	public Set<JsonString> getFields(){
		return Collections.unmodifiableSet(elements.keySet());
	}

	@Override
	public void accept(JsonVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for(Entry<JsonString,JsonElement> entry : elements.entrySet()) {
			sb.append("\"" + entry.getKey() + "\" : " + entry.getValue() + ",\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String toEncodedString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for(Entry<JsonString,JsonElement> entry : elements.entrySet()) {
			sb.append("\"" + entry.getKey().toEncodedString() + "\" : " + entry.getValue().toEncodedString() + ",\n");
		}
		sb.append("}");
		return sb.toString();
	}
	
	
	
}
