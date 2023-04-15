package threads;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TradeLogger extends Thread{



    private String line;

    public TradeLogger(String line) {
        this.line = line;
    }

    @Override
    public void run() {


        try (FileWriter fw = new FileWriter("/home/ivan/Desktop/pds/StockSimulation/stockBroker/src/main/resources/tradeLog.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(line);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
