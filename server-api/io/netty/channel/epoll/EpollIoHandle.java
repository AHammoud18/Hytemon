package io.netty.channel.epoll;

import io.netty.channel.IoHandle;
import io.netty.channel.unix.FileDescriptor;

public interface EpollIoHandle extends IoHandle {
  FileDescriptor fd();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollIoHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */