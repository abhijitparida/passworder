package app.passworder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FirstTimeSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_setup);

        EditText masterPassword = findViewById(R.id.masterPassword);
        final TextView passwordStrength = findViewById(R.id.strength);
        final ProgressBar passwordStrengthBar = findViewById(R.id.strengthBar);

        masterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Password.Strength strength = Password.calculateStrength(charSequence.toString());
                passwordStrength.setText(strength.getRemarks());
                passwordStrengthBar.setProgress(strength.getStrength());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    public void login(View view) {
        EditText masterPassword = findViewById(R.id.masterPassword);
        EditText masterPasswordConfirm = findViewById(R.id.masterPasswordConfirm);

        String password = masterPassword.getText().toString();
        String confirmPassword = masterPasswordConfirm.getText().toString();

        if (password.length() == 0) {
            Toast.makeText(this, "Passwords is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences store = getSharedPreferences("passworder", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = store.edit();
        String hash = Password.md5(password);
        editor.putString("masterPassword", hash);
        editor.apply();

        Intent intent = new Intent(this, PasswordListActivity.class);
        intent.putExtra("masterPassword", password);
        startActivity(intent);
        finish();
    }
}
