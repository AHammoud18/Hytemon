package org.bson.json;

interface JsonBuffer {
  int getPosition();
  
  int read();
  
  void unread(int paramInt);
  
  int mark();
  
  void reset(int paramInt);
  
  void discard(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */