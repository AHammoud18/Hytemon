package io.netty.channel.unix;

import io.netty.channel.Channel;

public interface UnixChannel extends Channel {
  FileDescriptor fd();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\UnixChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */