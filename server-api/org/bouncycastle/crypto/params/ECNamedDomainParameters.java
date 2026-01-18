package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class ECNamedDomainParameters extends ECDomainParameters {
  private ASN1ObjectIdentifier name;
  
  public static ECNamedDomainParameters lookup(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    X9ECParameters x9ECParameters = CustomNamedCurves.getByOID(paramASN1ObjectIdentifier);
    if (x9ECParameters == null)
      x9ECParameters = ECNamedCurveTable.getByOID(paramASN1ObjectIdentifier); 
    return new ECNamedDomainParameters(paramASN1ObjectIdentifier, x9ECParameters);
  }
  
  public ECNamedDomainParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger) {
    this(paramASN1ObjectIdentifier, paramECCurve, paramECPoint, paramBigInteger, ECConstants.ONE, null);
  }
  
  public ECNamedDomainParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
    this(paramASN1ObjectIdentifier, paramECCurve, paramECPoint, paramBigInteger1, paramBigInteger2, null);
  }
  
  public ECNamedDomainParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger1, BigInteger paramBigInteger2, byte[] paramArrayOfbyte) {
    super(paramECCurve, paramECPoint, paramBigInteger1, paramBigInteger2, paramArrayOfbyte);
    this.name = paramASN1ObjectIdentifier;
  }
  
  public ECNamedDomainParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ECDomainParameters paramECDomainParameters) {
    super(paramECDomainParameters.getCurve(), paramECDomainParameters.getG(), paramECDomainParameters.getN(), paramECDomainParameters.getH(), paramECDomainParameters.getSeed());
    this.name = paramASN1ObjectIdentifier;
  }
  
  public ECNamedDomainParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier, X9ECParameters paramX9ECParameters) {
    super(paramX9ECParameters);
    this.name = paramASN1ObjectIdentifier;
  }
  
  public ASN1ObjectIdentifier getName() {
    return this.name;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\params\ECNamedDomainParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */