package ChessboardDetail;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Ja on 2017-04-28.
 */
public class Position implements Comparable<Position> {
    public Position(int c, int r) {
        column = c;
        row = r;
    }

    public int column;
    public int row;

    public String toString() {
        return "Row: " + Integer.toString(row) + " Column: " + Integer.toString(column);
    }

    @Override
    public boolean equals(Object obj) {
        if (!Position.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Position position = (Position) obj;
        Log.d("Position", "calling compare with " + toString() + " and " + position.toString());
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return column * 1000 + row;
    }

    @Override
    public int compareTo(@NonNull Position other) {
        return Integer.compare(column, other.column) * 10 + Integer.compare(row, other.row);
    }
}
