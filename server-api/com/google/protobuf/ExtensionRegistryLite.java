/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class ExtensionRegistryLite
/*     */ {
/*     */   private static volatile boolean eagerlyParseMessageSets = false;
/*     */   static final String EXTENSION_CLASS_NAME = "com.google.protobuf.Extension";
/*     */   private static volatile ExtensionRegistryLite emptyRegistry;
/*     */   
/*     */   private static class ExtensionClassHolder
/*     */   {
/*  60 */     static final Class<?> INSTANCE = resolveExtensionClass();
/*     */     
/*     */     static Class<?> resolveExtensionClass() {
/*     */       try {
/*  64 */         return Class.forName("com.google.protobuf.Extension");
/*  65 */       } catch (ClassNotFoundException e) {
/*     */         
/*  67 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isEagerlyParseMessageSets() {
/*  73 */     return eagerlyParseMessageSets;
/*     */   }
/*     */   
/*     */   public static void setEagerlyParseMessageSets(boolean isEagerlyParse) {
/*  77 */     eagerlyParseMessageSets = isEagerlyParse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExtensionRegistryLite newInstance() {
/*  87 */     return Android.assumeLiteRuntime ? 
/*  88 */       new ExtensionRegistryLite() : 
/*  89 */       ExtensionRegistryFactory.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExtensionRegistryLite getEmptyRegistry() {
/*  99 */     if (Android.assumeLiteRuntime) {
/* 100 */       return EMPTY_REGISTRY_LITE;
/*     */     }
/* 102 */     ExtensionRegistryLite result = emptyRegistry;
/* 103 */     if (result == null) {
/* 104 */       synchronized (ExtensionRegistryLite.class) {
/* 105 */         result = emptyRegistry;
/* 106 */         if (result == null) {
/* 107 */           emptyRegistry = result = ExtensionRegistryFactory.createEmpty();
/*     */         }
/*     */       } 
/*     */     }
/* 111 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExtensionRegistryLite getUnmodifiable() {
/* 116 */     return new ExtensionRegistryLite(this);
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
/*     */   public <ContainingType extends MessageLite> GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(ContainingType containingTypeDefaultInstance, int fieldNumber) {
/* 128 */     return (GeneratedMessageLite.GeneratedExtension<ContainingType, ?>)this.extensionsByNumber
/* 129 */       .get(new ObjectIntPair(containingTypeDefaultInstance, fieldNumber));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void add(GeneratedMessageLite.GeneratedExtension<?, ?> extension) {
/* 134 */     this.extensionsByNumber.put(new ObjectIntPair(extension
/* 135 */           .getContainingTypeDefaultInstance(), extension.getNumber()), extension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(ExtensionLite<?, ?> extension) {
/* 144 */     if (extension instanceof GeneratedMessageLite.GeneratedExtension) {
/* 145 */       add((GeneratedMessageLite.GeneratedExtension<?, ?>)extension);
/*     */     }
/* 147 */     if (!Android.assumeLiteRuntime && ExtensionRegistryFactory.isFullRegistry(this)) {
/*     */       try {
/* 149 */         getClass().getMethod("add", new Class[] { ExtensionClassHolder.INSTANCE }).invoke(this, new Object[] { extension });
/* 150 */       } catch (Exception e) {
/* 151 */         throw new IllegalArgumentException(
/* 152 */             String.format("Could not invoke ExtensionRegistry#add for %s", new Object[] { extension }), e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ExtensionRegistryLite() {
/* 164 */     this.extensionsByNumber = new HashMap<>();
/*     */   }
/*     */ 
/*     */   
/* 168 */   static final ExtensionRegistryLite EMPTY_REGISTRY_LITE = new ExtensionRegistryLite(true);
/*     */   
/*     */   ExtensionRegistryLite(ExtensionRegistryLite other) {
/* 171 */     if (other == EMPTY_REGISTRY_LITE) {
/* 172 */       this.extensionsByNumber = Collections.emptyMap();
/*     */     } else {
/* 174 */       this.extensionsByNumber = Collections.unmodifiableMap(other.extensionsByNumber);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final Map<ObjectIntPair, GeneratedMessageLite.GeneratedExtension<?, ?>> extensionsByNumber;
/*     */   
/*     */   ExtensionRegistryLite(boolean empty) {
/* 182 */     this.extensionsByNumber = Collections.emptyMap();
/*     */   }
/*     */   
/*     */   private static final class ObjectIntPair
/*     */   {
/*     */     private final Object object;
/*     */     private final int number;
/*     */     
/*     */     ObjectIntPair(Object object, int number) {
/* 191 */       this.object = object;
/* 192 */       this.number = number;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 197 */       return System.identityHashCode(this.object) * 65535 + this.number;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 202 */       if (!(obj instanceof ObjectIntPair)) {
/* 203 */         return false;
/*     */       }
/* 205 */       ObjectIntPair other = (ObjectIntPair)obj;
/* 206 */       return (this.object == other.object && this.number == other.number);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ExtensionRegistryLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */