/*    */ package it.unimi.dsi.fastutil.booleans;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
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
/*    */ 
/*    */ public interface BooleanBidirectionalIterator
/*    */   extends BooleanIterator, ObjectBidirectionalIterator<Boolean>
/*    */ {
/*    */   @Deprecated
/*    */   default Boolean previous() {
/* 44 */     return Boolean.valueOf(previousBoolean());
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
/*    */   default int back(int n) {
/* 60 */     int i = n;
/* 61 */     for (; i-- != 0 && hasPrevious(); previousBoolean());
/* 62 */     return n - i - 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   default int skip(int n) {
/* 68 */     return super.skip(n);
/*    */   }
/*    */   
/*    */   boolean previousBoolean();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanBidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */