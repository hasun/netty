/**
 * Created by hasun on 2017-03-03.
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
    public static void main (String [] args ) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup , workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1)
                    .childOption(ChannelOption.SO_LINGER,0)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel (SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast (new LoggingHandler(LogLevel.INFO));
                            p.addLast (new EchoServerHandler());
                        }
                    });
//            ChannelFuture f = b.bind("localhost", 8888).sync();
//            f.channel().closeFuture().sync();
            ChannelFuture bindFuture = b.bind(8888);
            bindFuture.sync();
            Channel serverChannel = bindFuture.channel();
            ChannelFuture closeFuture = serverChannel.closeFuture();
            closeFuture.sync();
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
