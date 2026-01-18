package org.bouncycastle.pqc.crypto.falcon;

class FalconCodec {
  static final byte[] max_fg_bits = new byte[] { 
      0, 8, 8, 8, 8, 8, 7, 7, 6, 6, 
      5 };
  
  static final byte[] max_FG_bits = new byte[] { 
      0, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
      8 };
  
  static int modq_encode(byte[] paramArrayOfbyte, int paramInt1, short[] paramArrayOfshort, int paramInt2) {
    int i = 1 << paramInt2;
    byte b1;
    for (b1 = 0; b1 < i; b1++) {
      if ((paramArrayOfshort[b1] & 0xFFFF) >= 12289)
        return 0; 
    } 
    int j = i * 14 + 7 >> 3;
    if (paramArrayOfbyte == null)
      return j; 
    if (j > paramInt1)
      return 0; 
    byte b2 = 1;
    int k = 0;
    byte b3 = 0;
    for (b1 = 0; b1 < i; b1++) {
      k = k << 14 | paramArrayOfshort[b1] & 0xFFFF;
      b3 += true;
      while (b3 >= 8) {
        b3 -= 8;
        paramArrayOfbyte[b2++] = (byte)(k >> b3);
      } 
    } 
    if (b3 > 0)
      paramArrayOfbyte[b2] = (byte)(k << 8 - b3); 
    return j;
  }
  
  static int modq_decode(short[] paramArrayOfshort, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    int i = 1 << paramInt1;
    int j = i * 14 + 7 >> 3;
    if (j > paramInt2)
      return 0; 
    byte b2 = 0;
    int k = 0;
    byte b3 = 0;
    byte b1 = 0;
    while (b1 < i) {
      k = k << 8 | paramArrayOfbyte[b2++] & 0xFF;
      b3 += true;
      if (b3 >= 14) {
        b3 -= 14;
        int m = k >>> b3 & 0x3FFF;
        if (m >= 12289)
          return 0; 
        paramArrayOfshort[b1] = (short)m;
        b1++;
      } 
    } 
    return ((k & (1 << b3) - 1) != 0) ? 0 : j;
  }
  
  static int trim_i8_encode(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) {
    int i = 1 << paramInt3;
    int m = (1 << paramInt4 - 1) - 1;
    int k = -m;
    byte b;
    for (b = 0; b < i; b++) {
      if (paramArrayOfbyte2[b] < k || paramArrayOfbyte2[b] > m)
        return 0; 
    } 
    int j = i * paramInt4 + 7 >> 3;
    if (paramArrayOfbyte1 == null)
      return j; 
    if (j > paramInt2)
      return 0; 
    int n = paramInt1;
    int i1 = 0;
    int i3 = 0;
    int i2 = (1 << paramInt4) - 1;
    for (b = 0; b < i; b++) {
      i1 = i1 << paramInt4 | paramArrayOfbyte2[b] & 0xFFFF & i2;
      i3 += paramInt4;
      while (i3 >= 8) {
        i3 -= 8;
        paramArrayOfbyte1[n++] = (byte)(i1 >>> i3);
      } 
    } 
    if (i3 > 0)
      paramArrayOfbyte1[n] = (byte)(i1 << 8 - i3); 
    return j;
  }
  
  static int trim_i8_decode(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) {
    int i = 1 << paramInt1;
    int j = i * paramInt2 + 7 >> 3;
    if (j > paramInt4)
      return 0; 
    int k = paramInt3;
    byte b = 0;
    int m = 0;
    int i2 = 0;
    int n = (1 << paramInt2) - 1;
    int i1 = 1 << paramInt2 - 1;
    while (b < i) {
      m = m << 8 | paramArrayOfbyte2[k++] & 0xFF;
      i2 += true;
      while (i2 >= paramInt2 && b < i) {
        i2 -= paramInt2;
        int i3 = m >>> i2 & n;
        i3 |= -(i3 & i1);
        if (i3 == -i1)
          return 0; 
        paramArrayOfbyte1[b] = (byte)i3;
        b++;
      } 
    } 
    return ((m & (1 << i2) - 1) != 0) ? 0 : j;
  }
  
  static int comp_encode(byte[] paramArrayOfbyte, int paramInt1, short[] paramArrayOfshort, int paramInt2) {
    int i = 1 << paramInt2;
    byte b1 = 0;
    byte b2;
    for (b2 = 0; b2 < i; b2++) {
      if (paramArrayOfshort[b2] < -2047 || paramArrayOfshort[b2] > 2047)
        return 0; 
    } 
    int j = 0;
    int k = 0;
    byte b3 = 0;
    for (b2 = 0; b2 < i; b2++) {
      j <<= 1;
      short s = paramArrayOfshort[b2];
      if (s < 0) {
        s = -s;
        j |= 0x1;
      } 
      int m = s;
      j <<= 7;
      j |= m & 0x7F;
      m >>>= 7;
      k += true;
      j <<= m + 1;
      j |= 0x1;
      k += m + 1;
      while (k >= 8) {
        k -= 8;
        if (paramArrayOfbyte != null) {
          if (b3 >= paramInt1)
            return 0; 
          paramArrayOfbyte[b1 + b3] = (byte)(j >>> k);
        } 
        b3++;
      } 
    } 
    if (k > 0) {
      if (paramArrayOfbyte != null) {
        if (b3 >= paramInt1)
          return 0; 
        paramArrayOfbyte[b1 + b3] = (byte)(j << 8 - k);
      } 
      b3++;
    } 
    return b3;
  }
  
  static int comp_decode(short[] paramArrayOfshort, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    // Byte code:
    //   0: iconst_1
    //   1: iload_1
    //   2: ishl
    //   3: istore #5
    //   5: iconst_0
    //   6: istore #4
    //   8: iconst_0
    //   9: istore #8
    //   11: iconst_0
    //   12: istore #9
    //   14: iconst_0
    //   15: istore #7
    //   17: iconst_0
    //   18: istore #6
    //   20: iload #6
    //   22: iload #5
    //   24: if_icmpge -> 186
    //   27: iload #7
    //   29: iload_3
    //   30: if_icmplt -> 35
    //   33: iconst_0
    //   34: ireturn
    //   35: iload #8
    //   37: bipush #8
    //   39: ishl
    //   40: aload_2
    //   41: iload #4
    //   43: iload #7
    //   45: iadd
    //   46: baload
    //   47: sipush #255
    //   50: iand
    //   51: ior
    //   52: istore #8
    //   54: iinc #7, 1
    //   57: iload #8
    //   59: iload #9
    //   61: iushr
    //   62: istore #10
    //   64: iload #10
    //   66: sipush #128
    //   69: iand
    //   70: istore #11
    //   72: iload #10
    //   74: bipush #127
    //   76: iand
    //   77: istore #12
    //   79: iload #9
    //   81: ifne -> 118
    //   84: iload #7
    //   86: iload_3
    //   87: if_icmplt -> 92
    //   90: iconst_0
    //   91: ireturn
    //   92: iload #8
    //   94: bipush #8
    //   96: ishl
    //   97: aload_2
    //   98: iload #4
    //   100: iload #7
    //   102: iadd
    //   103: baload
    //   104: sipush #255
    //   107: iand
    //   108: ior
    //   109: istore #8
    //   111: iinc #7, 1
    //   114: bipush #8
    //   116: istore #9
    //   118: iinc #9, -1
    //   121: iload #8
    //   123: iload #9
    //   125: iushr
    //   126: iconst_1
    //   127: iand
    //   128: ifeq -> 134
    //   131: goto -> 150
    //   134: wide iinc #12 128
    //   140: iload #12
    //   142: sipush #2047
    //   145: if_icmple -> 79
    //   148: iconst_0
    //   149: ireturn
    //   150: iload #11
    //   152: ifeq -> 162
    //   155: iload #12
    //   157: ifne -> 162
    //   160: iconst_0
    //   161: ireturn
    //   162: aload_0
    //   163: iload #6
    //   165: iload #11
    //   167: ifeq -> 176
    //   170: iload #12
    //   172: ineg
    //   173: goto -> 178
    //   176: iload #12
    //   178: i2s
    //   179: sastore
    //   180: iinc #6, 1
    //   183: goto -> 20
    //   186: iload #8
    //   188: iconst_1
    //   189: iload #9
    //   191: ishl
    //   192: iconst_1
    //   193: isub
    //   194: iand
    //   195: ifeq -> 200
    //   198: iconst_0
    //   199: ireturn
    //   200: iload #7
    //   202: ireturn
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */