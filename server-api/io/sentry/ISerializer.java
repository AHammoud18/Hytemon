package io.sentry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ISerializer {
  @Nullable
  <T, R> T deserializeCollection(@NotNull Reader paramReader, @NotNull Class<T> paramClass, @Nullable JsonDeserializer<R> paramJsonDeserializer);
  
  @Nullable
  <T> T deserialize(@NotNull Reader paramReader, @NotNull Class<T> paramClass);
  
  @Nullable
  SentryEnvelope deserializeEnvelope(@NotNull InputStream paramInputStream);
  
  <T> void serialize(@NotNull T paramT, @NotNull Writer paramWriter) throws IOException;
  
  void serialize(@NotNull SentryEnvelope paramSentryEnvelope, @NotNull OutputStream paramOutputStream) throws Exception;
  
  @NotNull
  String serialize(@NotNull Map<String, Object> paramMap) throws Exception;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ISerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */