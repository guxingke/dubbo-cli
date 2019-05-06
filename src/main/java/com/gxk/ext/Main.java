package com.gxk.ext;

import com.gxk.ext.cmd.ConfigCmd;
import com.gxk.ext.cmd.DeinitCmd;
import com.gxk.ext.cmd.HelpCmd;
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
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    String home = System.getProperty("user.home");

    CmdRegistry registry = new CmdRegistry();
    registry.reg("ls", new LsCmd());
    registry.reg("init", new InitCmd());
    registry.reg("deinit", new DeinitCmd());
    registry.reg("invoke", new InvokeCmd());
    registry.reg(Arrays.asList("st", "status"), new StatusCmd());
    registry.reg(Arrays.asList("ctx", "context"), new ContextCmd());
    registry.reg("env", new EnvCmd());
    registry.reg("help", new HelpCmd());
    registry.reg("config", new ConfigCmd());
    registry.reg("version", ctx -> {
      log.info("0.1.0-SNAPSHOT");
    });

    Path configPath = Paths.get(home, Const.USER_CONFIG_FILE);

    // check init
    boolean needInit = needInit(args);
    boolean init = Files.exists(configPath);
    if (needInit && !init) {
      log.error("init first please.");
      System.exit(-2);
      return;
    }

    if (args.length == 0) {
      args = new String[]{"help"};
    }
    String cmd = args[0];
    String[] newArgs = normalizeArgs(args);

    if (registry.get(cmd) == null) {
      log.error("unknown cmd");
      System.exit(-1);
    }

    CmdContext ctx = initCmdContext(configPath, cmd, newArgs);
    registry.get(cmd).apply(ctx);
  }

  private static String[] normalizeArgs(String[] args) {
    String[] newArgs;
    if (args.length == 1) {
      newArgs = new String[0];
    } else {
      newArgs = new String[args.length - 1];
      System.arraycopy(args, 1, newArgs, 0, args.length - 1);
    }
    return newArgs;
  }

  private static CmdContext initCmdContext(Path configPath, String cmd, String[] args) {
    SystemConfig systemConfig = new SystemConfig();
    systemConfig.setConfigPath(configPath);

    UserConfig userConfig = null;
    if (needInit(cmd)) {
      userConfig = UserConfig.load(configPath);
    }
    return new CmdContext(systemConfig, userConfig, args);
  }

  private static boolean needInit(String[] cmds) {
    if (cmds.length == 0) {
      return false;
    }
    String cmd = cmds[0];
    return needInit(cmd);
  }

  private static boolean needInit(String cmd) {
    List<String> ret = Arrays.asList(
        "init",
        "help",
        "version"
    );
    return !ret.contains(cmd);
  }
}
