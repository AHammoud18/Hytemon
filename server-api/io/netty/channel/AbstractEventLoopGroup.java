package io.netty.channel;

import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;

public abstract class AbstractEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
  public abstract EventLoop next();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\AbstractEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */