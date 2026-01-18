package org.bouncycastle.oer;

import org.bouncycastle.asn1.ASN1Encodable;

public interface Switch {
  Element result(SwitchIndexer paramSwitchIndexer);
  
  ASN1Encodable[] keys();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\oer\Switch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */