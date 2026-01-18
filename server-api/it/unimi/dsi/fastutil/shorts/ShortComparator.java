/*    */ package it.unimi.dsi.fastutil.shorts;
/*    */ 
/*    */ import java.lang.invoke.SerializedLambda;
/*    */ import java.util.Comparator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ShortComparator
/*    */   extends Comparator<Short>
/*    */ {
/*    */   default ShortComparator reversed() {
/* 44 */     return ShortComparators.oppositeComparator(this);
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
/*    */   default int compare(Short ok1, Short ok2) {
/* 56 */     return compare(ok1.shortValue(), ok2.shortValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default ShortComparator thenComparing(ShortComparator second) {
/* 66 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   default Comparator<Short> thenComparing(Comparator<? super Short> second) {
/* 74 */     if (second instanceof ShortComparator) return thenComparing((ShortComparator)second); 
/* 75 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(short paramShort1, short paramShort2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */