/*    */ package it.unimi.dsi.fastutil.booleans;
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
/*    */ public interface BooleanComparator
/*    */   extends Comparator<Boolean>
/*    */ {
/*    */   default BooleanComparator reversed() {
/* 44 */     return BooleanComparators.oppositeComparator(this);
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
/*    */   default int compare(Boolean ok1, Boolean ok2) {
/* 56 */     return compare(ok1.booleanValue(), ok2.booleanValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default BooleanComparator thenComparing(BooleanComparator second) {
/* 66 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   default Comparator<Boolean> thenComparing(Comparator<? super Boolean> second) {
/* 74 */     if (second instanceof BooleanComparator) return thenComparing((BooleanComparator)second); 
/* 75 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(boolean paramBoolean1, boolean paramBoolean2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */