package socketServer;


import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServerData {

    private Map<String, ClientInfo> userStocks; // contains key as id and [connection, stockList] tuple as value


    public ServerData() {
        this.userStocks = new HashMap<>();
    }

    public Map<String, ClientInfo> getUserStocks() {
        return userStocks;
    }

    public void setUserStocks(Map<String, ClientInfo> userStocks) {
        this.userStocks = userStocks;
    }


}
