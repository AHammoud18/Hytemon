/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.ItemPullbackConfiguration;
/*    */ import com.hypixel.hytale.protocol.Vector3f;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*    */ public class ItemPullbackConfig
/*    */   implements NetworkSerializable<ItemPullbackConfiguration>
/*    */ {
/*    */   public static final BuilderCodec<ItemPullbackConfig> CODEC;
/*    */   @Nullable
/*    */   protected Vector3f leftOffsetOverride;
/*    */   @Nullable
/*    */   protected Vector3f leftRotationOverride;
/*    */   @Nullable
/*    */   protected Vector3f rightOffsetOverride;
/*    */   @Nullable
/*    */   protected Vector3f rightRotationOverride;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemPullbackConfig.class, ItemPullbackConfig::new).append(new KeyedCodec("LeftOffsetOverride", (Codec)Vector3d.AS_ARRAY_CODEC), (pullbackConfig, offOverride) -> pullbackConfig.leftOffsetOverride = (offOverride == null) ? null : new Vector3f((float)offOverride.getX(), (float)offOverride.getY(), (float)offOverride.getZ()), pullbackConfig -> (pullbackConfig.leftOffsetOverride == null) ? null : new Vector3d(pullbackConfig.leftOffsetOverride.x, pullbackConfig.leftOffsetOverride.y, pullbackConfig.leftOffsetOverride.z)).add()).append(new KeyedCodec("LeftRotationOverride", (Codec)Vector3d.AS_ARRAY_CODEC), (pullbackConfig, rotOverride) -> pullbackConfig.leftRotationOverride = (rotOverride == null) ? null : new Vector3f((float)rotOverride.getX(), (float)rotOverride.getY(), (float)rotOverride.getZ()), pullbackConfig -> (pullbackConfig.leftRotationOverride == null) ? null : new Vector3d(pullbackConfig.leftRotationOverride.x, pullbackConfig.leftRotationOverride.y, pullbackConfig.leftRotationOverride.z)).add()).append(new KeyedCodec("RightOffsetOverride", (Codec)Vector3d.AS_ARRAY_CODEC), (pullbackConfig, offOverride) -> pullbackConfig.rightOffsetOverride = (offOverride == null) ? null : new Vector3f((float)offOverride.getX(), (float)offOverride.getY(), (float)offOverride.getZ()), pullbackConfig -> (pullbackConfig.rightOffsetOverride == null) ? null : new Vector3d(pullbackConfig.rightOffsetOverride.x, pullbackConfig.rightOffsetOverride.y, pullbackConfig.rightOffsetOverride.z)).add()).append(new KeyedCodec("RightRotationOverride", (Codec)Vector3d.AS_ARRAY_CODEC), (pullbackConfig, rotOverride) -> pullbackConfig.rightRotationOverride = (rotOverride == null) ? null : new Vector3f((float)rotOverride.getX(), (float)rotOverride.getY(), (float)rotOverride.getZ()), pullbackConfig -> (pullbackConfig.rightRotationOverride == null) ? null : new Vector3d(pullbackConfig.rightRotationOverride.x, pullbackConfig.rightRotationOverride.y, pullbackConfig.rightRotationOverride.z)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ItemPullbackConfig() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemPullbackConfig(Vector3f leftOffsetOverride, Vector3f leftRotationOverride, Vector3f rightOffsetOverride, Vector3f rightRotationOverride) {
/* 52 */     this.leftOffsetOverride = leftOffsetOverride;
/* 53 */     this.leftRotationOverride = leftRotationOverride;
/* 54 */     this.rightOffsetOverride = rightOffsetOverride;
/* 55 */     this.rightRotationOverride = rightRotationOverride;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ItemPullbackConfiguration toPacket() {
/* 61 */     ItemPullbackConfiguration packet = new ItemPullbackConfiguration();
/* 62 */     packet.leftOffsetOverride = this.leftOffsetOverride;
/* 63 */     packet.leftRotationOverride = this.leftRotationOverride;
/* 64 */     packet.rightOffsetOverride = this.rightOffsetOverride;
/* 65 */     packet.rightRotationOverride = this.rightRotationOverride;
/* 66 */     return packet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemPullbackConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */