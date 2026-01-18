/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*     */ import com.google.crypto.tink.proto.Keyset;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ public final class CleartextKeysetHandle
/*     */ {
/*     */   @Deprecated
/*     */   public static final KeysetHandle parseFrom(byte[] serialized) throws GeneralSecurityException {
/*     */     try {
/*  49 */       Keyset keyset = Keyset.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry());
/*  50 */       return KeysetHandle.fromKeyset(keyset);
/*  51 */     } catch (InvalidProtocolBufferException e) {
/*  52 */       throw new GeneralSecurityException("invalid keyset");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeysetHandle read(KeysetReader reader) throws GeneralSecurityException, IOException {
/*  62 */     return KeysetHandle.fromKeyset(reader.read());
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
/*     */   @Deprecated
/*     */   public static KeysetHandle read(KeysetReader reader, Map<String, String> monitoringAnnotations) throws GeneralSecurityException, IOException {
/*  78 */     return KeysetHandle.fromKeysetAndAnnotations(reader
/*  79 */         .read(), MonitoringAnnotations.newBuilder().addAll(monitoringAnnotations).build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Keyset getKeyset(KeysetHandle keysetHandle) {
/*  89 */     return keysetHandle.getKeyset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static KeysetHandle fromKeyset(Keyset keyset) throws GeneralSecurityException {
/*  99 */     return KeysetHandle.fromKeyset(keyset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(KeysetHandle handle, KeysetWriter keysetWriter) throws IOException {
/* 108 */     keysetWriter.write(handle.getKeyset());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\CleartextKeysetHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */