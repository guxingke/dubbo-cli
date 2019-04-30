package com.gxk.ext.telnet.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TelnetClient implements com.gxk.ext.telnet.TelnetClient {

  private final String host;
  private final int port;

  public TelnetClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public String exec(String cmd) {
    if (cmd == null) {
      return null;
    }

    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      TelnetClientHandler handler = new TelnetClientHandler();

      b.group(group)
          .channel(NioSocketChannel.class)
          .handler(new TelnetClientInitializer(handler));

      // Start the connection attempt.
      Channel ch = b.connect(host, port).sync().channel();

      while (!handler.isReady()) {
        Thread.sleep(10L);
      }

      ChannelPromise promise = handler.send(cmd + "\r\n");

      promise.await();
      return handler.getData();
    } catch (Exception e) {
      return "";
    } finally {
      group.shutdownGracefully();
    }
  }
}
