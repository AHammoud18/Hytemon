package io.sentry.internal.gestures;

import org.jetbrains.annotations.Nullable;

public interface GestureTargetLocator {
  @Nullable
  UiElement locate(@Nullable Object paramObject, float paramFloat1, float paramFloat2, UiElement.Type paramType);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\gestures\GestureTargetLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */