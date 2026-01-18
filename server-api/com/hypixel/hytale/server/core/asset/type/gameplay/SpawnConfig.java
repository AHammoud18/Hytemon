/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.particle.config.WorldParticle;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class SpawnConfig
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<SpawnConfig> CODEC;
/*    */   protected WorldParticle[] firstSpawnParticles;
/*    */   protected WorldParticle[] spawnParticles;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpawnConfig.class, SpawnConfig::new).appendInherited(new KeyedCodec("FirstSpawnParticles", (Codec)new ArrayCodec((Codec)WorldParticle.CODEC, x$0 -> new WorldParticle[x$0])), (o, v) -> o.firstSpawnParticles = v, o -> o.firstSpawnParticles, (o, p) -> o.firstSpawnParticles = p.firstSpawnParticles).add()).appendInherited(new KeyedCodec("SpawnParticles", (Codec)new ArrayCodec((Codec)WorldParticle.CODEC, x$0 -> new WorldParticle[x$0])), (o, v) -> o.spawnParticles = v, o -> o.spawnParticles, (o, p) -> o.spawnParticles = p.spawnParticles).add()).build();
/*    */   }
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
/*    */   @Nullable
/*    */   public WorldParticle[] getFirstSpawnParticles() {
/* 52 */     return this.firstSpawnParticles;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public WorldParticle[] getSpawnParticles() {
/* 60 */     return this.spawnParticles;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\SpawnConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */