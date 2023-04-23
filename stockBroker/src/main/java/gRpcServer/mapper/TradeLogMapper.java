package gRpcServer.mapper;

import gRpc.TradeLogMessage;
import gRpcServer.model.TradeLog;

public class TradeLogMapper {

    public static TradeLogMessage logToMessage(TradeLog log) {
        return TradeLogMessage.newBuilder()
                .setLog(log.toString())
                .build();
    }
}
