package com.gxk.ext.telnet.netty;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

@Sharable
public class TelnetClientHandler extends ChannelInboundHandlerAdapter {

  private ChannelHandlerContext ctx;
  private ChannelPromise promise;
  private String data = "";
  private volatile boolean ready;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    this.ctx = ctx;
    ready = true;
  }

  public ChannelPromise send(Object msg) {
    if (ctx == null) {
      throw new IllegalStateException();
    }
    promise = ctx.writeAndFlush(msg).channel().newPromise();
    return promise;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (((String) msg).endsWith("dubbo>")) {
      this.data = (String) msg;
      promise.setSuccess();
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

  public String getData() {
    return data;
  }

  public boolean isReady() {
    return ready;
  }
}