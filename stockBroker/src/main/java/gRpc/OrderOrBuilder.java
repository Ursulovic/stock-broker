// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: StockService.proto

package gRpc;

public interface OrderOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Order)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string symbol = 1;</code>
   * @return The symbol.
   */
  java.lang.String getSymbol();
  /**
   * <code>string symbol = 1;</code>
   * @return The bytes for symbol.
   */
  com.google.protobuf.ByteString
      getSymbolBytes();

  /**
   * <code>double price = 2;</code>
   * @return The price.
   */
  double getPrice();

  /**
   * <code>uint32 quantity = 3;</code>
   * @return The quantity.
   */
  int getQuantity();

  /**
   * <code>.Action action = 4;</code>
   * @return The enum numeric value on the wire for action.
   */
  int getActionValue();
  /**
   * <code>.Action action = 4;</code>
   * @return The action.
   */
  gRpc.Action getAction();
}