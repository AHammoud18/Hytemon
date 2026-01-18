/*    */ package it.unimi.dsi.fastutil.booleans;
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
/*    */ public class BooleanReferenceMutablePair<V>
/*    */   implements BooleanReferencePair<V>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected boolean left;
/*    */   protected V right;
/*    */   
/*    */   public BooleanReferenceMutablePair(boolean left, V right) {
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
/*    */   public static <V> BooleanReferenceMutablePair<V> of(boolean left, V right) {
/* 49 */     return new BooleanReferenceMutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean leftBoolean() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public BooleanReferenceMutablePair<V> left(boolean l) {
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
/*    */   public BooleanReferenceMutablePair<V> right(V r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof BooleanReferencePair) {
/* 79 */       return (this.left == ((BooleanReferencePair)other).leftBoolean() && this.right == ((BooleanReferencePair)other).right());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Boolean.valueOf(this.left), ((Pair)other).left()) && this.right == ((Pair)other).right());
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return (this.left ? 1231 : 1237) * 19 + ((this.right == null) ? 0 : System.identityHashCode(this.right));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftBoolean() + "," + right() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanReferenceMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */