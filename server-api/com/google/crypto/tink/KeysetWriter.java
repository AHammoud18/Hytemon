package com.google.crypto.tink;

import com.google.crypto.tink.proto.EncryptedKeyset;
import com.google.crypto.tink.proto.Keyset;
import java.io.IOException;

public interface KeysetWriter {
  void write(Keyset paramKeyset) throws IOException;
  
  void write(EncryptedKeyset paramEncryptedKeyset) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KeysetWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */