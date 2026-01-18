/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.provider.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.IWorldMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapLoadException;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.provider.IWorldMapProvider;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenWorldMapProvider
/*    */   implements IWorldMapProvider
/*    */ {
/*    */   public static final String ID = "WorldGen";
/* 18 */   public static final BuilderCodec<WorldGenWorldMapProvider> CODEC = BuilderCodec.builder(WorldGenWorldMapProvider.class, WorldGenWorldMapProvider::new)
/* 19 */     .build();
/*    */ 
/*    */   
/*    */   public IWorldMap getGenerator(@Nonnull World world) throws WorldMapLoadException {
/* 23 */     IWorldGen generator = world.getChunkStore().getGenerator();
/* 24 */     if (generator instanceof IWorldMapProvider) {
/* 25 */       return ((IWorldMapProvider)generator).getGenerator(world);
/*    */     }
/*    */     
/* 28 */     return ChunkWorldMap.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 34 */     return "DisabledWorldMapProvider{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\provider\chunk\WorldGenWorldMapProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */