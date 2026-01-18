package org.bouncycastle.jce.interfaces;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface PKCS12BagAttributeCarrier {
  void setBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable);
  
  ASN1Encodable getBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier);
  
  Enumeration getBagAttributeKeys();
  
  boolean hasFriendlyName();
  
  void setFriendlyName(String paramString);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\interfaces\PKCS12BagAttributeCarrier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */