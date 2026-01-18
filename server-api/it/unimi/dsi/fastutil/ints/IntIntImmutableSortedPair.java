/*    */ package it.unimi.dsi.fastutil.ints;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.SortedPair;
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
/*    */ public class IntIntImmutableSortedPair
/*    */   extends IntIntImmutablePair
/*    */   implements IntIntSortedPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   private IntIntImmutableSortedPair(int left, int right) {
/* 26 */     super(left, right);
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
/*    */ 
/*    */ 
/*    */   
/*    */   public static IntIntImmutableSortedPair of(int left, int right) {
/* 42 */     if (left <= right) return new IntIntImmutableSortedPair(left, right); 
/* 43 */     return new IntIntImmutableSortedPair(right, left);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 49 */     if (other == null) return false; 
/* 50 */     if (other instanceof IntIntSortedPair) {
/* 51 */       return (this.left == ((IntIntSortedPair)other).leftInt() && this.right == ((IntIntSortedPair)other).rightInt());
/*    */     }
/* 53 */     if (other instanceof SortedPair) {
/* 54 */       return (Objects.equals(Integer.valueOf(this.left), ((SortedPair)other).left()) && Objects.equals(Integer.valueOf(this.right), ((SortedPair)other).right()));
/*    */     }
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return "{" + leftInt() + "," + rightInt() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntIntImmutableSortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */