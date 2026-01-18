package org.bouncycastle.operator;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface KeyUnwrapper {
  AlgorithmIdentifier getAlgorithmIdentifier();
  
  GenericKey generateUnwrappedKey(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte) throws OperatorException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\operator\KeyUnwrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */