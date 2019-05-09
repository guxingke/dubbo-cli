package com.gxk.ext.telnet.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import java.util.Arrays;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DubboTelnetDecoderTest {

  @Test
  public void test() {

    byte[] bytes = String.join("\r\n", Arrays.asList(
        "PROVIDER:",
        "com.dtx.pluto.client.biz.discovery.service.IStoryAuditingService:1.0",
        "dubbo>"
    )).getBytes();

    ByteBuf buf = Unpooled.buffer();
    buf.writeBytes(bytes);

    EmbeddedChannel channel = new EmbeddedChannel(new DubboTelnetDecoder());

    int all = buf.readableBytes();
    System.out.println(all);

    channel.writeInbound(buf.readBytes(2));
    channel.writeInbound(buf.readBytes(7));
    channel.writeInbound(buf.readBytes(all - 7 - 2));

    channel.finish();
  }
}