package com.github.luben.zstd;

public interface SequenceProducer {
  long getFunctionPointer();
  
  long createState();
  
  void freeState(long paramLong);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\SequenceProducer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */