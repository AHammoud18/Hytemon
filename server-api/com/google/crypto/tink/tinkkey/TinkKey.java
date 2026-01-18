package com.google.crypto.tink.tinkkey;

import com.google.crypto.tink.KeyTemplate;
import com.google.errorprone.annotations.Immutable;

@Immutable
public interface TinkKey {
  boolean hasSecret();
  
  KeyTemplate getKeyTemplate();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\tinkkey\TinkKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */