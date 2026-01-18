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
/*    */ public class ShortIntMutablePair
/*    */   implements ShortIntPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected short left;
/*    */   protected int right;
/*    */   
/*    */   public ShortIntMutablePair(short left, int right) {
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
/*    */   public static ShortIntMutablePair of(short left, int right) {
/* 49 */     return new ShortIntMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public short leftShort() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShortIntMutablePair left(short l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int rightInt() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShortIntMutablePair right(int r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ShortIntPair) {
/* 79 */       return (this.left == ((ShortIntPair)other).leftShort() && this.right == ((ShortIntPair)other).rightInt());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Short.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Integer.valueOf(this.right), ((Pair)other).right()));
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
/* 99 */     return "<" + leftShort() + "," + rightInt() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortIntMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */