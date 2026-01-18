package com.google.crypto.tink;

import com.google.errorprone.annotations.Immutable;
import javax.annotation.Nullable;

@Immutable
public abstract class Key {
  public abstract Parameters getParameters();
  
  @Nullable
  public abstract Integer getIdRequirementOrNull();
  
  public abstract boolean equalsKey(Key paramKey);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */