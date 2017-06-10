package WebCommunication;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ChessboardDetail.Chessboard;
import ChessboardDetail.Field;
import ChessboardDetail.Piece;
import ChessboardDetail.Player;
import ChessboardDetail.Position;
import ChessboardDetail.Type;

import static java.lang.Math.abs;

/**
 * Created by Ja on 2017-04-26.
 */

public class CheckersProxy implements ServerProxy {
    String id;

    Chessboard board;

    private final static String NO_MOVE_TAKEN = "Cannot move piece on opponents piece position";
    private final static String NO_MOVE_NOT_FORWARD = "Pawns can only move one field forward";
    private final static String NO_MOVE_NOT_DIAGONALLY = "Pawns can only move one field diagonally";
    private final static String NO_MOVE_NOT_TAKEN = "Pawn selected not on chessboard";

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
        board = new Chessboard(Arrays.asList(
                newPawn(1, 7, Player.opponent), newPawn(3, 7, Player.opponent),
                newPawn(5, 7, Player.opponent), newPawn(7, 7, Player.opponent),
                newPawn(0, 6, Player.opponent), newPawn(2, 6, Player.opponent),
                newPawn(4, 6, Player.opponent), newPawn(6, 6, Player.opponent),
                newPawn(1, 5, Player.opponent), newPawn(3, 5, Player.opponent),
                newPawn(5, 5, Player.opponent), newPawn(7, 5, Player.opponent),

                newPawn(0, 0, Player.player), newPawn(2, 0, Player.player),
                newPawn(4, 0, Player.player), newPawn(6, 0, Player.player),
                newPawn(1, 1, Player.player), newPawn(3, 1, Player.player),
                newPawn(5, 1, Player.player), newPawn(7, 1, Player.player),
                newPawn(0, 2, Player.player), newPawn(2, 2, Player.player),
                newPawn(4, 2, Player.player), newPawn(6, 2, Player.player)));
        return new GameData(board, Player.player, "", Player.player);
    }

    private Field newPawn(int col, int row, Player player) {
        return new Field(new Position(col, row), new Piece(player, Type.pawn));
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
    public GameData sendNewPosition(Position oldPosition, Position newPosition) {
        Player movingPlayer = board.getPlayer(oldPosition);
        String feedback = "";
        Position beatPiece = BeatPiece(oldPosition, newPosition, movingPlayer);
        if (beatPiece != null) board.removePiece(beatPiece);
        else feedback = isPlainMove(oldPosition, newPosition, movingPlayer);
        if (feedback.isEmpty() && board.movePiece(oldPosition, newPosition)) {
            movingPlayer = movingPlayer.getOpposite();
        } else if (feedback.isEmpty()) {
            feedback = NO_MOVE_NOT_TAKEN;
        }
        return new GameData(board, movingPlayer, feedback, Player.player);
    }

    private Position BeatPiece(Position oldPosition, Position newPosition, Player player) {
        if (board.isTaken(newPosition)) return null;
        Position between = getBetween(oldPosition, newPosition);
        if (between != null && board.isTaken(between, player.getOpposite())) return between;
        return null;
    }

    private Position getBetween(Position oldPosition, Position newPosition) {
        if (abs(oldPosition.row - newPosition.row) == 2 &&
                abs(oldPosition.column - newPosition.column) == 2)
            return new Position(oldPosition.column - (oldPosition.column - newPosition.column)/2,
                    oldPosition.row - (oldPosition.row - newPosition.row)/2);
        return null;
    }

    private String isPlainMove(Position oldPosition, Position newPosition, Player player) {
        if (board.isTaken(newPosition)) {
            return NO_MOVE_TAKEN;
        }
        if (!isForward(oldPosition, newPosition, player)) {
            return NO_MOVE_NOT_FORWARD;
        }
        if (abs(newPosition.column - oldPosition.column) != 1) {
            return NO_MOVE_NOT_DIAGONALLY;
        }
        return "";
    }

    private boolean isForward(Position oldPosition, Position newPosition, Player player) {
        int answer = player.equals(Player.player) ? 1 : -1;
        return newPosition.row - oldPosition.row == answer;
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
