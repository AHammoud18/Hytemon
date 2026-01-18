package com.hypixel.hytale.server.npc.util.expression.compile;

import java.text.ParseException;

public interface ParsedTokenConsumer {
  void pushOperand(Parser.ParsedToken paramParsedToken);
  
  void processOperator(Parser.ParsedToken paramParsedToken) throws ParseException;
  
  void processFunction(int paramInt) throws ParseException;
  
  void processTuple(Parser.ParsedToken paramParsedToken, int paramInt);
  
  void done();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\Parser$ParsedTokenConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */