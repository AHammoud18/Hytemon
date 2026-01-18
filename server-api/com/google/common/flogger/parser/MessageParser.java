package com.google.common.flogger.parser;

public abstract class MessageParser {
  public static final int MAX_ARG_COUNT = 1000000;
  
  protected abstract <T> void parseImpl(MessageBuilder<T> paramMessageBuilder) throws ParseException;
  
  public abstract void unescape(StringBuilder paramStringBuilder, String paramString, int paramInt1, int paramInt2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parser\MessageParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */