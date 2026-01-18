/*    */ package META-INF.versions.11.ch.randelshofer.fastdoubleparser;
/*    */ 
/*    */ import java.lang.invoke.MethodHandles;
/*    */ import java.lang.invoke.VarHandle;
/*    */ import java.math.BigInteger;
/*    */ import java.nio.ByteOrder;
/*    */ 
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
/* 18 */   private static final VarHandle readIntBE = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.BIG_ENDIAN);
/*    */ 
/*    */   
/*    */   private final int numInts;
/*    */   
/*    */   private final byte[] x;
/*    */   
/*    */   private int firstNonZeroInt;
/*    */ 
/*    */   
/*    */   public BigSignificand(long numBits) {
/* 29 */     if (numBits <= 0L || numBits >= 2147483647L) {
/* 30 */       throw new IllegalArgumentException("numBits=" + numBits);
/*    */     }
/* 32 */     int numLongs = (int)(numBits + 63L >>> 6L) + 1;
/* 33 */     this.numInts = numLongs << 1;
/* 34 */     int numBytes = numLongs << 3;
/* 35 */     this.x = new byte[numBytes];
/* 36 */     this.firstNonZeroInt = this.numInts;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(int value) {
/* 46 */     if (value == 0) {
/*    */       return;
/*    */     }
/* 49 */     long carry = value & 0xFFFFFFFFL;
/* 50 */     int i = this.numInts - 1;
/* 51 */     for (; carry != 0L; i--) {
/* 52 */       long sum = (x(i) & 0xFFFFFFFFL) + carry;
/* 53 */       x(i, (int)sum);
/* 54 */       carry = sum >>> 32L;
/*    */     } 
/* 56 */     this.firstNonZeroInt = Math.min(this.firstNonZeroInt, i + 1);
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
/* 68 */     long factorL = factor & 0xFFFFFFFFL;
/* 69 */     long carry = addend;
/* 70 */     int i = this.numInts - 1;
/* 71 */     for (; i >= this.firstNonZeroInt; i--) {
/* 72 */       long product = factorL * (x(i) & 0xFFFFFFFFL) + carry;
/* 73 */       x(i, (int)product);
/* 74 */       carry = product >>> 32L;
/*    */     } 
/* 76 */     if (carry != 0L) {
/* 77 */       x(i, (int)carry);
/* 78 */       this.firstNonZeroInt = i;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BigInteger toBigInteger() {
/* 87 */     return new BigInteger(this.x);
/*    */   }
/*    */   
/*    */   private void x(int i, int value) {
/* 91 */     readIntBE.set(this.x, i << 2, value);
/*    */   }
/*    */   
/*    */   private int x(int i) {
/* 95 */     return readIntBE.get(this.x, i << 2);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\11\ch\randelshofer\fastdoubleparser\BigSignificand.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */