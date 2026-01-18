package io.netty.channel.group;

import io.netty.channel.Channel;

public interface ChannelMatcher {
  boolean matches(Channel paramChannel);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\group\ChannelMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */