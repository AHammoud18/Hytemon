package io.sentry;

import java.util.concurrent.Future;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.NotNull;

@Experimental
public interface IDistributionApi {
  @NotNull
  UpdateStatus checkForUpdateBlocking();
  
  @NotNull
  Future<UpdateStatus> checkForUpdate();
  
  void downloadUpdate(@NotNull UpdateInfo paramUpdateInfo);
  
  boolean isEnabled();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IDistributionApi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */