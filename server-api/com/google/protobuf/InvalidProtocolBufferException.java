/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvalidProtocolBufferException
/*     */   extends IOException
/*     */ {
/*     */   private static final long serialVersionUID = -1616151763072450476L;
/*  20 */   private MessageLite unfinishedMessage = null;
/*     */   private boolean wasThrownFromInputStream;
/*     */   
/*     */   public InvalidProtocolBufferException(String description) {
/*  24 */     super(description);
/*     */   }
/*     */   
/*     */   public InvalidProtocolBufferException(Exception e) {
/*  28 */     super(e.getMessage(), e);
/*     */   }
/*     */   
/*     */   public InvalidProtocolBufferException(String description, Exception e) {
/*  32 */     super(description, e);
/*     */   }
/*     */   
/*     */   public InvalidProtocolBufferException(IOException e) {
/*  36 */     super(e.getMessage(), e);
/*     */   }
/*     */   
/*     */   public InvalidProtocolBufferException(String description, IOException e) {
/*  40 */     super(description, e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidProtocolBufferException setUnfinishedMessage(MessageLite unfinishedMessage) {
/*  50 */     this.unfinishedMessage = unfinishedMessage;
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageLite getUnfinishedMessage() {
/*  58 */     return this.unfinishedMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setThrownFromInputStream() {
/*  72 */     this.wasThrownFromInputStream = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean getThrownFromInputStream() {
/*  80 */     return this.wasThrownFromInputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IOException unwrapIOException() {
/*  88 */     return (getCause() instanceof IOException) ? (IOException)getCause() : this;
/*     */   }
/*     */   
/*     */   static InvalidProtocolBufferException truncatedMessage() {
/*  92 */     return new InvalidProtocolBufferException("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static InvalidProtocolBufferException negativeSize() {
/* 100 */     return new InvalidProtocolBufferException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static InvalidProtocolBufferException malformedVarint() {
/* 106 */     return new InvalidProtocolBufferException("CodedInputStream encountered a malformed varint.");
/*     */   }
/*     */   
/*     */   static InvalidProtocolBufferException invalidTag() {
/* 110 */     return new InvalidProtocolBufferException("Protocol message contained an invalid tag (zero).");
/*     */   }
/*     */   
/*     */   static InvalidProtocolBufferException invalidEndTag() {
/* 114 */     return new InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
/*     */   }
/*     */ 
/*     */   
/*     */   static InvalidWireTypeException invalidWireType() {
/* 119 */     return new InvalidWireTypeException("Protocol message tag had invalid wire type.");
/*     */   }
/*     */   
/*     */   public static class InvalidWireTypeException
/*     */     extends InvalidProtocolBufferException
/*     */   {
/*     */     private static final long serialVersionUID = 3283890091615336259L;
/*     */     
/*     */     public InvalidWireTypeException(String description) {
/* 128 */       super(description);
/*     */     }
/*     */   }
/*     */   
/*     */   static InvalidProtocolBufferException recursionLimitExceeded() {
/* 133 */     return new InvalidProtocolBufferException("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static InvalidProtocolBufferException sizeLimitExceeded() {
/* 139 */     return new InvalidProtocolBufferException("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static InvalidProtocolBufferException parseFailure() {
/* 146 */     return new InvalidProtocolBufferException("Failed to parse the message.");
/*     */   }
/*     */   
/*     */   static InvalidProtocolBufferException invalidUtf8() {
/* 150 */     return new InvalidProtocolBufferException("Protocol message had invalid UTF-8.");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\InvalidProtocolBufferException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */