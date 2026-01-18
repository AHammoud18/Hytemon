package org.jline.style;

import java.util.Map;
import javax.annotation.Nullable;

public interface StyleSource {
  @Nullable
  String get(String paramString1, String paramString2);
  
  void set(String paramString1, String paramString2, String paramString3);
  
  void remove(String paramString);
  
  void remove(String paramString1, String paramString2);
  
  void clear();
  
  Iterable<String> groups();
  
  Map<String, String> styles(String paramString);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\StyleSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */