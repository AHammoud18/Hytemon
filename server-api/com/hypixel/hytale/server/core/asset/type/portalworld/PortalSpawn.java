/*    */ package com.hypixel.hytale.server.core.asset.type.portalworld;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PortalSpawn
/*    */ {
/*    */   public static final BuilderCodec<PortalSpawn> CODEC;
/*    */   
/*    */   static {
/* 60 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PortalSpawn.class, PortalSpawn::new).append(new KeyedCodec("Y", (Codec)Codec.INTEGER), (spawn, o) -> spawn.checkSpawnY = o.intValue(), spawn -> Integer.valueOf(spawn.checkSpawnY)).documentation("The Y height where to start looking for X,Z candidate.").add()).append(new KeyedCodec("ScanHeight", (Codec)Codec.INTEGER), (spawn, o) -> spawn.scanHeight = o.intValue(), spawn -> Integer.valueOf(spawn.scanHeight)).documentation("How many blocks to scan downwards after picking a X,Y,Z candidate.").add()).append(new KeyedCodec("MinRadius", (Codec)Codec.INTEGER), (spawn, o) -> spawn.minRadius = o.intValue(), spawn -> Integer.valueOf(spawn.minRadius)).documentation("Picks a random X,Z point around center at [MinRadius]-[MaxRadius] radius to find chunks.").add()).append(new KeyedCodec("MaxRadius", (Codec)Codec.INTEGER), (spawn, o) -> spawn.maxRadius = o.intValue(), spawn -> Integer.valueOf(spawn.maxRadius)).documentation("Picks a random X,Z point around center at [MinRadius]-[MaxRadius] radius to find chunks.").add()).append(new KeyedCodec("Center", (Codec)Vector3i.CODEC), (spawn, o) -> spawn.center = o, spawn -> spawn.center).documentation("Picks a random X,Z point around [Center] at Radius radius.").add()).append(new KeyedCodec("ChunkDartThrows", (Codec)Codec.INTEGER), (spawn, o) -> spawn.chunkDartThrows = o.intValue(), spawn -> Integer.valueOf(spawn.chunkDartThrows)).documentation("How many attempts at picking a spawn.").add()).append(new KeyedCodec("ChecksPerChunk", (Codec)Codec.INTEGER), (spawn, o) -> spawn.checksPerChunk = o.intValue(), spawn -> Integer.valueOf(spawn.checksPerChunk)).documentation("For every chunk, how many random location checks are done within the chunk.").add()).build();
/*    */   }
/* 62 */   private Vector3i center = Vector3i.ZERO.clone();
/* 63 */   private int scanHeight = 16;
/*    */   private int checkSpawnY;
/*    */   private int minRadius;
/*    */   private int maxRadius;
/* 67 */   private int chunkDartThrows = 20;
/* 68 */   private int checksPerChunk = 5;
/*    */   
/*    */   public Vector3i getCenter() {
/* 71 */     return this.center;
/*    */   }
/*    */   
/*    */   public int getCheckSpawnY() {
/* 75 */     return this.checkSpawnY;
/*    */   }
/*    */   
/*    */   public int getScanHeight() {
/* 79 */     return this.scanHeight;
/*    */   }
/*    */   
/*    */   public int getMinRadius() {
/* 83 */     return this.minRadius;
/*    */   }
/*    */   
/*    */   public int getMaxRadius() {
/* 87 */     return this.maxRadius;
/*    */   }
/*    */   
/*    */   public int getChunkDartThrows() {
/* 91 */     return this.chunkDartThrows;
/*    */   }
/*    */   
/*    */   public int getChecksPerChunk() {
/* 95 */     return this.checksPerChunk;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\portalworld\PortalSpawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */