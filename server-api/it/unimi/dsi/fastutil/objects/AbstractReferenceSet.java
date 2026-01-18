/*    */ package it.unimi.dsi.fastutil.objects;
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
/*    */ public abstract class AbstractReferenceSet<K>
/*    */   extends AbstractReferenceCollection<K>
/*    */   implements Cloneable, ReferenceSet<K>
/*    */ {
/*    */   public boolean equals(Object o) {
/* 38 */     if (o == this) return true; 
/* 39 */     if (!(o instanceof Set)) return false; 
/* 40 */     Set<?> s = (Set)o;
/* 41 */     if (s.size() != size()) return false; 
/* 42 */     return containsAll(s);
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
/* 54 */     int h = 0, n = size();
/* 55 */     ObjectIterator<K> i = iterator();
/*    */     
/* 57 */     while (n-- != 0) {
/* 58 */       K k = i.next();
/* 59 */       h += System.identityHashCode(k);
/*    */     } 
/* 61 */     return h;
/*    */   }
/*    */   
/*    */   public abstract ObjectIterator<K> iterator();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReferenceSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */