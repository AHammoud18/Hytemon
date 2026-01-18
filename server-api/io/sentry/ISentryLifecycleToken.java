package io.sentry;

public interface ISentryLifecycleToken extends AutoCloseable {
  void close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ISentryLifecycleToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */