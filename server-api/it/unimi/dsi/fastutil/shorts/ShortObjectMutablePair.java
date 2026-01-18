/*    */ package it.unimi.dsi.fastutil.shorts;
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
/*    */ public class ShortObjectMutablePair<V>
/*    */   implements ShortObjectPair<V>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected short left;
/*    */   protected V right;
/*    */   
/*    */   public ShortObjectMutablePair(short left, V right) {
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
/*    */   public static <V> ShortObjectMutablePair<V> of(short left, V right) {
/* 49 */     return new ShortObjectMutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public short leftShort() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShortObjectMutablePair<V> left(short l) {
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
/*    */   public ShortObjectMutablePair<V> right(V r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ShortObjectPair) {
/* 79 */       return (this.left == ((ShortObjectPair)other).leftShort() && Objects.equals(this.right, ((ShortObjectPair)other).right()));
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Short.valueOf(this.left), ((Pair)other).left()) && Objects.equals(this.right, ((Pair)other).right()));
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
/* 99 */     return "<" + leftShort() + "," + right() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortObjectMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */