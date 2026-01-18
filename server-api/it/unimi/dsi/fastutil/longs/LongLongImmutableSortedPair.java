/*    */ package it.unimi.dsi.fastutil.longs;
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
/*    */ public class LongLongImmutableSortedPair
/*    */   extends LongLongImmutablePair
/*    */   implements LongLongSortedPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   private LongLongImmutableSortedPair(long left, long right) {
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
/*    */   public static LongLongImmutableSortedPair of(long left, long right) {
/* 42 */     if (left <= right) return new LongLongImmutableSortedPair(left, right); 
/* 43 */     return new LongLongImmutableSortedPair(right, left);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 49 */     if (other == null) return false; 
/* 50 */     if (other instanceof LongLongSortedPair) {
/* 51 */       return (this.left == ((LongLongSortedPair)other).leftLong() && this.right == ((LongLongSortedPair)other).rightLong());
/*    */     }
/* 53 */     if (other instanceof SortedPair) {
/* 54 */       return (Objects.equals(Long.valueOf(this.left), ((SortedPair)other).left()) && Objects.equals(Long.valueOf(this.right), ((SortedPair)other).right()));
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
/* 66 */     return "{" + leftLong() + "," + rightLong() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongLongImmutableSortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */