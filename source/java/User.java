import com.google.gson.*;
public class User {
    private String username; // user's username
    private String salt; // salt dedicated to this user
    private String hash; // Hashed password

    // Default case
    public User() {
        username = "USERNAME";
        salt = "SALT";
        hash = "HASH";
    }

    // User creation case
    public User(String username, String salt, String hash) {
        this.username = username;
        this.salt = salt;
        this.hash = hash;
    }

    // Getters for a couple of varaibles
    public String getSalt() { return salt; }
    public String getUsername() { return username; }
    public String getHash() { return hash; }

    // serialize: Serialize this user into a JsonObject.
    JsonObject serialize() {
        JsonObject json = new JsonObject();
		json.addProperty("username", username);
		json.addProperty("salt", salt);
        json.addProperty("hash", hash);
		return json;
    }

    // deserialize: Deserialize this user from a JsonObject.
    static User deserialize(JsonObject json) {
        return new User(json.get("username").getAsString(), json.get("salt").getAsString(), json.get("hash").getAsString());
    }
}
