package socketServer.messages;

import com.esotericsoftware.kryonet.Connection;

import java.util.List;

public class ClientInfo {

    private Connection connection; // konekcija ka clientu

    private List<String> stocks; // stokovi koje je korisnik izabrao

    public ClientInfo(Connection connection, List<String> stocks) {
        this.connection = connection;
        this.stocks = stocks;
    }

    public ClientInfo() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<String> getStocks() {
        return stocks;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }
}
