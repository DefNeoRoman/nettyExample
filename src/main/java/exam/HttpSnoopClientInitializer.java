package exam;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;

public class HttpSnoopClientInitializer extends ChannelInitializer<SocketChannel> {





    protected void initChannel(SocketChannel ch) throws Exception {

                  ChannelPipeline p = ch.pipeline();

                  // Enable HTTPS if necessary.
                  p.addLast(new HttpClientCodec());
                  // Remove the following line if you don't want automatic content decompression.
                  p.addLast(new HttpContentDecompressor());
                  // Uncomment the following line if you don't want to handle HttpContents.
                  //p.addLast(new HttpObjectAggregator(1048576));

                  p.addLast(new HttpSnoopClientHandler());
              }

}
