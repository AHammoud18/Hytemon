package org.bouncycastle.pqc.crypto.snova;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.GF16;

public class SnovaSigner implements MessageSigner {
  private SnovaParameters params;
  
  private SnovaEngine engine;
  
  private SecureRandom random;
  
  private final SHAKEDigest shake = new SHAKEDigest(256);
  
  private SnovaPublicKeyParameters pubKey;
  
  private SnovaPrivateKeyParameters privKey;
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) {
    if (paramBoolean) {
      this.pubKey = null;
      if (paramCipherParameters instanceof ParametersWithRandom) {
        ParametersWithRandom parametersWithRandom = (ParametersWithRandom)paramCipherParameters;
        this.privKey = (SnovaPrivateKeyParameters)parametersWithRandom.getParameters();
        this.random = parametersWithRandom.getRandom();
      } else {
        this.privKey = (SnovaPrivateKeyParameters)paramCipherParameters;
        this.random = CryptoServicesRegistrar.getSecureRandom();
      } 
      this.params = this.privKey.getParameters();
    } else {
      this.pubKey = (SnovaPublicKeyParameters)paramCipherParameters;
      this.params = this.pubKey.getParameters();
      this.privKey = null;
      this.random = null;
    } 
    this.engine = new SnovaEngine(this.params);
  }
  
  public byte[] generateSignature(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte4;
    byte[] arrayOfByte5;
    byte[] arrayOfByte1 = getMessageHash(paramArrayOfbyte);
    byte[] arrayOfByte2 = new byte[this.params.getSaltLength()];
    this.random.nextBytes(arrayOfByte2);
    byte[] arrayOfByte3 = new byte[(this.params.getN() * this.params.getLsq() + 1 >>> 1) + this.params.getSaltLength()];
    SnovaKeyElements snovaKeyElements = new SnovaKeyElements(this.params);
    if (this.params.isSkIsSeed()) {
      byte[] arrayOfByte = this.privKey.getPrivateKey();
      arrayOfByte4 = Arrays.copyOfRange(arrayOfByte, 0, 16);
      arrayOfByte5 = Arrays.copyOfRange(arrayOfByte, 16, arrayOfByte.length);
      this.engine.genMap1T12Map2(snovaKeyElements, arrayOfByte4, arrayOfByte5);
    } else {
      byte[] arrayOfByte6 = this.privKey.getPrivateKey();
      byte[] arrayOfByte7 = new byte[arrayOfByte6.length - 16 - 32 << 1];
      GF16Utils.decodeMergeInHalf(arrayOfByte6, arrayOfByte7, arrayOfByte7.length);
      int i = 0;
      i = SnovaKeyElements.copy3d(arrayOfByte7, i, snovaKeyElements.map1.aAlpha);
      i = SnovaKeyElements.copy3d(arrayOfByte7, i, snovaKeyElements.map1.bAlpha);
      i = SnovaKeyElements.copy3d(arrayOfByte7, i, snovaKeyElements.map1.qAlpha1);
      i = SnovaKeyElements.copy3d(arrayOfByte7, i, snovaKeyElements.map1.qAlpha2);
      i = SnovaKeyElements.copy3d(arrayOfByte7, i, snovaKeyElements.T12);
      i = SnovaKeyElements.copy4d(arrayOfByte7, i, snovaKeyElements.map2.f11);
      i = SnovaKeyElements.copy4d(arrayOfByte7, i, snovaKeyElements.map2.f12);
      SnovaKeyElements.copy4d(arrayOfByte7, i, snovaKeyElements.map2.f21);
      arrayOfByte4 = Arrays.copyOfRange(arrayOfByte6, arrayOfByte6.length - 16 - 32, arrayOfByte6.length - 32);
      arrayOfByte5 = Arrays.copyOfRange(arrayOfByte6, arrayOfByte6.length - 32, arrayOfByte6.length);
    } 
    signDigestCore(arrayOfByte3, arrayOfByte1, arrayOfByte2, snovaKeyElements.map1.aAlpha, snovaKeyElements.map1.bAlpha, snovaKeyElements.map1.qAlpha1, snovaKeyElements.map1.qAlpha2, snovaKeyElements.T12, snovaKeyElements.map2.f11, snovaKeyElements.map2.f12, snovaKeyElements.map2.f21, arrayOfByte4, arrayOfByte5);
    return Arrays.concatenate(arrayOfByte3, paramArrayOfbyte);
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    byte[] arrayOfByte1 = getMessageHash(paramArrayOfbyte1);
    MapGroup1 mapGroup1 = new MapGroup1(this.params);
    byte[] arrayOfByte2 = this.pubKey.getEncoded();
    byte[] arrayOfByte3 = Arrays.copyOf(arrayOfByte2, 16);
    byte[] arrayOfByte4 = Arrays.copyOfRange(arrayOfByte2, 16, arrayOfByte2.length);
    this.engine.genABQP(mapGroup1, arrayOfByte3);
    byte[][][][] arrayOfByte = new byte[this.params.getM()][this.params.getO()][this.params.getO()][this.params.getLsq()];
    if ((this.params.getLsq() & 0x1) == 0) {
      MapGroup1.decodeP(arrayOfByte4, 0, arrayOfByte, arrayOfByte4.length << 1);
    } else {
      byte[] arrayOfByte5 = new byte[arrayOfByte4.length << 1];
      GF16.decode(arrayOfByte4, arrayOfByte5, arrayOfByte5.length);
      MapGroup1.fillP(arrayOfByte5, 0, arrayOfByte, arrayOfByte5.length);
    } 
    return verifySignatureCore(arrayOfByte1, paramArrayOfbyte2, arrayOfByte3, mapGroup1, arrayOfByte);
  }
  
  void createSignedHash(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, byte[] paramArrayOfbyte3, int paramInt3, int paramInt4, byte[] paramArrayOfbyte4, int paramInt5) {
    this.shake.update(paramArrayOfbyte1, 0, paramInt1);
    this.shake.update(paramArrayOfbyte2, 0, paramInt2);
    this.shake.update(paramArrayOfbyte3, paramInt3, paramInt4);
    this.shake.doFinal(paramArrayOfbyte4, 0, paramInt5);
  }
  
  void signDigestCore(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[][][] paramArrayOfbyte4, byte[][][] paramArrayOfbyte5, byte[][][] paramArrayOfbyte6, byte[][][] paramArrayOfbyte7, byte[][][] paramArrayOfbyte8, byte[][][][] paramArrayOfbyte9, byte[][][][] paramArrayOfbyte10, byte[][][][] paramArrayOfbyte11, byte[] paramArrayOfbyte12, byte[] paramArrayOfbyte13) {
    int i = this.params.getM();
    int j = this.params.getL();
    int k = this.params.getLsq();
    int m = this.params.getAlpha();
    int n = this.params.getV();
    int i1 = this.params.getO();
    int i2 = this.params.getN();
    int i3 = i * k;
    int i4 = i1 * k;
    int i5 = n * k;
    int i6 = i4 + 1 >>> 1;
    byte[][] arrayOfByte1 = new byte[i3][i3 + 1];
    byte[][] arrayOfByte2 = new byte[k][k];
    byte[] arrayOfByte3 = new byte[i3];
    byte[][][] arrayOfByte4 = new byte[m][n][k];
    byte[][][] arrayOfByte5 = new byte[m][n][k];
    byte[] arrayOfByte6 = new byte[k];
    byte[] arrayOfByte7 = new byte[k];
    byte[] arrayOfByte8 = new byte[k];
    byte[] arrayOfByte9 = new byte[i3];
    byte[] arrayOfByte10 = new byte[i2 * k];
    byte[] arrayOfByte11 = new byte[i6];
    byte[] arrayOfByte12 = new byte[i5 + 1 >>> 1];
    byte[] arrayOfByte13 = new byte[j];
    byte b = 0;
    createSignedHash(paramArrayOfbyte12, paramArrayOfbyte12.length, paramArrayOfbyte2, paramArrayOfbyte2.length, paramArrayOfbyte3, 0, paramArrayOfbyte3.length, arrayOfByte11, i6);
    GF16.decode(arrayOfByte11, 0, arrayOfByte9, 0, arrayOfByte9.length);
    while (true) {
      byte b1;
      for (b1 = 0; b1 < arrayOfByte1.length; b1++)
        Arrays.fill(arrayOfByte1[b1], (byte)0); 
      b = (byte)(b + 1);
      for (b1 = 0; b1 < i3; b1++)
        arrayOfByte1[b1][i3] = arrayOfByte9[b1]; 
      this.shake.update(paramArrayOfbyte13, 0, paramArrayOfbyte13.length);
      this.shake.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
      this.shake.update(paramArrayOfbyte3, 0, paramArrayOfbyte3.length);
      this.shake.update(b);
      this.shake.doFinal(arrayOfByte12, 0, arrayOfByte12.length);
      GF16.decode(arrayOfByte12, arrayOfByte10, arrayOfByte12.length << 1);
      b1 = 0;
      int i8;
      for (i8 = 0; b1 < i; i8 += k) {
        Arrays.fill(arrayOfByte8, (byte)0);
        byte b2 = 0;
        int i9;
        for (i9 = b1; b2 < m; i9++) {
          if (i9 >= i1)
            i9 -= i1; 
          byte b3 = 0;
          int i10;
          for (i10 = 0; b3 < n; i10 += k) {
            GF16Utils.gf16mTranMulMul(arrayOfByte10, i10, paramArrayOfbyte4[b1][b2], paramArrayOfbyte5[b1][b2], paramArrayOfbyte6[b1][b2], paramArrayOfbyte7[b1][b2], arrayOfByte13, arrayOfByte4[b2][b3], arrayOfByte5[b2][b3], j);
            b3++;
          } 
          for (b3 = 0; b3 < n; b3++) {
            for (i10 = 0; i10 < n; i10++)
              GF16Utils.gf16mMulMulTo(arrayOfByte4[b2][b3], paramArrayOfbyte9[i9][b3][i10], arrayOfByte5[b2][i10], arrayOfByte13, arrayOfByte8, j); 
          } 
          b2++;
        } 
        b2 = 0;
        i9 = 0;
        while (b2 < j) {
          for (byte b3 = 0; b3 < j; b3++)
            arrayOfByte1[i8 + i9][i3] = (byte)(arrayOfByte1[i8 + i9][i3] ^ arrayOfByte8[i9++]); 
          b2++;
        } 
        b2 = 0;
        for (i9 = 0; b2 < i1; i9 += k) {
          byte b3 = 0;
          for (int i10 = b1; b3 < m; i10++) {
            if (i10 >= i1)
              i10 -= i1; 
            byte b4;
            for (b4 = 0; b4 < k; b4++)
              Arrays.fill(arrayOfByte2[b4], (byte)0); 
            for (b4 = 0; b4 < n; b4++) {
              GF16Utils.gf16mMulMul(arrayOfByte4[b3][b4], paramArrayOfbyte10[i10][b4][b2], paramArrayOfbyte7[b1][b3], arrayOfByte13, arrayOfByte6, j);
              GF16Utils.gf16mMulMul(paramArrayOfbyte6[b1][b3], paramArrayOfbyte11[i10][b2][b4], arrayOfByte5[b3][b4], arrayOfByte13, arrayOfByte7, j);
              byte b5 = 0;
              int i11 = 0;
              int i12 = 0;
              while (b5 < k) {
                if (i11 == j) {
                  i11 = 0;
                  i12 += j;
                } 
                byte b6 = arrayOfByte6[i12];
                byte b7 = arrayOfByte7[i11];
                byte b8 = 0;
                int i13 = 0;
                byte b9 = 0;
                int i14 = 0;
                int i15;
                for (i15 = 0; b8 < k; i15 += j) {
                  if (i13 == j) {
                    i13 = 0;
                    i15 = 0;
                    b9++;
                    i14 += j;
                    b6 = arrayOfByte6[i12 + b9];
                    b7 = arrayOfByte7[i14 + i11];
                  } 
                  byte b10 = paramArrayOfbyte5[b1][b3][i15 + i11];
                  byte b11 = paramArrayOfbyte4[b1][b3][i12 + i13];
                  arrayOfByte2[b5][b8] = (byte)(arrayOfByte2[b5][b8] ^ GF16.mul(b6, b10) ^ GF16.mul(b11, b7));
                  b8++;
                  i13++;
                } 
                b5++;
                i11++;
              } 
            } 
            for (b4 = 0; b4 < k; b4++) {
              for (byte b5 = 0; b5 < k; b5++)
                arrayOfByte1[i8 + b4][i9 + b5] = (byte)(arrayOfByte1[i8 + b4][i9 + b5] ^ arrayOfByte2[b4][b5]); 
            } 
            b3++;
          } 
          b2++;
        } 
        b1++;
      } 
      int i7 = performGaussianElimination(arrayOfByte1, arrayOfByte3, i3);
      if (i7 == 0) {
        b1 = 0;
        for (i8 = 0; b1 < n; i8 += k) {
          byte b2 = 0;
          int i9;
          for (i9 = 0; b2 < i1; i9 += k) {
            GF16Utils.gf16mMulTo(paramArrayOfbyte8[b1][b2], arrayOfByte3, i9, arrayOfByte10, i8, j);
            b2++;
          } 
          b1++;
        } 
        System.arraycopy(arrayOfByte3, 0, arrayOfByte10, i5, i4);
        GF16.encode(arrayOfByte10, paramArrayOfbyte1, arrayOfByte10.length);
        System.arraycopy(paramArrayOfbyte3, 0, paramArrayOfbyte1, paramArrayOfbyte1.length - 16, 16);
        return;
      } 
    } 
  }
  
  boolean verifySignatureCore(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, MapGroup1 paramMapGroup1, byte[][][][] paramArrayOfbyte) {
    int i = this.params.getLsq();
    int j = this.params.getO();
    int k = j * i;
    int m = k + 1 >>> 1;
    int n = this.params.getSaltLength();
    int i1 = this.params.getM();
    int i2 = this.params.getN();
    int i3 = i2 * i;
    int i4 = i3 + 1 >>> 1;
    byte[] arrayOfByte1 = new byte[m];
    createSignedHash(paramArrayOfbyte3, paramArrayOfbyte3.length, paramArrayOfbyte1, paramArrayOfbyte1.length, paramArrayOfbyte2, i4, n, arrayOfByte1, m);
    if ((k & 0x1) != 0)
      arrayOfByte1[m - 1] = (byte)(arrayOfByte1[m - 1] & 0xF); 
    byte[] arrayOfByte2 = new byte[i3];
    GF16.decode(paramArrayOfbyte2, 0, arrayOfByte2, 0, arrayOfByte2.length);
    byte[] arrayOfByte3 = new byte[i1 * i];
    evaluation(arrayOfByte3, paramMapGroup1, paramArrayOfbyte, arrayOfByte2);
    byte[] arrayOfByte4 = new byte[m];
    GF16.encode(arrayOfByte3, arrayOfByte4, arrayOfByte3.length);
    return Arrays.areEqual(arrayOfByte1, arrayOfByte4);
  }
  
  private void evaluation(byte[] paramArrayOfbyte1, MapGroup1 paramMapGroup1, byte[][][][] paramArrayOfbyte, byte[] paramArrayOfbyte2) {
    int i = this.params.getM();
    int j = this.params.getAlpha();
    int k = this.params.getN();
    int m = this.params.getL();
    int n = this.params.getLsq();
    int i1 = this.params.getO();
    byte[][][] arrayOfByte1 = new byte[j][k][n];
    byte[][][] arrayOfByte2 = new byte[j][k][n];
    byte[] arrayOfByte = new byte[n];
    byte b = 0;
    int i2;
    for (i2 = 0; b < i; i2 += n) {
      byte b1 = 0;
      int i3;
      for (i3 = 0; b1 < k; i3 += n) {
        for (byte b2 = 0; b2 < j; b2++)
          GF16Utils.gf16mTranMulMul(paramArrayOfbyte2, i3, paramMapGroup1.aAlpha[b][b2], paramMapGroup1.bAlpha[b][b2], paramMapGroup1.qAlpha1[b][b2], paramMapGroup1.qAlpha2[b][b2], arrayOfByte, arrayOfByte1[b2][b1], arrayOfByte2[b2][b1], m); 
        b1++;
      } 
      b1 = 0;
      for (i3 = b; b1 < j; i3++) {
        if (i3 >= i1)
          i3 -= i1; 
        for (byte b2 = 0; b2 < k; b2++) {
          byte[] arrayOfByte3 = getPMatrix(paramMapGroup1, paramArrayOfbyte, i3, b2, 0);
          GF16Utils.gf16mMul(arrayOfByte3, arrayOfByte2[b1][0], arrayOfByte, m);
          for (byte b3 = 1; b3 < k; b3++) {
            arrayOfByte3 = getPMatrix(paramMapGroup1, paramArrayOfbyte, i3, b2, b3);
            GF16Utils.gf16mMulTo(arrayOfByte3, arrayOfByte2[b1][b3], arrayOfByte, m);
          } 
          GF16Utils.gf16mMulTo(arrayOfByte1[b1][b2], arrayOfByte, paramArrayOfbyte1, i2, m);
        } 
        b1++;
      } 
      b++;
    } 
  }
  
  private byte[] getPMatrix(MapGroup1 paramMapGroup1, byte[][][][] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
    int i = this.params.getV();
    return (paramInt2 < i) ? ((paramInt3 < i) ? paramMapGroup1.p11[paramInt1][paramInt2][paramInt3] : paramMapGroup1.p12[paramInt1][paramInt2][paramInt3 - i]) : ((paramInt3 < i) ? paramMapGroup1.p21[paramInt1][paramInt2 - i][paramInt3] : paramArrayOfbyte[paramInt1][paramInt2 - i][paramInt3 - i]);
  }
  
  private int performGaussianElimination(byte[][] paramArrayOfbyte, byte[] paramArrayOfbyte1, int paramInt) {
    int i = paramInt + 1;
    int j;
    for (j = 0; j < paramInt; j++) {
      int k;
      for (k = j; k < paramInt && paramArrayOfbyte[k][j] == 0; k++);
      if (k >= paramInt)
        return 1; 
      if (k != j) {
        byte[] arrayOfByte = paramArrayOfbyte[j];
        paramArrayOfbyte[j] = paramArrayOfbyte[k];
        paramArrayOfbyte[k] = arrayOfByte;
      } 
      byte b = GF16.inv(paramArrayOfbyte[j][j]);
      int m;
      for (m = j; m < i; m++)
        paramArrayOfbyte[j][m] = GF16.mul(paramArrayOfbyte[j][m], b); 
      for (m = j + 1; m < paramInt; m++) {
        byte b1 = paramArrayOfbyte[m][j];
        if (b1 != 0)
          for (int n = j; n < i; n++)
            paramArrayOfbyte[m][n] = (byte)(paramArrayOfbyte[m][n] ^ GF16.mul(paramArrayOfbyte[j][n], b1));  
      } 
    } 
    for (j = paramInt - 1; j >= 0; j--) {
      byte b = paramArrayOfbyte[j][paramInt];
      for (int k = j + 1; k < paramInt; k++)
        b = (byte)(b ^ GF16.mul(paramArrayOfbyte[j][k], paramArrayOfbyte1[k])); 
      paramArrayOfbyte1[j] = b;
    } 
    return 0;
  }
  
  private byte[] getMessageHash(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte = new byte[this.shake.getDigestSize()];
    this.shake.update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    this.shake.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\snova\SnovaSigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */