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
/*    */ public class ShortBooleanMutablePair
/*    */   implements ShortBooleanPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected short left;
/*    */   protected boolean right;
/*    */   
/*    */   public ShortBooleanMutablePair(short left, boolean right) {
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
/*    */   public static ShortBooleanMutablePair of(short left, boolean right) {
/* 49 */     return new ShortBooleanMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public short leftShort() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShortBooleanMutablePair left(short l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean rightBoolean() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShortBooleanMutablePair right(boolean r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof ShortBooleanPair) {
/* 79 */       return (this.left == ((ShortBooleanPair)other).leftShort() && this.right == ((ShortBooleanPair)other).rightBoolean());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Short.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Boolean.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return this.left * 19 + (this.right ? 1231 : 1237);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftShort() + "," + rightBoolean() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortBooleanMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */