package com.gxk.ext.cmd;

import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;
import com.gxk.ext.telnet.TelnetClient;
import com.gxk.ext.telnet.TelnetClientFactory;
import com.gxk.ext.util.Utils;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LsCmd implements CmdHandler {

  @Override
  public void apply(CmdContext ctx) {
    TelnetClient client = TelnetClientFactory
        .createClient(Utils.parseTelnetConfig(ctx.getUserConfig()));

    String[] args = ctx.getArgs();
    if (args.length == 0) {
      doLsAll(client);
      return;
    }

    if (args.length > 1) {
      log.error("illegal args");
      doHelp();
      return;
    }

    if (Objects.equals("help", args[0])) {
      doHelp();
      return;
    }

    doLsService(client, args[0]);
  }

  private void doLsService(TelnetClient client, String service) {
    String cmd = String.format("ls -l %s", service);
    String ret = client.exec(cmd);
    log.info(ret);
  }

  private void doHelp() {
    List<String> lines = Arrays.asList(
        "like dubbo telnet ls",
        "",
        "USEAGE:",
        "ls ",
        "ls xxx.xxxxService"
    );
    lines.forEach(System.out::println);
  }

  private void doLsAll(TelnetClient client) {
    log.info(client.exec("ls -l"));
  }
}
