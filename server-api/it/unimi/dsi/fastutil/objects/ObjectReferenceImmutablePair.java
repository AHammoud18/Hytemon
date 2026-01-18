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
/*    */ public class ObjectReferenceImmutablePair<K, V>
/*    */   implements ObjectReferencePair<K, V>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected final K left;
/*    */   protected final V right;
/*    */   
/*    */   public ObjectReferenceImmutablePair(K left, V right) {
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
/*    */   public static <K, V> ObjectReferenceImmutablePair<K, V> of(K left, V right) {
/* 49 */     return new ObjectReferenceImmutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public K left() {
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
/* 66 */     if (other instanceof Pair) {
/* 67 */       return (Objects.equals(this.left, ((Pair)other).left()) && this.right == ((Pair)other).right());
/*    */     }
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 74 */     return ((this.left == null) ? 0 : this.left.hashCode()) * 19 + ((this.right == null) ? 0 : System.identityHashCode(this.right));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return "<" + left() + "," + right() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectReferenceImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */