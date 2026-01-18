/*    */ package com.google.crypto.tink.prf.internal;
/*    */ 
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.KeyManager;
/*    */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*    */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*    */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*    */ import com.google.crypto.tink.prf.Prf;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class LegacyFullPrf
/*    */   implements Prf
/*    */ {
/*    */   private final Prf rawPrf;
/*    */   
/*    */   public static Prf create(LegacyProtoKey key) throws GeneralSecurityException {
/* 43 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*    */ 
/*    */     
/* 46 */     KeyManager<Prf> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), Prf.class);
/*    */     
/* 48 */     return new LegacyFullPrf((Prf)manager.getPrimitive(protoKeySerialization.getValue()));
/*    */   }
/*    */   
/*    */   private LegacyFullPrf(Prf rawPrf) {
/* 52 */     this.rawPrf = rawPrf;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] compute(byte[] input, int outputLength) throws GeneralSecurityException {
/* 57 */     return this.rawPrf.compute(input, outputLength);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\internal\LegacyFullPrf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */