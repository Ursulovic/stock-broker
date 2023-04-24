package client;

import gRpc.Stock;

import java.util.List;

public class ClientData {

    private List<Stock> allStocks;

    public ClientData(List<Stock> allStocks) {
        this.allStocks = allStocks;
    }

    public ClientData() {
    }


}
