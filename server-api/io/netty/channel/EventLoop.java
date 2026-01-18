package io.netty.channel;

import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.OrderedEventExecutor;

public interface EventLoop extends OrderedEventExecutor, EventLoopGroup {
  EventLoopGroup parent();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\EventLoop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */