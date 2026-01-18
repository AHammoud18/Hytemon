package io.sentry;

import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import java.util.Collection;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IScopeObserver {
  void setUser(@Nullable User paramUser);
  
  void addBreadcrumb(@NotNull Breadcrumb paramBreadcrumb);
  
  void setBreadcrumbs(@NotNull Collection<Breadcrumb> paramCollection);
  
  void setTag(@NotNull String paramString1, @NotNull String paramString2);
  
  void removeTag(@NotNull String paramString);
  
  void setTags(@NotNull Map<String, String> paramMap);
  
  void setExtra(@NotNull String paramString1, @NotNull String paramString2);
  
  void removeExtra(@NotNull String paramString);
  
  void setExtras(@NotNull Map<String, Object> paramMap);
  
  void setRequest(@Nullable Request paramRequest);
  
  void setFingerprint(@NotNull Collection<String> paramCollection);
  
  void setLevel(@Nullable SentryLevel paramSentryLevel);
  
  void setContexts(@NotNull Contexts paramContexts);
  
  void setTransaction(@Nullable String paramString);
  
  void setTrace(@Nullable SpanContext paramSpanContext, @NotNull IScope paramIScope);
  
  void setReplayId(@NotNull SentryId paramSentryId);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IScopeObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */