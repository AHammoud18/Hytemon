/*    */ package it.unimi.dsi.fastutil.floats;
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
/*    */ public class FloatFloatMutablePair
/*    */   implements FloatFloatPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected float left;
/*    */   protected float right;
/*    */   
/*    */   public FloatFloatMutablePair(float left, float right) {
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
/*    */   public static FloatFloatMutablePair of(float left, float right) {
/* 49 */     return new FloatFloatMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public float leftFloat() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public FloatFloatMutablePair left(float l) {
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
/*    */   public FloatFloatMutablePair right(float r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof FloatFloatPair) {
/* 79 */       return (this.left == ((FloatFloatPair)other).leftFloat() && this.right == ((FloatFloatPair)other).rightFloat());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Float.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Float.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return HashCommon.float2int(this.left) * 19 + HashCommon.float2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftFloat() + "," + rightFloat() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatFloatMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */