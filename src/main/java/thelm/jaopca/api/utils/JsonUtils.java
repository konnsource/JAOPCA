package thelm.jaopca.api.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * Vanilla JsonUtils but without Forge's SideOnly
 */
public class JsonUtils {

	/**
	 * Does the given JsonObject contain a string field with the given name?
	 */
	public static boolean isString(JsonObject json, String memberName) {
		return !isJsonPrimitive(json, memberName) ? false : json.getAsJsonPrimitive(memberName).isString();
	}

	/**
	 * Is the given JsonElement a string?
	 */
	public static boolean isString(JsonElement json) {
		return !json.isJsonPrimitive() ? false : json.getAsJsonPrimitive().isString();
	}

	public static boolean isNumber(JsonObject json, String memberName) {
		return !isJsonPrimitive(json, memberName) ? false : json.getAsJsonPrimitive(memberName).isNumber();
	}

	public static boolean isNumber(JsonElement json) {
		return !json.isJsonPrimitive() ? false : json.getAsJsonPrimitive().isNumber();
	}

	public static boolean isBoolean(JsonObject json, String memberName) {
		return !isJsonPrimitive(json, memberName) ? false : json.getAsJsonPrimitive(memberName).isBoolean();
	}

	/**
	 * Does the given JsonObject contain an array field with the given name?
	 */
	public static boolean isJsonArray(JsonObject json, String memberName) {
		return !hasField(json, memberName) ? false : json.get(memberName).isJsonArray();
	}

	/**
	 * Does the given JsonObject contain a field with the given name whose type is primitive (String, Java primitive, or
	 * Java primitive wrapper)?
	 */
	public static boolean isJsonPrimitive(JsonObject json, String memberName) {
		return !hasField(json, memberName) ? false : json.get(memberName).isJsonPrimitive();
	}

	/**
	 * Does the given JsonObject contain a field with the given name?
	 */
	public static boolean hasField(JsonObject json, String memberName) {
		return json == null ? false : json.get(memberName) != null;
	}

	/**
	 * Gets the string value of the given JsonElement.  Expects the second parameter to be the name of the element's
	 * field if an error message needs to be thrown.
	 */
	public static String getString(JsonElement json, String memberName) {
		if(json.isJsonPrimitive()) {
			return json.getAsString();
		}
		else {
			throw new JsonSyntaxException("Expected "+memberName+" to be a string, was "+toString(json));
		}
	}

	/**
	 * Gets the string value of the field on the JsonObject with the given name.
	 */
	public static String getString(JsonObject json, String memberName) {
		if(json.has(memberName)) {
			return getString(json.get(memberName), memberName);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName+", expected to find a string");
		}
	}

	/**
	 * Gets the string value of the field on the JsonObject with the given name, or the given default value if the field
	 * is missing.
	 */

	public static String getString(JsonObject json, String memberName, String fallback) {
		return json.has(memberName) ? getString(json.get(memberName), memberName) : fallback;
	}

	/**
	 * Gets the boolean value of the given JsonElement.  Expects the second parameter to be the name of the element's
	 * field if an error message needs to be thrown.
	 */
	public static boolean getBoolean(JsonElement json, String memberName) {
		if(json.isJsonPrimitive()) {
			return json.getAsBoolean();
		}
		else {
			throw new JsonSyntaxException("Expected "+memberName+" to be a Boolean, was "+toString(json));
		}
	}

	/**
	 * Gets the boolean value of the field on the JsonObject with the given name.
	 */

	public static boolean getBoolean(JsonObject json, String memberName) {
		if(json.has(memberName)) {
			return getBoolean(json.get(memberName), memberName);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName+", expected to find a Boolean");
		}
	}

	/**
	 * Gets the boolean value of the field on the JsonObject with the given name, or the given default value if the
	 * field is missing.
	 */
	public static boolean getBoolean(JsonObject json, String memberName, boolean fallback) {
		return json.has(memberName) ? getBoolean(json.get(memberName), memberName) : fallback;
	}

	/**
	 * Gets the float value of the given JsonElement.  Expects the second parameter to be the name of the element's
	 * field if an error message needs to be thrown.
	 */
	public static float getFloat(JsonElement json, String memberName) {
		if(json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()) {
			return json.getAsFloat();
		}
		else {
			throw new JsonSyntaxException("Expected "+memberName+" to be a Float, was "+toString(json));
		}
	}

	/**
	 * Gets the float value of the field on the JsonObject with the given name.
	 */
	public static float getFloat(JsonObject json, String memberName) {
		if(json.has(memberName)) {
			return getFloat(json.get(memberName), memberName);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName+", expected to find a Float");
		}
	}

	/**
	 * Gets the float value of the field on the JsonObject with the given name, or the given default value if the field
	 * is missing.
	 */
	public static float getFloat(JsonObject json, String memberName, float fallback) {
		return json.has(memberName) ? getFloat(json.get(memberName), memberName) : fallback;
	}

	public static double getDouble(JsonElement json, String memberName) {
		if(json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()) {
			return json.getAsDouble();
		}
		else {
			throw new JsonSyntaxException("Expected "+memberName+" to be a Float, was "+toString(json));
		}
	}

	public static double getDouble(JsonObject json, String memberName) {
		if(json.has(memberName)) {
			return getDouble(json.get(memberName), memberName);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName+", expected to find a Double");
		}
	}

	public static double getDouble(JsonObject json, String memberName, double fallback) {
		return json.has(memberName) ? getDouble(json.get(memberName), memberName) : fallback;
	}

	/**
	 * Gets the integer value of the given JsonElement.  Expects the second parameter to be the name of the element's
	 * field if an error message needs to be thrown.
	 */
	public static int getInt(JsonElement json, String memberName) {
		if(json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()) {
			return json.getAsInt();
		}
		else {
			throw new JsonSyntaxException("Expected "+memberName+" to be a Int, was "+toString(json));
		}
	}

	/**
	 * Gets the integer value of the field on the JsonObject with the given name.
	 */
	public static int getInt(JsonObject json, String memberName) {
		if(json.has(memberName)) {
			return getInt(json.get(memberName), memberName);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName+", expected to find a Int");
		}
	}

	/**
	 * Gets the integer value of the field on the JsonObject with the given name, or the given default value if the
	 * field is missing.
	 */
	public static int getInt(JsonObject json, String memberName, int fallback) {
		return json.has(memberName) ? getInt(json.get(memberName), memberName) : fallback;
	}

	/**
	 * Gets the given JsonElement as a JsonObject.  Expects the second parameter to be the name of the element's field
	 * if an error message needs to be thrown.
	 */
	public static JsonObject getJsonObject(JsonElement json, String memberName) {
		if(json.isJsonObject()) {
			return json.getAsJsonObject();
		}
		else {
			throw new JsonSyntaxException("Expected "+memberName+" to be a JsonObject, was "+toString(json));
		}
	}

	public static JsonObject getJsonObject(JsonObject json, String memberName) {
		if(json.has(memberName)) {
			return getJsonObject(json.get(memberName), memberName);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName+", expected to find a JsonObject");
		}
	}

	/**
	 * Gets the JsonObject field on the JsonObject with the given name, or the given default value if the field is
	 * missing.
	 */

	public static JsonObject getJsonObject(JsonObject json, String memberName, JsonObject fallback) {
		return json.has(memberName) ? getJsonObject(json.get(memberName), memberName) : fallback;
	}

	/**
	 * Gets the given JsonElement as a JsonArray. Expects the second parameter to be the name of the element's field if
	 * an error message needs to be thrown.
	 */
	public static JsonArray getJsonArray(JsonElement json, String memberName) {
		if(json.isJsonArray()) {
			return json.getAsJsonArray();
		}
		else {
			throw new JsonSyntaxException("Expected "+memberName+" to be a JsonArray, was "+toString(json));
		}
	}

	/**
	 * Gets the JsonArray field on the JsonObject with the given name.
	 */
	public static JsonArray getJsonArray(JsonObject json, String memberName) {
		if(json.has(memberName)) {
			return getJsonArray(json.get(memberName), memberName);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName+", expected to find a JsonArray");
		}
	}

	/**
	 * Gets the JsonArray field on the JsonObject with the given name, or the given default value if the field is
	 * missing.
	 */
	public static JsonArray getJsonArray(JsonObject json, String memberName, JsonArray fallback) {
		return json.has(memberName) ? getJsonArray(json.get(memberName), memberName) : fallback;
	}

	public static <T> T deserializeClass(JsonElement json, String memberName, JsonDeserializationContext context, Type typeOfT) {
		if(json != null) {
			return context.deserialize(json, typeOfT);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName);
		}
	}

	public static <T> T deserializeClass(JsonObject json, String memberName, JsonDeserializationContext context, Type typeOfT) {
		if(json.has(memberName)) {
			return deserializeClass(json.get(memberName), memberName, context, typeOfT);
		}
		else {
			throw new JsonSyntaxException("Missing "+memberName);
		}
	}

	public static <T> T deserializeClass(JsonObject json, String memberName, T fallback, JsonDeserializationContext context, Type typeOfT) {
		return (T)(json.has(memberName) ? deserializeClass(json.get(memberName), memberName, context, typeOfT) : fallback);
	}

	/**
	 * Gets a human-readable description of the given JsonElement's type. For example: "a number (4)"
	 */
	public static String toString(JsonElement json) {
		String s = StringUtils.abbreviateMiddle(String.valueOf((Object)json), "...", 10);

		if(json == null) {
			return "null (missing)";
		}
		else if(json.isJsonNull()) {
			return "null (json)";
		}
		else if(json.isJsonArray()) {
			return "an array ("+s+")";
		}
		else if(json.isJsonObject()) {
			return "an object ("+s+")";
		}
		else {
			if(json.isJsonPrimitive()) {
				JsonPrimitive jsonprimitive = json.getAsJsonPrimitive();

				if(jsonprimitive.isNumber()) {
					return "a number ("+s+")";
				}

				if(jsonprimitive.isBoolean()) {
					return "a boolean ("+s+")";
				}
			}

			return s;
		}
	}

	public static <T> T gsonDeserialize(Gson gsonIn, Reader readerIn, Type typeOfT, boolean lenient) {
		try {
			JsonReader jsonreader = new JsonReader(readerIn);
			jsonreader.setLenient(lenient);
			return gsonIn.getAdapter((TypeToken<T>)TypeToken.get(typeOfT)).read(jsonreader);
		}
		catch(IOException ioexception) {
			throw new JsonParseException(ioexception);
		}
	}

	public static <T> T gsonDeserialize(Gson gsonIn, String json, Type typeOfT) {
		return gsonDeserialize(gsonIn, json, typeOfT, false);
	}

	public static <T> T gsonDeserialize(Gson gsonIn, String json, Type typeOfT, boolean lenient) {
		return gsonDeserialize(gsonIn, new StringReader(json), typeOfT, lenient);
	}
}