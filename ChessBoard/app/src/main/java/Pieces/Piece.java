package Pieces;

import java.util.Objects;

import WebCommunication.WebAgent;

public class Piece {
    public Player player;
    public Type type;

    public static Player toPlayer(String val)
    {
        if (Objects.equals(val, WebAgent.mLogin))
            return Player.player;
        //else if (val == "O")
        return Player.opponent;
    }

    public  static Type toType(String val)
    {
        if (Objects.equals(val, "P"))
            return Type.pawn;
        else if (Objects.equals(val, "R"))
            return Type.rook;
        else if (Objects.equals(val, "N"))
            return Type.knight;
        else if (Objects.equals(val, "B"))
            return Type.bishop;
        else if (Objects.equals(val, "Q"))
            return Type.queen;
        //else if (val == "K")
        return Type.king;
    }

    public static String toString(Type type)
    {
        if (type == Type.pawn)
            return "P";
        else if (type == Type.rook)
            return "R";
        else if (type == Type.knight)
            return "N";
        else if (type == Type.bishop)
            return "B";
        else if (type == Type.queen)
            return "Q";
        //else if (type == Type.king)
        return "K";
    }
}
