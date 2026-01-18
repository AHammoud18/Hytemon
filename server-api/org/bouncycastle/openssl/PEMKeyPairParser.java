package org.bouncycastle.openssl;

import java.io.IOException;

interface PEMKeyPairParser {
  PEMKeyPair parse(byte[] paramArrayOfbyte) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\openssl\PEMKeyPairParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */