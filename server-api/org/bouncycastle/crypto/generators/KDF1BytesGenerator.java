package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.Digest;

public class KDF1BytesGenerator extends BaseKDFBytesGenerator {
  public KDF1BytesGenerator(Digest paramDigest) {
    super(0, paramDigest);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\generators\KDF1BytesGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */