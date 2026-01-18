package io.netty.channel.pool;

import io.netty.channel.Channel;

public interface ChannelPoolHandler {
  void channelReleased(Channel paramChannel) throws Exception;
  
  void channelAcquired(Channel paramChannel) throws Exception;
  
  void channelCreated(Channel paramChannel) throws Exception;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\pool\ChannelPoolHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */