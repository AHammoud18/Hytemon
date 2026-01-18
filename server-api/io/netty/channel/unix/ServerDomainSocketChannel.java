package io.netty.channel.unix;

import io.netty.channel.ServerChannel;
import java.net.SocketAddress;

public interface ServerDomainSocketChannel extends ServerChannel, UnixChannel {
  DomainSocketAddress remoteAddress();
  
  DomainSocketAddress localAddress();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\ServerDomainSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */