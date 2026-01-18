package com.google.protobuf;

import java.util.List;

@CheckReturnValue
interface ListFieldSchema {
  <L> List<L> mutableListAt(Object paramObject, long paramLong);
  
  void makeImmutableListAt(Object paramObject, long paramLong);
  
  <L> void mergeListsAt(Object paramObject1, Object paramObject2, long paramLong);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ListFieldSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */