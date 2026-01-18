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
/*    */ class GeneratedMessageInfoFactory
/*    */   implements MessageInfoFactory
/*    */ {
/* 14 */   private static final GeneratedMessageInfoFactory instance = new GeneratedMessageInfoFactory();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static GeneratedMessageInfoFactory getInstance() {
/* 20 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSupported(Class<?> messageType) {
/* 25 */     return GeneratedMessageLite.class.isAssignableFrom(messageType);
/*    */   }
/*    */ 
/*    */   
/*    */   public MessageInfo messageInfoFor(Class<?> messageType) {
/* 30 */     if (!GeneratedMessageLite.class.isAssignableFrom(messageType)) {
/* 31 */       throw new IllegalArgumentException("Unsupported message type: " + messageType.getName());
/*    */     }
/*    */     
/*    */     try {
/* 35 */       return (MessageInfo)GeneratedMessageLite.<GeneratedMessageLite<?, ?>>getDefaultInstance((Class)messageType
/* 36 */           .asSubclass((Class)GeneratedMessageLite.class))
/* 37 */         .buildMessageInfo();
/* 38 */     } catch (Exception e) {
/* 39 */       throw new RuntimeException("Unable to get message info for " + messageType.getName(), e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\GeneratedMessageInfoFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */