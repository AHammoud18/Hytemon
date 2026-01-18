package org.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.RawAgreement;
import org.bouncycastle.util.BigIntegers;

public final class BasicRawAgreement implements RawAgreement {
  public final BasicAgreement basicAgreement;
  
  public BasicRawAgreement(BasicAgreement paramBasicAgreement) {
    if (paramBasicAgreement == null)
      throw new NullPointerException("'basicAgreement' cannot be null"); 
    this.basicAgreement = paramBasicAgreement;
  }
  
  public void init(CipherParameters paramCipherParameters) {
    this.basicAgreement.init(paramCipherParameters);
  }
  
  public int getAgreementSize() {
    return this.basicAgreement.getFieldSize();
  }
  
  public void calculateAgreement(CipherParameters paramCipherParameters, byte[] paramArrayOfbyte, int paramInt) {
    BigInteger bigInteger = this.basicAgreement.calculateAgreement(paramCipherParameters);
    BigIntegers.asUnsignedByteArray(bigInteger, paramArrayOfbyte, paramInt, getAgreementSize());
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\BasicRawAgreement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */