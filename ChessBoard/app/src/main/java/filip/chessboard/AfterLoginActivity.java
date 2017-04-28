package filip.chessboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AfterLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        Button logOutButton = (Button) findViewById(R.id.LogOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                logOut();
            }
        });

        Button startGameButton = (Button) findViewById(R.id.startGame);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                createGamesView();
            }
        });

        Button showFriendsButton = (Button) findViewById(R.id.showFriends);
        showFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                createShowFriendsView();
            }
        });
    }

    private void logOut() {
        this.finish();
    }

    private void createGamesView() {
        Intent intent = new Intent(this, GamesToJoinActivity.class);
        intent.putStringArrayListExtra(
                "games", (ArrayList<String>) WebCommunication.WebAgent.getGames());
        startActivity(intent);
    }

    private void createShowFriendsView() {
        Intent intent = new Intent(this, ShowFriendsActivity.class);
        intent.putStringArrayListExtra(
                "friends", (ArrayList<String>) WebCommunication.WebAgent.getFriends());
        startActivity(intent);
    }
}
