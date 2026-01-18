/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
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
/*     */ public class TypeRegistry
/*     */ {
/*     */   private final Map<String, Descriptors.Descriptor> types;
/*  24 */   private static final Logger logger = Logger.getLogger(TypeRegistry.class.getName());
/*     */   
/*     */   private static class EmptyTypeRegistryHolder {
/*  27 */     private static final TypeRegistry EMPTY = new TypeRegistry(
/*  28 */         Collections.emptyMap());
/*     */   }
/*     */   
/*     */   public static TypeRegistry getEmptyTypeRegistry() {
/*  32 */     return EmptyTypeRegistryHolder.EMPTY;
/*     */   }
/*     */   
/*     */   public static Builder newBuilder() {
/*  36 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Descriptors.Descriptor find(String name) {
/*  43 */     return this.types.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Descriptors.Descriptor getDescriptorForTypeUrl(String typeUrl) throws InvalidProtocolBufferException {
/*  51 */     return find(getTypeName(typeUrl));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   TypeRegistry(Map<String, Descriptors.Descriptor> types) {
/*  57 */     this.types = types;
/*     */   }
/*     */   
/*     */   private static String getTypeName(String typeUrl) throws InvalidProtocolBufferException {
/*  61 */     String[] parts = typeUrl.split("/");
/*  62 */     if (parts.length <= 1) {
/*  63 */       throw new InvalidProtocolBufferException("Invalid type url found: " + typeUrl);
/*     */     }
/*  65 */     return parts[parts.length - 1];
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
/*     */   public static final class Builder
/*     */   {
/*     */     private final Set<String> files;
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
/*     */     private Map<String, Descriptors.Descriptor> types;
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
/*     */     
/*     */     private Builder() {
/* 132 */       this.files = new HashSet<>();
/* 133 */       this.types = new HashMap<>();
/*     */     }
/*     */     
/*     */     public Builder add(Descriptors.Descriptor messageType) {
/*     */       if (this.types == null)
/*     */         throw new IllegalStateException("A TypeRegistry.Builder can only be used once."); 
/*     */       addFile(messageType.getFile());
/*     */       return this;
/*     */     }
/*     */     
/*     */     public Builder add(Iterable<Descriptors.Descriptor> messageTypes) {
/*     */       if (this.types == null)
/*     */         throw new IllegalStateException("A TypeRegistry.Builder can only be used once."); 
/*     */       for (Descriptors.Descriptor type : messageTypes)
/*     */         addFile(type.getFile()); 
/*     */       return this;
/*     */     }
/*     */     
/*     */     public TypeRegistry build() {
/*     */       TypeRegistry result = new TypeRegistry(this.types);
/*     */       this.types = null;
/*     */       return result;
/*     */     }
/*     */     
/*     */     private void addFile(Descriptors.FileDescriptor file) {
/*     */       if (!this.files.add(file.getFullName()))
/*     */         return; 
/*     */       for (Descriptors.FileDescriptor dependency : file.getDependencies())
/*     */         addFile(dependency); 
/*     */       for (Descriptors.Descriptor message : file.getMessageTypes())
/*     */         addMessage(message); 
/*     */     }
/*     */     
/*     */     private void addMessage(Descriptors.Descriptor message) {
/*     */       for (Descriptors.Descriptor nestedType : message.getNestedTypes())
/*     */         addMessage(nestedType); 
/*     */       if (this.types.containsKey(message.getFullName())) {
/*     */         TypeRegistry.logger.warning("Type " + message.getFullName() + " is added multiple times.");
/*     */         return;
/*     */       } 
/*     */       this.types.put(message.getFullName(), message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\TypeRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */