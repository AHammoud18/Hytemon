/*    */ package it.unimi.dsi.fastutil.doubles;
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
/*    */ public class DoubleBooleanMutablePair
/*    */   implements DoubleBooleanPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected double left;
/*    */   protected boolean right;
/*    */   
/*    */   public DoubleBooleanMutablePair(double left, boolean right) {
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
/*    */   public static DoubleBooleanMutablePair of(double left, boolean right) {
/* 49 */     return new DoubleBooleanMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public double leftDouble() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleBooleanMutablePair left(double l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean rightBoolean() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleBooleanMutablePair right(boolean r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof DoubleBooleanPair) {
/* 79 */       return (this.left == ((DoubleBooleanPair)other).leftDouble() && this.right == ((DoubleBooleanPair)other).rightBoolean());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Double.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Boolean.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return HashCommon.double2int(this.left) * 19 + (this.right ? 1231 : 1237);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftDouble() + "," + rightBoolean() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleBooleanMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */