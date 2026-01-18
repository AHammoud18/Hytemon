/*    */ package it.unimi.dsi.fastutil.ints;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractIntSet
/*    */   extends AbstractIntCollection
/*    */   implements Cloneable, IntSet
/*    */ {
/*    */   public boolean equals(Object o) {
/* 38 */     if (o == this) return true; 
/* 39 */     if (!(o instanceof Set)) return false; 
/* 40 */     Set<?> s = (Set)o;
/* 41 */     if (s.size() != size()) return false; 
/* 42 */     if (s instanceof IntSet) {
/* 43 */       return containsAll((IntSet)s);
/*    */     }
/* 45 */     return containsAll(s);
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
/*    */   public int hashCode() {
/* 57 */     int h = 0, n = size();
/* 58 */     IntIterator i = iterator();
/*    */     
/* 60 */     while (n-- != 0) {
/* 61 */       int k = i.nextInt();
/* 62 */       h += k;
/*    */     } 
/* 64 */     return h;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean remove(int k) {
/* 73 */     return super.rem(k);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public boolean rem(int k) {
/* 85 */     return remove(k);
/*    */   }
/*    */   
/*    */   public abstract IntIterator iterator();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractIntSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */