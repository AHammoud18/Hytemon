package io.netty.channel.unix;

import io.netty.channel.ChannelConfig;
import io.netty.channel.socket.DuplexChannel;
import java.net.SocketAddress;

public interface DomainSocketChannel extends UnixChannel, DuplexChannel {
  DomainSocketAddress remoteAddress();
  
  DomainSocketAddress localAddress();
  
  DomainSocketChannelConfig config();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\DomainSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */