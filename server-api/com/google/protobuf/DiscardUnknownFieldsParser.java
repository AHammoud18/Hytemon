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
/*    */ public final class DiscardUnknownFieldsParser
/*    */ {
/*    */   public static final <T extends Message> Parser<T> wrap(final Parser<T> parser) {
/* 32 */     return new AbstractParser<T>()
/*    */       {
/*    */         public T parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*    */         {
/*    */           try {
/* 37 */             input.discardUnknownFields();
/* 38 */             return (T)parser.parsePartialFrom(input, extensionRegistry);
/*    */           } finally {
/* 40 */             input.unsetDiscardUnknownFields();
/*    */           } 
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\DiscardUnknownFieldsParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */