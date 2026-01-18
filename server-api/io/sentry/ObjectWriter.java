package io.sentry;

import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ObjectWriter {
  ObjectWriter beginArray() throws IOException;
  
  ObjectWriter endArray() throws IOException;
  
  ObjectWriter beginObject() throws IOException;
  
  ObjectWriter endObject() throws IOException;
  
  ObjectWriter name(@NotNull String paramString) throws IOException;
  
  ObjectWriter value(@Nullable String paramString) throws IOException;
  
  ObjectWriter jsonValue(@Nullable String paramString) throws IOException;
  
  ObjectWriter nullValue() throws IOException;
  
  ObjectWriter value(boolean paramBoolean) throws IOException;
  
  ObjectWriter value(@Nullable Boolean paramBoolean) throws IOException;
  
  ObjectWriter value(double paramDouble) throws IOException;
  
  ObjectWriter value(long paramLong) throws IOException;
  
  ObjectWriter value(@Nullable Number paramNumber) throws IOException;
  
  ObjectWriter value(@NotNull ILogger paramILogger, @Nullable Object paramObject) throws IOException;
  
  void setLenient(boolean paramBoolean);
  
  void setIndent(@Nullable String paramString);
  
  @Nullable
  String getIndent();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ObjectWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */