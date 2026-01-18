/*     */ package com.google.crypto.tink.mac;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.internal.KeysetHandleInterface;
/*     */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*     */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrefixMap;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveWrapper;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class ChunkedMacWrapper
/*     */   implements PrimitiveWrapper<ChunkedMac, ChunkedMac>
/*     */ {
/*  46 */   private static final ChunkedMacWrapper WRAPPER = new ChunkedMacWrapper();
/*     */   
/*     */   private static Bytes getOutputPrefix(Key key) throws GeneralSecurityException {
/*  49 */     if (key instanceof MacKey) {
/*  50 */       return ((MacKey)key).getOutputPrefix();
/*     */     }
/*  52 */     if (key instanceof LegacyProtoKey) {
/*  53 */       return ((LegacyProtoKey)key).getOutputPrefix();
/*     */     }
/*  55 */     throw new GeneralSecurityException("Cannot get output prefix for key of class " + key
/*     */         
/*  57 */         .getClass().getName() + " with parameters " + key
/*     */         
/*  59 */         .getParameters());
/*     */   }
/*     */   
/*     */   private static class WrappedChunkedMacVerification implements ChunkedMacVerification {
/*     */     private final List<ChunkedMacVerification> verifications;
/*     */     
/*     */     private WrappedChunkedMacVerification(List<ChunkedMacVerification> verificationEntries) {
/*  66 */       this.verifications = verificationEntries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(ByteBuffer data) throws GeneralSecurityException {
/*  77 */       ByteBuffer clonedData = data.duplicate();
/*  78 */       clonedData.mark();
/*  79 */       for (ChunkedMacVerification entry : this.verifications) {
/*  80 */         clonedData.reset();
/*  81 */         entry.update(clonedData);
/*     */       } 
/*  83 */       data.position(data.limit());
/*     */     }
/*     */ 
/*     */     
/*     */     public void verifyMac() throws GeneralSecurityException {
/*  88 */       GeneralSecurityException errorSink = new GeneralSecurityException("MAC verification failed for all suitable keys in keyset");
/*     */       
/*  90 */       for (ChunkedMacVerification entry : this.verifications) {
/*     */         try {
/*  92 */           entry.verifyMac();
/*     */           
/*     */           return;
/*  95 */         } catch (GeneralSecurityException e) {
/*     */           
/*  97 */           errorSink.addSuppressed(e);
/*     */         } 
/*     */       } 
/*     */       
/* 101 */       throw errorSink;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   private static class WrappedChunkedMac
/*     */     implements ChunkedMac {
/*     */     private final PrefixMap<ChunkedMac> allChunkedMacs;
/*     */     private final ChunkedMac primaryChunkedMac;
/*     */     
/*     */     private WrappedChunkedMac(PrefixMap<ChunkedMac> allChunkedMacs, ChunkedMac primaryChunkedMac) {
/* 112 */       this.allChunkedMacs = allChunkedMacs;
/* 113 */       this.primaryChunkedMac = primaryChunkedMac;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChunkedMacComputation createComputation() throws GeneralSecurityException {
/* 118 */       return this.primaryChunkedMac.createComputation();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ChunkedMacVerification createVerification(byte[] tag) throws GeneralSecurityException {
/* 124 */       List<ChunkedMacVerification> allVerifications = new ArrayList<>();
/* 125 */       for (ChunkedMac mac : this.allChunkedMacs.getAllWithMatchingPrefix(tag)) {
/* 126 */         allVerifications.add(mac.createVerification(tag));
/*     */       }
/* 128 */       return new ChunkedMacWrapper.WrappedChunkedMacVerification(allVerifications);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkedMac wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<ChunkedMac> factory) throws GeneralSecurityException {
/* 140 */     KeysetHandleInterface.Entry primaryEntry = keysetHandle.getPrimary();
/* 141 */     if (primaryEntry == null) {
/* 142 */       throw new GeneralSecurityException("no primary in primitive set");
/*     */     }
/* 144 */     PrefixMap.Builder<ChunkedMac> allChunkedMacsBuilder = new PrefixMap.Builder();
/* 145 */     for (int i = 0; i < keysetHandle.size(); i++) {
/* 146 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/* 147 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 148 */         ChunkedMac chunkedMac = (ChunkedMac)factory.create(entry);
/* 149 */         allChunkedMacsBuilder.put(getOutputPrefix(entry.getKey()), chunkedMac);
/*     */       } 
/*     */     } 
/* 152 */     ChunkedMac primaryChunkedMac = (ChunkedMac)factory.create(primaryEntry);
/*     */     
/* 154 */     return new WrappedChunkedMac(allChunkedMacsBuilder.build(), primaryChunkedMac);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<ChunkedMac> getPrimitiveClass() {
/* 159 */     return ChunkedMac.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<ChunkedMac> getInputPrimitiveClass() {
/* 164 */     return ChunkedMac.class;
/*     */   }
/*     */   
/*     */   static void register() throws GeneralSecurityException {
/* 168 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 178 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\ChunkedMacWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */