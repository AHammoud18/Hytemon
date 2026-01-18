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
/*    */ public class DoubleIntMutablePair
/*    */   implements DoubleIntPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected double left;
/*    */   protected int right;
/*    */   
/*    */   public DoubleIntMutablePair(double left, int right) {
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
/*    */   public static DoubleIntMutablePair of(double left, int right) {
/* 49 */     return new DoubleIntMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public double leftDouble() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleIntMutablePair left(double l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int rightInt() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleIntMutablePair right(int r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof DoubleIntPair) {
/* 79 */       return (this.left == ((DoubleIntPair)other).leftDouble() && this.right == ((DoubleIntPair)other).rightInt());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Double.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Integer.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return HashCommon.double2int(this.left) * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftDouble() + "," + rightInt() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleIntMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */