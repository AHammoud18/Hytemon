/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MutableParametersRegistry
/*    */ {
/* 33 */   private final Map<String, Parameters> parametersMap = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/* 37 */   private static final MutableParametersRegistry globalInstance = new MutableParametersRegistry();
/*    */   
/*    */   public static MutableParametersRegistry globalInstance() {
/* 40 */     return globalInstance;
/*    */   }
/*    */   
/*    */   public synchronized void put(String name, Parameters value) throws GeneralSecurityException {
/* 44 */     if (this.parametersMap.containsKey(name)) {
/* 45 */       if (((Parameters)this.parametersMap.get(name)).equals(value)) {
/*    */         return;
/*    */       }
/* 48 */       throw new GeneralSecurityException("Parameters object with name " + name + " already exists (" + this.parametersMap
/*    */ 
/*    */ 
/*    */           
/* 52 */           .get(name) + "), cannot insert " + value);
/*    */     } 
/*    */ 
/*    */     
/* 56 */     this.parametersMap.put(name, value);
/*    */   }
/*    */   
/*    */   public synchronized Parameters get(String name) throws GeneralSecurityException {
/* 60 */     if (this.parametersMap.containsKey(name)) {
/* 61 */       return this.parametersMap.get(name);
/*    */     }
/* 63 */     throw new GeneralSecurityException("Name " + name + " does not exist");
/*    */   }
/*    */   
/*    */   public synchronized void putAll(Map<String, Parameters> values) throws GeneralSecurityException {
/* 67 */     for (Map.Entry<String, Parameters> entry : values.entrySet()) {
/* 68 */       put(entry.getKey(), entry.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public synchronized List<String> getNames() {
/* 73 */     List<String> results = new ArrayList<>();
/* 74 */     results.addAll(this.parametersMap.keySet());
/*    */     
/* 76 */     return Collections.unmodifiableList(results);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\MutableParametersRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */