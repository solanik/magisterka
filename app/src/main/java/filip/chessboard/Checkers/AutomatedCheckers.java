package filip.chessboard.Checkers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import ChessboardDetail.Chessboard;
import ChessboardDetail.Piece;
import ChessboardDetail.Player;
import ChessboardDetail.Position;
import WebCommunication.GameData;
import WebCommunication.WebAgent;

/**
 * Created by Ja on 2017-04-29.
 */

public class AutomatedCheckers {
    private class MovePositions{
        MovePositions(Position old, Position neew) {
            oldPosition = old;
            newPosition = neew;
        }
        Position oldPosition;
        Position newPosition;
    }

    private enum DirectionHorizontal {
        Left, Right
    }

    private enum DirectionVertical {
        Up, Down
    }

    public GameData move(Chessboard board) throws Exception {
        MovePositions movePositions = MovePositions(board);
        if (movePositions == null) throw new Exception("No more moves available");
        GameData newData = WebAgent.sendNewPosition(movePositions.oldPosition,
                movePositions.newPosition);
        if (!newData.feedback.isEmpty()) throw new Exception(
                "Couldn't move from " + movePositions.oldPosition +
                " to new position " + movePositions.newPosition +
                ". Controller returned feedback: " + newData.feedback);
        return new GameData(newData.board, Player.player, "", newData.firstPlayer);
    }
    
    private MovePositions MovePositions(Chessboard board) {
        MovePositions movePositions = BeatingPositions(board);
        if (movePositions == null) movePositions = EmptyPosition(board);
        return movePositions;
    }

    private MovePositions BeatingPositions(Chessboard board) {
        for (Position position : board.getTakenPositions()) {
            MovePositions beatingPositions = BeatingPositions(board, position);
            if (beatingPositions != null) return beatingPositions;
        }
        return null;
    }

    private MovePositions BeatingPositions(Chessboard board, Position position) {
        if (board.isTaken(position, Player.opponent)) {
            Position newPosition = HopOverOpponent(board, position, DirectionHorizontal.Left,
                    DirectionVertical.Down);
            if (newPosition == null)
                newPosition = HopOverOpponent(board, position, DirectionHorizontal.Right,
                        DirectionVertical.Down);
            if (newPosition == null)
                newPosition = HopOverOpponent(board, position, DirectionHorizontal.Left,
                        DirectionVertical.Up);
            if (newPosition == null)
                newPosition = HopOverOpponent(board, position, DirectionHorizontal.Right,
                        DirectionVertical.Up);
            if (newPosition != null) return new MovePositions(position, newPosition);
        }
        return null;
    }

    private Position HopOverOpponent(Chessboard board, Position start,
                                     DirectionHorizontal horizontal, DirectionVertical vertical) {
        Position playerField = FieldWithPlayer(board, start, horizontal, vertical);
        if (playerField == null) return null;
        return EmptyField(board, playerField, horizontal, vertical);
    }

    private MovePositions EmptyPosition(Chessboard board) {
        for (Position position : board.getTakenPositions()) {
            MovePositions emptyPositions = EmptyPosition(board, position);
            if (emptyPositions != null) return emptyPositions;
        }
        return null;
    }

    private MovePositions EmptyPosition(Chessboard board, Position position) {
        if (board.isTaken(position, Player.opponent)) {
            Position newPosition = EmptyField(board, position, DirectionHorizontal.Left,
                    DirectionVertical.Down);
            if (newPosition == null)
                newPosition = EmptyField(board, position, DirectionHorizontal.Right,
                        DirectionVertical.Down);
            if (newPosition != null) return new MovePositions(position, newPosition);
        }
        return null;
    }

    private Position FieldWithPlayer(Chessboard board, Position pos, DirectionHorizontal horizontal,
                                     DirectionVertical vertical) {
        Position sidewaysMove = MoveSideways(pos, horizontal, vertical);
        if (board.isTaken(sidewaysMove, Player.player)) return sidewaysMove;
        return null;
    }

    private Position EmptyField(Chessboard board, Position pos, DirectionHorizontal horizontal,
                                DirectionVertical vertical) {
        Position sidewaysMove = MoveSideways(pos, horizontal, vertical);
        if (!board.isTaken(sidewaysMove)) return sidewaysMove;
        return null;
    }

    private Position MoveSideways(Position pos, DirectionHorizontal horizontal,
                                  DirectionVertical vertical) {
        int horizontalAddend = horizontal == DirectionHorizontal.Left ? -1 : 1;
        boolean columnBoundary = horizontal == DirectionHorizontal.Left ? pos.column > 0 : pos.column < 7;
        int verticalAddend = vertical == DirectionVertical.Down ? -1 : 1;
        boolean rowBoundary = vertical == DirectionVertical.Down ? pos.row > 0 : pos.row < 7;
        if (rowBoundary && columnBoundary)
            return new Position(pos.column + horizontalAddend, pos.row + verticalAddend);
        return null;
    }
}
