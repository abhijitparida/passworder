package app.passworder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PasswordListActivity extends AppCompatActivity {

    ArrayList<Password> passwords;
    String masterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);
        masterPassword = getIntent().getStringExtra("masterPassword");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = findViewById(R.id.list);
        Database db = new Database(this);
        this.passwords = db.getAll();
        PasswordAdapter passwordAdapter = new PasswordAdapter(passwords);
        listView.setAdapter(passwordAdapter);
    }

    public void addPassword(View view) {
        Intent intent = new Intent(this, AddPasswordActivity.class);
        intent.putExtra("masterPassword", masterPassword);
        startActivity(intent);
    }

    class PasswordAdapter extends ArrayAdapter<Password> {

        PasswordAdapter(ArrayList<Password> passwords) {
            super(PasswordListActivity.this, R.layout.item_password, passwords);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(PasswordListActivity.this);
            convertView = layoutInflater.inflate(R.layout.item_password, parent, false);
            TextView website = convertView.findViewById(R.id.website);
            TextView password = convertView.findViewById(R.id.password);
            Password p = passwords.get(position);
            website.setText(p.getWebsite());
            password.setText("Password: " + p.generatePassword(masterPassword));
            return convertView;
        }
    }
}
