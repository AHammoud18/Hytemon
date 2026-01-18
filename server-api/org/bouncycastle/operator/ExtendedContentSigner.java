package org.bouncycastle.operator;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface ExtendedContentSigner extends ContentSigner {
  AlgorithmIdentifier getDigestAlgorithmIdentifier();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\operator\ExtendedContentSigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */