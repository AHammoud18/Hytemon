/*   */ package com.hypixel.hytale.server.core.universe.world.worldmap.provider;
/*   */ 
/*   */ import com.hypixel.hytale.codec.lookup.BuilderCodecMapCodec;
/*   */ import com.hypixel.hytale.server.core.universe.world.World;
/*   */ import com.hypixel.hytale.server.core.universe.world.worldmap.IWorldMap;
/*   */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapLoadException;
/*   */ 
/*   */ public interface IWorldMapProvider {
/* 9 */   public static final BuilderCodecMapCodec<IWorldMapProvider> CODEC = new BuilderCodecMapCodec("Type", true);
/*   */   
/*   */   IWorldMap getGenerator(World paramWorld) throws WorldMapLoadException;
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\provider\IWorldMapProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */