package app.passworder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {

    private String website;

    public Password(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return this.website;
    }

    public String generatePassword(String masterPassword) {
        return Password.md5(masterPassword + this.website).substring(0, 12);
    }

    public static Strength calculateStrength(String password) {

        String commonlyUsedPasswords[] = {"123456", "password", "12345678",
            "qwerty", "12345", "123456789", "letmein", "1234567", "football",
            "iloveyou", "admin", "welcome", "monkey", "login", "abc123",
            "starwars", "123123", "dragon", "passw0rd", "master", "hello",
            "freedom", "whatever", "qazwsx", "trustno1", "football", "696969", "batman",
              "secret"};

        for (int i = 0; i < commonlyUsedPasswords.length; i++) {
            if (commonlyUsedPasswords[i].equalsIgnoreCase(password)) {
                return new Strength(0, "Commonly used password");
            }
        }

        if (password.length() < 5) {
            return new Strength(30, "Weak password");
        }

        if (password.length() < 8) {
            return new Strength(60, "Strong password");
        }

        return new Strength(100, "Secure password");
    }

    public static String md5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder hexString = new StringBuilder();
            for (byte digestByte : md.digest(password.getBytes())) {
                hexString.append(String.format("%02X", digestByte));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static class Strength {

        private int strength;
        private String remarks;

        Strength(int strength, String remarks) {
            this.strength = strength;
            this.remarks = remarks;
        }

        public int getStrength() {
            return this.strength;
        }

        public String getRemarks() {
            return this.remarks;
        }
    }
}
