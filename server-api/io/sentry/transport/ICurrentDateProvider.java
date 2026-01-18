package io.sentry.transport;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface ICurrentDateProvider {
  long getCurrentTimeMillis();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\ICurrentDateProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */