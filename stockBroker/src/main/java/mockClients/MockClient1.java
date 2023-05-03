package mockClients;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import gRpc.*;
import gRpcServer.model.TradeLog;
import globalData.EnvVariables;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import socketServer.messages.PriceFeed;
import socketServer.messages.RealTimePrice;
import socketServer.messages.RequestStatus;
import socketServer.messages.RequestStocks;
import util.KryoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

public class MockClient1 {


    private static final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", EnvVariables.RPC_PORT).usePlaintext().build();

    private static final StockServiceGrpc.StockServiceBlockingStub stub = StockServiceGrpc.newBlockingStub(channel);

    private static final List<Stock> stocks = new ArrayList<>();



    public static void main(String[] args) throws IOException, InterruptedException {



        getStocks(stub);

//        System.out.println(stocks);


        Client client = new Client();

        new Thread(client).start();
        client.connect(20_000, "localhost", 8001);

        Kryo kryo = client.getKryo();
        KryoUtil.registerKryoClasses(kryo);

        String  id = "ivan";

        List<String> symbols = new ArrayList<>();
        symbols.add("A");
        symbols.add("AA");

        RequestStocks requestStocks = new RequestStocks(id, symbols);

        client.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof RequestStatus) {
                    RequestStatus requestStatus = (RequestStatus) o;
                    System.out.println(requestStatus.getMessage());

                }
                if (o instanceof PriceFeed) {
                    PriceFeed priceFeed = (PriceFeed) o;
                    try {
                        printPriceFeed(priceFeed);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                if (o instanceof TradeLog) {
                    TradeLog tradeLog = (TradeLog) o;
                    printTrade(tradeLog);
                }
            }
        });

        client.sendTCP(requestStocks);

        Order order = Order.newBuilder().setSymbol("A").setPrice(200).setAction(Action.BUY).setQuantity(10).setId(id).build();

        stub.setOrder(order);

    }

    private static void getStocks(StockServiceGrpc.StockServiceBlockingStub stub) {

        Empty empty = Empty.newBuilder().build();

        Iterator<Stock> it = stub.getAllStocks(empty);

        while (it.hasNext()) {
            Stock s = it.next();
            stocks.add(s);
        }

    }


    private static void printPriceFeed(PriceFeed priceFeed) throws IOException {
        AnsiConsole.systemInstall();

        Ansi.Color color;

        Runtime.getRuntime().exec("clear");
        for (RealTimePrice price : priceFeed.getRealTimePrices()) {
            String change;
            if (price.getChange() >= 0) {
                color = GREEN;
                change = price.getChange() + "↑";
            }
            else {
                color = RED;
                change = price.getChange() + "↓";
            }

//            System.out.println( ansi().eraseScreen().fg(RED).a("Hello").fg(GREEN).a(" World").reset() );

            String output = price.getSymbol() + " " + price.getPrice() + " ";

            System.out.println(ansi().eraseScreen().a(output).fg(color).a(change).reset());

//            System.out.println(ansi().a(price.getSymbol()+ " ").a(price.getPrice() + " ").fg(color).a(change));
        }

        System.out.println("----------------------------------------------");

    }

    private static void printTrade(TradeLog tradeLog) {
        System.out.println(ansi().reset().eraseScreen().fg(Ansi.Color.GREEN).a("Trade ").a(tradeLog.getSymbol()).a(" completed ").a(" for price of ").a(tradeLog.getPrice()).reset());
    }






}