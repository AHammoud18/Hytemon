/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public final class KeyManagerRegistry
/*     */ {
/*  37 */   private static final Logger logger = Logger.getLogger(KeyManagerRegistry.class.getName());
/*     */ 
/*     */   
/*     */   private ConcurrentMap<String, KeyManager<?>> keyManagerMap;
/*     */   
/*     */   private ConcurrentMap<String, Boolean> newKeyAllowedMap;
/*     */   
/*  44 */   private static final KeyManagerRegistry GLOBAL_INSTANCE = new KeyManagerRegistry();
/*     */ 
/*     */   
/*     */   public static KeyManagerRegistry globalInstance() {
/*  48 */     return GLOBAL_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resetGlobalInstanceTestOnly() {
/*  53 */     GLOBAL_INSTANCE.keyManagerMap = new ConcurrentHashMap<>();
/*  54 */     GLOBAL_INSTANCE.newKeyAllowedMap = new ConcurrentHashMap<>();
/*     */   }
/*     */   
/*     */   public KeyManagerRegistry(KeyManagerRegistry original) {
/*  58 */     this.keyManagerMap = new ConcurrentHashMap<>(original.keyManagerMap);
/*  59 */     this.newKeyAllowedMap = new ConcurrentHashMap<>(original.newKeyAllowedMap);
/*     */   }
/*     */   
/*     */   public KeyManagerRegistry() {
/*  63 */     this.keyManagerMap = new ConcurrentHashMap<>();
/*  64 */     this.newKeyAllowedMap = new ConcurrentHashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized KeyManager<?> getKeyManagerOrThrow(String typeUrl) throws GeneralSecurityException {
/*  69 */     if (!this.keyManagerMap.containsKey(typeUrl)) {
/*  70 */       throw new GeneralSecurityException("No key manager found for key type " + typeUrl + ", see https://developers.google.com/tink/faq/registration_errors");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  75 */     return this.keyManagerMap.get(typeUrl);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void insertKeyManager(KeyManager<?> manager, boolean forceOverwrite, boolean newKeyAllowed) throws GeneralSecurityException {
/*  81 */     String typeUrl = manager.getKeyType();
/*  82 */     if (newKeyAllowed && this.newKeyAllowedMap.containsKey(typeUrl) && !((Boolean)this.newKeyAllowedMap.get(typeUrl)).booleanValue()) {
/*  83 */       throw new GeneralSecurityException("New keys are already disallowed for key type " + typeUrl);
/*     */     }
/*  85 */     KeyManager<?> existing = this.keyManagerMap.get(typeUrl);
/*  86 */     if (existing != null && !existing.getClass().equals(manager.getClass())) {
/*  87 */       logger.warning("Attempted overwrite of a registered key manager for key type " + typeUrl);
/*  88 */       throw new GeneralSecurityException(
/*  89 */           String.format("typeUrl (%s) is already registered with %s, cannot be re-registered with %s", new Object[] {
/*     */               
/*  91 */               typeUrl, existing.getClass().getName(), manager.getClass().getName() }));
/*     */     } 
/*  93 */     if (!forceOverwrite) {
/*  94 */       this.keyManagerMap.putIfAbsent(typeUrl, manager);
/*     */     } else {
/*  96 */       this.keyManagerMap.put(typeUrl, manager);
/*     */     } 
/*  98 */     this.newKeyAllowedMap.put(typeUrl, Boolean.valueOf(newKeyAllowed));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized <P> void registerKeyManager(KeyManager<P> manager, boolean newKeyAllowed) throws GeneralSecurityException {
/* 104 */     registerKeyManagerWithFipsCompatibility(manager, TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS, newKeyAllowed);
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
/*     */   public synchronized <P> void registerKeyManagerWithFipsCompatibility(KeyManager<P> manager, TinkFipsUtil.AlgorithmFipsCompatibility compatibility, boolean newKeyAllowed) throws GeneralSecurityException {
/* 117 */     if (!compatibility.isCompatible()) {
/* 118 */       throw new GeneralSecurityException("Cannot register key manager: FIPS compatibility insufficient");
/*     */     }
/*     */     
/* 121 */     insertKeyManager(manager, false, newKeyAllowed);
/*     */   }
/*     */   
/*     */   public boolean typeUrlExists(String typeUrl) {
/* 125 */     return this.keyManagerMap.containsKey(typeUrl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <P> KeyManager<P> getKeyManager(String typeUrl, Class<P> primitiveClass) throws GeneralSecurityException {
/* 135 */     KeyManager<?> manager = getKeyManagerOrThrow(typeUrl);
/* 136 */     if (manager.getPrimitiveClass().equals(primitiveClass)) {
/* 137 */       return (KeyManager)manager;
/*     */     }
/* 139 */     throw new GeneralSecurityException("Primitive type " + primitiveClass
/*     */         
/* 141 */         .getName() + " not supported by key manager of type " + manager
/*     */         
/* 143 */         .getClass() + ", which only supports: " + manager
/*     */         
/* 145 */         .getPrimitiveClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyManager<?> getUntypedKeyManager(String typeUrl) throws GeneralSecurityException {
/* 152 */     return getKeyManagerOrThrow(typeUrl);
/*     */   }
/*     */   
/*     */   public boolean isNewKeyAllowed(String typeUrl) {
/* 156 */     return ((Boolean)this.newKeyAllowedMap.get(typeUrl)).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 160 */     return this.keyManagerMap.isEmpty();
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
/*     */   public synchronized void restrictToFipsIfEmptyAndGlobalInstance() throws GeneralSecurityException {
/* 172 */     if (this != globalInstance()) {
/* 173 */       throw new GeneralSecurityException("Only the global instance can be restricted to FIPS.");
/*     */     }
/*     */     
/* 176 */     if (TinkFipsUtil.useOnlyFips()) {
/*     */       return;
/*     */     }
/*     */     
/* 180 */     if (!isEmpty()) {
/* 181 */       throw new GeneralSecurityException("Could not enable FIPS mode as Registry is not empty.");
/*     */     }
/* 183 */     TinkFipsUtil.setFipsRestricted();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\KeyManagerRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */