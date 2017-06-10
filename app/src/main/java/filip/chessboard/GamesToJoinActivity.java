package filip.chessboard;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Objects;

public class GamesToJoinActivity extends ListActivity {
    String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGameName();
        setGamesList();
    }

    private void getGameName() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            gameName = extras.getString("gameName");
        } else {
            gameName = "No game name";
        }
    }

    private void setGamesList() {
        List<String> games = WebCommunication.WebAgent.getAwaitingGames(gameName);
        this.setListAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, games));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String result = WebCommunication.WebAgent.joinGame(
                l.getItemAtPosition(position).toString(), gameName);
        if (!Objects.equals(result, "OK")) {
            showAlert(result);
            return;
        }
        createChessboard(l.getItemAtPosition(position).toString());
    }

    private void showAlert(String result) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Failed to create game");
        alert.setMessage(result);
        alert.setPositiveButton("OK", null);
        runOnUiThread(new Runnable() {
            @Override public void run() { alert.show(); }
        });
    }

    private void createChessboard(String opponentName) {
        Intent intent = new Intent(this, ChessBoardActivity.class);
        intent.putExtra("opponentName", opponentName);
        intent.putExtra("gameName", gameName);
        startActivity(intent);
    }
}
