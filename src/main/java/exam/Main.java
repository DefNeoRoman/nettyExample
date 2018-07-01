package exam;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    static final String URL = "http://185.9.185.49:9095/reverse?format=xml&lat=51.5703648&lon=128.1748905";

    public static void main(String[] args) {
        URI uri = null;
        try {
            uri = new URI(URL);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String host =  uri.getHost();
        int port = uri.getPort();
        EventLoopGroup group = new NioEventLoopGroup();
             try {
                     Bootstrap b = new Bootstrap();
                     b.group(group)
                      .channel(NioSocketChannel.class)
                      .handler(new HttpSnoopClientInitializer());

                     // Make the connection attempt.
                     Channel ch = b.connect(host, port).sync().channel();

                     // Prepare the HTTP request.
                     HttpRequest request = new DefaultFullHttpRequest(
                                 HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
                     request.headers().set(HttpHeaderNames.HOST, host);
                     request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
                     request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);

                       // Set some example cookies.

                       // Send the HTTP request.
                       ch.writeAndFlush(request);

                       // Wait for the server to close the connection.
                       ch.closeFuture().sync();
                   } catch (InterruptedException e) {
                 e.printStackTrace();
             } finally {
                       // Shut down executor threads to exit.
                       group.shutdownGracefully();
                   }
    }
}
