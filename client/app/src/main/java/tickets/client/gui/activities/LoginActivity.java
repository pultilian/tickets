package tickets.client.gui.activities;

import tickets.client.ModelFacade;
import tickets.client.R;
import tickets.client.gui.presenters.ILoginPresenter;
import tickets.client.model.observable.IStateChange;
import tickets.common.UserData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements ILoginPresenter {

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
            UserData data;
            try {
                data = getData();
                LoginActivity.this.login(data);
            }
            catch (Exception e) {
                Toast.makeText(LoginActivity.this, "There was a problem logging in.", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    class RegisterAction implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            UserData data;
            try {
                data = getData();
                LoginActivity.this.register(data);
            }
            catch (Exception e) {
                Toast.makeText(LoginActivity.this, "There was a problem registering.", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    public UserData getData() throws Exception {
        String username = LoginActivity.this.usernameField.getText().toString();
        String password = LoginActivity.this.passwordField.getText().toString();
        UserData data = new UserData();
        if (!data.setUsername(username)) {
            throw new Exception();
        }
        if (!data.setPassword(password)) {
            throw new Exception();
        }
        return data;
    }

    //implementation of ILoginPresenter
    public void login(UserData data) {
        ModelFacade.SINGLETON.login(data);
        return;
    }

    public void register(UserData data) {
        ModelFacade.SINGLETON.register(data);
        return;
    }

    /**
     * Alert the observable about a change in global state, with the
     * understanding that the observable will notify all the other observers
     * of the state change. Each observer is given autonomy to react to the
     * change in state independently.
     **/
    public void updateObservable(IStateChange state) {
        return;
    }


    /**
     * Meant to be called by the Observable - this function defines
     * what the observer will do in reaction to a change in global state.
     **/
    public void notify(IStateChange state) {
        return;
    }
}