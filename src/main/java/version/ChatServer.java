package version;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {
    private int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ChatServer(8000).run();
    }
    public void run(){
        EventLoopGroup bossgroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossgroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInit());

            bootstrap.bind(port).sync().channel().closeFuture().sync();

        }catch (Exception e){

        }finally {
            bossgroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
