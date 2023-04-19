package socketServer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import socketServer.messages.RequestStocks;
import util.KryoUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class SocketServer {

    private static Selector selector = null;

    private static Kryo kryo = new Kryo();

    private static ServerData serverData;


    public static void main(String[] args) throws IOException {


        KryoUtil.registerKryoClasses(kryo);

        selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();


        InetSocketAddress address = new InetSocketAddress("localhost", 8081);

        serverSocket.bind(address);

        int ops = serverSocket.getChannel().validOps();


        serverSocketChannel.register(selector, ops, null);

        while (true) {
            selector.select();

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> i = selectedKeys.iterator();

            while (i.hasNext()) {
                SelectionKey key = i.next();
                i.remove();

                if (key.isAcceptable())
                    handleAccept(serverSocketChannel, key);
                if (key.isReadable())
                    handleRead(key);

            }

        }



    }
    // 1. client se konektuje sa sererom
    // 2. client pise serveru
    // 3. server pise clientu




    private static void handleAccept(ServerSocketChannel mySocket, SelectionKey key) throws IOException {

        System.out.println("Connection Accepted..");

        // Accept the connection and set non-blocking mode
        SocketChannel client = mySocket.accept();
        client.configureBlocking(false);

        // Register that client is reading this channel
        client.register(selector, SelectionKey.OP_READ);
    }

    private static void handleRead(SelectionKey key) throws IOException {


        SocketChannel client = (SocketChannel) key.channel();



        Input kryoInput = new Input(((SocketChannel) key.channel()).socket().getInputStream());


        RequestStocks requestStocks = kryo.readObject(kryoInput, RequestStocks.class);

        System.out.println(requestStocks.toString());

        serverData.getUserStocks().put(requestStocks.getId().toString(), requestStocks.getStocks());

        client.register(selector, SelectionKey.OP_WRITE);



//        // Parse data from buffer to String
//        String data = new String(buffer.array()).trim();
//        if (data.length() > 0) {
//            System.out.println("Received message: " + data);
//            if (data.equalsIgnoreCase("I Love KirikoChan")) {
//                client.close();
//                System.out.println("Connection closed...");
//            }
//        }
    }





    static class testClient {


        public static void main(String[] args) {
            try {

                Kryo kryo1 = new Kryo();
                KryoUtil.registerKryoClasses(kryo1);


                UUID uuid = new UUID(1000, 10);

                List<String> stocks = new ArrayList<>();

                stocks.add("A");
                stocks.add("AA");

                RequestStocks requestStocks = new RequestStocks(uuid.toString(), stocks);


                SocketChannel socketChannel  = SocketChannel.open(new InetSocketAddress("localhost", 8081));

                Output output = new Output(socketChannel.socket().getOutputStream());


                kryo1.writeObject(output, requestStocks);



//
//                String[] messages
//                        = { "Non-Blocking servers are the best.",
//                        "I Love GeeksForGeeks",
//                        "I Love KirikoChan",};
//
//                SocketChannel socketChannel  = SocketChannel.open(new InetSocketAddress("localhost", 8081));
//
//                for (String msg : messages) {
//                    ByteBuffer buffer = ByteBuffer.allocate(1024);
//                    buffer.put(msg.getBytes());
//                    buffer.flip();
//                    int bytesWritten = socketChannel.write(buffer);
//                    System.out.println(String.format(
//                            "Sending Message: %s\nbufforBytes: %d",
//                            msg, bytesWritten));
//                }
//
//                socketChannel.close();
//                System.out.println("Client connection closed");

            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
