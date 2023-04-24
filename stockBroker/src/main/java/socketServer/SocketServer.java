package socketServer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import globalData.EnvVariables;
import globalData.GlobalData;
import socketServer.constants.StatusCodes;
import socketServer.messages.ClientInfo;
import socketServer.messages.RequestStatus;
import socketServer.messages.RequestStocks;
import socketServer.threads.PriceFeedNotificator;
import util.KryoUtil;

import java.io.IOException;

public class SocketServer {




    public void startServer() throws IOException {


        Server server = new Server();
        server.start();
        server.bind(EnvVariables.SOCKET_PORT);

        Kryo kryo = server.getKryo();
        KryoUtil.registerKryoClasses(kryo);

        PriceFeedNotificator priceFeedNotificator = new PriceFeedNotificator(3000);
        priceFeedNotificator.setDaemon(true);
        priceFeedNotificator.start();

        server.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object o) {


                if (o instanceof RequestStocks) {
                    RequestStocks requestStocks = (RequestStocks) o;


                    RequestStatus requestStatus = validateRequestStocks(requestStocks);

                    connection.sendTCP(requestStatus);
                    if (requestStatus.getCode() == StatusCodes.OK)
                        initCommunication(connection, requestStocks);


                }

            }
        });

        System.out.println("Socket server running...");

    }



    private RequestStatus validateRequestStocks(RequestStocks requestStocks) {
        RequestStatus requestStatus;

        if (requestStocks.getId().isEmpty())
            return new RequestStatus("ID must be specified.!", StatusCodes.ID_MISSING_ERROR);
        else if (requestStocks.getStocks() == null || requestStocks.getStocks().isEmpty())
            return new RequestStatus("Stock list must contain at least 1 stock.", StatusCodes.EMPTY_OR_NULL_LIST);

        for (String s : requestStocks.getStocks()) {
            if (!GlobalData.stockList.containsKey(s)) {
                return new RequestStatus("Stock \"" + s + "\" doesn't exist", StatusCodes.NON_EXISTENT_STOCK);
            }
        }



        return new RequestStatus("Stock observing initiated!", StatusCodes.OK);
    }

    private void initCommunication(Connection connection, RequestStocks requestStocks) {

        ClientInfo clientInfo = new ClientInfo(connection, requestStocks.getStocks());

        GlobalData.userStocks.put(requestStocks.getId(), clientInfo);


    }









}
