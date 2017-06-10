package WebCommunication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ChessboardDetail.Position;

public class WebAgent {
    static public boolean logIn(String login, String password) {
        String result = mProxy.logIn(login, password);
        if (Objects.equals(result, "loggedin"))
        {
            mLogin = login;
            return true;
        }
        return false;
    }

    static public String register(String login, String password) {
        return mProxy.register(login, password);
    }

    static public List<String> getGames() {
        List<String> games = new ArrayList<>();
        games.add("chess");
        games.add("checkers");
        games.add("anti-chess");
        return games;
    }

    static public GameData getStartSetting(String gameName) throws Exception {
        return mProxy.getStartSetting(gameName);
    }

    static public List<String> getFriends() {
        return mProxy.getFriends();
    }

    static public List<String> getPlayerData(String name) throws Exception {
        return mProxy.getPlayerData(name);
    }

    static public String addFriend(String name) {
        return mProxy.addFriend(name);
    }

    static public GameData sendNewPosition(Position oldPosition, Position newPosition) throws Exception {
        return mProxy.sendNewPosition(oldPosition, newPosition);
    }

    static public String createNewGame(String gameName) {
        return mProxy.createNewGame(gameName);
    }

    static public String isOpponent(String gameName) {
        return mProxy.isOpponent(gameName);
    }

    static public List<String> getAwaitingGames(String gameName) {
        return mProxy.getAwaitingGames(gameName);
    }

    static public String joinGame(String oponentName, String gameName) {
        return mProxy.joinGame(oponentName, gameName);
    }

    static public String sendMessage(String name, String message) {
        return mProxy.sendMessage(name, message);
    }

    static public String getNewMessage(String name) {
        return mProxy.getNewMessage(name);
    }

    static public ServerProxy mProxy;
    static public String mLogin = null;
}
