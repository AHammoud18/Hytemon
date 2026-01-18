/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ExtensionRegistry
/*     */   extends ExtensionRegistryLite
/*     */ {
/*     */   private final Map<String, ExtensionInfo> immutableExtensionsByName;
/*     */   private final Map<String, ExtensionInfo> mutableExtensionsByName;
/*     */   private final Map<DescriptorIntPair, ExtensionInfo> immutableExtensionsByNumber;
/*     */   private final Map<DescriptorIntPair, ExtensionInfo> mutableExtensionsByNumber;
/*     */   
/*     */   public static ExtensionRegistry newInstance() {
/*  71 */     return new ExtensionRegistry();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ExtensionRegistry getEmptyRegistry() {
/*  76 */     return EMPTY_REGISTRY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionRegistry getUnmodifiable() {
/*  82 */     return new ExtensionRegistry(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ExtensionInfo
/*     */   {
/*     */     public final Descriptors.FieldDescriptor descriptor;
/*     */ 
/*     */     
/*     */     public final Message defaultInstance;
/*     */ 
/*     */ 
/*     */     
/*     */     private ExtensionInfo(Descriptors.FieldDescriptor descriptor) {
/*  97 */       this.descriptor = descriptor;
/*  98 */       this.defaultInstance = null;
/*     */     }
/*     */     
/*     */     private ExtensionInfo(Descriptors.FieldDescriptor descriptor, Message defaultInstance) {
/* 102 */       this.descriptor = descriptor;
/* 103 */       this.defaultInstance = defaultInstance;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ExtensionInfo findExtensionByName(String fullName) {
/* 110 */     return findImmutableExtensionByName(fullName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionInfo findImmutableExtensionByName(String fullName) {
/* 120 */     return this.immutableExtensionsByName.get(fullName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ExtensionInfo findMutableExtensionByName(String fullName) {
/* 131 */     return this.mutableExtensionsByName.get(fullName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ExtensionInfo findExtensionByNumber(Descriptors.Descriptor containingType, int fieldNumber) {
/* 138 */     return findImmutableExtensionByNumber(containingType, fieldNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionInfo findImmutableExtensionByNumber(Descriptors.Descriptor containingType, int fieldNumber) {
/* 148 */     return this.immutableExtensionsByNumber.get(new DescriptorIntPair(containingType, fieldNumber));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ExtensionInfo findMutableExtensionByNumber(Descriptors.Descriptor containingType, int fieldNumber) {
/* 159 */     return this.mutableExtensionsByNumber.get(new DescriptorIntPair(containingType, fieldNumber));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Set<ExtensionInfo> getAllMutableExtensionsByExtendedType(String fullName) {
/* 170 */     HashSet<ExtensionInfo> extensions = new HashSet<>();
/* 171 */     for (DescriptorIntPair pair : this.mutableExtensionsByNumber.keySet()) {
/* 172 */       if (pair.descriptor.getFullName().equals(fullName)) {
/* 173 */         extensions.add(this.mutableExtensionsByNumber.get(pair));
/*     */       }
/*     */     } 
/* 176 */     return extensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<ExtensionInfo> getAllImmutableExtensionsByExtendedType(String fullName) {
/* 187 */     HashSet<ExtensionInfo> extensions = new HashSet<>();
/* 188 */     for (DescriptorIntPair pair : this.immutableExtensionsByNumber.keySet()) {
/* 189 */       if (pair.descriptor.getFullName().equals(fullName)) {
/* 190 */         extensions.add(this.immutableExtensionsByNumber.get(pair));
/*     */       }
/*     */     } 
/* 193 */     return extensions;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Extension<?, ?> extension) {
/* 198 */     if (extension.getExtensionType() != Extension.ExtensionType.IMMUTABLE && extension
/* 199 */       .getExtensionType() != Extension.ExtensionType.MUTABLE) {
/*     */       return;
/*     */     }
/*     */     
/* 203 */     add(newExtensionInfo(extension), extension.getExtensionType());
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(GeneratedMessage.GeneratedExtension<?, ?> extension) {
/* 208 */     add(extension);
/*     */   }
/*     */   
/*     */   static ExtensionInfo newExtensionInfo(Extension<?, ?> extension) {
/* 212 */     if (extension.getDescriptor().getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 213 */       if (extension.getMessageDefaultInstance() == null) {
/* 214 */         throw new IllegalStateException("Registered message-type extension had null default instance: " + extension
/*     */             
/* 216 */             .getDescriptor().getFullName());
/*     */       }
/* 218 */       return new ExtensionInfo(extension
/* 219 */           .getDescriptor(), extension.getMessageDefaultInstance());
/*     */     } 
/* 221 */     return new ExtensionInfo(extension.getDescriptor(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Descriptors.FieldDescriptor type) {
/* 227 */     if (type.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 228 */       throw new IllegalArgumentException("ExtensionRegistry.add() must be provided a default instance when adding an embedded message extension.");
/*     */     }
/*     */ 
/*     */     
/* 232 */     ExtensionInfo info = new ExtensionInfo(type, null);
/* 233 */     add(info, Extension.ExtensionType.IMMUTABLE);
/* 234 */     add(info, Extension.ExtensionType.MUTABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Descriptors.FieldDescriptor type, Message defaultInstance) {
/* 239 */     if (type.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 240 */       throw new IllegalArgumentException("ExtensionRegistry.add() provided a default instance for a non-message extension.");
/*     */     }
/*     */     
/* 243 */     add(new ExtensionInfo(type, defaultInstance), Extension.ExtensionType.IMMUTABLE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExtensionRegistry() {
/* 250 */     this.immutableExtensionsByName = new HashMap<>();
/* 251 */     this.mutableExtensionsByName = new HashMap<>();
/* 252 */     this.immutableExtensionsByNumber = new HashMap<>();
/* 253 */     this.mutableExtensionsByNumber = new HashMap<>();
/*     */   }
/*     */   
/*     */   private ExtensionRegistry(ExtensionRegistry other) {
/* 257 */     super(other);
/* 258 */     this.immutableExtensionsByName = Collections.unmodifiableMap(other.immutableExtensionsByName);
/* 259 */     this.mutableExtensionsByName = Collections.unmodifiableMap(other.mutableExtensionsByName);
/* 260 */     this
/* 261 */       .immutableExtensionsByNumber = Collections.unmodifiableMap(other.immutableExtensionsByNumber);
/* 262 */     this.mutableExtensionsByNumber = Collections.unmodifiableMap(other.mutableExtensionsByNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ExtensionRegistry(boolean empty) {
/* 271 */     super(EMPTY_REGISTRY_LITE);
/* 272 */     this.immutableExtensionsByName = Collections.emptyMap();
/* 273 */     this.mutableExtensionsByName = Collections.emptyMap();
/* 274 */     this.immutableExtensionsByNumber = Collections.emptyMap();
/* 275 */     this.mutableExtensionsByNumber = Collections.emptyMap();
/*     */   }
/*     */   
/* 278 */   static final ExtensionRegistry EMPTY_REGISTRY = new ExtensionRegistry(true); private void add(ExtensionInfo extension, Extension.ExtensionType extensionType) {
/*     */     Map<String, ExtensionInfo> extensionsByName;
/*     */     Map<DescriptorIntPair, ExtensionInfo> extensionsByNumber;
/* 281 */     if (!extension.descriptor.isExtension()) {
/* 282 */       throw new IllegalArgumentException("ExtensionRegistry.add() was given a FieldDescriptor for a regular (non-extension) field.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     switch (extensionType) {
/*     */       case IMMUTABLE:
/* 291 */         extensionsByName = this.immutableExtensionsByName;
/* 292 */         extensionsByNumber = this.immutableExtensionsByNumber;
/*     */         break;
/*     */       case MUTABLE:
/* 295 */         extensionsByName = this.mutableExtensionsByName;
/* 296 */         extensionsByNumber = this.mutableExtensionsByNumber;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 303 */     extensionsByName.put(extension.descriptor.getFullName(), extension);
/* 304 */     extensionsByNumber.put(new DescriptorIntPair(extension.descriptor
/*     */           
/* 306 */           .getContainingType(), extension.descriptor.getNumber()), extension);
/*     */ 
/*     */     
/* 309 */     Descriptors.FieldDescriptor field = extension.descriptor;
/* 310 */     if (field.getContainingType().getOptions().getMessageSetWireFormat() && field
/* 311 */       .getType() == Descriptors.FieldDescriptor.Type.MESSAGE && field
/* 312 */       .isOptional() && field
/* 313 */       .getExtensionScope() == field.getMessageType())
/*     */     {
/*     */ 
/*     */       
/* 317 */       extensionsByName.put(field.getMessageType().getFullName(), extension);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DescriptorIntPair
/*     */   {
/*     */     private final Descriptors.Descriptor descriptor;
/*     */     private final int number;
/*     */     
/*     */     DescriptorIntPair(Descriptors.Descriptor descriptor, int number) {
/* 327 */       this.descriptor = descriptor;
/* 328 */       this.number = number;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 333 */       return this.descriptor.hashCode() * 65535 + this.number;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 338 */       if (!(obj instanceof DescriptorIntPair)) {
/* 339 */         return false;
/*     */       }
/* 341 */       DescriptorIntPair other = (DescriptorIntPair)obj;
/* 342 */       return (this.descriptor == other.descriptor && this.number == other.number);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ExtensionRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */