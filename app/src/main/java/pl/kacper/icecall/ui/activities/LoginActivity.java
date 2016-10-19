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

public class LoginActivity extends DaggerAppCompatActivity implements View.OnClickListener{
    @Inject
    PersistenceUtil persistenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initButtonClickListeners();
    }

    private void initButtonClickListeners() {
        findViewById(R.id.join_button).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.emergency_login_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join_button:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.login_button:
                EditText userNameEditText = (EditText) findViewById(R.id.user_name_input);
                String userName = userNameEditText.getText().toString();
                EditText passwordEditText = (EditText) findViewById(R.id.password_input);
                String password = passwordEditText.getText().toString();
                if(persistenceUtil.authUser(userName,password)) {
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.putExtra("USER_LOGIN", userName);
                    startActivity(intent1);
                } else {
                    Toast.makeText(this, "Login or Password incorrect", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.emergency_login_button:
                Intent intent2 = new Intent(this, UserListActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        persistenceUtil.saveData();
    }
}
