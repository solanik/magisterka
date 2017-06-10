package filip.chessboard;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowFriendsActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        ArrayList<String> friends;
        if (null != extras) {
            friends = extras.getStringArrayList("friends");
        } else {
            friends = new ArrayList<>();
        }
        assert friends != null;
        this.setListAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, friends));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, PlayerInfoActivity.class);
        try {
            intent.putStringArrayListExtra("playerInfo",
                    (ArrayList<String>) WebCommunication.WebAgent.getPlayerData(
                            l.getItemAtPosition(position).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }
}
