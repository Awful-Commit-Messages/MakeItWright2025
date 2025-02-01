import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Auth {

    /**
     * Hashing function using SHA256 that is prefixed by a salt before hashing.
     * @param noteInput Password input from keyboard that is given from attempt.
     * @param salt Pre-created salt unique to each user.
     * @return String of SHA256 hash hex code.
     * Credit: https://www.geeksforgeeks.org/sha-256-hash-in-java/
     */
    public static String hash(ArrayList<Integer> noteInput, String salt) throws NoSuchAlgorithmException {
        boolean testing = true;
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
        // Print concat string if testing
        if (testing){
            System.out.println(noteString.toString());
        }

        return "test"; // PLACEHOLDER
    }
}
