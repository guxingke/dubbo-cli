package com.gxk.ext;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import static org.junit.Assert.*;

public class MainTest {

  Main main;

  @Before
  public void setup() {
    main = new Main();
  }

  @Test
  public void test() {
    FormattingTuple tuple = MessageFormatter.arrayFormat("{} {} sdfa{}", new Object[]{"xxxxxxx", 1 , 2});
    String message = tuple.getMessage();
    assertTrue(message.contains("xxxxxxx"));
    assertTrue(main != null);
  }
}
