/*    */ package com.hypixel.hytale.builtin.adventure.memories.temple;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*    */ import java.util.function.Supplier;
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
/*    */ public class ForgottenTempleConfig
/*    */ {
/*    */   public static final BuilderCodec<ForgottenTempleConfig> CODEC;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ForgottenTempleConfig.class, ForgottenTempleConfig::new).append(new KeyedCodec("MinYRespawn", (Codec)Codec.DOUBLE), (config, o) -> config.minYRespawn = o.doubleValue(), config -> Double.valueOf(config.minYRespawn)).documentation("The Y at which players are teleported back to spawn.").add()).append(new KeyedCodec("RespawnSound", (Codec)Codec.STRING), (config, o) -> config.respawnSound = o, config -> config.respawnSound).documentation("The sound ID to play when players respawn in the temple.").add()).build();
/*    */   }
/* 26 */   private double minYRespawn = 5.0D;
/*    */   private String respawnSound;
/*    */   
/*    */   public double getMinYRespawn() {
/* 30 */     return this.minYRespawn;
/*    */   }
/*    */   
/*    */   public String getRespawnSound() {
/* 34 */     return this.respawnSound;
/*    */   }
/*    */   
/*    */   public int getRespawnSoundIndex() {
/* 38 */     if (this.respawnSound == null) {
/* 39 */       return 0;
/*    */     }
/* 41 */     return SoundEvent.getAssetMap().getIndex(this.respawnSound);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\temple\ForgottenTempleConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */