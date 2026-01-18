/*    */ package it.unimi.dsi.fastutil.chars;
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
/*    */ public class CharCharImmutableSortedPair
/*    */   extends CharCharImmutablePair
/*    */   implements CharCharSortedPair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   private CharCharImmutableSortedPair(char left, char right) {
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
/*    */   public static CharCharImmutableSortedPair of(char left, char right) {
/* 42 */     if (left <= right) return new CharCharImmutableSortedPair(left, right); 
/* 43 */     return new CharCharImmutableSortedPair(right, left);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 49 */     if (other == null) return false; 
/* 50 */     if (other instanceof CharCharSortedPair) {
/* 51 */       return (this.left == ((CharCharSortedPair)other).leftChar() && this.right == ((CharCharSortedPair)other).rightChar());
/*    */     }
/* 53 */     if (other instanceof SortedPair) {
/* 54 */       return (Objects.equals(Character.valueOf(this.left), ((SortedPair)other).left()) && Objects.equals(Character.valueOf(this.right), ((SortedPair)other).right()));
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
/* 66 */     return "{" + leftChar() + "," + rightChar() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharCharImmutableSortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */