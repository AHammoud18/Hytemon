package io.sentry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public interface JsonDeserializer<T> {
  @NotNull
  T deserialize(@NotNull ObjectReader paramObjectReader, @NotNull ILogger paramILogger) throws Exception;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JsonDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */