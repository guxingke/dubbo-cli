package com.gxk.ext.core;

import com.gxk.ext.logger.Log;

@FunctionalInterface
public interface CmdHandler {

  Log log = new Log();

  void apply(CmdContext ctx);
}
