package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.SavableDigest;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.Strings;

public class TupleHash implements Xof, SavableDigest {
  private static final byte[] N_TUPLE_HASH = Strings.toByteArray("TupleHash");
  
  private final CSHAKEDigest cshake;
  
  private int bitLength;
  
  private int outputLength;
  
  private boolean firstOutput;
  
  public TupleHash(int paramInt, byte[] paramArrayOfbyte) {
    this(paramInt, paramArrayOfbyte, paramInt * 2);
  }
  
  public TupleHash(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    this.cshake = new CSHAKEDigest(paramInt1, N_TUPLE_HASH, paramArrayOfbyte);
    this.bitLength = paramInt1;
    this.outputLength = (paramInt2 + 7) / 8;
    reset();
  }
  
  public TupleHash(TupleHash paramTupleHash) {
    this.cshake = new CSHAKEDigest(paramTupleHash.cshake);
    this.bitLength = paramTupleHash.bitLength;
    this.outputLength = paramTupleHash.outputLength;
    this.firstOutput = paramTupleHash.firstOutput;
  }
  
  public TupleHash(byte[] paramArrayOfbyte) {
    this.cshake = new CSHAKEDigest(Arrays.copyOfRange(paramArrayOfbyte, 0, paramArrayOfbyte.length - 9));
    this.bitLength = Pack.bigEndianToInt(paramArrayOfbyte, paramArrayOfbyte.length - 9);
    this.outputLength = Pack.bigEndianToInt(paramArrayOfbyte, paramArrayOfbyte.length - 5);
    this.firstOutput = (paramArrayOfbyte[paramArrayOfbyte.length - 1] != 0);
  }
  
  private void copyIn(TupleHash paramTupleHash) {
    this.cshake.reset((Memoable)paramTupleHash.cshake);
    this.bitLength = this.cshake.fixedOutputLength;
    this.outputLength = this.bitLength * 2 / 8;
    this.firstOutput = paramTupleHash.firstOutput;
  }
  
  public String getAlgorithmName() {
    return "TupleHash" + this.cshake.getAlgorithmName().substring(6);
  }
  
  public int getByteLength() {
    return this.cshake.getByteLength();
  }
  
  public int getDigestSize() {
    return this.outputLength;
  }
  
  public void update(byte paramByte) throws IllegalStateException {
    byte[] arrayOfByte = XofUtils.encode(paramByte);
    this.cshake.update(arrayOfByte, 0, arrayOfByte.length);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DataLengthException, IllegalStateException {
    byte[] arrayOfByte = XofUtils.encode(paramArrayOfbyte, paramInt1, paramInt2);
    this.cshake.update(arrayOfByte, 0, arrayOfByte.length);
  }
  
  private void wrapUp(int paramInt) {
    byte[] arrayOfByte = XofUtils.rightEncode(paramInt * 8L);
    this.cshake.update(arrayOfByte, 0, arrayOfByte.length);
    this.firstOutput = false;
  }
  
  public int doFinal(byte[] paramArrayOfbyte, int paramInt) throws DataLengthException, IllegalStateException {
    if (this.firstOutput)
      wrapUp(getDigestSize()); 
    int i = this.cshake.doFinal(paramArrayOfbyte, paramInt, getDigestSize());
    reset();
    return i;
  }
  
  public int doFinal(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (this.firstOutput)
      wrapUp(getDigestSize()); 
    int i = this.cshake.doFinal(paramArrayOfbyte, paramInt1, paramInt2);
    reset();
    return i;
  }
  
  public int doOutput(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (this.firstOutput)
      wrapUp(0); 
    return this.cshake.doOutput(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void reset() {
    this.cshake.reset();
    this.firstOutput = true;
  }
  
  public byte[] getEncodedState() {
    byte[] arrayOfByte1 = this.cshake.getEncodedState();
    byte[] arrayOfByte2 = new byte[9];
    Pack.intToBigEndian(this.bitLength, arrayOfByte2, 0);
    Pack.intToBigEndian(this.outputLength, arrayOfByte2, 4);
    arrayOfByte2[8] = this.firstOutput ? 1 : 0;
    return Arrays.concatenate(arrayOfByte1, arrayOfByte2);
  }
  
  public Memoable copy() {
    return (Memoable)new TupleHash(this);
  }
  
  public void reset(Memoable paramMemoable) {
    copyIn((TupleHash)paramMemoable);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\digests\TupleHash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */