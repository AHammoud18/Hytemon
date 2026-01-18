package org.bson.json;

public interface StrictJsonWriter {
  void writeName(String paramString);
  
  void writeBoolean(boolean paramBoolean);
  
  void writeBoolean(String paramString, boolean paramBoolean);
  
  void writeNumber(String paramString);
  
  void writeNumber(String paramString1, String paramString2);
  
  void writeString(String paramString);
  
  void writeString(String paramString1, String paramString2);
  
  void writeRaw(String paramString);
  
  void writeRaw(String paramString1, String paramString2);
  
  void writeNull();
  
  void writeNull(String paramString);
  
  void writeStartArray();
  
  void writeStartArray(String paramString);
  
  void writeStartObject();
  
  void writeStartObject(String paramString);
  
  void writeEndArray();
  
  void writeEndObject();
  
  boolean isTruncated();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\StrictJsonWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */