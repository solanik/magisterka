package filip.chessboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import WebCommunication.WebAgent;

import static android.app.ProgressDialog.STYLE_SPINNER;

public class StartGameActivity extends AppCompatActivity {
    String gameName;
    String opponentName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        setGameName();
        setButtons();
    }

    private void setGameName() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            gameName = extras.getString("gameName");
        }
        else {
            gameName = "No game name";
        }
        TextView gameNameTextView = (TextView) findViewById(R.id.StartGameTextView);
        gameNameTextView.setText(gameName);
    }

    private void setButtons() {
        Button startGame = (Button) findViewById(R.id.StartNewGameButton);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { onStartGame(); }
        });
        Button joinGame = (Button) findViewById(R.id.JoinExisitingGameButton);
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { onJoinGame(); }
        });
    }

    private void onStartGame() {
        String result = WebAgent.createNewGame(gameName);
        if (!Objects.equals(result, "OK")) {
            showResult(result);
        }
        else {
            waitForOpponent();
        }
    }

    private void showResult(String result) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Failed to create game");
        alert.setMessage(result);
        alert.setPositiveButton("OK", null);
        runOnUiThread(new Runnable() {
            @Override public void run() { alert.show(); }
        });
    }

    private void waitForOpponent() {
        final ProgressDialog dialog = createWaitForOpponentDialog();
        new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override public void run() { dialog.show(); }
                });
                askForOpponent();
                runOnUiThread(new Runnable() {
                    @Override public void run() { dialog.show(); }
                });
                startGameActivity();
            }
        }.start();
    }

    private ProgressDialog createWaitForOpponentDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(STYLE_SPINNER);
        dialog.setMessage("Please wait for opponent...");
        dialog.setCancelable(false);
        return dialog;
    }

    private void askForOpponent() {
        if (Objects.equals("", opponentName)) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    opponentName = WebCommunication.WebAgent.isOpponent(gameName);
                    askForOpponent();
                }
            }, 2000);
        }
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, ChessBoardActivity.class);
        intent.putExtra("gameName", gameName);
        intent.putExtra("opponentName", opponentName);
        startActivity(intent);
    }

    private void onJoinGame() {
        Intent intent = new Intent(this, GamesToJoinActivity.class);
        intent.putExtra("gameName", gameName);
        startActivity(intent);
    }
}
