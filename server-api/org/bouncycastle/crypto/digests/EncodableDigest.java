package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.EncodableService;

public interface EncodableDigest extends EncodableService {
  byte[] getEncodedState();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\digests\EncodableDigest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */