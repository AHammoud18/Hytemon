package com.hypixel.hytale.common.map;

import com.hypixel.hytale.function.function.BiDoubleToDoubleFunction;
import com.hypixel.hytale.function.function.BiIntToDoubleFunction;
import com.hypixel.hytale.function.function.BiLongToDoubleFunction;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import javax.annotation.Nullable;

public interface IWeightedMap<T> {
  @Nullable
  T get(double paramDouble);
  
  @Nullable
  T get(DoubleSupplier paramDoubleSupplier);
  
  @Nullable
  T get(Random paramRandom);
  
  @Nullable
  T get(int paramInt1, int paramInt2, BiIntToDoubleFunction paramBiIntToDoubleFunction);
  
  @Nullable
  T get(long paramLong1, long paramLong2, BiLongToDoubleFunction paramBiLongToDoubleFunction);
  
  @Nullable
  T get(double paramDouble1, double paramDouble2, BiDoubleToDoubleFunction paramBiDoubleToDoubleFunction);
  
  @Nullable
  <K> T get(int paramInt1, int paramInt2, int paramInt3, SeedCoordinateFunction<K> paramSeedCoordinateFunction, K paramK);
  
  int size();
  
  boolean contains(T paramT);
  
  void forEach(Consumer<T> paramConsumer);
  
  void forEachEntry(ObjDoubleConsumer<T> paramObjDoubleConsumer);
  
  T[] internalKeys();
  
  T[] toArray();
  
  <K> IWeightedMap<K> resolveKeys(Function<T, K> paramFunction, IntFunction<K[]> paramIntFunction);
  
  @FunctionalInterface
  public static interface SeedCoordinateFunction<T> {
    double apply(int param1Int1, int param1Int2, int param1Int3, T param1T);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\map\IWeightedMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */