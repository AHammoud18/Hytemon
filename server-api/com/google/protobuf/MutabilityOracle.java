/*    */ package com.google.protobuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ interface MutabilityOracle
/*    */ {
/* 12 */   public static final MutabilityOracle IMMUTABLE = new MutabilityOracle()
/*    */     {
/*    */       public void ensureMutable()
/*    */       {
/* 16 */         throw new UnsupportedOperationException();
/*    */       }
/*    */     };
/*    */   
/*    */   void ensureMutable();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MutabilityOracle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */