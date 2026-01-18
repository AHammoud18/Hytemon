/*    */ package com.hypixel.hytale.server.core.universe.playerdata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DefaultPlayerStorageProvider
/*    */   implements PlayerStorageProvider {
/*  8 */   public static final DefaultPlayerStorageProvider INSTANCE = new DefaultPlayerStorageProvider();
/*    */   
/*    */   public static final String ID = "Hytale";
/* 11 */   public static final BuilderCodec<DefaultPlayerStorageProvider> CODEC = BuilderCodec.builder(DefaultPlayerStorageProvider.class, () -> INSTANCE)
/* 12 */     .build();
/*    */   
/* 14 */   public static final DiskPlayerStorageProvider DEFAULT = new DiskPlayerStorageProvider();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PlayerStorage getPlayerStorage() {
/* 19 */     return DEFAULT.getPlayerStorage();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 25 */     return "DefaultPlayerStorageProvider{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\playerdata\DefaultPlayerStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */