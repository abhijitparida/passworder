package app.passworder;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Database {

    private Context context;
    private SharedPreferences store;

    public Database(Context context) {
        this.context = context;
        this.store = context.getSharedPreferences("passworder", Context.MODE_PRIVATE);
    }

    public ArrayList<Password> getAll() {
        ArrayList<Password> passwords = new ArrayList<>();
        for (String password : this.store.getStringSet("passwords", new HashSet<String>())) {
            passwords.add(new Password(password));
        }
        return passwords;
    }

    public void add(Password password) {
        SharedPreferences.Editor editor = this.store.edit();
        Set<String> passwords = this.store.getStringSet("passwords", new TreeSet<String>());
        Set<String> newPasswords = new TreeSet<>();
        newPasswords.add(password.getWebsite());
        newPasswords.addAll(passwords);
        editor.putStringSet("passwords", newPasswords);
        editor.apply();
    }
}
