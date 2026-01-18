package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.engines.ChaCha7539Engine;
import org.bouncycastle.crypto.macs.Poly1305;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class ChaCha20Poly1305 implements AEADCipher {
  private static final int BUF_SIZE = 64;
  
  private static final int KEY_SIZE = 32;
  
  private static final int NONCE_SIZE = 12;
  
  private static final int MAC_SIZE = 16;
  
  private static final byte[] ZEROES = new byte[15];
  
  private static final long AAD_LIMIT = -1L;
  
  private static final long DATA_LIMIT = 274877906880L;
  
  private final ChaCha7539Engine chacha20;
  
  private final Mac poly1305;
  
  private final byte[] key = new byte[32];
  
  private final byte[] nonce = new byte[12];
  
  private final byte[] buf = new byte[80];
  
  private final byte[] mac = new byte[16];
  
  private byte[] initialAAD;
  
  private long aadCount;
  
  private long dataCount;
  
  private int state = 0;
  
  private int bufPos;
  
  public ChaCha20Poly1305() {
    this((Mac)new Poly1305());
  }
  
  public ChaCha20Poly1305(Mac paramMac) {
    if (null == paramMac)
      throw new NullPointerException("'poly1305' cannot be null"); 
    if (16 != paramMac.getMacSize())
      throw new IllegalArgumentException("'poly1305' must be a 128-bit MAC"); 
    this.chacha20 = new ChaCha7539Engine();
    this.poly1305 = paramMac;
  }
  
  public String getAlgorithmName() {
    return "ChaCha20Poly1305";
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) throws IllegalArgumentException {
    KeyParameter keyParameter;
    byte[] arrayOfByte;
    ParametersWithIV parametersWithIV;
    if (paramCipherParameters instanceof AEADParameters) {
      AEADParameters aEADParameters = (AEADParameters)paramCipherParameters;
      int i = aEADParameters.getMacSize();
      if (128 != i)
        throw new IllegalArgumentException("Invalid value for MAC size: " + i); 
      keyParameter = aEADParameters.getKey();
      arrayOfByte = aEADParameters.getNonce();
      parametersWithIV = new ParametersWithIV((CipherParameters)keyParameter, arrayOfByte);
      this.initialAAD = aEADParameters.getAssociatedText();
    } else if (paramCipherParameters instanceof ParametersWithIV) {
      ParametersWithIV parametersWithIV1 = (ParametersWithIV)paramCipherParameters;
      keyParameter = (KeyParameter)parametersWithIV1.getParameters();
      arrayOfByte = parametersWithIV1.getIV();
      parametersWithIV = parametersWithIV1;
      this.initialAAD = null;
    } else {
      throw new IllegalArgumentException("invalid parameters passed to ChaCha20Poly1305");
    } 
    if (null == keyParameter) {
      if (0 == this.state)
        throw new IllegalArgumentException("Key must be specified in initial init"); 
    } else if (32 != keyParameter.getKeyLength()) {
      throw new IllegalArgumentException("Key must be 256 bits");
    } 
    if (null == arrayOfByte || 12 != arrayOfByte.length)
      throw new IllegalArgumentException("Nonce must be 96 bits"); 
    if (0 != this.state && paramBoolean && Arrays.areEqual(this.nonce, arrayOfByte) && (null == keyParameter || Arrays.areEqual(this.key, keyParameter.getKey())))
      throw new IllegalArgumentException("cannot reuse nonce for ChaCha20Poly1305 encryption"); 
    if (null != keyParameter)
      keyParameter.copyTo(this.key, 0, 32); 
    System.arraycopy(arrayOfByte, 0, this.nonce, 0, 12);
    this.chacha20.init(true, (CipherParameters)parametersWithIV);
    this.state = paramBoolean ? 1 : 5;
    reset(true, false);
  }
  
  public int getOutputSize(int paramInt) {
    int i = Math.max(0, paramInt) + this.bufPos;
    switch (this.state) {
      case 5:
      case 6:
      case 7:
        return Math.max(0, i - 16);
      case 1:
      case 2:
      case 3:
        return i + 16;
    } 
    throw new IllegalStateException();
  }
  
  public int getUpdateOutputSize(int paramInt) {
    int i = Math.max(0, paramInt) + this.bufPos;
    switch (this.state) {
      case 5:
      case 6:
      case 7:
        i = Math.max(0, i - 16);
      case 1:
      case 2:
      case 3:
        return i - i % 64;
    } 
    throw new IllegalStateException();
  }
  
  public void processAADByte(byte paramByte) {
    checkAAD();
    this.aadCount = incrementCount(this.aadCount, 1, -1L);
    this.poly1305.update(paramByte);
  }
  
  public void processAADBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (null == paramArrayOfbyte)
      throw new NullPointerException("'in' cannot be null"); 
    if (paramInt1 < 0)
      throw new IllegalArgumentException("'inOff' cannot be negative"); 
    if (paramInt2 < 0)
      throw new IllegalArgumentException("'len' cannot be negative"); 
    if (paramInt1 > paramArrayOfbyte.length - paramInt2)
      throw new DataLengthException("Input buffer too short"); 
    checkAAD();
    if (paramInt2 > 0) {
      this.aadCount = incrementCount(this.aadCount, paramInt2, -1L);
      this.poly1305.update(paramArrayOfbyte, paramInt1, paramInt2);
    } 
  }
  
  public int processByte(byte paramByte, byte[] paramArrayOfbyte, int paramInt) throws DataLengthException {
    checkData();
    switch (this.state) {
      case 7:
        this.buf[this.bufPos] = paramByte;
        if (++this.bufPos == this.buf.length) {
          this.poly1305.update(this.buf, 0, 64);
          processData(this.buf, 0, 64, paramArrayOfbyte, paramInt);
          System.arraycopy(this.buf, 64, this.buf, 0, 16);
          this.bufPos = 16;
          return 64;
        } 
        return 0;
      case 3:
        this.buf[this.bufPos] = paramByte;
        if (++this.bufPos == 64) {
          processData(this.buf, 0, 64, paramArrayOfbyte, paramInt);
          this.poly1305.update(paramArrayOfbyte, paramInt, 64);
          this.bufPos = 0;
          return 64;
        } 
        return 0;
    } 
    throw new IllegalStateException();
  }
  
  public int processBytes(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) throws DataLengthException {
    // Byte code:
    //   0: aconst_null
    //   1: aload_1
    //   2: if_acmpne -> 15
    //   5: new java/lang/NullPointerException
    //   8: dup
    //   9: ldc ''in' cannot be null'
    //   11: invokespecial <init> : (Ljava/lang/String;)V
    //   14: athrow
    //   15: aconst_null
    //   16: aload #4
    //   18: if_acmpne -> 21
    //   21: iload_2
    //   22: ifge -> 35
    //   25: new java/lang/IllegalArgumentException
    //   28: dup
    //   29: ldc ''inOff' cannot be negative'
    //   31: invokespecial <init> : (Ljava/lang/String;)V
    //   34: athrow
    //   35: iload_3
    //   36: ifge -> 49
    //   39: new java/lang/IllegalArgumentException
    //   42: dup
    //   43: ldc ''len' cannot be negative'
    //   45: invokespecial <init> : (Ljava/lang/String;)V
    //   48: athrow
    //   49: iload_2
    //   50: aload_1
    //   51: arraylength
    //   52: iload_3
    //   53: isub
    //   54: if_icmple -> 67
    //   57: new org/bouncycastle/crypto/DataLengthException
    //   60: dup
    //   61: ldc 'Input buffer too short'
    //   63: invokespecial <init> : (Ljava/lang/String;)V
    //   66: athrow
    //   67: iload #5
    //   69: ifge -> 82
    //   72: new java/lang/IllegalArgumentException
    //   75: dup
    //   76: ldc ''outOff' cannot be negative'
    //   78: invokespecial <init> : (Ljava/lang/String;)V
    //   81: athrow
    //   82: aload_1
    //   83: aload #4
    //   85: if_acmpne -> 118
    //   88: iload_2
    //   89: iload_3
    //   90: iload #5
    //   92: aload_0
    //   93: iload_3
    //   94: invokevirtual getUpdateOutputSize : (I)I
    //   97: invokestatic segmentsOverlap : (IIII)Z
    //   100: ifeq -> 118
    //   103: iload_3
    //   104: newarray byte
    //   106: astore_1
    //   107: aload #4
    //   109: iload_2
    //   110: aload_1
    //   111: iconst_0
    //   112: iload_3
    //   113: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   116: iconst_0
    //   117: istore_2
    //   118: aload_0
    //   119: invokespecial checkData : ()V
    //   122: iconst_0
    //   123: istore #6
    //   125: aload_0
    //   126: getfield state : I
    //   129: lookupswitch default -> 428, 3 -> 267, 7 -> 156
    //   156: iconst_0
    //   157: istore #7
    //   159: iload #7
    //   161: iload_3
    //   162: if_icmpge -> 264
    //   165: aload_0
    //   166: getfield buf : [B
    //   169: aload_0
    //   170: getfield bufPos : I
    //   173: aload_1
    //   174: iload_2
    //   175: iload #7
    //   177: iadd
    //   178: baload
    //   179: bastore
    //   180: aload_0
    //   181: dup
    //   182: getfield bufPos : I
    //   185: iconst_1
    //   186: iadd
    //   187: dup_x1
    //   188: putfield bufPos : I
    //   191: aload_0
    //   192: getfield buf : [B
    //   195: arraylength
    //   196: if_icmpne -> 258
    //   199: aload_0
    //   200: getfield poly1305 : Lorg/bouncycastle/crypto/Mac;
    //   203: aload_0
    //   204: getfield buf : [B
    //   207: iconst_0
    //   208: bipush #64
    //   210: invokeinterface update : ([BII)V
    //   215: aload_0
    //   216: aload_0
    //   217: getfield buf : [B
    //   220: iconst_0
    //   221: bipush #64
    //   223: aload #4
    //   225: iload #5
    //   227: iload #6
    //   229: iadd
    //   230: invokespecial processData : ([BII[BI)V
    //   233: aload_0
    //   234: getfield buf : [B
    //   237: bipush #64
    //   239: aload_0
    //   240: getfield buf : [B
    //   243: iconst_0
    //   244: bipush #16
    //   246: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   249: aload_0
    //   250: bipush #16
    //   252: putfield bufPos : I
    //   255: iinc #6, 64
    //   258: iinc #7, 1
    //   261: goto -> 159
    //   264: goto -> 436
    //   267: aload_0
    //   268: getfield bufPos : I
    //   271: ifeq -> 354
    //   274: iload_3
    //   275: ifle -> 354
    //   278: iinc #3, -1
    //   281: aload_0
    //   282: getfield buf : [B
    //   285: aload_0
    //   286: getfield bufPos : I
    //   289: aload_1
    //   290: iload_2
    //   291: iinc #2, 1
    //   294: baload
    //   295: bastore
    //   296: aload_0
    //   297: dup
    //   298: getfield bufPos : I
    //   301: iconst_1
    //   302: iadd
    //   303: dup_x1
    //   304: putfield bufPos : I
    //   307: bipush #64
    //   309: if_icmpne -> 274
    //   312: aload_0
    //   313: aload_0
    //   314: getfield buf : [B
    //   317: iconst_0
    //   318: bipush #64
    //   320: aload #4
    //   322: iload #5
    //   324: invokespecial processData : ([BII[BI)V
    //   327: aload_0
    //   328: getfield poly1305 : Lorg/bouncycastle/crypto/Mac;
    //   331: aload #4
    //   333: iload #5
    //   335: bipush #64
    //   337: invokeinterface update : ([BII)V
    //   342: aload_0
    //   343: iconst_0
    //   344: putfield bufPos : I
    //   347: bipush #64
    //   349: istore #6
    //   351: goto -> 354
    //   354: iload_3
    //   355: bipush #64
    //   357: if_icmplt -> 405
    //   360: aload_0
    //   361: aload_1
    //   362: iload_2
    //   363: bipush #64
    //   365: aload #4
    //   367: iload #5
    //   369: iload #6
    //   371: iadd
    //   372: invokespecial processData : ([BII[BI)V
    //   375: aload_0
    //   376: getfield poly1305 : Lorg/bouncycastle/crypto/Mac;
    //   379: aload #4
    //   381: iload #5
    //   383: iload #6
    //   385: iadd
    //   386: bipush #64
    //   388: invokeinterface update : ([BII)V
    //   393: iinc #2, 64
    //   396: iinc #3, -64
    //   399: iinc #6, 64
    //   402: goto -> 354
    //   405: iload_3
    //   406: ifle -> 436
    //   409: aload_1
    //   410: iload_2
    //   411: aload_0
    //   412: getfield buf : [B
    //   415: iconst_0
    //   416: iload_3
    //   417: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   420: aload_0
    //   421: iload_3
    //   422: putfield bufPos : I
    //   425: goto -> 436
    //   428: new java/lang/IllegalStateException
    //   431: dup
    //   432: invokespecial <init> : ()V
    //   435: athrow
    //   436: iload #6
    //   438: ireturn
  }
  
  public int doFinal(byte[] paramArrayOfbyte, int paramInt) throws IllegalStateException, InvalidCipherTextException {
    if (null == paramArrayOfbyte)
      throw new NullPointerException("'out' cannot be null"); 
    if (paramInt < 0)
      throw new IllegalArgumentException("'outOff' cannot be negative"); 
    checkData();
    Arrays.clear(this.mac);
    int i = 0;
    switch (this.state) {
      case 7:
        if (this.bufPos < 16)
          throw new InvalidCipherTextException("data too short"); 
        i = this.bufPos - 16;
        if (paramInt > paramArrayOfbyte.length - i)
          throw new OutputLengthException("Output buffer too short"); 
        if (i > 0) {
          this.poly1305.update(this.buf, 0, i);
          processData(this.buf, 0, i, paramArrayOfbyte, paramInt);
        } 
        finishData(8);
        if (!Arrays.constantTimeAreEqual(16, this.mac, 0, this.buf, i))
          throw new InvalidCipherTextException("mac check in ChaCha20Poly1305 failed"); 
        reset(false, true);
        return i;
      case 3:
        i = this.bufPos + 16;
        if (paramInt > paramArrayOfbyte.length - i)
          throw new OutputLengthException("Output buffer too short"); 
        if (this.bufPos > 0) {
          processData(this.buf, 0, this.bufPos, paramArrayOfbyte, paramInt);
          this.poly1305.update(paramArrayOfbyte, paramInt, this.bufPos);
        } 
        finishData(4);
        System.arraycopy(this.mac, 0, paramArrayOfbyte, paramInt + this.bufPos, 16);
        reset(false, true);
        return i;
    } 
    throw new IllegalStateException();
  }
  
  public byte[] getMac() {
    return Arrays.clone(this.mac);
  }
  
  public void reset() {
    reset(true, true);
  }
  
  private void checkAAD() {
    switch (this.state) {
      case 5:
        this.state = 6;
      case 1:
        this.state = 2;
      case 2:
      case 6:
        return;
      case 4:
        throw new IllegalStateException("ChaCha20Poly1305 cannot be reused for encryption");
    } 
    throw new IllegalStateException();
  }
  
  private void checkData() {
    switch (this.state) {
      case 5:
      case 6:
        finishAAD(7);
      case 1:
      case 2:
        finishAAD(3);
      case 3:
      case 7:
        return;
      case 4:
        throw new IllegalStateException("ChaCha20Poly1305 cannot be reused for encryption");
    } 
    throw new IllegalStateException();
  }
  
  private void finishAAD(int paramInt) {
    padMAC(this.aadCount);
    this.state = paramInt;
  }
  
  private void finishData(int paramInt) {
    padMAC(this.dataCount);
    byte[] arrayOfByte = new byte[16];
    Pack.longToLittleEndian(this.aadCount, arrayOfByte, 0);
    Pack.longToLittleEndian(this.dataCount, arrayOfByte, 8);
    this.poly1305.update(arrayOfByte, 0, 16);
    this.poly1305.doFinal(this.mac, 0);
    this.state = paramInt;
  }
  
  private long incrementCount(long paramLong1, int paramInt, long paramLong2) {
    if (paramLong1 + Long.MIN_VALUE > paramLong2 - paramInt + Long.MIN_VALUE)
      throw new IllegalStateException("Limit exceeded"); 
    return paramLong1 + paramInt;
  }
  
  private void initMAC() {
    byte[] arrayOfByte = new byte[64];
    try {
      this.chacha20.processBytes(arrayOfByte, 0, 64, arrayOfByte, 0);
      this.poly1305.init((CipherParameters)new KeyParameter(arrayOfByte, 0, 32));
    } finally {
      Arrays.clear(arrayOfByte);
    } 
  }
  
  private void padMAC(long paramLong) {
    int i = (int)paramLong & 0xF;
    if (0 != i)
      this.poly1305.update(ZEROES, 0, 16 - i); 
  }
  
  private void processData(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) {
    if (paramInt3 > paramArrayOfbyte2.length - paramInt2)
      throw new OutputLengthException("Output buffer too short"); 
    this.chacha20.processBytes(paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3);
    this.dataCount = incrementCount(this.dataCount, paramInt2, 274877906880L);
  }
  
  private void reset(boolean paramBoolean1, boolean paramBoolean2) {
    Arrays.clear(this.buf);
    if (paramBoolean1)
      Arrays.clear(this.mac); 
    this.aadCount = 0L;
    this.dataCount = 0L;
    this.bufPos = 0;
    switch (this.state) {
      case 1:
      case 5:
        break;
      case 6:
      case 7:
      case 8:
        this.state = 5;
        break;
      case 2:
      case 3:
      case 4:
        this.state = 4;
        return;
      default:
        throw new IllegalStateException();
    } 
    if (paramBoolean2)
      this.chacha20.reset(); 
    initMAC();
    if (null != this.initialAAD)
      processAADBytes(this.initialAAD, 0, this.initialAAD.length); 
  }
  
  private static final class State {
    static final int UNINITIALIZED = 0;
    
    static final int ENC_INIT = 1;
    
    static final int ENC_AAD = 2;
    
    static final int ENC_DATA = 3;
    
    static final int ENC_FINAL = 4;
    
    static final int DEC_INIT = 5;
    
    static final int DEC_AAD = 6;
    
    static final int DEC_DATA = 7;
    
    static final int DEC_FINAL = 8;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\modes\ChaCha20Poly1305.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */