package org.bouncycastle.cms;

import java.math.BigInteger;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.selector.X509CertificateHolderSelector;

public class KEMRecipientId extends PKIXRecipientId {
  private KEMRecipientId(X509CertificateHolderSelector paramX509CertificateHolderSelector) {
    super(4, paramX509CertificateHolderSelector);
  }
  
  public KEMRecipientId(byte[] paramArrayOfbyte) {
    super(4, null, null, paramArrayOfbyte);
  }
  
  public KEMRecipientId(X500Name paramX500Name, BigInteger paramBigInteger) {
    super(4, paramX500Name, paramBigInteger, null);
  }
  
  public KEMRecipientId(X500Name paramX500Name, BigInteger paramBigInteger, byte[] paramArrayOfbyte) {
    super(4, paramX500Name, paramBigInteger, paramArrayOfbyte);
  }
  
  public Object clone() {
    return new KEMRecipientId(this.baseSelector);
  }
  
  public boolean match(Object paramObject) {
    return (paramObject instanceof KEMRecipientInformation) ? ((KEMRecipientInformation)paramObject).getRID().equals(this) : super.match(paramObject);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\KEMRecipientId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */