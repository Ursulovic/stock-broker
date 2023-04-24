package socketServer.threads;

import gRpc.Stock;
import globalData.GlobalData;
import socketServer.messages.ClientInfo;
import socketServer.messages.PriceFeed;
import socketServer.messages.RealTimePrice;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class PriceFeedNotificator extends Thread{


    private long updateInterval;

    public PriceFeedNotificator(long updateInterval) {
        this.updateInterval = updateInterval;
    }

    @Override
    public void run() {



        while (true) {

            try {
                Thread.sleep(updateInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }



            for (Iterator<Map.Entry<String, ClientInfo>> it = GlobalData.userStocks.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, ClientInfo> entry = it.next();
                if (entry.getValue().getConnection() == null) {
                    it.remove();
                    continue;
                }
                PriceFeed priceFeed = new PriceFeed();

                for (String s : entry.getValue().getStocks()) {
                    Stock stock = GlobalData.stockList.get(s);

                    LocalDateTime date = Instant.ofEpochMilli(stock.getDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    LocalDate date2 = date.toLocalDate();
                    int hours = date.getHour();

                    RealTimePrice realTimePrice = new RealTimePrice(stock.getSymbol(),
                            date2,
                            hours,
                            stock.getPrice(),
                            stock.getPercentageChange());


                    priceFeed.getRealTimePrices().add(realTimePrice);
                }

                entry.getValue().getConnection().sendTCP(priceFeed);


            }

        }
    }


}
