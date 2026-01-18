package org.bouncycastle.crypto;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface StagedAgreement extends BasicAgreement {
  AsymmetricKeyParameter calculateStage(CipherParameters paramCipherParameters);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\StagedAgreement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */