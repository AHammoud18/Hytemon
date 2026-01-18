/*    */ package it.unimi.dsi.fastutil.bytes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.SortedPair;
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
/*    */ public class ByteByteImmutableSortedPair
/*    */   extends ByteByteImmutablePair
/*    */   implements ByteByteSortedPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   private ByteByteImmutableSortedPair(byte left, byte right) {
/* 26 */     super(left, right);
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
/*    */ 
/*    */ 
/*    */   
/*    */   public static ByteByteImmutableSortedPair of(byte left, byte right) {
/* 42 */     if (left <= right) return new ByteByteImmutableSortedPair(left, right); 
/* 43 */     return new ByteByteImmutableSortedPair(right, left);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 49 */     if (other == null) return false; 
/* 50 */     if (other instanceof ByteByteSortedPair) {
/* 51 */       return (this.left == ((ByteByteSortedPair)other).leftByte() && this.right == ((ByteByteSortedPair)other).rightByte());
/*    */     }
/* 53 */     if (other instanceof SortedPair) {
/* 54 */       return (Objects.equals(Byte.valueOf(this.left), ((SortedPair)other).left()) && Objects.equals(Byte.valueOf(this.right), ((SortedPair)other).right()));
/*    */     }
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return "{" + leftByte() + "," + rightByte() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteByteImmutableSortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */