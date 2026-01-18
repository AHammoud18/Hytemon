package com.hypixel.hytale.builtin.hytalegenerator.datastructures.bicoordinatecache;

public interface BiCoordinateCache<T> {
  T get(int paramInt1, int paramInt2);
  
  boolean isCached(int paramInt1, int paramInt2);
  
  T save(int paramInt1, int paramInt2, T paramT);
  
  void flush(int paramInt1, int paramInt2);
  
  void flush();
  
  int size();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\bicoordinatecache\BiCoordinateCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */