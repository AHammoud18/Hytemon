/*    */ package it.unimi.dsi.fastutil.shorts;
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
/*    */ public class ShortCharMutablePair
/*    */   implements ShortCharPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected short left;
/*    */   protected char right;
/*    */   
/*    */   public ShortCharMutablePair(short left, char right) {
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
/*    */   public static ShortCharMutablePair of(short left, char right) {
/* 49 */     return new ShortCharMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public short leftShort() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShortCharMutablePair left(short l) {
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
/*    */   public ShortCharMutablePair right(char r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ShortCharPair) {
/* 79 */       return (this.left == ((ShortCharPair)other).leftShort() && this.right == ((ShortCharPair)other).rightChar());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Short.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Character.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return this.left * 19 + this.right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftShort() + "," + rightChar() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortCharMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */