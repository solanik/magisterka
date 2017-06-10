package WebCommunication;

import ChessboardDetail.Chessboard;
import ChessboardDetail.Player;

public class GameData {

    public GameData(Chessboard chessboard, Player player, String feed, Player first)
    {
        board = chessboard;
        playerMoving = player;
        feedback = feed;
        firstPlayer = first;
    }

    public Chessboard board;
    public Player playerMoving;
    public String feedback;
    public Player firstPlayer;
}
