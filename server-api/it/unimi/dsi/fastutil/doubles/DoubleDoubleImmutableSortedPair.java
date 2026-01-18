/*    */ package it.unimi.dsi.fastutil.doubles;
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
/*    */ public class DoubleDoubleImmutableSortedPair
/*    */   extends DoubleDoubleImmutablePair
/*    */   implements DoubleDoubleSortedPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   private DoubleDoubleImmutableSortedPair(double left, double right) {
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
/*    */   public static DoubleDoubleImmutableSortedPair of(double left, double right) {
/* 42 */     if (left <= right) return new DoubleDoubleImmutableSortedPair(left, right); 
/* 43 */     return new DoubleDoubleImmutableSortedPair(right, left);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 49 */     if (other == null) return false; 
/* 50 */     if (other instanceof DoubleDoubleSortedPair) {
/* 51 */       return (this.left == ((DoubleDoubleSortedPair)other).leftDouble() && this.right == ((DoubleDoubleSortedPair)other).rightDouble());
/*    */     }
/* 53 */     if (other instanceof SortedPair) {
/* 54 */       return (Objects.equals(Double.valueOf(this.left), ((SortedPair)other).left()) && Objects.equals(Double.valueOf(this.right), ((SortedPair)other).right()));
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
/* 66 */     return "{" + leftDouble() + "," + rightDouble() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleDoubleImmutableSortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */