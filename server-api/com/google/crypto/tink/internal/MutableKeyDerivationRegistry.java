/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.io.InputStream;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class MutableKeyDerivationRegistry
/*     */ {
/*  32 */   private final Map<Class<? extends Parameters>, InsecureKeyCreator<? extends Parameters>> creators = new HashMap<>();
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
/*  45 */   private static final MutableKeyDerivationRegistry globalInstance = new MutableKeyDerivationRegistry();
/*     */ 
/*     */   
/*     */   public static MutableKeyDerivationRegistry globalInstance() {
/*  49 */     return globalInstance;
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
/*     */   public synchronized <ParametersT extends Parameters> void add(InsecureKeyCreator<ParametersT> creator, Class<ParametersT> parametersClass) throws GeneralSecurityException {
/*  61 */     InsecureKeyCreator<?> existingCreator = this.creators.get(parametersClass);
/*  62 */     if (existingCreator != null && 
/*  63 */       !existingCreator.equals(creator)) {
/*  64 */       throw new GeneralSecurityException("Different key creator for parameters class already inserted");
/*     */     }
/*     */ 
/*     */     
/*  68 */     this.creators.put(parametersClass, creator);
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
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public Key createKeyFromRandomness(Parameters parameters, InputStream inputStream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  88 */     return createKeyFromRandomnessTyped(parameters, inputStream, idRequirement, access);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized <ParametersT extends Parameters> Key createKeyFromRandomnessTyped(ParametersT parameters, InputStream inputStream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  98 */     Class<?> parametersClass = parameters.getClass();
/*  99 */     InsecureKeyCreator<?> creator = this.creators.get(parametersClass);
/* 100 */     if (creator == null) {
/* 101 */       throw new GeneralSecurityException("Cannot use key derivation to derive key for parameters " + parameters + ": no key creator for this class was registered.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     InsecureKeyCreator<ParametersT> castCreator = (InsecureKeyCreator)creator;
/* 108 */     return castCreator.createKeyFromRandomness(parameters, inputStream, idRequirement, access);
/*     */   }
/*     */   
/*     */   public static interface InsecureKeyCreator<ParametersT extends Parameters> {
/*     */     Key createKeyFromRandomness(ParametersT param1ParametersT, InputStream param1InputStream, @Nullable Integer param1Integer, SecretKeyAccess param1SecretKeyAccess) throws GeneralSecurityException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\MutableKeyDerivationRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */