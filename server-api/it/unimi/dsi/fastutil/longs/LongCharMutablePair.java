/*    */ package it.unimi.dsi.fastutil.longs;
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
/*    */ public class LongCharMutablePair
/*    */   implements LongCharPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected long left;
/*    */   protected char right;
/*    */   
/*    */   public LongCharMutablePair(long left, char right) {
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
/*    */   public static LongCharMutablePair of(long left, char right) {
/* 49 */     return new LongCharMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public long leftLong() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public LongCharMutablePair left(long l) {
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
/*    */   public LongCharMutablePair right(char r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof LongCharPair) {
/* 79 */       return (this.left == ((LongCharPair)other).leftLong() && this.right == ((LongCharPair)other).rightChar());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Long.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Character.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return HashCommon.long2int(this.left) * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftLong() + "," + rightChar() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongCharMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */