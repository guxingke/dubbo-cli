package com.gxk.ext.telnet.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class DubboTelnetDecoder extends ByteToMessageDecoder {

  private byte[] msg = new byte[0];
  private String end = "dubbo>";

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    int readableBytes = in.readableBytes();
    byte[] temp = new byte[readableBytes];
    in.readBytes(temp);

    byte[] agg = new byte[msg.length + temp.length];

    System.arraycopy(msg, 0, agg, 0, msg.length);
    System.arraycopy(temp, 0, agg, msg.length, temp.length);

    msg = agg;

    String str = new String(msg);
    if (str.endsWith(end)) {
      out.add(str);
    }
  }
}
