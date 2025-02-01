import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Auth {

    /**
     * Hashing function using SHA256 that is prefixed by a salt before hashing.
     * @param noteInput Array of integers that represent what notes were inputted during a login attempt.
     * @param salt Pre-created salt unique to each user.
     * @return String of SHA256 hash hex code.
     * @throws NoSuchAlgorithmException Throws if SHA-256 is not a valid hashing algo (It is)
     * Credit: https://www.geeksforgeeks.org/sha-256-hash-in-java/
     */
    public String hash(ArrayList<Integer> noteInput, String salt) throws NoSuchAlgorithmException {
        // Create digest for algo
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        StringBuilder noteString = new StringBuilder();

        // Concat list of ints to string, making sure that ALL ints have two digits (even 0, for < 10)
        for (Integer i : noteInput){
            if (i < 10){
                noteString.append("0"+i);
            } else{
                noteString.append(i);
            }
        }

        // Hash the concat notes to byte array
        String saltedData = salt + noteString;
        byte[] byteHash = digest.digest(saltedData.getBytes());

        // Convert bytes to signum format
        BigInteger numHash = new BigInteger(1, byteHash);

        // Convert signum to hex
        StringBuilder hash = new StringBuilder(numHash.toString(16));

        return hash.toString(); // PLACEHOLDER
    }

    /**
     * Authentication for a user with a note password.
     * @param noteInput Array of integers that represent what notes were inputted during a login attempt.
     * @param user User object for user that is logging in.
     * @return Boolean status if authentication was successful.
     * @throws NoSuchAlgorithmException Throws if SHA-256 is not a valid hashing algo (It is)
     */
    public boolean authenticate(ArrayList<Integer> noteInput, User user) throws NoSuchAlgorithmException {
        // Get truth hash and user salt from user object
        String userSalt = user.getSalt();
        String userHash = user.getHash();

        // Check for hash equality
        System.out.println(hash(noteInput, userSalt));
        System.out.println(userHash);
        return hash(noteInput, userSalt).equals(userHash);
    }
}
