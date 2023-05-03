package gRpcServer.threads;

import com.google.gson.Gson;
import gRpcServer.StockBrokerImpl;

import java.io.*;

public class TradeLogger extends Thread{



    private static final Object fileLock = "fileLock";


    private Gson gson = new Gson();


    @Override
    public void run() {




        synchronized (fileLock) {


            try(FileWriter fw = new FileWriter("/Users/ivan/Desktop/stock-broker/stockBroker/src/main/resources/tradeLog.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
            {

                out.println(gson.toJson(StockBrokerImpl.tradeLogs));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
