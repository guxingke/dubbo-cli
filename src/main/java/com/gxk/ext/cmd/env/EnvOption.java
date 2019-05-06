package com.gxk.ext.cmd.env;

import com.gxk.ext.cmd.CmdOption;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/*

help

use default
use beta

rm default

set default --host=xxxxx --port=xxxx --link=xxx --charset=xxxx
 */
@Data
public class EnvOption implements CmdOption {

  private boolean help;

  private boolean use;
  private boolean rm;
  private boolean set;

  private String env;

  private String host;
  private int port;
  private String link;
  private String charset;

  @Override
  public void parseArgs(String... args) {
    if (args.length == 0 || args.length == 1) {
      help = true;
      return;
    }

    if (args.length == 2) {
      String arg = args[0];
      String newEnv = args[1];
      switch (arg) {
        case "use":
          use = true;
          env = newEnv;
          return;
        case "rm":
          rm = true;
          env = newEnv;
        default:
          System.out.println("illegal args");
          help = true;
          return;
      }
    }

    if (args.length == 6) {
      String arg = args[0];
      String newEnv = args[1];
      switch (arg) {
        case "set":
          set = true;
          env = newEnv;

          Map<String, String> params = new HashMap<>(4);
          for (int i = 2; i < args.length; i++) {
            String temp = args[i];
            String[] split = temp.split("=");
            if (split.length != 2 || !split[0].startsWith("--")) {
              System.out.println("illegal args");
              return;
            }
            String key = split[0];
            String val = split[1];

            params.put(key, val);
          }

          if (params.size() != 4) {
            System.out.println("illegal args");
            return;
          }

          String hostVal = params.get("--host");
          host = hostVal;

          String portVal = params.get("--port");
          port = Integer.parseInt(portVal);

          String linkVal = params.get("--link");
          link = linkVal;

          String charsetVal = params.get("--charset");
          charset = charsetVal;
          return;
        default:
          System.out.println("illegal args");
          help = true;
          return;
      }
    }

    System.out.println("illegal args");
    help = true;
    return;
  }
}
