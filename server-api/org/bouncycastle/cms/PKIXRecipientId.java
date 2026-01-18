package org.bouncycastle.cms;

import java.math.BigInteger;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.selector.X509CertificateHolderSelector;

public class PKIXRecipientId extends RecipientId {
  protected final X509CertificateHolderSelector baseSelector;
  
  protected PKIXRecipientId(int paramInt, X509CertificateHolderSelector paramX509CertificateHolderSelector) {
    super(paramInt);
    this.baseSelector = paramX509CertificateHolderSelector;
  }
  
  protected PKIXRecipientId(int paramInt, X500Name paramX500Name, BigInteger paramBigInteger, byte[] paramArrayOfbyte) {
    this(paramInt, new X509CertificateHolderSelector(paramX500Name, paramBigInteger, paramArrayOfbyte));
  }
  
  public X500Name getIssuer() {
    return this.baseSelector.getIssuer();
  }
  
  public BigInteger getSerialNumber() {
    return this.baseSelector.getSerialNumber();
  }
  
  public byte[] getSubjectKeyIdentifier() {
    return this.baseSelector.getSubjectKeyIdentifier();
  }
  
  public Object clone() {
    return new PKIXRecipientId(getType(), this.baseSelector);
  }
  
  public int hashCode() {
    return this.baseSelector.hashCode();
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof PKIXRecipientId))
      return false; 
    PKIXRecipientId pKIXRecipientId = (PKIXRecipientId)paramObject;
    return this.baseSelector.equals(pKIXRecipientId.baseSelector);
  }
  
  public boolean match(Object paramObject) {
    return this.baseSelector.match(paramObject);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\PKIXRecipientId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */