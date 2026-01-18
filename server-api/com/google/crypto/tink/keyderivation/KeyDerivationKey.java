package com.google.crypto.tink.keyderivation;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.Parameters;

public abstract class KeyDerivationKey extends Key {
  public abstract KeyDerivationParameters getParameters();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\KeyDerivationKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */