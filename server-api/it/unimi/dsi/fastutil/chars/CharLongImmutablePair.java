/*    */ package it.unimi.dsi.fastutil.chars;
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
/*    */ public class CharLongImmutablePair
/*    */   implements CharLongPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected final char left;
/*    */   protected final long right;
/*    */   
/*    */   public CharLongImmutablePair(char left, long right) {
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
/*    */   public static CharLongImmutablePair of(char left, long right) {
/* 49 */     return new CharLongImmutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public char leftChar() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public long rightLong() {
/* 59 */     return this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 65 */     if (other == null) return false; 
/* 66 */     if (other instanceof CharLongPair) {
/* 67 */       return (this.left == ((CharLongPair)other).leftChar() && this.right == ((CharLongPair)other).rightLong());
/*    */     }
/* 69 */     if (other instanceof Pair) {
/* 70 */       return (Objects.equals(Character.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Long.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return this.left * 19 + HashCommon.long2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "<" + leftChar() + "," + rightLong() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharLongImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */