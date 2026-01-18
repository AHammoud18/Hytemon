package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class ECCSIKeyGenerationParameters extends KeyGenerationParameters {
  private final BigInteger q;
  
  private final ECPoint G;
  
  private final Digest digest;
  
  private final byte[] id;
  
  private final BigInteger ksak;
  
  private final ECPoint kpak;
  
  private final int n;
  
  public ECCSIKeyGenerationParameters(SecureRandom paramSecureRandom, X9ECParameters paramX9ECParameters, Digest paramDigest, byte[] paramArrayOfbyte) {
    super(paramSecureRandom, paramX9ECParameters.getCurve().getA().bitLength());
    this.q = paramX9ECParameters.getCurve().getOrder();
    this.G = paramX9ECParameters.getG();
    this.digest = paramDigest;
    this.id = Arrays.clone(paramArrayOfbyte);
    this.n = paramX9ECParameters.getCurve().getA().bitLength();
    this.ksak = BigIntegers.createRandomBigInteger(this.n, paramSecureRandom).mod(this.q);
    this.kpak = this.G.multiply(this.ksak).normalize();
  }
  
  public byte[] getId() {
    return Arrays.clone(this.id);
  }
  
  public ECPoint getKPAK() {
    return this.kpak;
  }
  
  public BigInteger computeSSK(BigInteger paramBigInteger) {
    return this.ksak.add(paramBigInteger).mod(this.q);
  }
  
  public BigInteger getQ() {
    return this.q;
  }
  
  public ECPoint getG() {
    return this.G;
  }
  
  public Digest getDigest() {
    return this.digest;
  }
  
  public int getN() {
    return this.n;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\params\ECCSIKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */