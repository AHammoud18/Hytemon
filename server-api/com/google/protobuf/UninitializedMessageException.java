/*    */ package com.google.protobuf;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public class UninitializedMessageException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -7466929953374883507L;
/*    */   private final List<String> missingFields;
/*    */   
/*    */   public UninitializedMessageException(MessageLite message) {
/* 28 */     super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
/*    */ 
/*    */     
/* 31 */     this.missingFields = null;
/*    */   }
/*    */   
/*    */   public UninitializedMessageException(List<String> missingFields) {
/* 35 */     super(buildDescription(missingFields));
/* 36 */     this.missingFields = missingFields;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getMissingFields() {
/* 47 */     return Collections.unmodifiableList(this.missingFields);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidProtocolBufferException asInvalidProtocolBufferException() {
/* 56 */     return new InvalidProtocolBufferException(getMessage());
/*    */   }
/*    */ 
/*    */   
/*    */   private static String buildDescription(List<String> missingFields) {
/* 61 */     StringBuilder description = new StringBuilder("Message missing required fields: ");
/* 62 */     boolean first = true;
/* 63 */     for (String field : missingFields) {
/* 64 */       if (first) {
/* 65 */         first = false;
/*    */       } else {
/* 67 */         description.append(", ");
/*    */       } 
/* 69 */       description.append(field);
/*    */     } 
/* 71 */     return description.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UninitializedMessageException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */