package ChessboardDetail;

/**
 * Created by Ja on 2017-04-28.
 */
public class Field {
    public Field(Position pos) {
        position = pos;
        piece = null;
    }

    public Field(Position pos, Piece pie) {
        position = pos;
        piece = pie;
    }

    public Position getPosition() {
        return position;
    }

    public Piece piece;
    private Position position;
}
