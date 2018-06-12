
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

package jointyjson.compiler;

import java.util.List;

import jointyc.analysis.parser.SyntaxTree;
import jointyc.analysis.semantic.Interpreter;
import jointyc.analysis.semantic.annotation.NoBufferClear;
import jointyc.analysis.semantic.annotation.NonTerminalToken;
import jointyc.analysis.semantic.annotation.TerminalToken;
import jointyc.analysis.semantic.exception.SemanticException;
import jointyjson.model.JsonArray;
import jointyjson.model.JsonBoolean;
import jointyjson.model.JsonElement;
import jointyjson.model.JsonNull;
import jointyjson.model.JsonNumber;
import jointyjson.model.JsonObject;
import jointyjson.model.JsonString;

/**
 * Defines the JSON interpreter that builds the model
 * @author Salvatore Giampa'
 *
 */
public class JsonInterpreter implements Interpreter {

	@TerminalToken(type="json.number")
	public JsonNumber number(SyntaxTree tree) {
		return new JsonNumber(Double.valueOf(tree.token()));
	}
	
	@TerminalToken(type="json.unicode")
	public String unicode(SyntaxTree tree) {
		return tree.token();
	}
	
	@TerminalToken(type="json.ctrlQuote")
	public String ctrlQuote() {
		return "\"";
	}
	
	@TerminalToken(type="json.ctrlBackSlash")
	public String ctrlBackSlash() {
		return "\\";
	}
	
	@TerminalToken(type="json.ctrlSlash")
	public String ctrlSlash() {
		return "/";
	}
	
	@TerminalToken(type="json.ctrlBackSpace")
	public String ctrlBackSpace() {
		return "\b";
	}
	
	@TerminalToken(type="json.ctrlFormFeed")
	public String ctrlFormFeed() {
		return "\f";
	}
	
	@TerminalToken(type="json.ctrlNewLine")
	public String ctrlNewLine() {
		return "\n";
	}
	
	@TerminalToken(type="json.ctrlCarriageRetun")
	public String ctrlCarriageRetun() {
		return "\r";
	}
	
	@TerminalToken(type="json.ctrlHorizontalTab")
	public String ctrlHorizontalTab() {
		return "\t";
	}
	
	@TerminalToken(type="json.ctrlHex")
	public String ctrlHex(SyntaxTree tree) {
		String hexCode = tree.token().substring(2);
		char hexChar = (char)(int)Integer.valueOf(hexCode, 16);
		return "" + hexChar;
	}
	
	@TerminalToken(type="json.null")
	public JsonNull jsonNull() {
		return JsonNull.getInstance();
	}
	
	@TerminalToken(type="json.boolean")
	public JsonBoolean jsonBoolean(SyntaxTree tree) {
		boolean bool = Boolean.parseBoolean(tree.token());
		return JsonBoolean.getInstance(bool);
	}
	
	@NonTerminalToken(ruleHead="json.object")
	public JsonObject jsonObject(JsonElement... keyValues) {
		JsonObject obj = new JsonObject();
		for(int i=0; i<keyValues.length; i+=2) {
			//<name 
			JsonString name = (JsonString) keyValues[i];
			
			//, value>
			JsonElement value = (JsonElement) keyValues[i+1];
			
			//add the <name, value> to the new json object
			obj.put(name.toString(), value);
		}
		return obj;
	}
	
	@NonTerminalToken(ruleHead="json.array")
	public JsonArray jsonArray(JsonElement... elements) {
		JsonArray array = new JsonArray();
		for(JsonElement e : elements) {
			//add the value to the array
			array.add(e);
		}
		return array;
	}
	
	@NonTerminalToken(ruleHead="json.string")
	public JsonString jsonString(String... chars) {
		// the results buffer will contain a list of strings to be concatenated
		StringBuilder sb = new StringBuilder();
		
		for(String c : chars)
			sb.append(c);
		return JsonString.get(sb.toString());
	}
}
