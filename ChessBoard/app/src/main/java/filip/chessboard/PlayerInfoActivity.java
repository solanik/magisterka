package filip.chessboard;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PlayerInfoActivity extends AppCompatActivity {
    String name;
    boolean isActive;
    Integer winPercentage;
    boolean isFriend;
    Button conversationButton;
    Button requestFriendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        setButtons();
        setDataFromExtra();
        setTextViews();
    }

    private void setButtons() {
        setConversationButton();
        setRequestFriendButton();
    }

    private void setConversationButton() {
        conversationButton = (Button) findViewById(R.id.ConversationButton);
        conversationButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { startConversation(); }
        });
    }

    private void startConversation() {
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    private void setRequestFriendButton() {
        requestFriendButton = (Button) findViewById(R.id.RequestFriendButton);
        requestFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { requestFriend(); }
        });
    }

    private void requestFriend() {
        String result = WebCommunication.WebAgent.addFriend(name);
        if (Objects.equals(result, "OK"))
            try {
                setData(WebCommunication.WebAgent.getPlayerData(name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        else
            showAlert("Failed to add friend");
    }

    private void setDataFromExtra() {
        List<String> playerInfo;
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            playerInfo = extras.getStringArrayList("playerInfo");
        } else {
            playerInfo = new ArrayList<>();
        }
        setData(playerInfo);
    }

    private void setData(List<String> playerInfo) {
        if (playerInfo.size() < 4) {
            showAlert("No name found to show data");
            this.finish();
        }
        parseData(playerInfo);
    }

    private void showAlert(String message) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(message);
        alert.setNeutralButton("OK", null);
        runOnUiThread(new Runnable() {
            @Override public void run() { alert.show(); }
        });
    }

    private void parseData(List<String> playerInfo) {
        name = playerInfo.get(0);
        isActive = Objects.equals(playerInfo.get(1), "active");
        winPercentage = Integer.parseInt(playerInfo.get(2));
        isFriend = Objects.equals(playerInfo.get(3), "friend");
        conversationButton.setVisibility(isFriend ? VISIBLE : GONE);
        requestFriendButton.setVisibility(isFriend ? VISIBLE : GONE);
    }

    private void setTextViews() {
        setNameTextView();
        setWinPercentageTextView();
        setIsActiveTextView();
    }

    private void setNameTextView() {
        TextView nameText = (TextView) findViewById(R.id.nameTextView);
        nameText.setText(name);
    }

    private void setWinPercentageTextView() {
        TextView winPercentageText = (TextView) findViewById(R.id.winPrecentageText);
        winPercentageText.setText("Win percentage: " + winPercentage.toString() + "%");
        winPercentageText.setTextColor(getWinPercentageColor());
    }

    private int getWinPercentageColor() {
        if (winPercentage < 33)
            return Color.RED;
        else if (winPercentage < 66)
            return Color.YELLOW;
        else
            return Color.GREEN;
    }

    private void setIsActiveTextView() {
        TextView isActiveText = (TextView) findViewById(R.id.isActiveText);
        isActiveText.setText(isActive ? "active" : "not active");
        isActiveText.setTextColor(isActive ? Color.GREEN : Color.GRAY);
    }
}
