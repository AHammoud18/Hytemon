/*    */ package org.bson.diagnostics;
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
/*    */ class NoOpLogger
/*    */   implements Logger
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   NoOpLogger(String name) {
/* 26 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 31 */     return this.name;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\diagnostics\NoOpLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */