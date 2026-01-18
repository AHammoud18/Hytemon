package org.bouncycastle.cms;

import java.math.BigInteger;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.selector.X509CertificateHolderSelector;

public class KeyTransRecipientId extends PKIXRecipientId {
  private KeyTransRecipientId(X509CertificateHolderSelector paramX509CertificateHolderSelector) {
    super(0, paramX509CertificateHolderSelector);
  }
  
  public KeyTransRecipientId(byte[] paramArrayOfbyte) {
    super(0, null, null, paramArrayOfbyte);
  }
  
  public KeyTransRecipientId(X500Name paramX500Name, BigInteger paramBigInteger) {
    super(0, paramX500Name, paramBigInteger, null);
  }
  
  public KeyTransRecipientId(X500Name paramX500Name, BigInteger paramBigInteger, byte[] paramArrayOfbyte) {
    super(0, paramX500Name, paramBigInteger, paramArrayOfbyte);
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof KeyTransRecipientId))
      return false; 
    KeyTransRecipientId keyTransRecipientId = (KeyTransRecipientId)paramObject;
    return this.baseSelector.equals(keyTransRecipientId.baseSelector);
  }
  
  public Object clone() {
    return new KeyTransRecipientId(this.baseSelector);
  }
  
  public boolean match(Object paramObject) {
    return (paramObject instanceof KeyTransRecipientInformation) ? ((KeyTransRecipientInformation)paramObject).getRID().equals(this) : super.match(paramObject);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\KeyTransRecipientId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */