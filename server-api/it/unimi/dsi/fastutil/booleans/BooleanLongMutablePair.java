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
/*    */ public class BooleanLongMutablePair
/*    */   implements BooleanLongPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected boolean left;
/*    */   protected long right;
/*    */   
/*    */   public BooleanLongMutablePair(boolean left, long right) {
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
/*    */   public static BooleanLongMutablePair of(boolean left, long right) {
/* 49 */     return new BooleanLongMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean leftBoolean() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public BooleanLongMutablePair left(boolean l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public long rightLong() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public BooleanLongMutablePair right(long r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof BooleanLongPair) {
/* 79 */       return (this.left == ((BooleanLongPair)other).leftBoolean() && this.right == ((BooleanLongPair)other).rightLong());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Boolean.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Long.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return (this.left ? 1231 : 1237) * 19 + HashCommon.long2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftBoolean() + "," + rightLong() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanLongMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */