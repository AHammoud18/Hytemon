package io.netty.channel.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public interface SocketChannel extends DuplexChannel {
  ServerSocketChannel parent();
  
  SocketChannelConfig config();
  
  InetSocketAddress localAddress();
  
  InetSocketAddress remoteAddress();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\SocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */