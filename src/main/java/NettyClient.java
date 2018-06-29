import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;


public class NettyClient {
    public static void main(String[] args) throws Exception {

        String host = "185.9.185.49";
        int port = 9095;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(new RequestDataEncoder(),
                            new ResponseDataDecoder(), new ClientHandler());
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            HttpRequest httpRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"http://185.9.185.49:9095/reverse?format=xml&lat=51.5703648&lon=128.1748905");
            f.channel().writeAndFlush(httpRequest);

            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
