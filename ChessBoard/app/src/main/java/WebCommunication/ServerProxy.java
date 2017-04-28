package WebCommunication;

import java.util.List;

interface ServerProxy {
    String logIn(String login, String password);
    String register(String login, String password);
    GameData getStartSetting(String gameName) throws Exception;
    List<String> getFriends();
    List<String> getPlayerData(String name) throws Exception;
    String addFriend(String name);
    GameData sendNewPosition(String positions) throws Exception;
    String createNewGame(String gameName);
    String isOpponent(String gameName);
    List<String> getAwaitingGames(String gameName);
    String joinGame(String oponentName, String gameName);
    String sendMessage(String name, String message);
    String getNewMessage(String name);
}
