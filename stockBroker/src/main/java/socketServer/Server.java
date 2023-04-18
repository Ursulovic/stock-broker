package socketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private static Selector selector = null;

    public static void main(String[] args) throws IOException {

        selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        InetSocketAddress address = new InetSocketAddress("localhost", 8081);

        serverSocketChannel.socket().bind(address);



        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);

        while (true) {
            selector.select(1000);

            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

            while (selectedKeys.hasNext()) {
                SelectionKey key = selectedKeys.next();
                selectedKeys.remove();

                if (!key.isAcceptable())
                    continue;

                if (key.isAcceptable())
                    acceptConnection(key);


            }



        }

    }

    private static void acceptConnection(SelectionKey key) throws IOException {
        System.out.println("Connection accepted...");

        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_READ);

    }
}
