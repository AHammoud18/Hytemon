/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class RespawnConfig
/*    */ {
/*    */   @Nonnull
/*    */   public static BuilderCodec<RespawnConfig> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RespawnConfig.class, RespawnConfig::new).append(new KeyedCodec("RadiusLimitRespawnPoint", (Codec)Codec.INTEGER), (worldConfig, integer) -> worldConfig.radiusLimitRespawnPoint = integer.intValue(), worldConfig -> Integer.valueOf(worldConfig.radiusLimitRespawnPoint)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("MaxRespawnPointsPerPlayer", (Codec)Codec.INTEGER), (worldConfig, integer) -> worldConfig.maxRespawnPointsPerPlayer = integer.intValue(), worldConfig -> Integer.valueOf(worldConfig.maxRespawnPointsPerPlayer)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 35 */   protected int radiusLimitRespawnPoint = 500;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   protected int maxRespawnPointsPerPlayer = 3;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRadiusLimitRespawnPoint() {
/* 46 */     return this.radiusLimitRespawnPoint;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxRespawnPointsPerPlayer() {
/* 53 */     return this.maxRespawnPointsPerPlayer;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\RespawnConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */