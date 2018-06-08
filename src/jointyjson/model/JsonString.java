
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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class JsonString implements JsonElement, Comparable<JsonString> {
	private static final int FLYWEIGHT_MAX_SIZE = 5000;
	private static LinkedHashMap<String, JsonString> flyweight = new LinkedHashMap<String, JsonString>() {
		protected boolean removeEldestEntry(Map.Entry<String,JsonString> eldest) {
			return size() > FLYWEIGHT_MAX_SIZE;
		};
	};
	
	public static JsonString get(String str) {
		JsonString jstr = flyweight.get(str);
		
		if(jstr == null) {
			jstr = new JsonString(str);
			flyweight.put(str, jstr);
		}
		return jstr;
	}
	
	
	private String value;
	
	private JsonString(String value) {
		this.value = value;
	}
	
	public void set(String value) {
		if(value==null)
			throw new NullPointerException("value cannot be null");
		if(value.contains("'") && value.contains("\""))
			throw new IllegalArgumentException("A json string can contain characters \" or characters ' alternatively");
		this.value = value;
	}
	
	@Override
	public void accept(JsonVisitor visitor) {
		visitor.visit(this);
	}
	
	public String toEncodedString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<value.length(); i++) {
			char current = value.charAt(i);
			
			switch(current) {
			case '"':
				sb.append("\\\"");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				if(current < 32 || current >= 127) {
					String code = Integer.toHexString(current);
					int padding = 4 - code.length();
					sb.append("\\u");
					for(int p=0; p<padding; p++)
						sb.append("0");
					sb.append(code);
				} 
				else sb.append(current);
			}
			
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonString other = (JsonString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public int compareTo(JsonString other) {
		return value.compareTo(other.value);
	}
	
	

}
