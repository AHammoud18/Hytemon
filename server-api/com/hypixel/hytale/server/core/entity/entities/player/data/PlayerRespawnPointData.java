/*    */ package com.hypixel.hytale.server.core.entity.entities.player.data;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*    */ 
/*    */ public final class PlayerRespawnPointData
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<PlayerRespawnPointData> CODEC;
/*    */   private Vector3i blockPosition;
/*    */   private Vector3d respawnPosition;
/*    */   private String name;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlayerRespawnPointData.class, PlayerRespawnPointData::new).append(new KeyedCodec("BlockPosition", (Codec)Vector3i.CODEC), (respawnPointData, vector3i) -> respawnPointData.blockPosition = vector3i, respawnPointData -> respawnPointData.blockPosition).documentation("The position of the respawn block.").add()).append(new KeyedCodec("RespawnPosition", (Codec)Vector3d.CODEC), (respawnPointData, vector3f) -> respawnPointData.respawnPosition = vector3f, respawnPointData -> respawnPointData.respawnPosition).documentation("The position at which the player will respawn.").add()).append(new KeyedCodec("Name", (Codec)Codec.STRING), (respawnPointData, s) -> respawnPointData.name = s, respawnPointData -> respawnPointData.name).documentation("The name of the respawn point.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerRespawnPointData(@Nonnull Vector3i blockPosition, @Nonnull Vector3d respawnPosition, @Nonnull String name) {
/* 42 */     this.blockPosition = blockPosition;
/* 43 */     this.respawnPosition = respawnPosition;
/* 44 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   private PlayerRespawnPointData() {}
/*    */   
/*    */   public Vector3i getBlockPosition() {
/* 51 */     return this.blockPosition;
/*    */   }
/*    */   
/*    */   public Vector3d getRespawnPosition() {
/* 55 */     return this.respawnPosition;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 59 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(@Nonnull String name) {
/* 63 */     this.name = name;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\data\PlayerRespawnPointData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */