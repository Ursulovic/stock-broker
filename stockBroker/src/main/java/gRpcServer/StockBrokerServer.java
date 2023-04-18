package gRpcServer;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class StockBrokerServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder
                .forPort(8000)
                .addService(new StockBrokerImpl())
                .build();

        server.start();
        System.out.println("Server running...");
        server.awaitTermination();


    }
}
