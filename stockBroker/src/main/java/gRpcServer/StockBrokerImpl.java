package gRpcServer;

import com.esotericsoftware.kryonet.Connection;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import gRpc.*;
import globalData.GlobalData;
import io.grpc.stub.StreamObserver;
import gRpcServer.mapper.TradeLogMapper;
import gRpcServer.model.TradeLog;
import gRpcServer.threads.TradeLogger;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StockBrokerImpl extends StockServiceGrpc.StockServiceImplBase {

    //svaka kompnaija ima svoje

    public static Map<String, List<Order>> buyOrders;

    public static Map<String, List<Order>> sellOrders;



    public static final Map<LocalDate, List<TradeLog>> tradeLogs = new HashMap<>();


    public StockBrokerImpl() {
        sellOrders = new ConcurrentHashMap<>();
        buyOrders = new ConcurrentHashMap<>();
        initTradeLogs();
        initStockLists();

    }

    @Override
    public void getAllStocks(Empty request, StreamObserver<Stock> responseObserver) {


        for (String s : GlobalData.stockList.keySet()) {
            responseObserver.onNext(GlobalData.stockList.get(s));
        }
        responseObserver.onCompleted();

    }


    @Override
    public void getLogs(TradesDate request, StreamObserver<TradeLogMessage> responseObserver) {

        List<TradeLog> logs = tradeLogs.get(LocalDate.parse(request.getDate()));




        if (logs != null) {
            for (TradeLog log : logs)
                responseObserver.onNext(TradeLogMapper.logToMessage(log));
        }


        responseObserver.onCompleted();

    }

    @Override
    public void setOrder(Order request, StreamObserver<Status> responseObserver) {

        Status orderStatus;

        Order forDeletion = null;

        TradeLogger tradeLogger;

        List<Order> list;


        if (!GlobalData.stockList.containsKey(request.getSymbol())) {
            orderStatus = Status.newBuilder().setResponseCode(3).setResponseMessage("Specified stock doesn't exist.").build();
            responseObserver.onNext(orderStatus);
            responseObserver.onCompleted();
            return;
        }


        if (request.getAction() == Action.BUY) {
            list = sellOrders.get(request.getSymbol());

        }
        else {
            list = buyOrders.get(request.getSymbol());
        }



        for (Order o : list) {
            if (o.getPrice() == request.getPrice()
                    && o.getQuantity() == request.getQuantity()) {

                Stock stock1 = GlobalData.stockList.get(request.getSymbol());

                double change = calculatePriceChange(GlobalData.stockList.get(request.getSymbol()).getPrice(), request.getPrice());



                Stock stock2 = Stock.newBuilder()
                        .setDate(new Date().getTime())
                        .setSymbol(stock1.getSymbol())
                        .setName(stock1.getName())
                        .setPrice(request.getPrice())
                        .setPercentageChange(change)
                        .build();

                GlobalData.stockList.put(stock2.getSymbol(), stock2);
                forDeletion = o;

                notifyUsers(request.getId(), o.getId(), o);

                break;


            }
        }


        if (forDeletion != null) {

            TradeLog tradeLog = new TradeLog(forDeletion.getSymbol(), forDeletion.getPrice());




            synchronized (tradeLogs) {
                if (!tradeLogs.containsKey(LocalDate.now())) {

                    List<TradeLog> logs = new ArrayList<>();

                    logs.add(tradeLog);

                    tradeLogs.put(LocalDate.now(), logs);
                }
                else
                    tradeLogs.get(LocalDate.now()).add(tradeLog);
            }


            tradeLogger = new TradeLogger();
            tradeLogger.start();

            // brisanje iz liste
            if (request.getAction() == Action.BUY)
                sellOrders.remove(forDeletion);
            else
                buyOrders.remove(forDeletion);
        } else {
            if (request.getAction() == Action.BUY)
                buyOrders.get(request.getSymbol()).add(request);
            else
                sellOrders.get(request.getSymbol()).add(request);
        }





        orderStatus = Status.newBuilder().setResponseCode(0).setResponseMessage("Success!").build();

        responseObserver.onNext(orderStatus);

        responseObserver.onCompleted();
    }



    private double calculatePriceChange(double oldPrice, double newPrice) {
        return (newPrice - oldPrice) / oldPrice * 100;
    }





    @Override
    public void filterOrders(FilterQuery request, StreamObserver<Order> responseObserver) {

//        if (!GlobalData.stockList.containsKey(request.getSymbol())) {
//            // resi
//        }

        List<Order> orders = new ArrayList<>();


        if (request.getAction() == FilterAction.ASK) {

            for (Order o : sellOrders.get(request.getSymbol())) {
                if (o.getSymbol().equalsIgnoreCase(request.getSymbol()))
                    orders.add(o);
            }

            orders.sort((order, t1) -> (int) (t1.getPrice() - order.getPrice()));
        }
        else {
            for (Order o :
                    buyOrders.get(request.getSymbol())) {
                if (o.getSymbol().equalsIgnoreCase(request.getSymbol()))
                    orders.add(o);
            }

            orders.sort((order, t1) -> (int) (order.getPrice() - t1.getPrice()));
        }

        for (int i = 0; i < request.getQuantity() && i < orders.size(); i++)
            responseObserver.onNext(orders.get(i));

        responseObserver.onCompleted();


    }

    private void initTradeLogs() {


        try {

            Gson gson = new Gson();

            Reader reader = Files.newBufferedReader(Paths.get("/Users/ivan/Desktop/stock-broker/stockBroker/src/main/resources/tradeLog.txt"));

            Map<?, ?> map = gson.fromJson(reader, Map.class);

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                LocalDate date = LocalDate.parse(entry.getKey().toString());

                List<TradeLog> logs = new ArrayList<>();


                for (Object o : ((List<?>) entry.getValue())) {
                    String s = o.toString();


                    String[] symbolAndPrice = s.split(" ");

                    String symbol = symbolAndPrice[0].substring(symbolAndPrice[0].indexOf("=") + 1).replace(",", "");

                    String priceString = symbolAndPrice[1].substring(symbolAndPrice[1].indexOf("=") + 1).replace("}", "");

                    double price = Double.parseDouble(priceString);

                    TradeLog tradeLog = new TradeLog(symbol, price);

                    logs.add(tradeLog);
                }

                tradeLogs.put(date, logs);

            }

            reader.close();




        }catch (Exception e) {
            e.printStackTrace();
        }





    }

    private void notifyUsers(String sellerId, String buyerId, Order o) {
        sendNotification(sellerId, o);
        sendNotification(buyerId, o);
    }
    private void sendNotification(String id, Order order) {


        Connection connection = GlobalData.userStocks.get(id).getConnection();

        if (connection != null) {
            TradeLog tradeLog = new TradeLog(order.getSymbol(), order.getPrice());
            connection.sendTCP(tradeLog);
        }

    }


    public static void initStockLists() {
        for (String s : GlobalData.stockList.keySet()) {
            List<Order> orders = new ArrayList<>();
            StockBrokerImpl.sellOrders.put(s, orders);
            StockBrokerImpl.buyOrders.put(s, orders);

        }
    }


    public Map<String, List<Order>> getBuyOrders() {
        return buyOrders;
    }

    public Map<String, List<Order>> getSellOrders() {
        return sellOrders;
    }
}
