public class User {
    String username; // user's username
    String salt; // salt dedicated to this user
    String password; // Hashed password

    // Default case
    public User() {
        username = "USERNAME";
        salt = "SALT";
        password = "PASSWORD";
    }

    // User creation case
    public User(String username, String salt, String password) {
        this.username = username;
        this.salt = salt;
        this.password = password;
    }

    public boolean passwordCheck(String other) {
        if (password == other) {
            return true;
        }
        return false;
    }
    public String getSalt() { return salt; }
    public String getUsername() { return username; }
}
