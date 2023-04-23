package socketServer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import gRpcServer.model.TradeLog;
import socketServer.messages.PriceFeed;
import socketServer.messages.RealTimePrice;
import socketServer.messages.RequestStatus;
import socketServer.messages.RequestStocks;
import util.KryoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        new Thread(client).start();
        client.connect(20_000, "localhost", 8001);

        Kryo kryo = client.getKryo();
        KryoUtil.registerKryoClasses(kryo);

//        String id = UUID.randomUUID().toString();
        String  id = "ivan";

        List<String> symbols = new ArrayList<>();
        symbols.add("A");
        symbols.add("AA");

        RequestStocks requestStocks = new RequestStocks(id, symbols);

        client.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof RequestStatus) {
                    System.out.println(((RequestStatus) o).getMessage());
                }
                if (o instanceof PriceFeed) {
                    PriceFeed priceFeed = (PriceFeed) o;
                    System.out.println(priceFeed);
                }
                if (o instanceof TradeLog) {
                    TradeLog tradeLog = (TradeLog) o;
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    System.out.println(tradeLog);
                }
            }
        });

        client.sendTCP(requestStocks);





    }

}