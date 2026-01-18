package com.google.crypto.tink.internal;

import com.google.crypto.tink.Key;
import java.security.GeneralSecurityException;
import javax.annotation.Nullable;

public interface KeyCreator<ParametersT extends com.google.crypto.tink.Parameters> {
  Key createKey(ParametersT paramParametersT, @Nullable Integer paramInteger) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\KeyCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */