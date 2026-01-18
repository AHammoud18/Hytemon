/*    */ package com.hypixel.hytale.server.core.auth;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.HostAddress;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerAuthentication
/*    */ {
/*    */   public static final int MAX_REFERRAL_DATA_SIZE = 4096;
/*    */   private UUID uuid;
/*    */   private String username;
/*    */   private byte[] referralData;
/*    */   private HostAddress referralSource;
/*    */   
/*    */   public PlayerAuthentication() {}
/*    */   
/*    */   public PlayerAuthentication(@Nonnull UUID uuid, @Nonnull String username) {
/* 25 */     this.uuid = uuid;
/* 26 */     this.username = username;
/*    */   }
/*    */   @Nonnull
/*    */   public String getUsername() {
/* 30 */     if (this.username == null) {
/* 31 */       throw new UnsupportedOperationException("Username not set - incomplete authentication");
/*    */     }
/* 33 */     return this.username;
/*    */   }
/*    */   @Nonnull
/*    */   public UUID getUuid() {
/* 37 */     if (this.uuid == null) {
/* 38 */       throw new UnsupportedOperationException("UUID not set - incomplete authentication");
/*    */     }
/* 40 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public void setUsername(@Nonnull String username) {
/* 44 */     this.username = username;
/*    */   }
/*    */   
/*    */   public void setUuid(@Nonnull UUID uuid) {
/* 48 */     this.uuid = uuid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public byte[] getReferralData() {
/* 58 */     return this.referralData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setReferralData(@Nullable byte[] referralData) {
/* 68 */     if (referralData != null && referralData.length > 4096) {
/* 69 */       throw new IllegalArgumentException("Referral data exceeds maximum size of 4096 bytes (got " + referralData.length + ")");
/*    */     }
/*    */ 
/*    */     
/* 73 */     this.referralData = referralData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public HostAddress getReferralSource() {
/* 83 */     return this.referralSource;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setReferralSource(@Nullable HostAddress referralSource) {
/* 92 */     this.referralSource = referralSource;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\PlayerAuthentication.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */