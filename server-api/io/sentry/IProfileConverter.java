package io.sentry;

import io.sentry.protocol.profiling.SentryProfile;
import java.io.IOException;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public interface IProfileConverter {
  @NotNull
  SentryProfile convertFromFile(@NotNull String paramString) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IProfileConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */