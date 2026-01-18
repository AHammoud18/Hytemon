/*     */ package com.hypixel.hytale.server.core.entity.entities.player.data;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PlayerDeathPositionData
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PlayerDeathPositionData> CODEC;
/*     */   @Nonnull
/*     */   public static final ArrayCodec<PlayerDeathPositionData> ARRAY_CODEC;
/*     */   private String markerId;
/*     */   private Transform transform;
/*     */   private int day;
/*     */   
/*     */   static {
/*  42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlayerDeathPositionData.class, PlayerDeathPositionData::new).append(new KeyedCodec("MarkerId", (Codec)Codec.STRING), (data, value) -> data.markerId = value, data -> data.markerId).documentation("The unique ID of the associated map marker.").add()).append(new KeyedCodec("Transform", (Codec)Transform.CODEC), (data, value) -> data.transform = value, data -> data.transform).documentation("The transform of this death position.").add()).append(new KeyedCodec("Day", (Codec)Codec.INTEGER), (data, value) -> data.day = value.intValue(), data -> Integer.valueOf(data.day)).documentation("The in-game day in which the player died.").add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     ARRAY_CODEC = new ArrayCodec((Codec)CODEC, x$0 -> new PlayerDeathPositionData[x$0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlayerDeathPositionData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerDeathPositionData(@Nonnull String markerId, @Nonnull Transform transform, int day) {
/*  80 */     this.markerId = markerId;
/*  81 */     this.transform = transform;
/*  82 */     this.day = day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMarkerId() {
/*  89 */     return this.markerId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform getTransform() {
/*  96 */     return this.transform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDay() {
/* 103 */     return this.day;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\data\PlayerDeathPositionData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */