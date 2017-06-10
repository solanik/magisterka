package filip.chessboard;

import android.content.DialogInterface;
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

import ChessboardDetail.Chessboard;
import ChessboardDetail.Field;
import ChessboardDetail.Piece;
import ChessboardDetail.Player;
import ChessboardDetail.Position;
import WebCommunication.GameData;
import WebCommunication.WebAgent;
import filip.chessboard.Checkers.AutomatedCheckers;

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

    Chessboard board;
    HashMap<Position, Square> squares;
    HashMap<String, Position> buttonTranslator;
    Square clickedSquare = null;
    String gameName;
    Button conversationButton;
    Button opponentInfoButton;
    String opponentName = "opponent";
    boolean playerMove = false;
    Player firstPlayer;
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
        squares.put(new Position(0, 0), new Square((Button) findViewById(R.id.squareA1), null));
        squares.put(new Position(1, 0), new Square((Button) findViewById(R.id.squareB1), null));
        squares.put(new Position(2, 0), new Square((Button) findViewById(R.id.squareC1), null));
        squares.put(new Position(3, 0), new Square((Button) findViewById(R.id.squareD1), null));
        squares.put(new Position(4, 0), new Square((Button) findViewById(R.id.squareE1), null));
        squares.put(new Position(5, 0), new Square((Button) findViewById(R.id.squareF1), null));
        squares.put(new Position(6, 0), new Square((Button) findViewById(R.id.squareG1), null));
        squares.put(new Position(7, 0), new Square((Button) findViewById(R.id.squareH1), null));

        squares.put(new Position(0, 1), new Square((Button) findViewById(R.id.squareA2), null));
        squares.put(new Position(1, 1), new Square((Button) findViewById(R.id.squareB2), null));
        squares.put(new Position(2, 1), new Square((Button) findViewById(R.id.squareC2), null));
        squares.put(new Position(3, 1), new Square((Button) findViewById(R.id.squareD2), null));
        squares.put(new Position(4, 1), new Square((Button) findViewById(R.id.squareE2), null));
        squares.put(new Position(5, 1), new Square((Button) findViewById(R.id.squareF2), null));
        squares.put(new Position(6, 1), new Square((Button) findViewById(R.id.squareG2), null));
        squares.put(new Position(7, 1), new Square((Button) findViewById(R.id.squareH2), null));

        squares.put(new Position(0, 2), new Square((Button) findViewById(R.id.squareA3), null));
        squares.put(new Position(1, 2), new Square((Button) findViewById(R.id.squareB3), null));
        squares.put(new Position(2, 2), new Square((Button) findViewById(R.id.squareC3), null));
        squares.put(new Position(3, 2), new Square((Button) findViewById(R.id.squareD3), null));
        squares.put(new Position(4, 2), new Square((Button) findViewById(R.id.squareE3), null));
        squares.put(new Position(5, 2), new Square((Button) findViewById(R.id.squareF3), null));
        squares.put(new Position(6, 2), new Square((Button) findViewById(R.id.squareG3), null));
        squares.put(new Position(7, 2), new Square((Button) findViewById(R.id.squareH3), null));

        squares.put(new Position(0, 3), new Square((Button) findViewById(R.id.squareA4), null));
        squares.put(new Position(1, 3), new Square((Button) findViewById(R.id.squareB4), null));
        squares.put(new Position(2, 3), new Square((Button) findViewById(R.id.squareC4), null));
        squares.put(new Position(3, 3), new Square((Button) findViewById(R.id.squareD4), null));
        squares.put(new Position(4, 3), new Square((Button) findViewById(R.id.squareE4), null));
        squares.put(new Position(5, 3), new Square((Button) findViewById(R.id.squareF4), null));
        squares.put(new Position(6, 3), new Square((Button) findViewById(R.id.squareG4), null));
        squares.put(new Position(7, 3), new Square((Button) findViewById(R.id.squareH4), null));

        squares.put(new Position(0, 4), new Square((Button) findViewById(R.id.squareA5), null));
        squares.put(new Position(1, 4), new Square((Button) findViewById(R.id.squareB5), null));
        squares.put(new Position(2, 4), new Square((Button) findViewById(R.id.squareC5), null));
        squares.put(new Position(3, 4), new Square((Button) findViewById(R.id.squareD5), null));
        squares.put(new Position(4, 4), new Square((Button) findViewById(R.id.squareE5), null));
        squares.put(new Position(5, 4), new Square((Button) findViewById(R.id.squareF5), null));
        squares.put(new Position(6, 4), new Square((Button) findViewById(R.id.squareG5), null));
        squares.put(new Position(7, 4), new Square((Button) findViewById(R.id.squareH5), null));

        squares.put(new Position(0, 5), new Square((Button) findViewById(R.id.squareA6), null));
        squares.put(new Position(1, 5), new Square((Button) findViewById(R.id.squareB6), null));
        squares.put(new Position(2, 5), new Square((Button) findViewById(R.id.squareC6), null));
        squares.put(new Position(3, 5), new Square((Button) findViewById(R.id.squareD6), null));
        squares.put(new Position(4, 5), new Square((Button) findViewById(R.id.squareE6), null));
        squares.put(new Position(5, 5), new Square((Button) findViewById(R.id.squareF6), null));
        squares.put(new Position(6, 5), new Square((Button) findViewById(R.id.squareG6), null));
        squares.put(new Position(7, 5), new Square((Button) findViewById(R.id.squareH6), null));

        squares.put(new Position(0, 6), new Square((Button) findViewById(R.id.squareA7), null));
        squares.put(new Position(1, 6), new Square((Button) findViewById(R.id.squareB7), null));
        squares.put(new Position(2, 6), new Square((Button) findViewById(R.id.squareC7), null));
        squares.put(new Position(3, 6), new Square((Button) findViewById(R.id.squareD7), null));
        squares.put(new Position(4, 6), new Square((Button) findViewById(R.id.squareE7), null));
        squares.put(new Position(5, 6), new Square((Button) findViewById(R.id.squareF7), null));
        squares.put(new Position(6, 6), new Square((Button) findViewById(R.id.squareG7), null));
        squares.put(new Position(7, 6), new Square((Button) findViewById(R.id.squareH7), null));

        squares.put(new Position(0, 7), new Square((Button) findViewById(R.id.squareA8), null));
        Position pos17 = new Position(1, 7);
        Log.d("ChessBoardActivity", "putting square on position " + pos17);
        squares.put(pos17, new Square((Button) findViewById(R.id.squareB8), null));
        if (squares.get(pos17) == null) {
            Log.d("ChessBoardActivity", "I've put it in, but there seems to be no effect");
        }
        squares.put(new Position(2, 7), new Square((Button) findViewById(R.id.squareC8), null));
        squares.put(new Position(3, 7), new Square((Button) findViewById(R.id.squareD8), null));
        squares.put(new Position(4, 7), new Square((Button) findViewById(R.id.squareE8), null));
        squares.put(new Position(5, 7), new Square((Button) findViewById(R.id.squareF8), null));
        squares.put(new Position(6, 7), new Square((Button) findViewById(R.id.squareG8), null));
        squares.put(new Position(7, 7), new Square((Button) findViewById(R.id.squareH8), null));
    }

    private void setButtonTranslator() {
        buttonTranslator = new HashMap<>();
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA1).getId()), new Position(0, 0));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB1).getId()), new Position(1, 0));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC1).getId()), new Position(2, 0));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD1).getId()), new Position(3, 0));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE1).getId()), new Position(4, 0));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF1).getId()), new Position(5, 0));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG1).getId()), new Position(6, 0));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH1).getId()), new Position(7, 0));

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA2).getId()), new Position(0, 1));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB2).getId()), new Position(1, 1));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC2).getId()), new Position(2, 1));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD2).getId()), new Position(3, 1));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE2).getId()), new Position(4, 1));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF2).getId()), new Position(5, 1));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG2).getId()), new Position(6, 1));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH2).getId()), new Position(7, 1));

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA3).getId()), new Position(0, 2));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB3).getId()), new Position(1, 2));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC3).getId()), new Position(2, 2));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD3).getId()), new Position(3, 2));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE3).getId()), new Position(4, 2));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF3).getId()), new Position(5, 2));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG3).getId()), new Position(6, 2));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH3).getId()), new Position(7, 2));

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA4).getId()), new Position(0, 3));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB4).getId()), new Position(1, 3));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC4).getId()), new Position(2, 3));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD4).getId()), new Position(3, 3));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE4).getId()), new Position(4, 3));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF4).getId()), new Position(5, 3));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG4).getId()), new Position(6, 3));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH4).getId()), new Position(7, 3));

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA5).getId()), new Position(0, 4));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB5).getId()), new Position(1, 4));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC5).getId()), new Position(2, 4));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD5).getId()), new Position(3, 4));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE5).getId()), new Position(4, 4));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF5).getId()), new Position(5, 4));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG5).getId()), new Position(6, 4));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH5).getId()), new Position(7, 4));

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA6).getId()), new Position(0, 5));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB6).getId()), new Position(1, 5));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC6).getId()), new Position(2, 5));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD6).getId()), new Position(3, 5));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE6).getId()), new Position(4, 5));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF6).getId()), new Position(5, 5));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG6).getId()), new Position(6, 5));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH6).getId()), new Position(7, 5));

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA7).getId()), new Position(0, 6));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB7).getId()), new Position(1, 6));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC7).getId()), new Position(2, 6));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD7).getId()), new Position(3, 6));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE7).getId()), new Position(4, 6));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF7).getId()), new Position(5, 6));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG7).getId()), new Position(6, 6));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH7).getId()), new Position(7, 6));

        buttonTranslator.put(Integer.toString(findViewById(R.id.squareA8).getId()), new Position(0, 7));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareB8).getId()), new Position(1, 7));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareC8).getId()), new Position(2, 7));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareD8).getId()), new Position(3, 7));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareE8).getId()), new Position(4, 7));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareF8).getId()), new Position(5, 7));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareG8).getId()), new Position(6, 7));
        buttonTranslator.put(Integer.toString(findViewById(R.id.squareH8).getId()), new Position(7, 7));
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

    private void manageFirstPlayerData(Player player) {
        firstPlayer = player;
        playerMove = isFirstPlayer();
        setColors();
    }

    private void setColors() {
        playerColor = isFirstPlayer() ? Color.WHITE : Color.BLACK;
        opponentColor = isFirstPlayer() ? Color.BLACK : Color.WHITE;
    }

    private boolean isFirstPlayer() {
        return firstPlayer.equals(Player.player);
    }

    private void manageGameData(GameData data) {
        clearAll();
        board = data.board;
        setPieces();
        setDefaultColors();
        setBoardText();
        playerMove = Objects.equals(data.playerMoving, Player.player);
        showFeedback(data.feedback);
        if (data.feedback.isEmpty())
            showWhoseTurn();
    }

    private void clearAll() {
        for (Map.Entry<Position, Square> square : squares.entrySet()) {
            square.getValue().piece = null;
        }
    }

    private void setPieces() {
        for (Position position : board.getFields().keySet()) {
            Log.d("ChessBoardActivity", "setting piece at position " + position);
            if (squares.get(position) == null) {
                Log.d("ChessBoardActivity", "square on position " + position + " is null");
            }
            squares.get(position).piece = new Piece(board.getPiece(position));
        }
    }

    private void setDefaultColors() {
        clickedSquare = null;
        int colorBrown = Color.rgb(139,69,19); // BROWN
        int colorOrange = Color.rgb(255,140,0); // ORANGE
        for (Map.Entry<Position, Square> square : squares.entrySet()) {
            Log.d("ChessBoardActivity", square.getKey().toString());
            Position position = square.getKey();
            int keySum = position.column + position.row;
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
        if (playerMove) {
            alert.setPositiveButton("OK", null);
        } else {
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    opponentMove(board);
                }
            });
        }
        alert.show();
    }

    void setActionOnClick() {
        for (final Map.Entry<Position, Square> square : squares.entrySet()) {
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
            Position oldPosition = buttonTranslator.get(
                    Integer.toString(clickedSquare.button.getId()));
            Position newPosition = buttonTranslator.get(Integer.toString(square.button.getId()));
            try {
                GameData gameData = WebAgent.sendNewPosition(oldPosition, newPosition);
                manageGameData(gameData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (isTheSameClicked(square.button.getTag().toString())) {
            setDefaultColors();
        } else if (!isClicked() && 0 != square.button.getText().length() &&
                    isClickedOwnedByPlayer(square.piece)) {
            square.button.setBackgroundColor(Color.rgb(127, 255, 212)); // AQUAMARINE
            clickedSquare = square;
        }
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
        for (Map.Entry<Position, Square> square : squares.entrySet()) {
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
        if (square.piece.player == Player.player)
            square.button.setTextColor(playerColor);
        else square.button.setTextColor(opponentColor);
    }

    private void buttonSetText(Square square) {
        square.button.setTypeface(null, Typeface.BOLD);
        square.button.setText(Piece.toString(square.piece.type));
    }

    private void opponentMove(Chessboard board) {
        try {
            AutomatedCheckers automated = new AutomatedCheckers();
            manageGameData(automated.move(board));
        } catch (Exception e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Automated error");
            alert.setMessage("Automated player run into a problem: " + e.getMessage());
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }
}
