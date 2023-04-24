package socketServer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import messages.PriceFeed;
import messages.RequestStatus;
import messages.RequestStocks;
import messages.TradeLog;
import org.example.EnvVariables;
import org.fusesource.jansi.AnsiConsole;
import util.KryoUtil;



import java.io.IOException;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class SocketClient {

    public static void startClient(Client client, RequestStocks requestStocks) throws IOException {

        new Thread(client).start();
        client.connect(20_000, "localhost", EnvVariables.socketPort);


        Kryo kryo = client.getKryo();
        KryoUtil.kryoRegister(kryo);



        client.sendTCP(requestStocks);

        client.addListener(new Listener() {

            @Override
            public void connected(Connection connection) {
                System.out.println("Connected!");
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Disconnected!");
            }

            @Override
            public void received(Connection connection, Object o) {
                System.out.println("Received");
                if (o instanceof PriceFeed) {
                    printPriceFeed((PriceFeed) o);
                }
                if (o instanceof TradeLog) {
                }
                if (o instanceof RequestStatus) {
                    RequestStatus requestStatus = (RequestStatus) o;
                    System.out.println(requestStatus.getMessage());
                }
            }
        });


    }

    private static void printPriceFeed(PriceFeed priceFeed) {
        AnsiConsole.systemInstall();
        System.out.println(priceFeed);

    }

    private static void printTradeLog(TradeLog tradeLog) {
        System.out.println(tradeLog);
    }

}
