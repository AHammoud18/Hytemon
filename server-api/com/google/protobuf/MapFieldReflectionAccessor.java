package com.google.protobuf;

import java.util.List;

public abstract class MapFieldReflectionAccessor {
  abstract List<Message> getList();
  
  abstract List<Message> getMutableList();
  
  abstract Message getMapEntryMessageDefaultInstance();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MapFieldReflectionAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */