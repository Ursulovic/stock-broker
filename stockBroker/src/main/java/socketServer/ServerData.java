package socketServer;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServerData {

    private Map<String, List<String>> userStocks;

    public ServerData() {
        this.userStocks = new HashMap<>();
    }


    public Map<String, List<String>> getUserStocks() {
        return userStocks;
    }

    public void setUserStocks(Map<String, List<String>> userStocks) {
        this.userStocks = userStocks;
    }
}
