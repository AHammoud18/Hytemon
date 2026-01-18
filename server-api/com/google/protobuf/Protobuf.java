/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ @CheckReturnValue
/*     */ final class Protobuf
/*     */ {
/*  23 */   private static final Protobuf INSTANCE = new Protobuf();
/*     */ 
/*     */   
/*     */   private final SchemaFactory schemaFactory;
/*     */   
/*  28 */   private final ConcurrentMap<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public static Protobuf getInstance() {
/*  33 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void writeTo(T message, Writer writer) throws IOException {
/*  38 */     schemaFor(message).writeTo(message, writer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void mergeFrom(T message, Reader reader) throws IOException {
/*  43 */     mergeFrom(message, reader, ExtensionRegistryLite.getEmptyRegistry());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void mergeFrom(T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  49 */     schemaFor(message).mergeFrom(message, reader, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void makeImmutable(T message) {
/*  54 */     schemaFor(message).makeImmutable(message);
/*     */   }
/*     */ 
/*     */   
/*     */   <T> boolean isInitialized(T message) {
/*  59 */     return schemaFor(message).isInitialized(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Schema<T> schemaFor(Class<T> messageType) {
/*  64 */     Internal.checkNotNull(messageType, "messageType");
/*     */     
/*  66 */     Schema<T> schema = (Schema<T>)this.schemaCache.get(messageType);
/*  67 */     if (schema == null) {
/*  68 */       schema = this.schemaFactory.createSchema(messageType);
/*     */       
/*  70 */       Schema<T> previous = (Schema)registerSchema(messageType, schema);
/*  71 */       if (previous != null)
/*     */       {
/*  73 */         schema = previous;
/*     */       }
/*     */     } 
/*  76 */     return schema;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Schema<T> schemaFor(T message) {
/*  82 */     return schemaFor((Class)message.getClass());
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
/*     */   public Schema<?> registerSchema(Class<?> messageType, Schema<?> schema) {
/*  94 */     Internal.checkNotNull(messageType, "messageType");
/*  95 */     Internal.checkNotNull(schema, "schema");
/*  96 */     return this.schemaCache.putIfAbsent(messageType, schema);
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
/*     */   @CanIgnoreReturnValue
/*     */   public Schema<?> registerSchemaOverride(Class<?> messageType, Schema<?> schema) {
/* 110 */     Internal.checkNotNull(messageType, "messageType");
/* 111 */     Internal.checkNotNull(schema, "schema");
/* 112 */     return this.schemaCache.put(messageType, schema);
/*     */   }
/*     */   
/*     */   private Protobuf() {
/* 116 */     this.schemaFactory = new ManifestSchemaFactory();
/*     */   }
/*     */   
/*     */   int getTotalSchemaSize() {
/* 120 */     int result = 0;
/* 121 */     for (Schema<?> schema : this.schemaCache.values()) {
/* 122 */       if (schema instanceof MessageSchema) {
/* 123 */         result += ((MessageSchema)schema).getSchemaSize();
/*     */       }
/*     */     } 
/* 126 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Protobuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */