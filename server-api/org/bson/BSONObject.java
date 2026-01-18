package org.bson;

import java.util.Map;
import java.util.Set;

public interface BSONObject {
  Object put(String paramString, Object paramObject);
  
  void putAll(BSONObject paramBSONObject);
  
  void putAll(Map paramMap);
  
  Object get(String paramString);
  
  Map toMap();
  
  Object removeField(String paramString);
  
  boolean containsField(String paramString);
  
  Set<String> keySet();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BSONObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */