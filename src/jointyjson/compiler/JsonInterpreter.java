
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
class JsonInterpreter implements Interpreter {
	
	@Override
	public Object terminal(SyntaxTree tree) throws SemanticException {
		switch(tree.type()) {
		case "json.number":
			return new JsonNumber(Double.valueOf(tree.token()));
		case "json.unicode":
			return tree.token();
		case "json.ctrlQuote":
			return "\"";
		case "json.ctrlBackSlash":
			return "\\";
		case "json.ctrlSlash":
			return "/";
		case "json.ctrlBackSpace":
			return "\b";
		case "json.ctrlFormFeed":
			return "\f";
		case "json.ctrlNewLine":
			return "\n";
		case "json.ctrlCarriageRetun":
			return "\r";
		case "json.ctrlHorizontalTab":
			return "\t";
		case "json.ctrlHex":
			String hexCode = tree.token().substring(2);
			char hexChar = (char)(int)Integer.valueOf(hexCode, 16);
			return "" + hexChar;
		case "json.null":
			return JsonNull.getInstance();
		case "json.boolean":
			boolean bool = Boolean.parseBoolean(tree.token());
			return JsonBoolean.getInstance(bool);
		}
		return null;
	}

	@Override
	public void nonTerminal(SyntaxTree tree, List<Object> resultsBuffer) throws SemanticException {
		if(tree.query("json.object")) {
			//the results buffer will contain a list of <name, value> couples
			
			JsonObject obj = new JsonObject();
			while(resultsBuffer.size() > 0) {
				//<name 
				JsonString name = (JsonString) resultsBuffer.remove(0);
				
				//, value>
				JsonElement value = (JsonElement) resultsBuffer.remove(0);
				
				//add the <name, value> to the new json object
				obj.put(name.toString(), value);
			}
			
			//returns the built object
			resultsBuffer.add(obj);
		}
		else if(tree.query("json.array")) {
			// the results buffer will contain a list of <value>
			JsonArray array = new JsonArray();
			while(resultsBuffer.size() > 0) {
				//<value>
				JsonElement value = (JsonElement) resultsBuffer.remove(0);
				
				//add the <value> to the array
				array.add(value);
			}
			
			//returns the built array
			resultsBuffer.add(array);
		}
		else if(tree.query("json.string")) {
			// the results buffer will contain a list of strings to be concatenated
			StringBuilder sb = new StringBuilder();
			
			for(Object o : resultsBuffer)
				sb.append((String)o);
			
			resultsBuffer.clear();
			resultsBuffer.add(JsonString.get(sb.toString()));
		}
		
		/*
		 * the following can be omitted
		 * 
		 * else if(tree.query("json.arrayTail"){
		 * 		//forward results to the upper node semantics (do nothing)
		 * }
		 * else if(...){
		 * 		//forward results to the upper node semantics (do nothing)
		 * }
		 * etc. etc. etc.
		*/
	}
}
