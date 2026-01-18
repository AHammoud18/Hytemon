package io.netty.channel;

import io.netty.bootstrap.ChannelFactory;

public interface ChannelFactory<T extends Channel> extends ChannelFactory<T> {
  T newChannel();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */