package io.netty.util.internal;

public interface PriorityQueueNode {
  public static final int INDEX_NOT_IN_QUEUE = -1;
  
  int priorityQueueIndex(DefaultPriorityQueue<?> paramDefaultPriorityQueue);
  
  void priorityQueueIndex(DefaultPriorityQueue<?> paramDefaultPriorityQueue, int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\PriorityQueueNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */