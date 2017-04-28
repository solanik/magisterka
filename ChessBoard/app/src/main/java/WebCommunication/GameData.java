package WebCommunication;

import java.util.List;

public class GameData {
    GameData(List<String> pos, String play, String feed, String first)
    {
        positions = pos;
        playerMoving = play;
        feedback = feed;
        firstPlayer = first;
    }

    public List<String> positions;
    public String playerMoving;
    public String feedback;
    public String firstPlayer;
}
