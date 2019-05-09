package com.gxk.ext.telnet.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;

public class TelnetClientInitializer extends ChannelInitializer<SocketChannel> {

  private static final StringEncoder ENCODER = new StringEncoder();

  private final TelnetClientHandler handler;

  public TelnetClientInitializer(TelnetClientHandler handler) {
    this.handler = handler;
  }

  @Override
  public void initChannel(SocketChannel ch) {
    ChannelPipeline pipeline = ch.pipeline();

    pipeline.addLast(new DubboTelnetDecoder());
    pipeline.addLast(ENCODER);

    // and then business logic.
    pipeline.addLast(handler);
  }
}