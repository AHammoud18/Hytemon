package org.bouncycastle.cert.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface Control {
  ASN1ObjectIdentifier getType();
  
  ASN1Encodable getValue();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cert\crmf\Control.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */