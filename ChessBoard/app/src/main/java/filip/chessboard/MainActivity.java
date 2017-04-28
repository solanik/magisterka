package filip.chessboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import static android.app.ProgressDialog.STYLE_SPINNER;

public class MainActivity extends AppCompatActivity {
        EditText loginText;
        EditText passwordText;
        CheckBox passwordVisible;
        Button loginButton;
        Button setIpButton;
        Button registerButton;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        setViews();
        setUpViews();
    }

    private void setViews() {
        loginText = (EditText) findViewById(R.id.LoginText);
        passwordText = (EditText) findViewById(R.id.PasswordText);
        passwordVisible = (CheckBox) findViewById(R.id.PasswordVisible);
        loginButton = (Button) findViewById(R.id.LoginButton);
        setIpButton = (Button) findViewById(R.id.SetIpButton);
        registerButton = (Button) findViewById(R.id.RegisterButton);
    }

    private void setUpViews() {
        passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordVisibleChecked(false);
        passwordVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwordVisibleChecked(isChecked);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { loginButtonClick(); }
        });
        setIpButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { onSetIpClick(); }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { onRegisterClick(); }
        });
    }

    private void passwordVisibleChecked(Boolean isChecked) {
        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | (isChecked
                ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : InputType.TYPE_TEXT_VARIATION_PASSWORD));
    }

    private void loginButtonClick() {
        if (!isLoginAndPassword()) {
            promptErrorNoDataProvided();
            return;
        }
        logIn();
    }

    private void promptLoginResultFailed() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Failed to log in");
        alert.setPositiveButton("OK", null);
        runOnUiThread(new Runnable() {
            @Override public void run() { alert.show(); }
        });
    }

    private void logIn() {
        final ProgressDialog dialog = createProgressDialog();
        new Thread() {
            @Override public void run() {
                runOnUiThread(new Runnable() {
                    @Override public void run() { dialog.show(); }
                });
                if (WebCommunication.WebAgent.logIn(loginText.getText().toString(),
                        passwordText.getText().toString())) {
                    createAfterLoginView();
                } else {
                    promptLoginResultFailed();
                }
                runOnUiThread(new Runnable() {
                    @Override public void run() { dialog.cancel(); }
                });
            }
        }.start();
    }

    private ProgressDialog createProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(STYLE_SPINNER);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        return dialog;
    }

    private void createAfterLoginView() {
        Intent intent = new Intent(this, ChessBoardActivity.class);
        intent.putExtra("gameName", "checkers");
        intent.putExtra("opponentName", "opponent");
        startActivity(intent);
    }

    private void onSetIpClick() {
        EditText ipTextView = (EditText) findViewById(R.id.SetIpText);
        String ip = ipTextView.getText().toString();

        WebCommunication.WebAgent.mProxy = new WebCommunication.CheckersProxy();
    }

    private void onRegisterClick() {
        if (!isLoginAndPassword()) {
            promptErrorNoDataProvided();
            return;
        }
        register();
    }

    private boolean isLoginAndPassword() {
        return loginText.toString().length() != 0 && passwordText.toString().length() != 0;
    }

    private void promptErrorNoDataProvided() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error");
        alert.setMessage("Please provide login and password");
        alert.setNeutralButton("OK", null);
        alert.show();
    }

    private void register() {
        String result = WebCommunication.WebAgent.register(
                loginText.getText().toString(), passwordText.getText().toString());
        promptRegisterResultFailed(result);
    }

    private void promptRegisterResultFailed(String result) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Registration result:");
        alert.setMessage(result);
        alert.setPositiveButton("OK", null);
        runOnUiThread(new Runnable() {
            @Override public void run() { alert.show(); }
        });
    }
}