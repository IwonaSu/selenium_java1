import java.util.Random;

public class EmailGenerator {
    public static String generateRandomEmail(String prefix, String domain, int randomIntLength) {
        String randomNumber = "";

        for (int i =0; i< randomIntLength; i++) {
            randomNumber = randomNumber + new Random().nextInt(10);
        }
        String randomEmail = prefix + randomNumber + "@" + domain;
        return randomEmail;
    }
}
