package ChessboardDetail;

public enum Player {
    player,
    opponent;

    public Player getOpposite() {
        if (this.equals(Player.opponent))
            return Player.player;
        return Player.opponent;
    }
}
