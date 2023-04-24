package globalData;

import com.opencsv.CSVReader;
import gRpc.Stock;
import socketServer.messages.ClientInfo;

import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalData {

    public static final Map<String, Stock> stockList = new ConcurrentHashMap<>();


    public static final Map<String, ClientInfo> userStocks = new ConcurrentHashMap<>(); // contains key as id and [connection, stockList] tuple as value




    public synchronized static void loadInitStocks() {

        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/nasdaq_screener_1681137281094.csv"))) {

            List<String[]> s = reader.readAll();

            for (int i = 1; i < s.size(); i++) {
                String symbol = s.get(i)[0];
                String name = s.get(i)[1];
                double lastSale = Double.parseDouble(s.get(i)[2].replace("$", ""));
                double change = Double.parseDouble(s.get(i)[3].replace("%", ""));
                long date = new Date().getTime();

                Stock stock = Stock.newBuilder()
                        .setName(name)
                        .setPrice(lastSale)
                        .setPercentageChange(change)
                        .setDate(date)
                        .setSymbol(symbol).build();

                GlobalData.stockList.put(symbol, stock);



            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
