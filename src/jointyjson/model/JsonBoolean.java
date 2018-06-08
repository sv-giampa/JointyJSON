
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

/**
 * Defines the boolean type for JSON
 * @author Salvatore Giampa'
 *
 */
public class JsonBoolean implements JsonElement {
	private static JsonBoolean TRUE = new JsonBoolean(true);
	private static JsonBoolean FALSE = new JsonBoolean(false);
	
	/**
	 * Gets the JSON boolean singleton corresponding to the specified Java boolean value
	 * @param value the java boolean value
	 * @return the corresponding JsonBoolean singleton
	 */
	public static JsonBoolean getInstance(boolean value) {
		if(value)
			return TRUE;
		return FALSE;
	}
	
	private boolean value;
	
	private JsonBoolean(boolean value) {
		this.value = value;
	}
	
	@Override
	public void accept(JsonVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Gets the Java boolean value corresponding to this JSON boolean object
	 * @return the corresponding Java boolean value
	 */
	public boolean getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "" + value;
	}

	@Override
	public String toEncodedString() {
		return "" + value;
	}

}
