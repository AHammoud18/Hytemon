/*   */ package com.hypixel.hytale.server.core.universe.world.worldgen.provider;
/*   */ 
/*   */ import com.hypixel.hytale.codec.lookup.BuilderCodecMapCodec;
/*   */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*   */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
/*   */ 
/*   */ public interface IWorldGenProvider {
/* 8 */   public static final BuilderCodecMapCodec<IWorldGenProvider> CODEC = new BuilderCodecMapCodec("Type", true);
/*   */   
/*   */   IWorldGen getGenerator() throws WorldGenLoadException;
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\provider\IWorldGenProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */