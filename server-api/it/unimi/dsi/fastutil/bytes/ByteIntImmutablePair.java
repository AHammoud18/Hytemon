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
/*    */ public class ByteIntImmutablePair
/*    */   implements ByteIntPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected final byte left;
/*    */   protected final int right;
/*    */   
/*    */   public ByteIntImmutablePair(byte left, int right) {
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
/*    */   public static ByteIntImmutablePair of(byte left, int right) {
/* 49 */     return new ByteIntImmutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte leftByte() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public int rightInt() {
/* 59 */     return this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 65 */     if (other == null) return false; 
/* 66 */     if (other instanceof ByteIntPair) {
/* 67 */       return (this.left == ((ByteIntPair)other).leftByte() && this.right == ((ByteIntPair)other).rightInt());
/*    */     }
/* 69 */     if (other instanceof Pair) {
/* 70 */       return (Objects.equals(Byte.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Integer.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return this.left * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "<" + leftByte() + "," + rightInt() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteIntImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */