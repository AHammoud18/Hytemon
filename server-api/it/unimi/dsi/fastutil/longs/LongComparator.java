/*    */ package it.unimi.dsi.fastutil.longs;
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
/*    */ public interface LongComparator
/*    */   extends Comparator<Long>
/*    */ {
/*    */   default LongComparator reversed() {
/* 44 */     return LongComparators.oppositeComparator(this);
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
/*    */   default int compare(Long ok1, Long ok2) {
/* 56 */     return compare(ok1.longValue(), ok2.longValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default LongComparator thenComparing(LongComparator second) {
/* 66 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   default Comparator<Long> thenComparing(Comparator<? super Long> second) {
/* 74 */     if (second instanceof LongComparator) return thenComparing((LongComparator)second); 
/* 75 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(long paramLong1, long paramLong2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */