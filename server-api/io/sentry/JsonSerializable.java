package io.sentry;

import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public interface JsonSerializable {
  void serialize(@NotNull ObjectWriter paramObjectWriter, @NotNull ILogger paramILogger) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JsonSerializable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */