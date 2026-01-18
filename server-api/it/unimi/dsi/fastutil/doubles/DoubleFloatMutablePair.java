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
/*    */ public class DoubleFloatMutablePair
/*    */   implements DoubleFloatPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected double left;
/*    */   protected float right;
/*    */   
/*    */   public DoubleFloatMutablePair(double left, float right) {
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
/*    */   public static DoubleFloatMutablePair of(double left, float right) {
/* 49 */     return new DoubleFloatMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public double leftDouble() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleFloatMutablePair left(double l) {
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
/*    */   public DoubleFloatMutablePair right(float r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof DoubleFloatPair) {
/* 79 */       return (this.left == ((DoubleFloatPair)other).leftDouble() && this.right == ((DoubleFloatPair)other).rightFloat());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Double.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Float.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return HashCommon.double2int(this.left) * 19 + HashCommon.float2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftDouble() + "," + rightFloat() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleFloatMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */