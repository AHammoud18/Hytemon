/*    */ package it.unimi.dsi.fastutil.booleans;
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
/*    */ public class BooleanLongImmutablePair
/*    */   implements BooleanLongPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected final boolean left;
/*    */   protected final long right;
/*    */   
/*    */   public BooleanLongImmutablePair(boolean left, long right) {
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
/*    */   public static BooleanLongImmutablePair of(boolean left, long right) {
/* 49 */     return new BooleanLongImmutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean leftBoolean() {
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
/* 66 */     if (other instanceof BooleanLongPair) {
/* 67 */       return (this.left == ((BooleanLongPair)other).leftBoolean() && this.right == ((BooleanLongPair)other).rightLong());
/*    */     }
/* 69 */     if (other instanceof Pair) {
/* 70 */       return (Objects.equals(Boolean.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Long.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return (this.left ? 1231 : 1237) * 19 + HashCommon.long2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "<" + leftBoolean() + "," + rightLong() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanLongImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */