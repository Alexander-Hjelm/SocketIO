package SocketIO;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
	// Add more message properties here, as needed...
	public Type type;
	public String id;
	public String data;
	
	public enum Type {
		// Add more message types here, as needed...
		TYPE_A,
		TYPE_B,
		TYPE_C
	}
	
	public Message(Type type, String id, String data) {
		this.type = type;
		this.id = id;
		this.data = data;
	}
	
	public Message() {}
	
	public JSONObject ToJson() throws JSONException {
		JSONObject obj = new JSONObject();
        obj.put("type", type.name());
        obj.put("id", id);
        obj.put("data", data);
        return obj;
	}
	
	public static Message FromJson(JSONObject obj) throws JSONException {
		return new Message(Type.valueOf((String)obj.get("type")), (String)obj.get("id"),(String) obj.get("data"));
	}
	
}
