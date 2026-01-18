/*    */ package it.unimi.dsi.fastutil.bytes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.Pair;
/*    */ import java.io.Serializable;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ByteShortMutablePair
/*    */   implements ByteShortPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected byte left;
/*    */   protected short right;
/*    */   
/*    */   public ByteShortMutablePair(byte left, short right) {
/* 35 */     this.left = left;
/* 36 */     this.right = right;
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
/*    */   
/*    */   public static ByteShortMutablePair of(byte left, short right) {
/* 49 */     return new ByteShortMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte leftByte() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteShortMutablePair left(byte l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public short rightShort() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteShortMutablePair right(short r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ByteShortPair) {
/* 79 */       return (this.left == ((ByteShortPair)other).leftByte() && this.right == ((ByteShortPair)other).rightShort());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Byte.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Short.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return this.left * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftByte() + "," + rightShort() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteShortMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */