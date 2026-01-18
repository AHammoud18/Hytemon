/*    */ package it.unimi.dsi.fastutil.objects;
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
/*    */ public class ObjectObjectImmutableSortedPair<K extends Comparable<K>>
/*    */   extends ObjectObjectImmutablePair<K, K>
/*    */   implements SortedPair<K>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   private ObjectObjectImmutableSortedPair(K left, K right) {
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
/*    */   public static <K extends Comparable<K>> ObjectObjectImmutableSortedPair<K> of(K left, K right) {
/* 42 */     if (left.compareTo(right) <= 0) return new ObjectObjectImmutableSortedPair<>(left, right); 
/* 43 */     return new ObjectObjectImmutableSortedPair<>(right, left);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 49 */     if (other == null) return false; 
/* 50 */     if (other instanceof SortedPair) {
/* 51 */       return (Objects.equals(this.left, ((SortedPair)other).left()) && Objects.equals(this.right, ((SortedPair)other).right()));
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "{" + left() + "," + right() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectObjectImmutableSortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */