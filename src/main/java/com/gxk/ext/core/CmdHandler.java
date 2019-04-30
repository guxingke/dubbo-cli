package com.gxk.ext.core;

@FunctionalInterface
public interface CmdHandler {

  void apply(CmdContext ctx);
}
