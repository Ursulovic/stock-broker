package util;

import com.esotericsoftware.kryo.Kryo;
import socketServer.messages.RealTimePrice;
import socketServer.messages.RequestStocks;

import java.util.ArrayList;
import java.util.UUID;

public class KryoUtil {

    public static void registerKryoClasses(Kryo kryo) {

        kryo.register(String.class);
        kryo.register(ArrayList.class);

        kryo.register(RequestStocks.class);
        kryo.register(RealTimePrice.class);

    }



}
