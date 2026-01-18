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
/*    */ public class BooleanFloatImmutablePair
/*    */   implements BooleanFloatPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected final boolean left;
/*    */   protected final float right;
/*    */   
/*    */   public BooleanFloatImmutablePair(boolean left, float right) {
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
/*    */   public static BooleanFloatImmutablePair of(boolean left, float right) {
/* 49 */     return new BooleanFloatImmutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean leftBoolean() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public float rightFloat() {
/* 59 */     return this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 65 */     if (other == null) return false; 
/* 66 */     if (other instanceof BooleanFloatPair) {
/* 67 */       return (this.left == ((BooleanFloatPair)other).leftBoolean() && this.right == ((BooleanFloatPair)other).rightFloat());
/*    */     }
/* 69 */     if (other instanceof Pair) {
/* 70 */       return (Objects.equals(Boolean.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Float.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return (this.left ? 1231 : 1237) * 19 + HashCommon.float2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "<" + leftBoolean() + "," + rightFloat() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanFloatImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */