/*    */ package it.unimi.dsi.fastutil.booleans;
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
/*    */ public class BooleanShortMutablePair
/*    */   implements BooleanShortPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected boolean left;
/*    */   protected short right;
/*    */   
/*    */   public BooleanShortMutablePair(boolean left, short right) {
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
/*    */   public static BooleanShortMutablePair of(boolean left, short right) {
/* 49 */     return new BooleanShortMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean leftBoolean() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public BooleanShortMutablePair left(boolean l) {
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
/*    */   public BooleanShortMutablePair right(short r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof BooleanShortPair) {
/* 79 */       return (this.left == ((BooleanShortPair)other).leftBoolean() && this.right == ((BooleanShortPair)other).rightShort());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Boolean.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Short.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return (this.left ? 1231 : 1237) * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftBoolean() + "," + rightShort() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanShortMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */