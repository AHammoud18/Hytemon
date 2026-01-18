package io.sentry;

import io.sentry.rrweb.RRWebEvent;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Internal
public interface ReplayBreadcrumbConverter {
  @Nullable
  RRWebEvent convert(@NotNull Breadcrumb paramBreadcrumb);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ReplayBreadcrumbConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */