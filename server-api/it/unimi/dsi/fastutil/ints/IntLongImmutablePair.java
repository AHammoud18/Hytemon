/*    */ package it.unimi.dsi.fastutil.ints;
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
/*    */ public class IntLongImmutablePair
/*    */   implements IntLongPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected final int left;
/*    */   protected final long right;
/*    */   
/*    */   public IntLongImmutablePair(int left, long right) {
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
/*    */   public static IntLongImmutablePair of(int left, long right) {
/* 49 */     return new IntLongImmutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public int leftInt() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public long rightLong() {
/* 59 */     return this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 65 */     if (other == null) return false; 
/* 66 */     if (other instanceof IntLongPair) {
/* 67 */       return (this.left == ((IntLongPair)other).leftInt() && this.right == ((IntLongPair)other).rightLong());
/*    */     }
/* 69 */     if (other instanceof Pair) {
/* 70 */       return (Objects.equals(Integer.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Long.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return this.left * 19 + HashCommon.long2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "<" + leftInt() + "," + rightLong() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntLongImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */