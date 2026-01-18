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
/*    */ 
/*    */ public interface BooleanSupplier
/*    */ {
/* 32 */   public static final BooleanSupplier FALSE_SUPPLIER = new BooleanSupplier()
/*    */     {
/*    */       public boolean get() {
/* 35 */         return false;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public static final BooleanSupplier TRUE_SUPPLIER = new BooleanSupplier()
/*    */     {
/*    */       public boolean get() {
/* 45 */         return true;
/*    */       }
/*    */     };
/*    */   
/*    */   boolean get() throws Exception;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\BooleanSupplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */