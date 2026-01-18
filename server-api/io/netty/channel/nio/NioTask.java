package io.netty.channel.nio;

import java.nio.channels.SelectionKey;

public interface NioTask<C extends java.nio.channels.SelectableChannel> {
  void channelReady(C paramC, SelectionKey paramSelectionKey) throws Exception;
  
  void channelUnregistered(C paramC, Throwable paramThrowable) throws Exception;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\NioTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */