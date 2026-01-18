package io.sentry.internal.debugmeta;

import java.util.List;
import java.util.Properties;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

@Internal
public interface IDebugMetaLoader {
  @Nullable
  List<Properties> loadDebugMeta();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\debugmeta\IDebugMetaLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */