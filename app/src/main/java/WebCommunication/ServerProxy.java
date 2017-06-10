package WebCommunication;

import java.util.List;

import ChessboardDetail.Position;

interface ServerProxy {
    String logIn(String login, String password);
    String register(String login, String password);
    GameData getStartSetting(String gameName) throws Exception;
    List<String> getFriends();
    List<String> getPlayerData(String name) throws Exception;
    String addFriend(String name);
    GameData sendNewPosition(Position oldPosition, Position newPosition) throws Exception;
    String createNewGame(String gameName);
    String isOpponent(String gameName);
    List<String> getAwaitingGames(String gameName);
    String joinGame(String opponentName, String gameName);
    String sendMessage(String name, String message);
    String getNewMessage(String name);
}
