package com.gxk.ext.cmd;

import com.gxk.ext.config.Context;
import com.gxk.ext.config.Env;
import com.gxk.ext.config.UserConfig;
import com.gxk.ext.constants.Const;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitCmd implements CmdHandler {

  @Override
  public void apply(CmdContext ctx) {
    String[] args = ctx.getArgs();

    if (args.length > 0) {
      log.info("init dubbo cli, gen default userConfig for current user.");
      return;
    }

    Path path = ctx.getSystemConfig().getConfigPath();
    if (path.toFile().exists()) {
      log.info("already init. nothing to do.");
      return;
    }

    try {
      Files.createDirectories(path.getParent());
    } catch (IOException e) {
      log.error("cmd err", e);
    }

    defaultConfig().save(path);
  }

  public static UserConfig defaultConfig() {
    UserConfig userConfig = new UserConfig();

    Map<String, Context> ctxs = new LinkedHashMap<>();
    Context defaultContext = new Context();

    Map<String, Env> defaultEnvs = new LinkedHashMap<>();
    defaultEnvs.put("local", new Env("127.0.0.1", 20880, Const.LINK_TELNET, Const.CHARSET_GBK));
    defaultContext.setEnvs(defaultEnvs);
    defaultContext.setActive("local");

    ctxs.put("default", defaultContext);

    Context testCtx = new Context();
    Map<String, Env> tes = new LinkedHashMap<>();
    tes.put("local", new Env("127.0.0.1", 31015, Const.LINK_TELNET, Const.CHARSET_GBK));
    tes.put("beta", new Env("10.1.2.51", 31015, Const.LINK_TELNET, Const.CHARSET_GBK));
    testCtx.setEnvs(tes);
    testCtx.setActive("local");
    ctxs.put("test", testCtx);

    userConfig.setContexts(ctxs);
    userConfig.setActive("default");
    return userConfig;
  }
}

