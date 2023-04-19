package socketServer.messages;

import java.util.List;
import java.util.UUID;

public class RequestStocks {

    private String id;

    private List<String> stocks;

    public RequestStocks(String id, List<String> stocks) {
        this.id = id;
        this.stocks = stocks;
    }

    public RequestStocks() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getStocks() {
        return stocks;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "RequestStocks{" +
                "id=" + id +
                ", stocks=" + stocks +
                '}';
    }
}
