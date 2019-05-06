package com.gxk.ext.cmd.invoke;

import com.gxk.ext.cmd.CmdOption;
import java.util.Arrays;
import java.util.Objects;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*
help

ls

rm <alias>
set <alias> <service>.<method>(arg1,"{}") <default_val1>,<default_val2>
e.g
set f1 com.gxk.demo.IHelloService.hello({}, {}) "test",true

# invoke
## direct call
test com.gxk.demo.IHelloService.hello("test", true)

## alias call
f1
f1 "test" true

 */
@Data
@Slf4j
public class InvokeOption implements CmdOption {

  private boolean help;

  private boolean ls;
  private boolean rm;
  private boolean set;

  private boolean testCall;
  private String cmd;

  private boolean aliasCall;
  private String alias;
  private String aliasTpl;
  private String aliasArgs;

  @Override
  public void parseArgs(String... args) {
    if (args.length == 0 || Objects.equals("help", args[0])) {
      help = true;
      return;
    }

    if (args.length == 1) {
      switch (args[0]) {
        case "ls":
          ls = true;
          return;
        default:
          aliasCall = true;
          alias = args[0];
          return;
      }
    }

    if (args.length == 2) {
      switch (args[0]) {
        case "test":
          testCall = true;
          cmd = args[1];
          return;
        case "rm":
          rm = true;
          alias = args[1];
          return;
        default:
          aliasCall = true;
          alias = args[0];
          aliasArgs = args[1];
          return;
      }
    }

    if (args.length == 3) {
      aliasCall = true;
      alias = args[0];
      aliasArgs = String.join(", ", Arrays.asList(args[1], args[2]));
      return;
    }

    if (args.length == 4) {
      switch (args[0]) {
        case "set":
          set = true;
          alias = args[1];
          aliasTpl = args[2];
          aliasArgs = args[3];
          return;
        default:
          aliasCall = true;
          alias = args[0];
          aliasArgs = String.join(", ", Arrays.asList(args[1], args[2], args[3]));
          return;
      }
    }

    aliasCall = true;
    alias = args[0];

    String[] argsArr = new String[args.length - 1];
    System.arraycopy(args, 1, argsArr, 0, argsArr.length);
    aliasArgs = String.join(", ", argsArr);

    return;
  }
}
