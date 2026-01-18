package org.bouncycastle.operator;

import java.io.OutputStream;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface DigestCalculator {
  public static final AlgorithmIdentifier SHA_256 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
  
  public static final AlgorithmIdentifier SHA_512 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512);
  
  AlgorithmIdentifier getAlgorithmIdentifier();
  
  OutputStream getOutputStream();
  
  byte[] getDigest();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\operator\DigestCalculator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */