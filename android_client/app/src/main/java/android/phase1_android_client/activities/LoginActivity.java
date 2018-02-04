package android.phase1_android_client.activities;

import android.phase1_android_client.R;
import android.phase1_android_client.model.observable.I_Observer;
import android.phase1_android_client.model.observable.I_StateChange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements I_Observer {

    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new LoginAction());

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new RegisterAction());
    }


    //on click listeners for login and register buttons
    class LoginAction implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String username = LoginActivity.this.usernameField.getText().toString();
            String password = LoginActivity.this.passwordField.getText().toString();

            //

            return;
        }
    }

    class RegisterAction implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String username = LoginActivity.this.usernameField.getText().toString();
            String password = LoginActivity.this.passwordField.getText().toString();

            //

            return;
        }
    }

    //implementation of I_Observer
    /**
     * Alert the observable about a change in global state, with the
     * understanding that the observable will notify all the other observers
     * of the state change. Each observer is given autonomy to react to the
     * change in state independently.
     **/
    public void updateObservable(I_StateChange state) {
        return;
    }


    /**
     * Meant to be called by the Observable - this function defines
     * what the observer will do in reaction to a change in global state.
     **/
    public void notify(I_StateChange state) {
        return;
    }
}
