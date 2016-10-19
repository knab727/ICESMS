package pl.kacper.icecall.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import pl.kacper.icecall.R;
import pl.kacper.icecall.configuration.DaggerAppCompatActivity;
import pl.kacper.icecall.persistance.PersistenceUtil;

public class RegisterActivity extends DaggerAppCompatActivity {

    @Inject
    PersistenceUtil persistenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerUser(View view) {
        EditText userNameEditText = (EditText) findViewById(R.id.register_user_name_input);
        String userName = userNameEditText.getText().toString();
        EditText passwordEditText = (EditText) findViewById(R.id.register_password_input);
        String password = passwordEditText.getText().toString();
        EditText passwordConfirmEditText = (EditText) findViewById(R.id.register_password_input_confirm);
        String passwordConfirm = passwordConfirmEditText.getText().toString();
        if(!password.equals(passwordConfirm)) {
            Toast.makeText(this, "The passwords must match", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!persistenceUtil.userExists(userName)) {
            persistenceUtil.createUser(userName, password);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USER_LOGIN", userName);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "A user with this login already exists", Toast.LENGTH_SHORT).show();
        }
    }
}
