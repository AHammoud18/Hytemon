/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import com.google.crypto.tink.proto.KeyData;
/*    */ import com.google.crypto.tink.proto.Keyset;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.InvalidProtocolBufferException;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class NoSecretKeysetHandle
/*    */ {
/*    */   @Deprecated
/*    */   public static final KeysetHandle parseFrom(byte[] serialized) throws GeneralSecurityException {
/*    */     try {
/* 44 */       Keyset keyset = Keyset.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry());
/* 45 */       validate(keyset);
/* 46 */       return KeysetHandle.fromKeyset(keyset);
/* 47 */     } catch (InvalidProtocolBufferException e) {
/* 48 */       throw new GeneralSecurityException("invalid keyset");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final KeysetHandle read(KeysetReader reader) throws GeneralSecurityException, IOException {
/* 58 */     Keyset keyset = reader.read();
/* 59 */     validate(keyset);
/* 60 */     return KeysetHandle.fromKeyset(keyset);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void validate(Keyset keyset) throws GeneralSecurityException {
/* 69 */     for (Keyset.Key key : keyset.getKeyList()) {
/* 70 */       if (key.getKeyData().getKeyMaterialType() == KeyData.KeyMaterialType.UNKNOWN_KEYMATERIAL || key
/* 71 */         .getKeyData().getKeyMaterialType() == KeyData.KeyMaterialType.SYMMETRIC || key
/* 72 */         .getKeyData().getKeyMaterialType() == KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE)
/* 73 */         throw new GeneralSecurityException("keyset contains secret key material"); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\NoSecretKeysetHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */