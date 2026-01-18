/*    */ package it.unimi.dsi.fastutil.objects;
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
/*    */ public class ReferenceShortMutablePair<K>
/*    */   implements ReferenceShortPair<K>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected K left;
/*    */   protected short right;
/*    */   
/*    */   public ReferenceShortMutablePair(K left, short right) {
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
/*    */   public static <K> ReferenceShortMutablePair<K> of(K left, short right) {
/* 49 */     return new ReferenceShortMutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public K left() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReferenceShortMutablePair<K> left(K l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public short rightShort() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReferenceShortMutablePair<K> right(short r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ReferenceShortPair) {
/* 79 */       return (this.left == ((ReferenceShortPair)other).left() && this.right == ((ReferenceShortPair)other).rightShort());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (this.left == ((Pair)other).left() && Objects.equals(Short.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return System.identityHashCode(this.left) * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + left() + "," + rightShort() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceShortMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */