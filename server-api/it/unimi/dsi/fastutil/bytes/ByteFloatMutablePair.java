/*    */ package it.unimi.dsi.fastutil.bytes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.HashCommon;
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
/*    */ public class ByteFloatMutablePair
/*    */   implements ByteFloatPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected byte left;
/*    */   protected float right;
/*    */   
/*    */   public ByteFloatMutablePair(byte left, float right) {
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
/*    */   public static ByteFloatMutablePair of(byte left, float right) {
/* 49 */     return new ByteFloatMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte leftByte() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteFloatMutablePair left(byte l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public float rightFloat() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteFloatMutablePair right(float r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ByteFloatPair) {
/* 79 */       return (this.left == ((ByteFloatPair)other).leftByte() && this.right == ((ByteFloatPair)other).rightFloat());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Byte.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Float.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return this.left * 19 + HashCommon.float2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftByte() + "," + rightFloat() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteFloatMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */