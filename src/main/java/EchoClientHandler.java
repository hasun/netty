import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by hasun on 2017-03-03.
 */
public class EchoClientHandler  extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String sendMessage = "hello netty";
        ByteBuf messageBuffer = Unpooled.buffer();
        messageBuffer.writeBytes(sendMessage.getBytes());

        StringBuilder builder = new StringBuilder();
        builder.append("전송한 문자열 [");
        builder.append(sendMessage);
        builder.append("]");

        System.out.println(builder.toString());
        ctx.writeAndFlush(messageBuffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        String readMessage = ((ByteBuf) msg ).toString(Charset.defaultCharset());
        StringBuilder builder = new StringBuilder();
        builder.append("수신한 문자열 [");
        builder.append(readMessage);
        builder.append("]");

    }
}
