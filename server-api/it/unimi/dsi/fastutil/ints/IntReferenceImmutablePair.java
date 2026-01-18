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
/*    */ public class IntReferenceImmutablePair<V>
/*    */   implements IntReferencePair<V>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected final int left;
/*    */   protected final V right;
/*    */   
/*    */   public IntReferenceImmutablePair(int left, V right) {
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
/*    */   public static <V> IntReferenceImmutablePair<V> of(int left, V right) {
/* 49 */     return new IntReferenceImmutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public int leftInt() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public V right() {
/* 59 */     return this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 65 */     if (other == null) return false; 
/* 66 */     if (other instanceof IntReferencePair) {
/* 67 */       return (this.left == ((IntReferencePair)other).leftInt() && this.right == ((IntReferencePair)other).right());
/*    */     }
/* 69 */     if (other instanceof Pair) {
/* 70 */       return (Objects.equals(Integer.valueOf(this.left), ((Pair)other).left()) && this.right == ((Pair)other).right());
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return this.left * 19 + ((this.right == null) ? 0 : System.identityHashCode(this.right));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "<" + leftInt() + "," + right() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntReferenceImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */