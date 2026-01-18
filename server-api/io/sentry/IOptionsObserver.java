package io.sentry;

import io.sentry.protocol.SdkVersion;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IOptionsObserver {
  void setRelease(@Nullable String paramString);
  
  void setProguardUuid(@Nullable String paramString);
  
  void setSdkVersion(@Nullable SdkVersion paramSdkVersion);
  
  void setEnvironment(@Nullable String paramString);
  
  void setDist(@Nullable String paramString);
  
  void setTags(@NotNull Map<String, String> paramMap);
  
  void setReplayErrorSampleRate(@Nullable Double paramDouble);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IOptionsObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */