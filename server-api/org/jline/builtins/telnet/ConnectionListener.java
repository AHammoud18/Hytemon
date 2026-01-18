package org.jline.builtins.telnet;

public interface ConnectionListener {
  default void connectionIdle(ConnectionEvent ce) {}
  
  default void connectionTimedOut(ConnectionEvent ce) {}
  
  default void connectionLogoutRequest(ConnectionEvent ce) {}
  
  default void connectionSentBreak(ConnectionEvent ce) {}
  
  default void connectionTerminalGeometryChanged(ConnectionEvent ce) {}
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\telnet\ConnectionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */