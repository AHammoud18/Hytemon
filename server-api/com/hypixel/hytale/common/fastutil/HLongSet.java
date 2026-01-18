package com.hypixel.hytale.common.fastutil;

import com.hypixel.hytale.function.predicate.LongTriIntBiObjPredicate;
import it.unimi.dsi.fastutil.longs.LongSet;

public interface HLongSet extends LongSet {
  <T, V> void removeIf(LongTriIntBiObjPredicate<T, V> paramLongTriIntBiObjPredicate, int paramInt1, int paramInt2, int paramInt3, T paramT, V paramV);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\fastutil\HLongSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */