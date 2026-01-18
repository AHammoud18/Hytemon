package io.netty.buffer;

public interface SizeClassesMetric {
  int sizeIdx2size(int paramInt);
  
  int sizeIdx2sizeCompute(int paramInt);
  
  long pageIdx2size(int paramInt);
  
  long pageIdx2sizeCompute(int paramInt);
  
  int size2SizeIdx(int paramInt);
  
  int pages2pageIdx(int paramInt);
  
  int pages2pageIdxFloor(int paramInt);
  
  int normalizeSize(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\SizeClassesMetric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */