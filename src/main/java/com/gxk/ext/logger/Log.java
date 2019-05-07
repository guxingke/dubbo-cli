package com.gxk.ext.logger;

public class Log {

  public void info(String msg) {
    System.out.println(msg);
  }

  public void error(String msg) {
    System.err.println(msg);
  }

  public void error(String msg, Throwable t) {
    System.err.println(msg);
    System.err.println(t.getMessage());
  }
}
