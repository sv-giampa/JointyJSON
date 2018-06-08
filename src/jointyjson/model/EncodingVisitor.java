
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
 * Defines a visitor implementation to encode the JSON structure in the corresponding JSON string.
 * @author Salvatore Giampa'
 *
 */
public class EncodingVisitor implements JsonVisitor {
	
	private StringBuilder sb = new StringBuilder();
	private int level = 0;
	
	@Override
	public void visit(JsonObject element) {
		sb.append("{\n");
		level++;
		boolean first = true;
		for(JsonString field : element.getFields()) {
			if(!first) sb.append(",\n");
			first = false;
			level("\"" + field.toEncodedString() + "\" : ");
			
			element.get(field.toString()).accept(this);
		}
		sb.append("\n");
		level--;
		level("}");
	}

	@Override
	public void visit(JsonArray element) {
		sb.append("[");
		level++;
		boolean first = true;
		for(JsonElement elem : element) {
			if(!first) sb.append(", ");
			first = false;
			elem.accept(this);
		}
		level--;
		sb.append("]");
	}

	@Override
	public void visit(JsonString element) {
		sb.append("\"" + element.toEncodedString() + "\"");
	}

	@Override
	public void visit(JsonNumber element) {
		sb.append(element.get());
	}
	
	private void level(String string) {
		for(int i = 0; i<level; i++) sb.append("    ");
		sb.append(string);
	}
	
	public String getJsonString() {
		String json = sb.toString();
		sb = new StringBuilder();
		return json;
	}

	@Override
	public void visit(JsonNull element) {
		sb.append("null");
	}

	@Override
	public void visit(JsonBoolean element) {
		sb.append(element.getValue());
		
	}

}
