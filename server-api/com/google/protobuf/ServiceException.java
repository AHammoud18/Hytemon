/*    */ package com.google.protobuf;
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
/*    */ public class ServiceException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -1219262335729891920L;
/*    */   
/*    */   public ServiceException(String message) {
/* 19 */     super(message);
/*    */   }
/*    */   
/*    */   public ServiceException(Throwable cause) {
/* 23 */     super(cause);
/*    */   }
/*    */   
/*    */   public ServiceException(String message, Throwable cause) {
/* 27 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ServiceException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */