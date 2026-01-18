/*     */ package com.hypixel.hytale.server.core.universe.world.meta.state;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Supplier;
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
/*     */ public class BlockMapMarkerData
/*     */ {
/*     */   public static final BuilderCodec<BlockMapMarkerData> CODEC;
/*     */   private Vector3i position;
/*     */   private String name;
/*     */   private String icon;
/*     */   private String markerId;
/*     */   
/*     */   static {
/*  92 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockMapMarkerData.class, BlockMapMarkerData::new).append(new KeyedCodec("Position", (Codec)Vector3i.CODEC), (o, v) -> o.position = v, o -> o.position).add()).append(new KeyedCodec("Name", (Codec)Codec.STRING), (o, v) -> o.name = v, o -> o.name).add()).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (o, v) -> o.icon = v, o -> o.icon).add()).append(new KeyedCodec("MarkerId", (Codec)Codec.STRING), (o, v) -> o.markerId = v, o -> o.markerId).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMapMarkerData() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMapMarkerData(Vector3i position, String name, String icon, String markerId) {
/* 103 */     this.position = position;
/* 104 */     this.name = name;
/* 105 */     this.icon = icon;
/* 106 */     this.markerId = markerId;
/*     */   }
/*     */   
/*     */   public Vector3i getPosition() {
/* 110 */     return this.position;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 114 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 118 */     return this.icon;
/*     */   }
/*     */   
/*     */   public String getMarkerId() {
/* 122 */     return this.markerId;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\state\BlockMapMarkersResource$BlockMapMarkerData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */