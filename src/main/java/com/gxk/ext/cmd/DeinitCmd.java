package com.gxk.ext.cmd;

import com.gxk.ext.constants.Const;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;
import com.gxk.ext.util.Utils;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeinitCmd implements CmdHandler {

  @Override
  public void apply(CmdContext ctx) {
    String homeDir = System.getProperty("user.home");

    Path dir = Paths.get(homeDir, Const.USER_CONFIG_DIR);
    Utils.rm(dir, true);
  }
}

