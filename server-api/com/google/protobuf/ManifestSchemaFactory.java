/*     */ package com.google.protobuf;
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
/*     */ @CheckReturnValue
/*     */ final class ManifestSchemaFactory
/*     */   implements SchemaFactory
/*     */ {
/*     */   private final MessageInfoFactory messageInfoFactory;
/*     */   
/*     */   public ManifestSchemaFactory() {
/*  22 */     this(getDefaultMessageInfoFactory());
/*     */   }
/*     */   
/*     */   private ManifestSchemaFactory(MessageInfoFactory messageInfoFactory) {
/*  26 */     this.messageInfoFactory = Internal.<MessageInfoFactory>checkNotNull(messageInfoFactory, "messageInfoFactory");
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Schema<T> createSchema(Class<T> messageType) {
/*  31 */     SchemaUtil.requireGeneratedMessage(messageType);
/*     */     
/*  33 */     MessageInfo messageInfo = this.messageInfoFactory.messageInfoFor(messageType);
/*     */ 
/*     */     
/*  36 */     if (messageInfo.isMessageSetWireFormat()) {
/*  37 */       return useLiteRuntime(messageType) ? 
/*  38 */         MessageSetSchema.<T>newSchema(
/*  39 */           SchemaUtil.unknownFieldSetLiteSchema(), 
/*  40 */           ExtensionSchemas.lite(), messageInfo
/*  41 */           .getDefaultInstance()) : 
/*  42 */         MessageSetSchema.<T>newSchema(
/*  43 */           SchemaUtil.unknownFieldSetFullSchema(), 
/*  44 */           ExtensionSchemas.full(), messageInfo
/*  45 */           .getDefaultInstance());
/*     */     }
/*     */     
/*  48 */     return newSchema(messageType, messageInfo);
/*     */   }
/*     */   
/*     */   private static <T> Schema<T> newSchema(Class<T> messageType, MessageInfo messageInfo) {
/*  52 */     return useLiteRuntime(messageType) ? 
/*  53 */       MessageSchema.<T>newSchema(messageType, messageInfo, 
/*     */ 
/*     */         
/*  56 */         NewInstanceSchemas.lite(), 
/*  57 */         ListFieldSchemas.lite(), 
/*  58 */         SchemaUtil.unknownFieldSetLiteSchema(), 
/*  59 */         allowExtensions(messageInfo) ? ExtensionSchemas.lite() : null, 
/*  60 */         MapFieldSchemas.lite()) : 
/*  61 */       MessageSchema.<T>newSchema(messageType, messageInfo, 
/*     */ 
/*     */         
/*  64 */         NewInstanceSchemas.full(), 
/*  65 */         ListFieldSchemas.full(), 
/*  66 */         SchemaUtil.unknownFieldSetFullSchema(), 
/*  67 */         allowExtensions(messageInfo) ? ExtensionSchemas.full() : null, 
/*  68 */         MapFieldSchemas.full());
/*     */   }
/*     */   
/*     */   private static boolean allowExtensions(MessageInfo messageInfo) {
/*  72 */     switch (messageInfo.getSyntax()) {
/*     */       case PROTO3:
/*  74 */         return false;
/*     */     } 
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static MessageInfoFactory getDefaultMessageInfoFactory() {
/*  81 */     return new CompositeMessageInfoFactory(new MessageInfoFactory[] {
/*  82 */           GeneratedMessageInfoFactory.getInstance(), getDescriptorMessageInfoFactory() });
/*     */   }
/*     */   
/*     */   private static class CompositeMessageInfoFactory implements MessageInfoFactory {
/*     */     private MessageInfoFactory[] factories;
/*     */     
/*     */     CompositeMessageInfoFactory(MessageInfoFactory... factories) {
/*  89 */       this.factories = factories;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupported(Class<?> clazz) {
/*  94 */       for (MessageInfoFactory factory : this.factories) {
/*  95 */         if (factory.isSupported(clazz)) {
/*  96 */           return true;
/*     */         }
/*     */       } 
/*  99 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public MessageInfo messageInfoFor(Class<?> clazz) {
/* 104 */       for (MessageInfoFactory factory : this.factories) {
/* 105 */         if (factory.isSupported(clazz)) {
/* 106 */           return factory.messageInfoFor(clazz);
/*     */         }
/*     */       } 
/* 109 */       throw new UnsupportedOperationException("No factory is available for message type: " + clazz
/* 110 */           .getName());
/*     */     }
/*     */   }
/*     */   
/* 114 */   private static final MessageInfoFactory EMPTY_FACTORY = new MessageInfoFactory()
/*     */     {
/*     */       public boolean isSupported(Class<?> clazz)
/*     */       {
/* 118 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public MessageInfo messageInfoFor(Class<?> clazz) {
/* 123 */         throw new IllegalStateException("This should never be called.");
/*     */       }
/*     */     };
/*     */   
/*     */   private static MessageInfoFactory getDescriptorMessageInfoFactory() {
/* 128 */     if (Android.assumeLiteRuntime) {
/* 129 */       return EMPTY_FACTORY;
/*     */     }
/*     */     try {
/* 132 */       Class<?> clazz = Class.forName("com.google.protobuf.DescriptorMessageInfoFactory");
/* 133 */       return (MessageInfoFactory)clazz.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
/* 134 */     } catch (Exception e) {
/* 135 */       return EMPTY_FACTORY;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean useLiteRuntime(Class<?> messageType) {
/* 140 */     return (Android.assumeLiteRuntime || GeneratedMessageLite.class.isAssignableFrom(messageType));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ManifestSchemaFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */