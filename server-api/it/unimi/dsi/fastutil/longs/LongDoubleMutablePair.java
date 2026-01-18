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
/*    */ public class LongDoubleMutablePair
/*    */   implements LongDoublePair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   protected long left;
/*    */   protected double right;
/*    */   
/*    */   public LongDoubleMutablePair(long left, double right) {
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
/*    */   public static LongDoubleMutablePair of(long left, double right) {
/* 49 */     return new LongDoubleMutablePair(left, right);
/*    */   }
/*    */ 
/*    */   
/*    */   public long leftLong() {
/* 54 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public LongDoubleMutablePair left(long l) {
/* 59 */     this.left = l;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public double rightDouble() {
/* 65 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public LongDoubleMutablePair right(double r) {
/* 70 */     this.right = r;
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 77 */     if (other == null) return false; 
/* 78 */     if (other instanceof LongDoublePair) {
/* 79 */       return (this.left == ((LongDoublePair)other).leftLong() && this.right == ((LongDoublePair)other).rightDouble());
/*    */     }
/* 81 */     if (other instanceof Pair) {
/* 82 */       return (Objects.equals(Long.valueOf(this.left), ((Pair)other).left()) && Objects.equals(Double.valueOf(this.right), ((Pair)other).right()));
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return HashCommon.long2int(this.left) * 19 + HashCommon.double2int(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "<" + leftLong() + "," + rightDouble() + ">";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongDoubleMutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */