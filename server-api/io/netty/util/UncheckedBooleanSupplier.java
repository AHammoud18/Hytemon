/*    */ package io.netty.util;
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
/*    */ public interface UncheckedBooleanSupplier
/*    */   extends BooleanSupplier
/*    */ {
/* 32 */   public static final UncheckedBooleanSupplier FALSE_SUPPLIER = new UncheckedBooleanSupplier()
/*    */     {
/*    */       public boolean get() {
/* 35 */         return false;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public static final UncheckedBooleanSupplier TRUE_SUPPLIER = new UncheckedBooleanSupplier()
/*    */     {
/*    */       public boolean get() {
/* 45 */         return true;
/*    */       }
/*    */     };
/*    */   
/*    */   boolean get();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\UncheckedBooleanSupplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */