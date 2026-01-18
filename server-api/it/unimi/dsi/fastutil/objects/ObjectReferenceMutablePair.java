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
/*    */ public class ObjectReferenceMutablePair<K, V>
/*    */   implements ObjectReferencePair<K, V>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected K left;
/*    */   protected V right;
/*    */   
/*    */   public ObjectReferenceMutablePair(K left, V right) {
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
/*    */   public static <K, V> ObjectReferenceMutablePair<K, V> of(K left, V right) {
/* 49 */     return new ObjectReferenceMutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public K left() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectReferenceMutablePair<K, V> left(K l) {
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
/*    */   public ObjectReferenceMutablePair<K, V> right(V r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof Pair) {
/* 79 */       return (Objects.equals(this.left, ((Pair)other).left()) && this.right == ((Pair)other).right());
/*    */     }
/* 81 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 86 */     return ((this.left == null) ? 0 : this.left.hashCode()) * 19 + ((this.right == null) ? 0 : System.identityHashCode(this.right));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 96 */     return "<" + left() + "," + right() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectReferenceMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */