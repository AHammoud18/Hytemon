/*    */ package com.hypixel.hytale.server.core.universe.world.spawn;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IndividualSpawnProvider
/*    */   implements ISpawnProvider
/*    */ {
/*    */   @Nonnull
/*    */   public static BuilderCodec<IndividualSpawnProvider> CODEC;
/*    */   private Transform[] spawnPoints;
/*    */   
/*    */   static {
/* 29 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(IndividualSpawnProvider.class, IndividualSpawnProvider::new).documentation("A spawn provider that selects a spawn point from a list based on the player being spawned in. This gives random but consistent spawn points for players.")).append(new KeyedCodec("SpawnPoints", (Codec)new ArrayCodec((Codec)Transform.CODEC, x$0 -> new Transform[x$0])), (o, i) -> o.spawnPoints = i, o -> o.spawnPoints).documentation("The list of spawn points to select from.").add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public IndividualSpawnProvider() {}
/*    */   
/*    */   public IndividualSpawnProvider(Transform spawnPoint) {
/* 36 */     this.spawnPoints = new Transform[1];
/* 37 */     this.spawnPoints[0] = spawnPoint;
/*    */   }
/*    */   
/*    */   public IndividualSpawnProvider(Transform[] spawnPoints) {
/* 41 */     this.spawnPoints = spawnPoints;
/*    */   }
/*    */ 
/*    */   
/*    */   public Transform getSpawnPoint(@Nonnull World world, @Nonnull UUID uuid) {
/* 46 */     return this.spawnPoints[Math.abs((int)HashUtil.hashUuid(uuid)) % this.spawnPoints.length];
/*    */   }
/*    */ 
/*    */   
/*    */   public Transform[] getSpawnPoints() {
/* 51 */     return this.spawnPoints;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Transform getFirstSpawnPoint() {
/* 56 */     return (this.spawnPoints.length == 0) ? null : this.spawnPoints[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isWithinSpawnDistance(@Nonnull Vector3d position, double distance) {
/* 61 */     double distanceSquared = distance * distance;
/* 62 */     for (Transform point : this.spawnPoints) {
/* 63 */       if (position.distanceSquaredTo(point.getPosition()) < distanceSquared) {
/* 64 */         return true;
/*    */       }
/*    */     } 
/* 67 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\spawn\IndividualSpawnProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */