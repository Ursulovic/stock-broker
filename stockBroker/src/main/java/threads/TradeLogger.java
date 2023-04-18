package threads;

import com.google.gson.Gson;
import server.StockBrokerImpl;

import java.io.*;

public class TradeLogger extends Thread{



    private static final Object fileLock = "fileLock";

    public static File file = new File("src/main/java/resources/tradeLog.txt");

    private Gson gson = new Gson();


    @Override
    public void run() {




        synchronized (fileLock) {


            try(FileWriter fw = new FileWriter("/home/ivan/Desktop/pds/StockSimulation/stockBroker/src/main/resources/tradeLog.txt", false);
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
