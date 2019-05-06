package com.gxk.ext.telnet.netty;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.Objects;

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
    byte[] bytes = (byte[]) msg;
    String mm = new String(bytes);
    data += mm;
    if (mm.endsWith("dubbo>")) {
      promise.setSuccess();
    }
  }

  //    @Override
//    public void channelRead(ChannelHandlerContext ctx, Objects msg) throws Exception {
//        log.info(msg);
//        data += msg;
//        promise.setSuccess();
//    }

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