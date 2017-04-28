package WebCommunication;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Ja on 2017-04-26.
 */

public class CheckersProxy implements ServerProxy {
    String id;

    List<String> positions;

    private String generateResponse(String success, String fail) {
        Random rand = new Random();
        if ((rand.nextInt() % 2) == 1)
            return success;
        else
            return fail;
    }

    @Override
    public String logIn(String login, String password) {
        id = login;
        return "loggedin";
    }

    @Override
    public String register(String login, String password) {
        return generateResponse("OK", "fail");
    }

    @Override
    public GameData getStartSetting(String gameName) {
        positions = Arrays.asList("17:P:O", "37:P:O", "57:P:O", "77:P:O", "06:P:O",
                "26:P:O", "46:P:O", "66:P:O", "15:P:O", "35:P:O", "55:P:O", "75:P:O",
                "00:P:"+id, "20:P:"+id, "40:P:"+id, "60:P:"+id, "11:P:"+id, "31:P:"+id,
                "51:P:"+id, "71:P:"+id, "02:P:"+id, "22:P:"+id, "42:P:"+id, "62:P:"+id);
        return new GameData(positions, id, "", id);
    }

    @Override
    public List<String> getFriends() {
        return Arrays.asList("ann", "hann", "mann");
    }

    @Override
    public List<String> getPlayerData(String name) {
        return Arrays.asList(name, "active", "79", "friend");
    }

    @Override
    public String addFriend(String name) {
        return generateResponse("OK", "fail");
    }

    @Override
    public GameData sendNewPosition(String newPositions) {
        Log.d("CheckersProxy", newPositions);
        String feedback =
                checkIfMovePossible(newPositions.substring(0, 2), newPositions.substring(3, 5));
        if (feedback.isEmpty()) {
            movePawn(newPositions.substring(0, 2), newPositions.substring(3, 5));
        }
        return new GameData(positions, id, feedback, id);
    }

    private String checkIfMovePossible(String from, String to) {
        int fromRow = Character.getNumericValue(from.charAt(1));
        int toRow = Character.getNumericValue(to.charAt(1));
        if (toRow <= fromRow) {
            return "Pawns can only move forward";
        }
        int fromCol = Character.getNumericValue(from.charAt(0));
        int toCol = Character.getNumericValue(to.charAt(0));
        if (Math.abs(fromCol - toCol) != 1) {
            return "Pawns can only move one field diagonally";
        }
        return "";
    }

    private void movePawn(String from, String to) {
        for (int i = 0; i < positions.size(); ++i) {
            if (positions.get(i).substring(0, 2).equals(from)) {
                Log.d("CheckersProxy", positions.get(i));
                positions.set(i, to + positions.get(i).substring(2));
                Log.d("CheckersProxy", positions.get(i));
            }
        }
    }

    @Override
    public String createNewGame(String gameName) {
        return generateResponse("OK", "fail");
    }

    @Override
    public String isOpponent(String gameName) {
        return generateResponse("O", "fail");
    }

    @Override
    public List<String> getAwaitingGames(String gameName) {
        return Collections.singletonList("O");
    }

    @Override
    public String joinGame(String opponentName, String gameName) {
        return generateResponse("O", "fail");
    }

    @Override
    public String sendMessage(String name, String message) {
        return generateResponse("O", "fail");
    }

    @Override
    public String getNewMessage(String name) {
        return "\n<div align=\"right\"><font color='green'>" + name +
                ":patatatnias\nvery good</font></div>";
    }
}
