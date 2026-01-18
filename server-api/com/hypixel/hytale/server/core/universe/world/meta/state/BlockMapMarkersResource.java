/*     */ package com.hypixel.hytale.server.core.universe.world.meta.state;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.math.block.BlockUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
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
/*     */ public class BlockMapMarkersResource
/*     */   implements Resource<ChunkStore>
/*     */ {
/*     */   public static final BuilderCodec<BlockMapMarkersResource> CODEC;
/*     */   
/*     */   static {
/*  39 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockMapMarkersResource.class, BlockMapMarkersResource::new).append(new KeyedCodec("Markers", (Codec)new MapCodec((Codec)BlockMapMarkerData.CODEC, HashMap::new), true), (o, markersMap) -> { if (markersMap == null) return;  for (Map.Entry<String, BlockMapMarkerData> entry : (Iterable<Map.Entry<String, BlockMapMarkerData>>)markersMap.entrySet()) o.markers.put(Long.valueOf(entry.getKey()).longValue(), entry.getValue());  }o -> { HashMap<String, BlockMapMarkerData> returnMap = new HashMap<>(o.markers.size()); ObjectIterator<Map.Entry<Long, BlockMapMarkerData>> objectIterator = o.markers.entrySet().iterator(); while (objectIterator.hasNext()) { Map.Entry<Long, BlockMapMarkerData> entry = objectIterator.next(); returnMap.put(String.valueOf(entry.getKey()), entry.getValue()); }  return (Function)returnMap; }).add()).build();
/*     */   }
/*  41 */   private Long2ObjectMap<BlockMapMarkerData> markers = (Long2ObjectMap<BlockMapMarkerData>)new Long2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMapMarkersResource(Long2ObjectMap<BlockMapMarkerData> markers) {
/*  47 */     this.markers = markers;
/*     */   }
/*     */   
/*     */   public static ResourceType<ChunkStore, BlockMapMarkersResource> getResourceType() {
/*  51 */     return BlockModule.get().getBlockMapMarkersResourceType();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Long2ObjectMap<BlockMapMarkerData> getMarkers() {
/*  56 */     return this.markers;
/*     */   }
/*     */   
/*     */   public void addMarker(@Nonnull Vector3i position, @Nonnull String name, @Nonnull String icon) {
/*  60 */     long key = BlockUtil.pack(position);
/*  61 */     this.markers.put(key, new BlockMapMarkerData(position, name, icon, UUID.randomUUID().toString()));
/*     */   }
/*     */   
/*     */   public void removeMarker(@Nonnull Vector3i position) {
/*  65 */     long key = BlockUtil.pack(position);
/*  66 */     this.markers.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public Resource<ChunkStore> clone() {
/*  71 */     return new BlockMapMarkersResource((Long2ObjectMap<BlockMapMarkerData>)new Long2ObjectOpenHashMap(this.markers));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockMapMarkersResource() {}
/*     */ 
/*     */   
/*     */   public static class BlockMapMarkerData
/*     */   {
/*     */     public static final BuilderCodec<BlockMapMarkerData> CODEC;
/*     */     
/*     */     private Vector3i position;
/*     */     
/*     */     private String name;
/*     */     
/*     */     private String icon;
/*     */     
/*     */     private String markerId;
/*     */ 
/*     */     
/*     */     static {
/*  92 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockMapMarkerData.class, BlockMapMarkerData::new).append(new KeyedCodec("Position", (Codec)Vector3i.CODEC), (o, v) -> o.position = v, o -> o.position).add()).append(new KeyedCodec("Name", (Codec)Codec.STRING), (o, v) -> o.name = v, o -> o.name).add()).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (o, v) -> o.icon = v, o -> o.icon).add()).append(new KeyedCodec("MarkerId", (Codec)Codec.STRING), (o, v) -> o.markerId = v, o -> o.markerId).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BlockMapMarkerData() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public BlockMapMarkerData(Vector3i position, String name, String icon, String markerId) {
/* 103 */       this.position = position;
/* 104 */       this.name = name;
/* 105 */       this.icon = icon;
/* 106 */       this.markerId = markerId;
/*     */     }
/*     */     
/*     */     public Vector3i getPosition() {
/* 110 */       return this.position;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 114 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getIcon() {
/* 118 */       return this.icon;
/*     */     }
/*     */     
/*     */     public String getMarkerId() {
/* 122 */       return this.markerId;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\state\BlockMapMarkersResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */