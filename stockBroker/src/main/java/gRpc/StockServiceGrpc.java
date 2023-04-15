package gRpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: StockService.proto")
public final class StockServiceGrpc {

  private StockServiceGrpc() {}

  public static final String SERVICE_NAME = "StockService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gRpc.Empty,
      gRpc.Stock> getGetAllStocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllStocks",
      requestType = gRpc.Empty.class,
      responseType = gRpc.Stock.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<gRpc.Empty,
      gRpc.Stock> getGetAllStocksMethod() {
    io.grpc.MethodDescriptor<gRpc.Empty, gRpc.Stock> getGetAllStocksMethod;
    if ((getGetAllStocksMethod = StockServiceGrpc.getGetAllStocksMethod) == null) {
      synchronized (StockServiceGrpc.class) {
        if ((getGetAllStocksMethod = StockServiceGrpc.getGetAllStocksMethod) == null) {
          StockServiceGrpc.getGetAllStocksMethod = getGetAllStocksMethod = 
              io.grpc.MethodDescriptor.<gRpc.Empty, gRpc.Stock>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "StockService", "getAllStocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRpc.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRpc.Stock.getDefaultInstance()))
                  .setSchemaDescriptor(new StockServiceMethodDescriptorSupplier("getAllStocks"))
                  .build();
          }
        }
     }
     return getGetAllStocksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gRpc.Order,
      gRpc.Status> getSetOrderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "setOrder",
      requestType = gRpc.Order.class,
      responseType = gRpc.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gRpc.Order,
      gRpc.Status> getSetOrderMethod() {
    io.grpc.MethodDescriptor<gRpc.Order, gRpc.Status> getSetOrderMethod;
    if ((getSetOrderMethod = StockServiceGrpc.getSetOrderMethod) == null) {
      synchronized (StockServiceGrpc.class) {
        if ((getSetOrderMethod = StockServiceGrpc.getSetOrderMethod) == null) {
          StockServiceGrpc.getSetOrderMethod = getSetOrderMethod = 
              io.grpc.MethodDescriptor.<gRpc.Order, gRpc.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "StockService", "setOrder"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRpc.Order.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRpc.Status.getDefaultInstance()))
                  .setSchemaDescriptor(new StockServiceMethodDescriptorSupplier("setOrder"))
                  .build();
          }
        }
     }
     return getSetOrderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gRpc.FilterQuery,
      gRpc.Order> getFilterOrdersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "filterOrders",
      requestType = gRpc.FilterQuery.class,
      responseType = gRpc.Order.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<gRpc.FilterQuery,
      gRpc.Order> getFilterOrdersMethod() {
    io.grpc.MethodDescriptor<gRpc.FilterQuery, gRpc.Order> getFilterOrdersMethod;
    if ((getFilterOrdersMethod = StockServiceGrpc.getFilterOrdersMethod) == null) {
      synchronized (StockServiceGrpc.class) {
        if ((getFilterOrdersMethod = StockServiceGrpc.getFilterOrdersMethod) == null) {
          StockServiceGrpc.getFilterOrdersMethod = getFilterOrdersMethod = 
              io.grpc.MethodDescriptor.<gRpc.FilterQuery, gRpc.Order>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "StockService", "filterOrders"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRpc.FilterQuery.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRpc.Order.getDefaultInstance()))
                  .setSchemaDescriptor(new StockServiceMethodDescriptorSupplier("filterOrders"))
                  .build();
          }
        }
     }
     return getFilterOrdersMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StockServiceStub newStub(io.grpc.Channel channel) {
    return new StockServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StockServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new StockServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StockServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new StockServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class StockServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAllStocks(gRpc.Empty request,
        io.grpc.stub.StreamObserver<gRpc.Stock> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllStocksMethod(), responseObserver);
    }

    /**
     */
    public void setOrder(gRpc.Order request,
        io.grpc.stub.StreamObserver<gRpc.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getSetOrderMethod(), responseObserver);
    }

    /**
     */
    public void filterOrders(gRpc.FilterQuery request,
        io.grpc.stub.StreamObserver<gRpc.Order> responseObserver) {
      asyncUnimplementedUnaryCall(getFilterOrdersMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAllStocksMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                gRpc.Empty,
                gRpc.Stock>(
                  this, METHODID_GET_ALL_STOCKS)))
          .addMethod(
            getSetOrderMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gRpc.Order,
                gRpc.Status>(
                  this, METHODID_SET_ORDER)))
          .addMethod(
            getFilterOrdersMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                gRpc.FilterQuery,
                gRpc.Order>(
                  this, METHODID_FILTER_ORDERS)))
          .build();
    }
  }

  /**
   */
  public static final class StockServiceStub extends io.grpc.stub.AbstractStub<StockServiceStub> {
    private StockServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StockServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StockServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAllStocks(gRpc.Empty request,
        io.grpc.stub.StreamObserver<gRpc.Stock> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetAllStocksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setOrder(gRpc.Order request,
        io.grpc.stub.StreamObserver<gRpc.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetOrderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void filterOrders(gRpc.FilterQuery request,
        io.grpc.stub.StreamObserver<gRpc.Order> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getFilterOrdersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StockServiceBlockingStub extends io.grpc.stub.AbstractStub<StockServiceBlockingStub> {
    private StockServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StockServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StockServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<gRpc.Stock> getAllStocks(
        gRpc.Empty request) {
      return blockingServerStreamingCall(
          getChannel(), getGetAllStocksMethod(), getCallOptions(), request);
    }

    /**
     */
    public gRpc.Status setOrder(gRpc.Order request) {
      return blockingUnaryCall(
          getChannel(), getSetOrderMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<gRpc.Order> filterOrders(
        gRpc.FilterQuery request) {
      return blockingServerStreamingCall(
          getChannel(), getFilterOrdersMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StockServiceFutureStub extends io.grpc.stub.AbstractStub<StockServiceFutureStub> {
    private StockServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StockServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StockServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gRpc.Status> setOrder(
        gRpc.Order request) {
      return futureUnaryCall(
          getChannel().newCall(getSetOrderMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_STOCKS = 0;
  private static final int METHODID_SET_ORDER = 1;
  private static final int METHODID_FILTER_ORDERS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StockServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StockServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_STOCKS:
          serviceImpl.getAllStocks((gRpc.Empty) request,
              (io.grpc.stub.StreamObserver<gRpc.Stock>) responseObserver);
          break;
        case METHODID_SET_ORDER:
          serviceImpl.setOrder((gRpc.Order) request,
              (io.grpc.stub.StreamObserver<gRpc.Status>) responseObserver);
          break;
        case METHODID_FILTER_ORDERS:
          serviceImpl.filterOrders((gRpc.FilterQuery) request,
              (io.grpc.stub.StreamObserver<gRpc.Order>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StockServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StockServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gRpc.StockServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StockService");
    }
  }

  private static final class StockServiceFileDescriptorSupplier
      extends StockServiceBaseDescriptorSupplier {
    StockServiceFileDescriptorSupplier() {}
  }

  private static final class StockServiceMethodDescriptorSupplier
      extends StockServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StockServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StockServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StockServiceFileDescriptorSupplier())
              .addMethod(getGetAllStocksMethod())
              .addMethod(getSetOrderMethod())
              .addMethod(getFilterOrdersMethod())
              .build();
        }
      }
    }
    return result;
  }
}
