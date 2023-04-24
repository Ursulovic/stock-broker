package client;

import gRpc.Stock;
import io.grpc.ManagedChannel;

import java.util.List;

public class Client {

    private static String id;

    private static ManagedChannel channel;

    private static List<String> stocksObserved;

    private static ClientData clientData = new ClientData();

    public static void main(String[] args) {
        parseArgs(args);



    }

    private static void parseArgs(String[] args) {
        id = args[1];
        for (int i = 2; i < args.length; i++)
            stocksObserved.add(args[i]);
    }



}
