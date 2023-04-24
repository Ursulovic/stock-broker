package org.example;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import gRpc.Empty;
import gRpc.Stock;
import gRpc.StockServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import messages.PriceFeed;
import messages.RequestStatus;
import messages.RequestStocks;
import messages.TradeLog;
import socketServer.SocketClient;
import util.KryoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Main {

    private static String id;

    private static final List<Stock> stocks = new ArrayList<>();

    private static final List<String> chosenStocks = new ArrayList<>();

    private static final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",EnvVariables.rpcPort).usePlaintext().build();

    private static final StockServiceGrpc.StockServiceBlockingStub stub = StockServiceGrpc.newBlockingStub(channel);

    private static Client client = new Client(1000000, 1000000);


    public static void main(String[] args) throws IOException {
        getStocks(stub);
        parseArgs(args);
        RequestStocks requestStocks = new RequestStocks(id, chosenStocks);
        SocketClient.startClient(client, requestStocks);














    }

    private static void parseArgs(String[] args) {
        id = args[0];
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                chosenStocks.add(args[i]);
            }
        }
        else {
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                int val = random.nextInt(stocks.size());
                chosenStocks.add(stocks.get(val).getSymbol());
            }
        }
    }

    private static void getStocks(StockServiceGrpc.StockServiceBlockingStub stub) {

        Empty empty = Empty.newBuilder().build();

        Iterator<Stock> it = stub.getAllStocks(empty);

        while (it.hasNext()) {
            Stock s = it.next();
            stocks.add(s);
        }




    }



}