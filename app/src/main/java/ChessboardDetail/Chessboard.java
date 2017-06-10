package ChessboardDetail;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Ja on 2017-04-28.
 */

public class Chessboard {
    public Chessboard(List<Field> fieldList) {
        fields = new HashMap<>();
        for (Field field : fieldList) {
            Log.d("Chessboard", "putting on " + field.getPosition().toString() + " piece " + field.piece.toString());
            fields.put(field.getPosition(), field.piece);
        }
    }

    public boolean isTaken(Position position) {
        return fields.containsKey(position);
    }

    public boolean isTaken(Position position, Player player) {
        Piece piece = fields.get(position);
        if (piece == null) {
            return false;
        }
        return piece.player == player;
    }

    public boolean movePiece(Position oldPosition, Position newPosition) {
        if (!isTaken(oldPosition)) return false;
        fields.put(newPosition, fields.get(oldPosition));
        fields.remove(oldPosition);
        return true;
    }

    public void removePiece(Position position) {
        fields.remove(position);
    }

    public Piece getPiece(Position position) {
        if (fields.containsKey(position)) {
            return fields.get(position);
        }
        return null;
    }

    public Player getPlayer(Position position) {
        Piece piece = getPiece(position);
        if (piece == null)
            return null;
        return piece.player;
    }

    public Set<Position> getTakenPositions() { return fields.keySet(); }

    public HashMap<Position, Piece> getFields() { return fields; }
    private HashMap<Position, Piece> fields;
}
