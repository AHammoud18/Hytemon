package io.netty.bootstrap;

@Deprecated
public interface ChannelFactory<T extends io.netty.channel.Channel> {
  T newChannel();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\ChannelFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */