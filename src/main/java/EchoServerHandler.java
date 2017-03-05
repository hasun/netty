import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by hasun on 2017-03-03.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead (ChannelHandlerContext ctx , Object msg) throws Exception {
        ByteBuf readMessage = (ByteBuf) msg;
        System.out.println ("수신한 문자열 [" +readMessage.toString(Charset.defaultCharset())+ "]");
        ChannelFuture channelFuture = ctx.writeAndFlush(msg);
        channelFuture.addListener(ChannelFutureListener.CLOSE);

    }

//    @Override
//    public void channelReadComplete (ChannelHandlerContext ctx) {
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
