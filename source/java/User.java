public class User {
    String username; // user's username
    String salt; // salt dedicated to this user
    String hash; // Hashed password

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

    public String getSalt() { return salt; }
    public String getUsername() { return username; }
}
