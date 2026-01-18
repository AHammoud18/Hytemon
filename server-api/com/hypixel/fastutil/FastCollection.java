package com.hypixel.fastutil;

import java.util.Collection;

public interface FastCollection<E> extends Collection<E> {
  void forEachWithFloat(FastConsumerF<? super E> paramFastConsumerF, float paramFloat);
  
  void forEachWithInt(FastConsumerI<? super E> paramFastConsumerI, int paramInt);
  
  void forEachWithLong(FastConsumerL<? super E> paramFastConsumerL, long paramLong);
  
  <A, B, C, D> void forEach(FastConsumerD9<? super E, A, B, C, D> paramFastConsumerD9, A paramA, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, B paramB, C paramC, D paramD);
  
  <A, B, C, D> void forEach(FastConsumerD6<? super E, A, B, C, D> paramFastConsumerD6, A paramA, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, B paramB, C paramC, D paramD);
  
  @FunctionalInterface
  public static interface FastConsumerD6<A, B, C, D, E> {
    void accept(A param1A, B param1B, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, C param1C, D param1D, E param1E);
  }
  
  @FunctionalInterface
  public static interface FastConsumerD9<A, B, C, D, E> {
    void accept(A param1A, B param1B, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, C param1C, D param1D, E param1E);
  }
  
  @FunctionalInterface
  public static interface FastConsumerL<A> {
    void accept(A param1A, long param1Long);
  }
  
  @FunctionalInterface
  public static interface FastConsumerI<A> {
    void accept(A param1A, int param1Int);
  }
  
  @FunctionalInterface
  public static interface FastConsumerF<A> {
    void accept(A param1A, float param1Float);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\fastutil\FastCollection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */