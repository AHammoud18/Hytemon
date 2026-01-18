package org.jline.reader;

public interface Expander {
  String expandHistory(History paramHistory, String paramString);
  
  String expandVar(String paramString);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\Expander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */