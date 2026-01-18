package com.hypixel.hytale.server.core.io.transport;

import io.netty.channel.ChannelFuture;
import java.net.InetSocketAddress;

public interface Transport {
  TransportType getType();
  
  ChannelFuture bind(InetSocketAddress paramInetSocketAddress) throws InterruptedException;
  
  void shutdown();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\transport\Transport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */