/*    */ package ch.randelshofer.fastdoubleparser;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class BigSignificand
/*    */ {
/*    */   private static final long LONG_MASK = 4294967295L;
/*    */   private final int numInts;
/*    */   private final int[] x;
/*    */   private int firstNonZeroInt;
/*    */   
/*    */   public BigSignificand(long numBits) {
/* 21 */     if (numBits <= 0L || numBits >= 2147483647L) {
/* 22 */       throw new IllegalArgumentException("numBits=" + numBits);
/*    */     }
/* 24 */     int numLongs = (int)(numBits + 63L >>> 6L) + 1;
/* 25 */     this.numInts = numLongs << 1;
/* 26 */     this.x = new int[this.numInts];
/* 27 */     this.firstNonZeroInt = this.numInts;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(int value) {
/* 37 */     if (value == 0) {
/*    */       return;
/*    */     }
/* 40 */     long carry = value & 0xFFFFFFFFL;
/* 41 */     int i = this.numInts - 1;
/* 42 */     for (; carry != 0L; i--) {
/* 43 */       long sum = (x(i) & 0xFFFFFFFFL) + carry;
/* 44 */       x(i, (int)sum);
/* 45 */       carry = sum >>> 32L;
/*    */     } 
/* 47 */     this.firstNonZeroInt = Math.min(this.firstNonZeroInt, i + 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void fma(int factor, int addend) {
/* 59 */     long factorL = factor & 0xFFFFFFFFL;
/* 60 */     long carry = addend;
/* 61 */     int i = this.numInts - 1;
/* 62 */     for (; i >= this.firstNonZeroInt; i--) {
/* 63 */       long product = factorL * (x(i) & 0xFFFFFFFFL) + carry;
/* 64 */       x(i, (int)product);
/* 65 */       carry = product >>> 32L;
/*    */     } 
/* 67 */     if (carry != 0L) {
/* 68 */       x(i, (int)carry);
/* 69 */       this.firstNonZeroInt = i;
/*    */     } 
/*    */   }
/*    */   
/*    */   public BigInteger toBigInteger() {
/* 74 */     byte[] bytes = new byte[this.x.length << 2];
/* 75 */     IntBuffer buf = ByteBuffer.wrap(bytes).asIntBuffer();
/* 76 */     for (int i = 0; i < this.x.length; i++) {
/* 77 */       buf.put(i, this.x[i]);
/*    */     }
/* 79 */     return new BigInteger(bytes);
/*    */   }
/*    */   
/*    */   private void x(int i, int value) {
/* 83 */     this.x[i] = value;
/*    */   }
/*    */   
/*    */   private int x(int i) {
/* 87 */     return this.x[i];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\BigSignificand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */