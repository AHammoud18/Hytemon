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
/*    */ public class IntObjectMutablePair<V>
/*    */   implements IntObjectPair<V>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected int left;
/*    */   protected V right;
/*    */   
/*    */   public IntObjectMutablePair(int left, V right) {
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
/*    */   public static <V> IntObjectMutablePair<V> of(int left, V right) {
/* 49 */     return new IntObjectMutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public int leftInt() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntObjectMutablePair<V> left(int l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public V right() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntObjectMutablePair<V> right(V r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof IntObjectPair) {
/* 79 */       return (this.left == ((IntObjectPair)other).leftInt() && Objects.equals(this.right, ((IntObjectPair)other).right()));
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Integer.valueOf(this.left), ((Pair)other).left()) && Objects.equals(this.right, ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return this.left * 19 + ((this.right == null) ? 0 : this.right.hashCode());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftInt() + "," + right() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntObjectMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */