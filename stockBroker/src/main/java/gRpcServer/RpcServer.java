package gRpcServer;

import globalData.EnvVariables;
import globalData.GlobalData;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class RpcServer {

    public void startServer() throws IOException, InterruptedException {

            Server server = ServerBuilder
                    .forPort(EnvVariables.RPC_PORT)
                    .addService(new StockBrokerImpl())
                    .build();

            server.start();
            System.out.println("Rpc server running...");
            server.awaitTermination();


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GlobalData.loadInitStocks();
        RpcServer rpcServer = new RpcServer();
        rpcServer.startServer();
    }
}
