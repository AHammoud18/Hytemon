package io.netty.channel.epoll;

import io.netty.channel.IoEvent;

public interface EpollIoEvent extends IoEvent {
  EpollIoOps ops();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollIoEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */