package io.sentry.hints;

import org.jetbrains.annotations.Nullable;

public interface AbnormalExit {
  @Nullable
  String mechanism();
  
  boolean ignoreCurrentThread();
  
  @Nullable
  Long timestamp();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\hints\AbnormalExit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */