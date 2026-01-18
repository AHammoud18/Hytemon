package org.bouncycastle.eac.operator;

import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface EACSigner {
  ASN1ObjectIdentifier getUsageIdentifier();
  
  OutputStream getOutputStream();
  
  byte[] getSignature();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\eac\operator\EACSigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */