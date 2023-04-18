package util;

import com.esotericsoftware.kryo.Kryo;
import socketServer.messages.RealTimePrice;

public class KryoUtil {

    public static void registerKryoClasses(Kryo kryo) {

        kryo.register(RealTimePrice.class);

    }



}
