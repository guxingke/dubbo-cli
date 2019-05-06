package com.gxk.ext.cmd;

import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelpCmd implements CmdHandler {

  @Override
  public void apply(CmdContext ctx) {
    InputStream is = this.getClass().getClassLoader().getResourceAsStream("help.txt");
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
      StringBuilder out = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        out.append(line).append("\n");
      }
      log.info(out.toString());
    } catch (Exception e) {
      log.error("io err", e);
    }
  }
}
