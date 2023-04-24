package util;

import com.esotericsoftware.kryo.Kryo;
import messages.RequestStocks;
import messages.TradeLog;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class KryoUtil {

    public static void kryoRegister(Kryo kryo) {



        kryo.register(String.class);
        kryo.register(Integer.class);
        kryo.register(ArrayList.class);
        kryo.register(LinkageError.class);
        kryo.register(LocalDateTime.class);
        kryo.register(TradeLog.class);
        kryo.register(RequestStocks.class);

    }

}
