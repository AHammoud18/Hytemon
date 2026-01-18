package io.netty.channel;

public interface MaxMessagesRecvByteBufAllocator extends RecvByteBufAllocator {
  int maxMessagesPerRead();
  
  MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\MaxMessagesRecvByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */