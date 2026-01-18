package io.sentry;

import java.io.IOException;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IEnvelopeReader {
  @Nullable
  SentryEnvelope read(@NotNull InputStream paramInputStream) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IEnvelopeReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */