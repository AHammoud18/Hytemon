package io.sentry;

import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import java.util.Collection;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ScopeObserverAdapter implements IScopeObserver {
  public void setUser(@Nullable User user) {}
  
  public void addBreadcrumb(@NotNull Breadcrumb crumb) {}
  
  public void setBreadcrumbs(@NotNull Collection<Breadcrumb> breadcrumbs) {}
  
  public void setTag(@NotNull String key, @NotNull String value) {}
  
  public void removeTag(@NotNull String key) {}
  
  public void setTags(@NotNull Map<String, String> tags) {}
  
  public void setExtra(@NotNull String key, @NotNull String value) {}
  
  public void removeExtra(@NotNull String key) {}
  
  public void setExtras(@NotNull Map<String, Object> extras) {}
  
  public void setRequest(@Nullable Request request) {}
  
  public void setFingerprint(@NotNull Collection<String> fingerprint) {}
  
  public void setLevel(@Nullable SentryLevel level) {}
  
  public void setContexts(@NotNull Contexts contexts) {}
  
  public void setTransaction(@Nullable String transaction) {}
  
  public void setTrace(@Nullable SpanContext spanContext, @NotNull IScope scope) {}
  
  public void setReplayId(@NotNull SentryId replayId) {}
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ScopeObserverAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */