/*    */ package it.unimi.dsi.fastutil.doubles;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.HashCommon;
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
/*    */ public class DoubleReferenceImmutablePair<V>
/*    */   implements DoubleReferencePair<V>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected final double left;
/*    */   protected final V right;
/*    */   
/*    */   public DoubleReferenceImmutablePair(double left, V right) {
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
/*    */   public static <V> DoubleReferenceImmutablePair<V> of(double left, V right) {
/* 49 */     return new DoubleReferenceImmutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public double leftDouble() {
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
/* 66 */     if (other instanceof DoubleReferencePair) {
/* 67 */       return (this.left == ((DoubleReferencePair)other).leftDouble() && this.right == ((DoubleReferencePair)other).right());
/*    */     }
/* 69 */     if (other instanceof Pair) {
/* 70 */       return (Objects.equals(Double.valueOf(this.left), ((Pair)other).left()) && this.right == ((Pair)other).right());
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return HashCommon.double2int(this.left) * 19 + ((this.right == null) ? 0 : System.identityHashCode(this.right));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "<" + leftDouble() + "," + right() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleReferenceImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */