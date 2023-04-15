package server;

import com.opencsv.CSVReader;
import gRpc.*;
import io.grpc.stub.StreamObserver;
import threads.TradeLogger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.*;

public class StockBrokerImpl extends StockServiceGrpc.StockServiceImplBase {

    private final Map<String, Stock> stockList;

    private final List<Order> buyOrders;

    private final List<Order> sellOrders;



    public StockBrokerImpl() {
        this.sellOrders = new ArrayList<>();
        this.buyOrders = new ArrayList<>();
        this.stockList = new HashMap<>();
        loadInitStocks();
    }

    @Override
    public void getAllStocks(Empty request, StreamObserver<Stock> responseObserver) {


        for (String s : stockList.keySet()) {
            responseObserver.onNext(stockList.get(s));
        }
        responseObserver.onCompleted();

    }


    @Override
    public void setOrder(Order request, StreamObserver<Status> responseObserver) {

        Status orderStatus;

        Order ord = null;

        TradeLogger tradeLogger;

        List<Order> list;


        if (!stockList.containsKey(request.getSymbol())) {
            orderStatus = Status.newBuilder().setResponseCode(3).setResponseMessage("Specified stock doesn't exist.").build();
            responseObserver.onNext(orderStatus);
            responseObserver.onCompleted();
            return;
        }


        if (request.getAction() == Action.BUY)
            list = sellOrders;
        else
            list = buyOrders;

        for (Order o : list) {
            if (o.getSymbol().equalsIgnoreCase(request.getSymbol())
                    && o.getPrice() == request.getPrice()
                    && o.getQuantity() == request.getQuantity()) {

                Stock stock1 = stockList.get(request.getSymbol());

                double change = calculatePriceChange(stockList.get(request.getSymbol()).getPrice(), request.getPrice());



                Stock stock2 = Stock.newBuilder()
                        .setDate(new Date().getTime())
                        .setSymbol(stock1.getSymbol())
                        .setName(stock1.getName())
                        .setPrice(request.getPrice())
                        .setPercentageChange(change)
                        .build();

                stockList.put(stock2.getSymbol(), stock2);
                ord = o;

            }
        }


        if (ord != null) {
            tradeLogger = new TradeLogger(generateLog(ord.getSymbol(), ord.getPrice()));
            tradeLogger.start();
            if (request.getAction() == Action.BUY)
                sellOrders.remove(ord);
            else
                buyOrders.remove(ord);
        } else {
            if (request.getAction() == Action.BUY)
                buyOrders.add(request);
            else
                sellOrders.add(request);
        }



        orderStatus = Status.newBuilder().setResponseCode(0).setResponseMessage("Success!").build();

        responseObserver.onNext(orderStatus);

        responseObserver.onCompleted();
    }

    private String generateLog(String symbol, double price) {
        return "STOCK WITH SYMBOL " +
                symbol +
                " SOLD FOR " +
                price +
                " DOLLARS AT "  + new Date();
    }

    private double calculatePriceChange(double oldPrice, double newPrice) {
        return (newPrice - oldPrice) / oldPrice * 100;
    }



    @Override
    public void filterOrders(FilterQuery request, StreamObserver<Order> responseObserver) {

        if (!stockList.containsKey(request.getSymbol())) {
            // resi
        }

        List<Order> orders = new ArrayList<>();


        if (request.getAction() == FilterAction.ASK) {

            for (Order o :
                    sellOrders) {
                if (o.getSymbol().equalsIgnoreCase(request.getSymbol()))
                    orders.add(o);
            }

            orders.sort((order, t1) -> (int) (t1.getPrice() - order.getPrice()));
        }
        else {
            for (Order o :
                    buyOrders) {
                if (o.getSymbol().equalsIgnoreCase(request.getSymbol()))
                    orders.add(o);
            }

            orders.sort((order, t1) -> (int) (order.getPrice() - t1.getPrice()));
        }

        for (int i = 0; i < request.getQuantity() && i < orders.size(); i++)
            responseObserver.onNext(orders.get(i));

        responseObserver.onCompleted();


    }

    private void loadInitStocks() {

        try (CSVReader reader = new CSVReader(new FileReader("/home/ivan/Desktop/pds/StockSimulation/stockBroker/src/main/resources/nasdaq_screener_1681137281094.csv"))) {

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

                stockList.put(symbol, stock);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Map<String, Stock> getStockList() {
        return stockList;
    }

    public List<Order> getBuyOrders() {
        return buyOrders;
    }

    public List<Order> getSellOrders() {
        return sellOrders;
    }
}
