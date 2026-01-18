package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.crypto.digests.CSHAKEDigest;
import org.bouncycastle.crypto.digests.EncodableDigest;
import org.bouncycastle.crypto.digests.XofUtils;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.Strings;

public class KMAC implements Mac, Xof, Memoable, EncodableDigest {
  private static final byte[] padding = new byte[100];
  
  private final CSHAKEDigest cshake;
  
  private int bitLength;
  
  private int outputLength;
  
  private byte[] key;
  
  private boolean initialised;
  
  private boolean firstOutput;
  
  public KMAC(int paramInt, byte[] paramArrayOfbyte) {
    this.cshake = new CSHAKEDigest(paramInt, Strings.toByteArray("KMAC"), paramArrayOfbyte);
    this.bitLength = paramInt;
    this.outputLength = paramInt * 2 / 8;
  }
  
  public KMAC(KMAC paramKMAC) {
    this.cshake = new CSHAKEDigest(paramKMAC.cshake);
    this.bitLength = paramKMAC.bitLength;
    this.outputLength = paramKMAC.outputLength;
    this.key = paramKMAC.key;
    this.initialised = paramKMAC.initialised;
    this.firstOutput = paramKMAC.firstOutput;
  }
  
  public KMAC(byte[] paramArrayOfbyte) {
    this.key = new byte[paramArrayOfbyte[0] & 0xFF];
    System.arraycopy(paramArrayOfbyte, 1, this.key, 0, this.key.length);
    this.cshake = new CSHAKEDigest(Arrays.copyOfRange(paramArrayOfbyte, 1 + this.key.length, paramArrayOfbyte.length - 10));
    this.bitLength = Pack.bigEndianToInt(paramArrayOfbyte, paramArrayOfbyte.length - 10);
    this.outputLength = Pack.bigEndianToInt(paramArrayOfbyte, paramArrayOfbyte.length - 6);
    this.initialised = (paramArrayOfbyte[paramArrayOfbyte.length - 2] != 0);
    this.firstOutput = (paramArrayOfbyte[paramArrayOfbyte.length - 1] != 0);
  }
  
  private void copyIn(KMAC paramKMAC) {
    this.cshake.reset((Memoable)paramKMAC.cshake);
    this.bitLength = paramKMAC.bitLength;
    this.outputLength = paramKMAC.outputLength;
    this.initialised = paramKMAC.initialised;
    this.firstOutput = paramKMAC.firstOutput;
  }
  
  public void init(CipherParameters paramCipherParameters) throws IllegalArgumentException {
    KeyParameter keyParameter = (KeyParameter)paramCipherParameters;
    this.key = Arrays.clone(keyParameter.getKey());
    if (this.key.length > 255)
      throw new IllegalArgumentException("key length must be between 0 and 2040 bits"); 
    this.initialised = true;
    reset();
  }
  
  public String getAlgorithmName() {
    return "KMAC" + this.cshake.getAlgorithmName().substring(6);
  }
  
  public int getByteLength() {
    return this.cshake.getByteLength();
  }
  
  public int getMacSize() {
    return this.outputLength;
  }
  
  public int getDigestSize() {
    return this.outputLength;
  }
  
  public void update(byte paramByte) throws IllegalStateException {
    if (!this.initialised)
      throw new IllegalStateException("KMAC not initialized"); 
    this.cshake.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DataLengthException, IllegalStateException {
    if (!this.initialised)
      throw new IllegalStateException("KMAC not initialized"); 
    this.cshake.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public int doFinal(byte[] paramArrayOfbyte, int paramInt) throws DataLengthException, IllegalStateException {
    if (this.firstOutput) {
      if (!this.initialised)
        throw new IllegalStateException("KMAC not initialized"); 
      byte[] arrayOfByte = XofUtils.rightEncode((getMacSize() * 8));
      this.cshake.update(arrayOfByte, 0, arrayOfByte.length);
    } 
    int i = this.cshake.doFinal(paramArrayOfbyte, paramInt, getMacSize());
    reset();
    return i;
  }
  
  public int doFinal(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (this.firstOutput) {
      if (!this.initialised)
        throw new IllegalStateException("KMAC not initialized"); 
      byte[] arrayOfByte = XofUtils.rightEncode((paramInt2 * 8));
      this.cshake.update(arrayOfByte, 0, arrayOfByte.length);
    } 
    int i = this.cshake.doFinal(paramArrayOfbyte, paramInt1, paramInt2);
    reset();
    return i;
  }
  
  public int doOutput(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (this.firstOutput) {
      if (!this.initialised)
        throw new IllegalStateException("KMAC not initialized"); 
      byte[] arrayOfByte = XofUtils.rightEncode(0L);
      this.cshake.update(arrayOfByte, 0, arrayOfByte.length);
      this.firstOutput = false;
    } 
    return this.cshake.doOutput(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void reset() {
    this.cshake.reset();
    if (this.key != null)
      if (this.bitLength == 128) {
        bytePad(this.key, 168);
      } else {
        bytePad(this.key, 136);
      }  
    this.firstOutput = true;
  }
  
  private void bytePad(byte[] paramArrayOfbyte, int paramInt) {
    byte[] arrayOfByte1 = XofUtils.leftEncode(paramInt);
    update(arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = encode(paramArrayOfbyte);
    update(arrayOfByte2, 0, arrayOfByte2.length);
    int i = paramInt - (arrayOfByte1.length + arrayOfByte2.length) % paramInt;
    if (i > 0 && i != paramInt) {
      while (i > padding.length) {
        update(padding, 0, padding.length);
        i -= padding.length;
      } 
      update(padding, 0, i);
    } 
  }
  
  private static byte[] encode(byte[] paramArrayOfbyte) {
    return Arrays.concatenate(XofUtils.leftEncode((paramArrayOfbyte.length * 8)), paramArrayOfbyte);
  }
  
  public byte[] getEncodedState() {
    if (!this.initialised)
      throw new IllegalStateException("KMAC not initialised"); 
    byte[] arrayOfByte1 = this.cshake.getEncodedState();
    byte[] arrayOfByte2 = new byte[10];
    Pack.intToBigEndian(this.bitLength, arrayOfByte2, 0);
    Pack.intToBigEndian(this.outputLength, arrayOfByte2, 4);
    arrayOfByte2[8] = this.initialised ? 1 : 0;
    arrayOfByte2[9] = this.firstOutput ? 1 : 0;
    byte[] arrayOfByte3 = new byte[1 + this.key.length + arrayOfByte1.length + arrayOfByte2.length];
    arrayOfByte3[0] = (byte)this.key.length;
    System.arraycopy(this.key, 0, arrayOfByte3, 1, this.key.length);
    System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 1 + this.key.length, arrayOfByte1.length);
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 1 + this.key.length + arrayOfByte1.length, arrayOfByte2.length);
    return arrayOfByte3;
  }
  
  public Memoable copy() {
    return new KMAC(this);
  }
  
  public void reset(Memoable paramMemoable) {
    copyIn((KMAC)paramMemoable);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\macs\KMAC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */