package WebCommunication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TCPProxy implements ServerProxy {
    private TCPClient client;
    private String id;

    public TCPProxy(String ipToSet) {
        int portNumber = 8080;
        client = new TCPClient(ipToSet, portNumber);
    }

    public String logIn(String login, String password) {
        try {
            client.sendMessage("Login");
            String response = client.receiveMessage();
            if (Objects.equals(response.substring(0, 3), "id:"))
                id = response.substring(3);
            else
                return response;
            client.sendMessage(createMessageWithId("LoginData:ConnectReq"));
            sleep();
            client.sendMessage(createMessageWithId("LoginData:" + login));
            sleep();
            client.sendMessage(createMessageWithId("LoginData:" + password));
            return client.receiveMessage();
        } catch (Exception e) {
            id = "noConnection";
            e.printStackTrace();
        }
        return "loggedin";
    }

    private String createMessageWithId(String message) {
        return "Id:" + id + message;
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String register(String login, String password) {
        try {
            client.sendMessage("Register:"+login+"#"+password);
            return client.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "REGISTRATION FAILED";
    }

    public GameData getStartSetting(String gameName) {
        try {
            client.sendMessage(createMessageWithId("GameData:MoveReq"));
            String posData = client.receiveMessage();
            if (!Objects.equals(posData, "Echo: MoveReq")) {
                return getGameData(posData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> positions = (ArrayList<String>) Arrays.asList(
                "06:P:O", "16:P:O", "26:P:O", "36:P:O", "46:P:O", "56:P:O", "66:P:O",
                "76:P:O", "07:R:O", "17:N:O", "27:B:O", "37:Q:O", "47:K:O", "57:B:O",
                "67:N:O", "77:R:O", "01:P:"+id, "11:P:"+id, "21:P:"+id, "31:P:"+id,
                "41:P:"+id, "51:P:"+id, "61:P:"+id, "71:P:"+id, "00:R:"+id, "10:N:"+id,
                "20:B:"+id, "30:Q:"+id, "40:K:"+id, "50:B:"+id, "60:N:"+id, "70:R:"+id);
        return new GameData(positions, id, "", id);
    }

    private GameData getGameData(String gameData) {
        String delimiter = "@";
        String[] splitData = gameData.split(delimiter);
        return new GameData(getPositions(splitData[0]), splitData[1], splitData[2], splitData[3]);
    }

    private List<String> getPositions(String posData) {
        String delimiter = "#";
        String[] splitData = posData.split(delimiter);
        return new ArrayList<>(Arrays.asList(splitData));
    }

    public List<String> getFriends() {
        try {
            client.sendMessage(createMessageWithId("Friends"));
            String friends = client.receiveMessage();
            String delimiter = "#";
            return new ArrayList<>(Arrays.asList(friends.split(delimiter)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(Arrays.asList("ann", "hannah", "mann"));
    }

    public List<String> getPlayerData(String name) {
        try {
            client.sendMessage(createMessageWithId("PlayerInfo:" + name));
            String playerInfo = client.receiveMessage();
            String delimiter = "#";
            return new ArrayList<>(Arrays.asList(playerInfo.split(delimiter)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(Arrays.asList(name, "active", "79", "friend"));
    }

    public String addFriend(String name) {
        try {
            client.sendMessage(createMessageWithId("AddFriend:"+name));
            return client.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    public GameData sendNewPosition(String positions) {
        try {
            client.sendMessage(createMessageWithId("GameData:NewPosition:" + positions));
            return getGameData(client.receiveMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getStartSetting("chess");
    }

    public String createNewGame(String gameName) {
        try {
            client.sendMessage(createMessageWithId("CreateGame:" + gameName));
            return client.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    public String isOpponent(String gameName) {
        try {
            client.sendMessage(createMessageWithId("IsOpponent:" + gameName));
            client.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "O";
    }

    public List<String> getAwaitingGames(String gameName) {
        try {
            client.sendMessage(createMessageWithId("ListGames:" + gameName));
            String games = client.receiveMessage();
            String delimiter = "#";
            return new ArrayList<>(Arrays.asList(games.split(delimiter)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.singletonList("O");
    }

    public String joinGame(String oponentName, String gameName) {
        try {
            client.sendMessage(createMessageWithId(
                    "JoinGame:login:" + oponentName + ",gameName:" + gameName));
            return client.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    public String sendMessage(String name, String message) {
        try {
            client.sendMessage(createMessageWithId(
                    "SendMessage:login:" + name + "#message:" + message));
            return client.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    public String getNewMessage(String name) {
        try {
            client.sendMessage(createMessageWithId("GetNewMessage:login:" + name));
            return client.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "\n<div align=\"right\"><font color='green'>" + name +
                ":patatatnias\nvery good</font></div>";
    }
}
