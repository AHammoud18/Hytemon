/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractParser<MessageType extends MessageLite>
/*     */   implements Parser<MessageType>
/*     */ {
/*     */   private UninitializedMessageException newUninitializedMessageException(MessageType message) {
/*  29 */     if (message instanceof AbstractMessageLite) {
/*  30 */       return ((AbstractMessageLite)message).newUninitializedMessageException();
/*     */     }
/*  32 */     return new UninitializedMessageException((MessageLite)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MessageType checkMessageInitialized(MessageType message) throws InvalidProtocolBufferException {
/*  43 */     if (message != null && !message.isInitialized()) {
/*  44 */       throw newUninitializedMessageException(message)
/*  45 */         .asInvalidProtocolBufferException()
/*  46 */         .setUnfinishedMessage(message);
/*     */     }
/*  48 */     return message;
/*     */   }
/*     */ 
/*     */   
/*  52 */   private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(CodedInputStream input) throws InvalidProtocolBufferException {
/*  57 */     return parsePartialFrom(input, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  63 */     return checkMessageInitialized(parsePartialFrom(input, extensionRegistry));
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(CodedInputStream input) throws InvalidProtocolBufferException {
/*  68 */     return parseFrom(input, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*     */     try {
/*  76 */       CodedInputStream input = data.newCodedInput();
/*  77 */       MessageLite messageLite = (MessageLite)parsePartialFrom(input, extensionRegistry);
/*     */       try {
/*  79 */         input.checkLastTagWas(0);
/*  80 */       } catch (InvalidProtocolBufferException e) {
/*  81 */         throw e.setUnfinishedMessage(messageLite);
/*     */       } 
/*  83 */       return (MessageType)messageLite;
/*  84 */     } catch (InvalidProtocolBufferException e) {
/*  85 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(ByteString data) throws InvalidProtocolBufferException {
/*  91 */     return parsePartialFrom(data, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  97 */     return checkMessageInitialized(parsePartialFrom(data, extensionRegistry));
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 102 */     return parseFrom(data, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*     */     MessageLite messageLite;
/*     */     try {
/* 110 */       CodedInputStream input = CodedInputStream.newInstance(data);
/* 111 */       messageLite = (MessageLite)parsePartialFrom(input, extensionRegistry);
/*     */       try {
/* 113 */         input.checkLastTagWas(0);
/* 114 */       } catch (InvalidProtocolBufferException e) {
/* 115 */         throw e.setUnfinishedMessage(messageLite);
/*     */       } 
/* 117 */     } catch (InvalidProtocolBufferException e) {
/* 118 */       throw e;
/*     */     } 
/*     */     
/* 121 */     return checkMessageInitialized((MessageType)messageLite);
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 126 */     return parseFrom(data, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(byte[] data, int off, int len, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*     */     try {
/* 134 */       CodedInputStream input = CodedInputStream.newInstance(data, off, len);
/* 135 */       MessageLite messageLite = (MessageLite)parsePartialFrom(input, extensionRegistry);
/*     */       try {
/* 137 */         input.checkLastTagWas(0);
/* 138 */       } catch (InvalidProtocolBufferException e) {
/* 139 */         throw e.setUnfinishedMessage(messageLite);
/*     */       } 
/* 141 */       return (MessageType)messageLite;
/* 142 */     } catch (InvalidProtocolBufferException e) {
/* 143 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(byte[] data, int off, int len) throws InvalidProtocolBufferException {
/* 150 */     return parsePartialFrom(data, off, len, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 156 */     return parsePartialFrom(data, 0, data.length, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(byte[] data) throws InvalidProtocolBufferException {
/* 161 */     return parsePartialFrom(data, 0, data.length, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(byte[] data, int off, int len, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 168 */     return checkMessageInitialized(parsePartialFrom(data, off, len, extensionRegistry));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(byte[] data, int off, int len) throws InvalidProtocolBufferException {
/* 174 */     return parseFrom(data, off, len, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 180 */     return parseFrom(data, 0, data.length, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 185 */     return parseFrom(data, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 191 */     CodedInputStream codedInput = CodedInputStream.newInstance(input);
/* 192 */     MessageLite messageLite = (MessageLite)parsePartialFrom(codedInput, extensionRegistry);
/*     */     try {
/* 194 */       codedInput.checkLastTagWas(0);
/* 195 */     } catch (InvalidProtocolBufferException e) {
/* 196 */       throw e.setUnfinishedMessage(messageLite);
/*     */     } 
/* 198 */     return (MessageType)messageLite;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parsePartialFrom(InputStream input) throws InvalidProtocolBufferException {
/* 203 */     return parsePartialFrom(input, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 209 */     return checkMessageInitialized(parsePartialFrom(input, extensionRegistry));
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parseFrom(InputStream input) throws InvalidProtocolBufferException {
/* 214 */     return parseFrom(input, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parsePartialDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*     */     int size;
/*     */     try {
/* 223 */       int firstByte = input.read();
/* 224 */       if (firstByte == -1) {
/* 225 */         return null;
/*     */       }
/* 227 */       size = CodedInputStream.readRawVarint32(firstByte, input);
/* 228 */     } catch (IOException e) {
/* 229 */       throw new InvalidProtocolBufferException(e);
/*     */     } 
/* 231 */     InputStream limitedInput = new AbstractMessageLite.Builder.LimitedInputStream(input, size);
/* 232 */     return parsePartialFrom(limitedInput, extensionRegistry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parsePartialDelimitedFrom(InputStream input) throws InvalidProtocolBufferException {
/* 238 */     return parsePartialDelimitedFrom(input, EMPTY_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageType parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 244 */     return checkMessageInitialized(parsePartialDelimitedFrom(input, extensionRegistry));
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageType parseDelimitedFrom(InputStream input) throws InvalidProtocolBufferException {
/* 249 */     return parseDelimitedFrom(input, EMPTY_REGISTRY);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\AbstractParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */