package io.netty.channel;

import java.util.Map;

public interface MaxBytesRecvByteBufAllocator extends RecvByteBufAllocator {
  int maxBytesPerRead();
  
  MaxBytesRecvByteBufAllocator maxBytesPerRead(int paramInt);
  
  int maxBytesPerIndividualRead();
  
  MaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int paramInt);
  
  Map.Entry<Integer, Integer> maxBytesPerReadPair();
  
  MaxBytesRecvByteBufAllocator maxBytesPerReadPair(int paramInt1, int paramInt2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\MaxBytesRecvByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */