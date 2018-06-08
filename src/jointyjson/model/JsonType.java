
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
 * Defines an enumerator class for the JSON types. It is used to query the a JsonObject for a field type.
 * @author Salvatore Giampa'
 *
 */
public enum JsonType {
	ARRAY, OBJECT, STRING, NUMBER, BOOLEAN, NULL, UNKNOWN
}
