//package WebCommunication;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
//public class TestProxy implements ServerProxy {
//    String login_;
//
//    private String generateResponse(String success, String fail) {
//        Random rand = new Random();
//        if ((rand.nextInt() % 2) == 1)
//            return success;
//        else
//            return fail;
//    }
//
//    @Override
//    public String logIn(String login, String password) {
//        login_ = login;
//        return "loggedin";
//    }
//
//    @Override
//    public String register(String login, String password) {
//        return generateResponse("OK", "fail");
//    }
//
//    @Override
//    public GameData getStartSetting(String gameName) {
//        String id = login_;
//        List<String> positions = Arrays.asList("17:P:O", "37:P:O", "57:P:O", "77:P:O",
//                "06:P:O", "26:P:O", "46:P:O", "66:P:O", "15:P:O", "35:P:O", "55:P:O",
//                "75:P:O",
//                "00:P:"+id, "20:P:"+id, "40:P:"+id, "60:P:"+id, "11:P:"+id, "31:P:"+id,
//                "51:P:"+id, "71:P:"+id, "02:P:"+id, "22:P:"+id, "42:P:"+id, "62:P:"+id);
//        return new GameData(positions, id, "", id);
//    }
//
//    @Override
//    public List<String> getFriends() {
//        return Arrays.asList("ann", "hann", "mann");
//    }
//
//    @Override
//    public List<String> getPlayerData(String name) {
//        return Arrays.asList(name, "active", "79", "friend");
//    }
//
//    @Override
//    public String addFriend(String name) {
//        return generateResponse("OK", "fail");
//    }
//
//    @Override
//    public GameData sendNewPosition(String positions) {
//        return getStartSetting("chess");
//    }
//
//    @Override
//    public String createNewGame(String gameName) {
//        return generateResponse("OK", "fail");
//    }
//
//    @Override
//    public String isOpponent(String gameName) {
//        return generateResponse("O", "fail");
//    }
//
//    @Override
//    public List<String> getAwaitingGames(String gameName) {
//        return Collections.singletonList("O");
//    }
//
//    @Override
//    public String joinGame(String opponentName, String gameName) {
//        return generateResponse("O", "fail");
//    }
//
//    @Override
//    public String sendMessage(String name, String message) {
//        return generateResponse("O", "fail");
//    }
//
//    @Override
//    public String getNewMessage(String name) {
//        return "\n<div align=\"right\"><font color='green'>" + name +
//                ":patatatnias\nvery good</font></div>";
//    }
//}
