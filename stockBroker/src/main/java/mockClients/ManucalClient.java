package mockClients;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import gRpc.*;
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
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class ManucalClient {

    private static final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", EnvVariables.RPC_PORT).usePlaintext().build();

    private static final StockServiceGrpc.StockServiceBlockingStub stub = StockServiceGrpc.newBlockingStub(channel);

    private static final List<Stock> chosenStocks = new ArrayList<>();


    public static Client client = new Client();



    public static void main(String[] args) throws IOException {



        new Thread(client).start();
        client.connect(20_000, "localhost", 8001);

        configureClient(client);

        Kryo kryo = client.getKryo();
        KryoUtil.registerKryoClasses(kryo);




        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your id: ");

        String id = scanner.nextLine();

        while (true) {

            String input = scanner.nextLine();

            String[] params = input.split(" ");

            String command = params[0];



            String symbol;

            double price;

            int quantity;


            if (command.equalsIgnoreCase("BUY")) {


                symbol = params[1];
                price = Double.parseDouble(params[2]);
                quantity= Integer.parseInt(params[3]);


                addOrder(symbol, quantity, price, command, id);

            }
            else if (command.equalsIgnoreCase("SELL")) {

                symbol = params[1];
                price = Double.parseDouble(params[2]);
                quantity= Integer.parseInt(params[3]);


                addOrder(symbol, quantity, price, command, id);

            }
            else if (command.equalsIgnoreCase("ASK")) {

                symbol = params[1];

                String action = params[2];

                FilterAction filterAction;

                if (action.equalsIgnoreCase("ask"))
                    filterAction = FilterAction.ASK;
                else
                    filterAction= FilterAction.BID;


                quantity = Integer.parseInt(params[2]);

                filterStocks(symbol, filterAction, quantity);


            }
            else if (command.equalsIgnoreCase("BID")) {

                symbol = params[1];

                String action = params[2];

                FilterAction filterAction;

                if (action.equalsIgnoreCase("ask"))
                    filterAction = FilterAction.ASK;
                else
                    filterAction= FilterAction.BID;


                quantity = Integer.parseInt(params[2]);

                filterStocks(symbol, filterAction, quantity);



            } else if (command.equalsIgnoreCase("subscribe")) {

                symbol = params[1];

                List<String> stocks = new ArrayList<>();

                for (int i = 1; i < params.length; i++) {
                    stocks.add(params[i]);
                }

                subscribe(id, stocks);

            }


        }

    }

    public static void filterStocks(String symbol, FilterAction filterAction, int quantity) {

        FilterQuery filterQuery = FilterQuery.newBuilder()
                .setAction(filterAction)
                .setQuantity(quantity)
                .setSymbol(symbol)
                .build();


        Iterator<Order> it = stub.filterOrders(filterQuery);



        System.out.println(symbol);
        System.out.println("-------------------------------");
        while (it.hasNext()){
            Order entry = it.next();

            System.out.println(entry.getSymbol() + " " + entry.getPrice());

        }

        System.out.println("-------------------------------");



    }

    public static void subscribe(String id, List<String> stocks) {
        RequestStocks requestStocks = new RequestStocks(id, stocks);

        client.sendTCP(requestStocks);
    }


    public static void addOrder(String symnol, int quantity, double price, String action, String id){

        Action action1;

        if (action.equalsIgnoreCase("buy"))
            action1 = Action.BUY;
        else
            action1 = Action.SELL;

        Order order = Order.newBuilder()
                .setId(id)
                .setAction(action1)
                .setSymbol(symnol)
                .setQuantity(quantity)
                .setPrice(price)
                .build();

        Status status = stub.setOrder(order);

        System.out.println(status.getResponseMessage());

    }

    public static void configureClient(Client client) {

        client.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof PriceFeed) {
                    PriceFeed priceFeed = (PriceFeed)o;

                    printPriceFeed(priceFeed);

                }
                if (o instanceof RequestStatus) {
                    System.out.println(((RequestStatus) o).getMessage());
                }
            }
        });

    }



    public static void printPriceFeed(PriceFeed priceFeed) {

        Ansi.Color color;



        for (RealTimePrice price : priceFeed.getRealTimePrices()) {
            String change = "";
            if (price.getChange() >= 0) {
                color = Ansi.Color.GREEN;
                change = price.getChange() + "↑";
            }
            else {
                color = Ansi.Color.RED;
                change = price.getChange() + "↓";
            }
            System.out.println(ansi().a(price.getSymbol()+ " ").a(price.getPrice() + " ").fg(color).a(change));
        }

        System.out.println("----------------------------------------------");

    }



}
