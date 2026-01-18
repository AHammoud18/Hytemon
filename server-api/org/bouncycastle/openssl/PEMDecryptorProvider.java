package org.bouncycastle.openssl;

import org.bouncycastle.operator.OperatorCreationException;

public interface PEMDecryptorProvider {
  PEMDecryptor get(String paramString) throws OperatorCreationException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\openssl\PEMDecryptorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */