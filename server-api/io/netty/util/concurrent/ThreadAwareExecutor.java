package io.netty.util.concurrent;

import java.util.concurrent.Executor;

public interface ThreadAwareExecutor extends Executor {
  boolean isExecutorThread(Thread paramThread);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\ThreadAwareExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */