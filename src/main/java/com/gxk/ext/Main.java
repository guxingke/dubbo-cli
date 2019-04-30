package com.gxk.ext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gxk.ext.cmd.InitCmd;
import com.gxk.ext.cmd.invoke.InvokeCmd;
import com.gxk.ext.cmd.LsCmd;
import com.gxk.ext.cmd.StatusCmd;
import com.gxk.ext.cmd.context.ContextCmd;
import com.gxk.ext.cmd.env.EnvCmd;
import com.gxk.ext.config.SystemConfig;
import com.gxk.ext.config.UserConfig;
import com.gxk.ext.constants.Const;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdRegistry;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    String home = System.getProperty("user.home");

    CmdRegistry registry = new CmdRegistry();
    registry.reg("ls", new LsCmd());
    registry.reg("init", new InitCmd());
    registry.reg("invoke", new InvokeCmd());
    registry.reg(Arrays.asList("st", "status"), new StatusCmd());
    registry.reg(Arrays.asList("ctx", "context"), new ContextCmd());
    registry.reg("env", new EnvCmd());

    registry.reg("help", ctx -> {
      UserConfig config = ctx.getUserConfig();
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String json = gson.toJson(config);
      System.out.println(json);
    });

    // check init
    Path configPath = Paths.get(home, Const.USER_CONFIG_FILE);
    if (!Files.exists(configPath)) {
      log.error("init first please.");
      System.exit(-2);
      return;
    }

    SystemConfig systemConfig = new SystemConfig();
    systemConfig.setConfigPath(configPath);

    UserConfig userConfig = UserConfig.load(configPath);

    if (args.length == 0) {
      args = new String[]{"help"};
    }
    String cmd = args[0];

    if (registry.get(cmd) == null) {
      System.exit(-1);
    }

    String[] newArgs;
    if (args.length == 1) {
      newArgs = new String[0];
    } else {
      newArgs = new String[args.length - 1];
      System.arraycopy(args, 1, newArgs, 0, args.length - 1);
    }

    CmdContext ctx = new CmdContext(systemConfig, userConfig, newArgs);
    registry.get(cmd).apply(ctx);
  }
}
