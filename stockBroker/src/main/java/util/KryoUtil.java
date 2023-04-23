package util;

import com.esotericsoftware.kryo.Kryo;
import gRpcServer.model.TradeLog;
import socketServer.messages.PriceFeed;
import socketServer.messages.RealTimePrice;
import socketServer.messages.RequestStatus;
import socketServer.messages.RequestStocks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class KryoUtil {

    public static void registerKryoClasses(Kryo kryo) {

        kryo.register(String.class);
        kryo.register(ArrayList.class);

        kryo.register(LocalDateTime.class);
        kryo.register(LocalDate.class);


        kryo.register(RequestStocks.class);
        kryo.register(RealTimePrice.class);
        kryo.register(RequestStatus.class);
        kryo.register(Process.class);
        kryo.register(PriceFeed.class);
        kryo.register(TradeLog.class);

    }



}
