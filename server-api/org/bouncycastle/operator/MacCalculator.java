package org.bouncycastle.operator;

import java.io.OutputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface MacCalculator {
  AlgorithmIdentifier getAlgorithmIdentifier();
  
  OutputStream getOutputStream();
  
  byte[] getMac();
  
  GenericKey getKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\operator\MacCalculator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */