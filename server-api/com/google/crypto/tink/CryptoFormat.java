/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*    */ import com.google.crypto.tink.proto.Keyset;
/*    */ import com.google.crypto.tink.proto.OutputPrefixType;
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
/*    */ 
/*    */ 
/*    */ public final class CryptoFormat
/*    */ {
/*    */   public static final int NON_RAW_PREFIX_SIZE = 5;
/*    */   public static final int LEGACY_PREFIX_SIZE = 5;
/*    */   public static final byte LEGACY_START_BYTE = 0;
/*    */   public static final int TINK_PREFIX_SIZE = 5;
/*    */   public static final byte TINK_START_BYTE = 1;
/*    */   public static final int RAW_PREFIX_SIZE = 0;
/* 46 */   public static final byte[] RAW_PREFIX = new byte[0];
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
/*    */   public static byte[] getOutputPrefix(Keyset.Key key) throws GeneralSecurityException {
/* 58 */     switch (key.getOutputPrefixType()) {
/*    */       case LEGACY:
/*    */       case CRUNCHY:
/* 61 */         return OutputPrefixUtil.getLegacyOutputPrefix(key.getKeyId()).toByteArray();
/*    */       case TINK:
/* 63 */         return OutputPrefixUtil.getTinkOutputPrefix(key.getKeyId()).toByteArray();
/*    */       case RAW:
/* 65 */         return RAW_PREFIX;
/*    */     } 
/* 67 */     throw new GeneralSecurityException("unknown output prefix type");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\CryptoFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */