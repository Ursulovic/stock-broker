package serverStarter;

import gRpcServer.RpcServer;
import gRpcServer.StockBrokerImpl;
import globalData.EnvVariables;
import globalData.GlobalData;
import io.grpc.ServerBuilder;
import socketServer.SocketServer;

import java.io.IOException;

public class Server {

    private static final SocketServer socketServer = new SocketServer();

    private static final RpcServer rpcServer = new RpcServer();

    public static void main(String[] args) throws IOException, InterruptedException {

        GlobalData.loadInitStocks();

        socketServer.startServer();
        rpcServer.startServer();



    }



}
