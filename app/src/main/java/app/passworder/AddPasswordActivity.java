package app.passworder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddPasswordActivity extends AppCompatActivity {

    String masterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        masterPassword = getIntent().getStringExtra("masterPassword");

        final EditText website = findViewById(R.id.website);
        final TextView password = findViewById(R.id.password);
        website.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Password p = new Password(website.getText().toString());
                password.setText(p.generatePassword(masterPassword));
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    public void savePassword(View view) {
        EditText website = findViewById(R.id.website);
        Database db = new Database(this);
        db.add(new Password(website.getText().toString()));
        finish();
    }
}
