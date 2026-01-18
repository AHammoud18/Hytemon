/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public final class KmsClients
/*     */ {
/*     */   private static List<KmsClient> autoClients;
/*  39 */   private static final CopyOnWriteArrayList<KmsClient> clients = new CopyOnWriteArrayList<>();
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
/*     */   public static void add(KmsClient client) {
/*  53 */     clients.add(client);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsClient get(String keyUri) throws GeneralSecurityException {
/*  63 */     for (KmsClient client : clients) {
/*  64 */       if (client.doesSupport(keyUri)) {
/*  65 */         return client;
/*     */       }
/*     */     } 
/*  68 */     throw new GeneralSecurityException("No KMS client does support: " + keyUri);
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
/*     */   @Deprecated
/*     */   public static synchronized KmsClient getAutoLoaded(String keyUri) throws GeneralSecurityException {
/*  86 */     if (autoClients == null) {
/*  87 */       autoClients = loadAutoKmsClients();
/*     */     }
/*  89 */     for (KmsClient client : autoClients) {
/*  90 */       if (client.doesSupport(keyUri)) {
/*  91 */         return client;
/*     */       }
/*     */     } 
/*  94 */     throw new GeneralSecurityException("No KMS client does support: " + keyUri);
/*     */   }
/*     */   
/*     */   static void reset() {
/*  98 */     clients.clear();
/*     */   }
/*     */   
/*     */   private static List<KmsClient> loadAutoKmsClients() {
/* 102 */     List<KmsClient> clients = new ArrayList<>();
/* 103 */     ServiceLoader<KmsClient> clientLoader = ServiceLoader.load(KmsClient.class);
/* 104 */     for (KmsClient element : clientLoader) {
/* 105 */       clients.add(element);
/*     */     }
/* 107 */     return Collections.unmodifiableList(clients);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KmsClients.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */