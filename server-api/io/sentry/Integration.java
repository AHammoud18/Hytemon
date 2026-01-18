package io.sentry;

import org.jetbrains.annotations.NotNull;

public interface Integration {
  void register(@NotNull IScopes paramIScopes, @NotNull SentryOptions paramSentryOptions);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Integration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */