package org.bouncycastle.crypto.signers;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.ECCSIPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECCSIPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class ECCSISigner implements Signer {
  private final BigInteger q;
  
  private final ECPoint G;
  
  private final Digest digest;
  
  private BigInteger j;
  
  private BigInteger r;
  
  private ECPoint Y;
  
  private final ECPoint kpak;
  
  private final byte[] id;
  
  private CipherParameters param;
  
  private ByteArrayOutputStream stream;
  
  private boolean forSigning;
  
  private final int N;
  
  public ECCSISigner(ECPoint paramECPoint, X9ECParameters paramX9ECParameters, Digest paramDigest, byte[] paramArrayOfbyte) {
    this.kpak = paramECPoint;
    this.id = paramArrayOfbyte;
    this.q = paramX9ECParameters.getCurve().getOrder();
    this.G = paramX9ECParameters.getG();
    this.digest = paramDigest;
    this.digest.reset();
    this.N = paramX9ECParameters.getCurve().getOrder().bitLength() + 7 >> 3;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) {
    this.forSigning = paramBoolean;
    this.param = paramCipherParameters;
    reset();
  }
  
  public void update(byte paramByte) {
    if (this.forSigning) {
      this.digest.update(paramByte);
    } else {
      this.stream.write(paramByte);
    } 
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (this.forSigning) {
      this.digest.update(paramArrayOfbyte, paramInt1, paramInt2);
    } else {
      this.stream.write(paramArrayOfbyte, paramInt1, paramInt2);
    } 
  }
  
  public byte[] generateSignature() throws CryptoException, DataLengthException {
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    ECCSIPrivateKeyParameters eCCSIPrivateKeyParameters = (ECCSIPrivateKeyParameters)((ParametersWithRandom)this.param).getParameters();
    BigInteger bigInteger1 = eCCSIPrivateKeyParameters.getSSK();
    BigInteger bigInteger2 = (new BigInteger(1, arrayOfByte)).add(this.r.multiply(bigInteger1)).mod(this.q);
    if (bigInteger2.equals(BigInteger.ZERO))
      throw new IllegalArgumentException("Invalid j, retry"); 
    BigInteger bigInteger3 = bigInteger2.modInverse(this.q).multiply(this.j).mod(this.q);
    return Arrays.concatenate(BigIntegers.asUnsignedByteArray(this.N, this.r), BigIntegers.asUnsignedByteArray(this.N, bigInteger3), eCCSIPrivateKeyParameters.getPublicKeyParameters().getPVT().getEncoded(false));
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte = Arrays.copyOf(paramArrayOfbyte, this.N);
    BigInteger bigInteger1 = new BigInteger(1, Arrays.copyOfRange(paramArrayOfbyte, this.N, this.N << 1));
    this.r = (new BigInteger(1, arrayOfByte)).mod(this.q);
    this.digest.update(arrayOfByte, 0, this.N);
    arrayOfByte = this.stream.toByteArray();
    this.digest.update(arrayOfByte, 0, arrayOfByte.length);
    arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    BigInteger bigInteger2 = (new BigInteger(1, arrayOfByte)).mod(this.q);
    ECPoint eCPoint1 = this.G.multiply(bigInteger2).normalize();
    ECPoint eCPoint2 = this.Y.multiply(this.r).normalize();
    ECPoint eCPoint3 = eCPoint1.add(eCPoint2).normalize();
    ECPoint eCPoint4 = eCPoint3.multiply(bigInteger1).normalize();
    BigInteger bigInteger3 = eCPoint4.getAffineXCoord().toBigInteger();
    return bigInteger3.mod(this.q).equals(this.r.mod(this.q));
  }
  
  public void reset() {
    ECPoint eCPoint2;
    this.digest.reset();
    CipherParameters cipherParameters = this.param;
    SecureRandom secureRandom = null;
    if (cipherParameters instanceof ParametersWithRandom) {
      secureRandom = ((ParametersWithRandom)cipherParameters).getRandom();
      cipherParameters = ((ParametersWithRandom)cipherParameters).getParameters();
    } 
    ECPoint eCPoint1 = null;
    if (this.forSigning) {
      ECCSIPrivateKeyParameters eCCSIPrivateKeyParameters = (ECCSIPrivateKeyParameters)cipherParameters;
      eCPoint2 = eCCSIPrivateKeyParameters.getPublicKeyParameters().getPVT();
      this.j = BigIntegers.createRandomBigInteger(this.q.bitLength(), secureRandom);
      ECPoint eCPoint = this.G.multiply(this.j).normalize();
      this.r = eCPoint.getAffineXCoord().toBigInteger().mod(this.q);
      eCPoint1 = this.G.multiply(eCCSIPrivateKeyParameters.getSSK());
    } else {
      ECCSIPublicKeyParameters eCCSIPublicKeyParameters = (ECCSIPublicKeyParameters)cipherParameters;
      eCPoint2 = eCCSIPublicKeyParameters.getPVT();
      this.stream = new ByteArrayOutputStream();
    } 
    byte[] arrayOfByte = this.G.getEncoded(false);
    this.digest.update(arrayOfByte, 0, arrayOfByte.length);
    arrayOfByte = this.kpak.getEncoded(false);
    this.digest.update(arrayOfByte, 0, arrayOfByte.length);
    this.digest.update(this.id, 0, this.id.length);
    arrayOfByte = eCPoint2.getEncoded(false);
    this.digest.update(arrayOfByte, 0, arrayOfByte.length);
    arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    BigInteger bigInteger = (new BigInteger(1, arrayOfByte)).mod(this.q);
    this.digest.update(arrayOfByte, 0, arrayOfByte.length);
    if (this.forSigning) {
      eCPoint1 = eCPoint1.subtract(eCPoint2.multiply(bigInteger)).normalize();
      if (!eCPoint1.equals(this.kpak))
        throw new IllegalArgumentException("Invalid KPAK"); 
      byte[] arrayOfByte1 = BigIntegers.asUnsignedByteArray(this.N, this.r);
      this.digest.update(arrayOfByte1, 0, arrayOfByte1.length);
    } else {
      this.Y = eCPoint2.multiply(bigInteger).add(this.kpak).normalize();
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\signers\ECCSISigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */