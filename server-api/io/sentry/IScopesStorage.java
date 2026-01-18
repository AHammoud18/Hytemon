package io.sentry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Internal
public interface IScopesStorage {
  void init();
  
  @NotNull
  ISentryLifecycleToken set(@Nullable IScopes paramIScopes);
  
  @Nullable
  IScopes get();
  
  void close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IScopesStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */