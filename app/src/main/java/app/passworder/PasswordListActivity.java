package app.passworder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
            final TextView password = convertView.findViewById(R.id.password);
            final Password p = passwords.get(position);
            website.setText(p.getWebsite());
            password.setText("Password: ************");

            Button copyPassword = convertView.findViewById(R.id.copyPassword);
            copyPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("password", p.generatePassword(masterPassword));
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(PasswordListActivity.this, "Password copied", Toast.LENGTH_SHORT).show();
                }
            });

            final ImageView showPassword = convertView.findViewById(R.id.showPassword);
            final ImageView hidePassword = convertView.findViewById(R.id.hidePassword);
            showPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPassword.setVisibility(View.GONE);
                    hidePassword.setVisibility(View.VISIBLE);
                    password.setText("Password: " + p.generatePassword(masterPassword));
                }
            });
            hidePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hidePassword.setVisibility(View.GONE);
                    showPassword.setVisibility(View.VISIBLE);
                    password.setText("Password: ************");
                }
            });

            return convertView;
        }
    }
}
