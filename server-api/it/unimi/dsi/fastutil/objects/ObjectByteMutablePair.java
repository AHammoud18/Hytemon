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
/*    */ public class ObjectByteMutablePair<K>
/*    */   implements ObjectBytePair<K>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected K left;
/*    */   protected byte right;
/*    */   
/*    */   public ObjectByteMutablePair(K left, byte right) {
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
/*    */   public static <K> ObjectByteMutablePair<K> of(K left, byte right) {
/* 49 */     return new ObjectByteMutablePair<>(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public K left() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectByteMutablePair<K> left(K l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte rightByte() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectByteMutablePair<K> right(byte r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ObjectBytePair) {
/* 79 */       return (Objects.equals(this.left, ((ObjectBytePair)other).left()) && this.right == ((ObjectBytePair)other).rightByte());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(this.left, ((Pair)other).left()) && Objects.equals(Byte.valueOf(this.right), ((Pair)other).right()));
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
/* 99 */     return "<" + left() + "," + rightByte() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectByteMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */