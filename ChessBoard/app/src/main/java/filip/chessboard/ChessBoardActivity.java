package filip.chessboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Pieces.Piece;
import Pieces.Player;
import WebCommunication.GameData;
import WebCommunication.WebAgent;

public class ChessBoardActivity extends AppCompatActivity {
    private class Square
    {
        Square(Button but, Piece piec)
        {
            button = but;
            piece = piec;
        }
        public Button button;
        Piece piece;
    }

    Map<String, Square> squares;
    Map<String, String> buttonTranslator;
    Square clickedSquare = null;
    String gameName;
    Button conversationButton;
    Button opponentInfoButton;
    String opponentName = "opponent";
    boolean playerMove = false;
    String firstPlayer;
    int playerColor;
    int opponentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_board);

        setSquares();
        setButtonTranslator();
        getExtras();
        GameData gameData = getGameData();

        manageFirstPlayerData(gameData.firstPlayer);
        manageGameData(gameData);
        if (playerMove)
            showWhoseTurn();

        setActionOnClick();
        conversationButton = (Button) findViewById(R.id.WriteMessageButton);
        conversationButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { onConversationButtonClick(); }
        });
        opponentInfoButton= (Button) findViewById(R.id.OpponentInfoButton);
        opponentInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { onOpponentInfoButtonClick(); }
        });

        startListening();
    }

    private void setSquares() {
        squares = new HashMap<>();
        squares.put("00", new Square((Button) findViewById(R.id.squareA1), null));
        squares.put("10", new Square((Button) findViewById(R.id.squareB1), null));
        squares.put("20", new Square((Button) findViewById(R.id.squareC1), null));
        squares.put("30", new Square((Button) findViewById(R.id.squareD1), null));
        squares.put("40", new Square((Button) findViewById(R.id.squareE1), null));
        squares.put("50", new Square((Button) findViewById(R.id.squareF1), null));
        squares.put("60", new Square((Button) findViewById(R.id.squareG1), null));
        squares.put("70", new Square((Button) findViewById(R.id.squareH1), null));

        squares.put("01", new Square((Button) findViewById(R.id.squareA2), null));
        squares.put("11", new Square((Button) findViewById(R.id.squareB2), null));
        squares.put("21", new Square((Button) findViewById(R.id.squareC2), null));
        squares.put("31", new Square((Button) findViewById(R.id.squareD2), null));
        squares.put("41", new Square((Button) findViewById(R.id.squareE2), null));
        squares.put("51", new Square((Button) findViewById(R.id.squareF2), null));
        squares.put("61", new Square((Button) findViewById(R.id.squareG2), null));
        squares.put("71", new Square((Button) findViewById(R.id.squareH2), null));

        squares.put("02", new Square((Button) findViewById(R.id.squareA3), null));
        squares.put("12", new Square((Button) findViewById(R.id.squareB3), null));
        squares.put("22", new Square((Button) findViewById(R.id.squareC3), null));
        squares.put("32", new Square((Button) findViewById(R.id.squareD3), null));
        squares.put("42", new Square((Button) findViewById(R.id.squareE3), null));
        squares.put("52", new Square((Button) findViewById(R.id.squareF3), null));
        squares.put("62", new Square((Button) findViewById(R.id.squareG3), null));
        squares.put("72", new Square((Button) findViewById(R.id.squareH3), null));

        squares.put("03", new Square((Button) findViewById(R.id.squareA4), null));
        squares.put("13", new Square((Button) findViewById(R.id.squareB4), null));
        squares.put("23", new Square((Button) findViewById(R.id.squareC4), null));
        squares.put("33", new Square((Button) findViewById(R.id.squareD4), null));
        squares.put("43", new Square((Button) findViewById(R.id.squareE4), null));
        squares.put("53", new Square((Button) findViewById(R.id.squareF4), null));
        squares.put("63", new Square((Button) findViewById(R.id.squareG4), null));
        squares.put("73", new Square((Button) findViewById(R.id.squareH4), null));

        squares.put("04", new Square((Button) findViewById(R.id.squareA5), null));
        squares.put("14", new Square((Button) findViewById(R.id.squareB5), null));
        squares.put("24", new Square((Button) findViewById(R.id.squareC5), null));
        squares.put("34", new Square((Button) findViewById(R.id.squareD5), null));
        squares.put("44", new Square((Button) findViewById(R.id.squareE5), null));
        squares.put("54", new Square((Button) findViewById(R.id.squareF5), null));
        squares.put("64", new Square((Button) findViewById(R.id.squareG5), null));
        squares.put("74", new Square((Button) findViewById(R.id.squareH5), null));

        squares.put("05", new Square((Button) findViewById(R.id.squareA6), null));
        squares.put("15", new Square((Button) findViewById(R.id.squareB6), null));
        squares.put("25", new Square((Button) findViewById(R.id.squareC6), null));
        squares.put("35", new Square((Button) findViewById(R.id.squareD6), null));
        squares.put("45", new Square((Button) findViewById(R.id.squareE6), null));
        squares.put("55", new Square((Button) findViewById(R.id.squareF6), null));
        squares.put("65", new Square((Button) findViewById(R.id.squareG6), null));
        squares.put("75", new Square((Button) findViewById(R.id.squareH6), null));

        squares.put("06", new Square((Button) findViewById(R.id.squareA7), null));
        squares.put("16", new Square((Button) findViewById(R.id.squareB7), null));
        squares.put("26", new Square((Button) findViewById(R.id.squareC7), null));
        squares.put("36", new Square((Button) findViewById(R.id.squareD7), null));
        squares.put("46", new Square((Button) findViewById(R.id.squareE7), null));
        squares.put("56", new Square((Button) findViewById(R.id.squareF7), null));
        squares.put("66", new Square((Button) findViewById(R.id.squareG7), null));
        squares.put("76", new Square((Button) findViewById(R.id.squareH7), null));

        squares.put("07", new Square((Button) findViewById(R.id.squareA8), null));
        squares.put("17", new Square((Button) findViewById(R.id.squareB8), null));
        squares.put("27", new Square((Button) findViewById(R.id.squareC8), null));
        squares.put("37", new Square((Button) findViewById(R.id.squareD8), null));
        squares.put("47", new Square((Button) findViewById(R.id.squareE8), null));
        squares.put("57", new Square((Button) findViewById(R.id.squareF8), null));
        squares.put("67", new Square((Button) findViewById(R.id.squareG8), null));
        squares.put("77", new Square((Button) findViewById(R.id.squareH8), null));
    }

    private void setButtonTranslator() {
        buttonTranslator = new HashMap<>();
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA1).getId()), "00");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB1).getId()), "10");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC1).getId()), "20");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD1).getId()), "30");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE1).getId()), "40");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF1).getId()), "50");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG1).getId()), "60");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH1).getId()), "70");

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA2).getId()), "01");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB2).getId()), "11");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC2).getId()), "21");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD2).getId()), "31");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE2).getId()), "41");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF2).getId()), "51");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG2).getId()), "61");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH2).getId()), "71");

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA3).getId()), "02");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB3).getId()), "12");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC3).getId()), "22");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD3).getId()), "32");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE3).getId()), "42");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF3).getId()), "52");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG3).getId()), "62");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH3).getId()), "72");

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA4).getId()), "03");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB4).getId()), "13");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC4).getId()), "23");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD4).getId()), "33");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE4).getId()), "43");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF4).getId()), "53");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG4).getId()), "63");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH4).getId()), "73");

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA5).getId()), "04");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB5).getId()), "14");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC5).getId()), "24");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD5).getId()), "34");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE5).getId()), "44");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF5).getId()), "54");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG5).getId()), "64");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH5).getId()), "74");

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA6).getId()), "05");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB6).getId()), "15");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC6).getId()), "25");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD6).getId()), "35");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE6).getId()), "45");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF6).getId()), "55");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG6).getId()), "65");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH6).getId()), "75");

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA7).getId()), "06");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB7).getId()), "16");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC7).getId()), "26");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD7).getId()), "36");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE7).getId()), "46");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF7).getId()), "56");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG7).getId()), "66");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH7).getId()), "76");

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA8).getId()), "07");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB8).getId()), "17");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC8).getId()), "27");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD8).getId()), "37");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE8).getId()), "47");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF8).getId()), "57");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG8).getId()), "67");
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH8).getId()), "77");
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            gameName = extras.getString("gameName");
            opponentName = extras.getString("opponentName");
        } else {
            gameName = "No game name";
            opponentName = "No opponent name";
        }
    }

    private GameData getGameData() {
        GameData gameData = null;
        try {
            gameData = WebCommunication.WebAgent.getStartSetting(gameName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert gameData != null;
        return gameData;
    }

    private void manageFirstPlayerData(String firstName) {
        firstPlayer = firstName;
        playerMove = Objects.equals(firstPlayer, WebAgent.mLogin);
        setColors();
    }

    private void setColors() {
        playerColor = Objects.equals(firstPlayer, WebAgent.mLogin)
                ? Color.WHITE
                : Color.BLACK;
        opponentColor = Objects.equals(firstPlayer, WebAgent.mLogin)
                ? Color.BLACK
                : Color.WHITE;
    }

    private void manageGameData(GameData data) {
        clearAll();
        setPieces(data.positions);
        setDefaultColors();
        setBoardText();
        boolean oldPlayerMove = playerMove;
        playerMove = Objects.equals(data.playerMoving, WebAgent.mLogin);
        showFeedback(data.feedback);
        if (oldPlayerMove != playerMove)
            showWhoseTurn();
    }

    private void clearAll() {
        for (Map.Entry<String, Square> square : squares.entrySet()) {
            square.getValue().piece = null;
        }
    }

    private void setPieces(List<String> positions) {
        for (String position : positions) {
            setPiece(position);
        }
    }

    private void setPiece(String position) {
        String delimiter = ":";
        String[] positionParts = position.split(delimiter);
        Piece piece = new Piece();
        piece.type = Piece.toType(positionParts[1]);
        piece.player = Piece.toPlayer(positionParts[2]);
        squares.get(positionParts[0]).piece = piece;
    }

    private void setDefaultColors() {
        clickedSquare = null;
        int counter = 0;
        int colorBrown = Color.rgb(139,69,19); // BROWN
        int colorOrange = Color.rgb(255,140,0); // ORANGE
        int color = 0;
        for (Map.Entry<String, Square> square : squares.entrySet()) {
            Log.d("ChessBoardActivity", square.getKey());
            String key = square.getKey();
            int keySum = Character.getNumericValue(key.charAt(0)) +
                    Character.getNumericValue(key.charAt(1));
            if (keySum % 2 == 0) {
                square.getValue().button.setBackgroundColor(colorBrown);
            } else {
                square.getValue().button.setBackgroundColor(colorOrange);
            }
        }
    }

    private void showFeedback(String feedback) {
        if (!Objects.equals(feedback, "")) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Request status");
            alert.setMessage(feedback);
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    private void showWhoseTurn() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Turn");
        alert.setMessage("Now it's " + (playerMove ? "your" : opponentName) + " turn");
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    void setActionOnClick() {
        for (final Map.Entry<String, Square> square : squares.entrySet()) {
            square.getValue().button.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onSquareClick(square.getValue());
                }
            });
        }
    }

    private void onSquareClick(Square square) {
        if (!playerMove) {
            return;
        }
        if (isAnotherClicked(square.button.getTag().toString()) &&
                            !isClickedOwnedByPlayer(square.piece)) {
            String oldPosition = buttonTranslator.get(Integer.toString(
                    clickedSquare.button.getId()));
            String newPosition = buttonTranslator.get(Integer.toString(
                    square.button.getId()));
            GameData gameData = null;
            try {
                gameData = WebAgent.sendNewPosition(oldPosition + "#" + newPosition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            manageGameData(gameData);
            assert gameData != null;
            if (playerMove && Objects.equals(gameData.feedback, ""))
                showWhoseTurn();
        } else if (isTheSameClicked(square.button.getTag().toString())) {
            setDefaultColors();
        } else if (!isClicked() && 0 != square.button.getText().length() &&
                    isClickedOwnedByPlayer(square.piece)) {
            square.button.setBackgroundColor(Color.rgb(127, 255, 212)); // AQUAMARINE
            clickedSquare = square;
        }
        setBoardText();
    }

    private boolean isAnotherClicked(String tag)
    {
        return isClicked() && !Objects.equals(clickedSquare.button.getTag().toString(), tag);
    }

    private boolean isClickedOwnedByPlayer(Piece piece)
    {
        return piece != null && Player.player == piece.player;
    }

    private boolean isTheSameClicked(String tag)
    {
        return isClicked() && Objects.equals(clickedSquare.button.getTag().toString(), tag);
    }

    private boolean isClicked()
    {
        return clickedSquare != null;
    }

    private void onConversationButtonClick() {
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra("name", opponentName);
        startActivity(intent);
    }

    private void onOpponentInfoButtonClick() {
        Intent intent = new Intent(this, PlayerInfoActivity.class);
        try {
            intent.putStringArrayListExtra("playerInfo",
                    (ArrayList<String>) WebAgent.getPlayerData(opponentName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    private void startListening() {
//        new Thread() {
//            @Override public void run() {
//                listen();
//            }
//        }.start();
    }

    private void listen() {
        while (true) {
            Handler handler = new Handler();
            GameData gameData = null;
            try {
                gameData = WebAgent.getStartSetting(gameName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final GameData finalGameData = gameData;
            handler.postDelayed(new Runnable() {
                @Override public void run() {
                    manageGameData(finalGameData);
                } }, 2000);
        }
    }

    private void setBoardText() {
        for (Map.Entry<String, Square> square : squares.entrySet()) {
            setBoardText(square.getValue());
        }
        TextView gameNameTextView = (TextView) findViewById(R.id.gameNameTextView);
        gameNameTextView.setText(gameName);
    }

    private void setBoardText(Square square) {
        if (square.piece == null) {
            square.button.setText("");
            return;
        }
        buttonSetColor(square);
        buttonSetText(square);
    }

    private void buttonSetColor(Square square) {
        if (isSecondPlayer(square)) {
            square.button.setTextColor(Color.BLACK);
        } else {
            square.button.setTextColor(Color.WHITE);
        }
    }

    private boolean isSecondPlayer(Square square) {
        return (square.piece.player == Pieces.Player.opponent
                && !Objects.equals(firstPlayer, opponentName))
                || (square.piece.player == Pieces.Player.player
                && Objects.equals(firstPlayer, opponentName));
    }

    private void buttonSetText(Square square) {
        square.button.setTypeface(null, Typeface.BOLD);
        square.button.setText(Piece.toString(square.piece.type));
    }
}
