
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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Defines an iterable heterogeneous json array
 * @author Salvatore Giampà
 *
 */
public class JsonArray implements JsonElement, Iterable<JsonElement>{
	private ArrayList<JsonElement> array = new ArrayList<>();
	
	public int size() {
		return array.size();
	}

	
	/**
	 * Adds a JsonElement to this JsonArray
	 * @param element the element to add
	 */
	public void add(JsonElement element) {
		array.add(element);
	}
	
	/**
	 * Inserts a JsonElement in this JsonArray to the specified position
	 * @param index the position where the sepcified element must be added
	 * @param element the element to add
	 */
	public void insert(int index, JsonElement element) {
		array.add(index, element);
	}
	
	/**
	 * Gets the JSON element at the specified index
	 * @param index the index of the JsonElement object to get
	 * @return the JsonElement at the specified index
	 */
	public JsonElement get(int index) {
		return array.get(index);
	}

	/**
	 * Removes the JSON element at the specified index
	 * @param index the index of the JsonElement object to remove
	 * @return the JsonElement at the specified index
	 */
	public JsonElement remove(int index) {
		return array.remove(index);
	}

	@Override
	public void accept(JsonVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Iterator<JsonElement> iterator() {
		return array.iterator();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		for(JsonElement elem : array) {
			if(!first) sb.append(", ");
			sb.append(elem);
			first = false;
		}
		sb.append("]");
		return sb.toString();
	}


	@Override
	public String toEncodedString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		for(JsonElement elem : array) {
			if(!first) sb.append(", ");
			sb.append(elem.toEncodedString());
			first = false;
		}
		sb.append("]");
		return sb.toString();
	}
}
