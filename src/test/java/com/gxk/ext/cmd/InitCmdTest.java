package com.gxk.ext.cmd;

import com.gxk.ext.core.CmdContext;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class InitCmdTest {

  @Test
  public void test() {
    InitCmd cmd = new InitCmd();
    cmd.apply(CmdContext.mockContext());
  }
}