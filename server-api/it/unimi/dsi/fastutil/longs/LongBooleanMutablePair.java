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
/*    */ public class LongBooleanMutablePair
/*    */   implements LongBooleanPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected long left;
/*    */   protected boolean right;
/*    */   
/*    */   public LongBooleanMutablePair(long left, boolean right) {
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
/*    */   public static LongBooleanMutablePair of(long left, boolean right) {
/* 49 */     return new LongBooleanMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public long leftLong() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public LongBooleanMutablePair left(long l) {
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
/*    */   public LongBooleanMutablePair right(boolean r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof LongBooleanPair) {
/* 79 */       return (this.left == ((LongBooleanPair)other).leftLong() && this.right == ((LongBooleanPair)other).rightBoolean());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Long.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Boolean.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return HashCommon.long2int(this.left) * 19 + (this.right ? 1231 : 1237);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftLong() + "," + rightBoolean() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongBooleanMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */