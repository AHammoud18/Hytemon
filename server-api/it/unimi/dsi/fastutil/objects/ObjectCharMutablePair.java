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
/*    */ public class ObjectCharMutablePair<K>
/*    */   implements ObjectCharPair<K>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected K left;
/*    */   protected char right;
/*    */   
/*    */   public ObjectCharMutablePair(K left, char right) {
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
/*    */   public static <K> ObjectCharMutablePair<K> of(K left, char right) {
/* 49 */     return new ObjectCharMutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public K left() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectCharMutablePair<K> left(K l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public char rightChar() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectCharMutablePair<K> right(char r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ObjectCharPair) {
/* 79 */       return (Objects.equals(this.left, ((ObjectCharPair)other).left()) && this.right == ((ObjectCharPair)other).rightChar());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(this.left, ((Pair)other).left()) && Objects.equals(Character.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return ((this.left == null) ? 0 : this.left.hashCode()) * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + left() + "," + rightChar() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectCharMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */