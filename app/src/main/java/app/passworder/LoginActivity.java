package app.passworder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences store = getSharedPreferences("passworder", Context.MODE_PRIVATE);
        String masterPasswordHash = store.getString("masterPassword", null);
        if (masterPasswordHash == null) {
            startActivity(new Intent(this, FirstTimeSetupActivity.class));
            finish();
        }
    }

    public void login(View view) {
        EditText masterPassword = findViewById(R.id.masterPassword);
        String password = masterPassword.getText().toString();

        SharedPreferences store = getSharedPreferences("passworder", Context.MODE_PRIVATE);
        String masterPasswordHash = store.getString("masterPassword", null);

        if (!Password.md5(password).equals(masterPasswordHash)) {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, PasswordListActivity.class);
            intent.putExtra("masterPassword", password);
            startActivity(intent);
            finish();
        }
    }
}
