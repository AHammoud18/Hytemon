/*    */ package it.unimi.dsi.fastutil.ints;
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
/*    */ public class IntIntMutablePair
/*    */   implements IntIntPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected int left;
/*    */   protected int right;
/*    */   
/*    */   public IntIntMutablePair(int left, int right) {
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
/*    */   public static IntIntMutablePair of(int left, int right) {
/* 49 */     return new IntIntMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public int leftInt() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntIntMutablePair left(int l) {
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
/*    */   public IntIntMutablePair right(int r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof IntIntPair) {
/* 79 */       return (this.left == ((IntIntPair)other).leftInt() && this.right == ((IntIntPair)other).rightInt());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Integer.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Integer.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return this.left * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftInt() + "," + rightInt() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntIntMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */