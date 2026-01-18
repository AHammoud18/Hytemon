/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.codec.Vector3iArrayCodec;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolShowAnchor;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.BlockEntity;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Path;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class PrefabEditingMetadata
/*     */ {
/*     */   private static final float PREFAB_ANCHOR_ENTITY_SCALE = 2.1F;
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PrefabEditingMetadata> CODEC;
/*     */   private UUID uuid;
/*     */   private Path prefabPath;
/*     */   private Vector3i minPoint;
/*     */   private Vector3i maxPoint;
/*     */   private Vector3i anchorPoint;
/*     */   private Vector3i pastePosition;
/*     */   @Nullable
/*     */   private UUID anchorEntityUuid;
/*     */   private Vector3i anchorEntityPosition;
/*     */   private Vector3i originalFileAnchor;
/*     */   
/*     */   static {
/*  86 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PrefabEditingMetadata.class, PrefabEditingMetadata::new).append(new KeyedCodec("Path", (Codec)Codec.PATH), (o, path) -> o.prefabPath = path, o -> o.prefabPath).add()).append(new KeyedCodec("MinPoint", (Codec)new Vector3iArrayCodec()), (o, minPoint) -> o.minPoint = minPoint, o -> o.minPoint).add()).append(new KeyedCodec("MaxPoint", (Codec)new Vector3iArrayCodec()), (o, maxPoint) -> o.maxPoint = maxPoint, o -> o.maxPoint).add()).append(new KeyedCodec("AnchorPoint", (Codec)new Vector3iArrayCodec()), (o, anchorPoint) -> o.anchorPoint = anchorPoint, o -> o.anchorPoint).add()).append(new KeyedCodec("PastePosition", (Codec)new Vector3iArrayCodec()), (o, pastePosition) -> o.pastePosition = pastePosition, o -> o.pastePosition).add()).append(new KeyedCodec("AnchorEntityUuid", (Codec)Codec.UUID_STRING, false), (o, anchorEntityUuid) -> o.anchorEntityUuid = anchorEntityUuid, o -> o.anchorEntityUuid).add()).append(new KeyedCodec("AnchorEntityPosition", (Codec)new Vector3iArrayCodec(), false), (o, anchorEntityPosition) -> o.anchorEntityPosition = anchorEntityPosition, o -> o.anchorEntityPosition).add()).append(new KeyedCodec("Uuid", (Codec)Codec.UUID_STRING), (o, uuid) -> o.uuid = uuid, o -> o.uuid).add()).append(new KeyedCodec("Dirty", (Codec)Codec.BOOLEAN, true), (o, dirty) -> o.dirty = dirty.booleanValue(), o -> Boolean.valueOf(o.dirty)).add()).build();
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
/*     */   private boolean dirty = false;
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
/*     */   public PrefabEditingMetadata(@Nonnull Path prefabPath, @Nonnull Vector3i minPoint, @Nonnull Vector3i maxPoint, @Nonnull Vector3i anchorPoint, @Nonnull Vector3i pastePosition, @Nonnull World world) {
/* 168 */     this.prefabPath = prefabPath;
/* 169 */     this.minPoint = minPoint;
/* 170 */     this.maxPoint = maxPoint;
/* 171 */     if (minPoint.x > maxPoint.x) throw new IllegalStateException("minX must be less than or equal to maxX: " + String.valueOf(prefabPath)); 
/* 172 */     if (minPoint.y > maxPoint.y) throw new IllegalStateException("minY must be less than or equal to maxY: " + String.valueOf(prefabPath)); 
/* 173 */     if (minPoint.z > maxPoint.z) throw new IllegalStateException("minZ must be less than or equal to maxZ: " + String.valueOf(prefabPath)); 
/* 174 */     this.uuid = UUID.randomUUID();
/*     */     
/* 176 */     this.anchorPoint = anchorPoint;
/* 177 */     this.pastePosition = pastePosition;
/*     */ 
/*     */ 
/*     */     
/* 181 */     this.originalFileAnchor = new Vector3i(anchorPoint.x - pastePosition.x, anchorPoint.y - pastePosition.y, anchorPoint.z - pastePosition.z);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     createAnchorEntityAt(pastePosition, world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createAnchorEntityAt(@Nonnull Vector3i position, @Nonnull World world) {
/* 201 */     this.anchorEntityPosition = position.clone();
/*     */     
/* 203 */     Store<EntityStore> store = world.getEntityStore().getStore();
/*     */ 
/*     */     
/* 206 */     if (this.anchorEntityUuid != null) {
/* 207 */       Ref<EntityStore> entityReference = ((EntityStore)store.getExternalData()).getRefFromUUID(this.anchorEntityUuid);
/*     */       
/* 209 */       if (entityReference != null && entityReference.isValid()) {
/* 210 */         world.execute(() -> store.removeEntity(entityReference, RemoveReason.REMOVE));
/*     */       }
/*     */     } 
/*     */     
/* 214 */     TimeResource timeResource = (TimeResource)store.getResource(TimeResource.getResourceType());
/*     */ 
/*     */     
/* 217 */     Holder<EntityStore> blockEntityHolder = BlockEntity.assembleDefaultBlockEntity(timeResource, "Editor_Anchor", position.toVector3d().add(0.5D, 0.0D, 0.5D));
/*     */ 
/*     */     
/* 220 */     blockEntityHolder.addComponent(Intangible.getComponentType(), (Component)Intangible.INSTANCE);
/*     */ 
/*     */     
/* 223 */     blockEntityHolder.addComponent(PrefabAnchor.getComponentType(), PrefabAnchor.INSTANCE);
/*     */ 
/*     */     
/* 226 */     blockEntityHolder.addComponent(EntityScaleComponent.getComponentType(), (Component)new EntityScaleComponent(2.1F));
/*     */ 
/*     */     
/* 229 */     this.anchorEntityUuid = ((UUIDComponent)blockEntityHolder.ensureAndGetComponent(UUIDComponent.getComponentType())).getUuid();
/*     */ 
/*     */     
/* 232 */     world.execute(() -> store.addEntity(blockEntityHolder, AddReason.SPAWN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrefabPath(@Nonnull Path prefabPath) {
/* 242 */     this.prefabPath = prefabPath;
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
/*     */   public void setAnchorPoint(@Nonnull Vector3i newEntityPosition, @Nonnull World world) {
/* 255 */     int deltaX = newEntityPosition.x - this.anchorEntityPosition.x;
/* 256 */     int deltaY = newEntityPosition.y - this.anchorEntityPosition.y;
/* 257 */     int deltaZ = newEntityPosition.z - this.anchorEntityPosition.z;
/*     */ 
/*     */     
/* 260 */     this.anchorPoint = new Vector3i(this.anchorPoint.x + deltaX, this.anchorPoint.y + deltaY, this.anchorPoint.z + deltaZ);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     createAnchorEntityAt(newEntityPosition, world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateAnchorEntity(@Nonnull World world) {
/* 277 */     if (this.anchorEntityPosition != null) {
/* 278 */       createAnchorEntityAt(this.anchorEntityPosition, world);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAnchorHighlightingPacket(@Nonnull PacketHandler displayTo) {
/* 289 */     displayTo.writeNoCache((Packet)new BuilderToolShowAnchor(this.anchorEntityPosition.x, this.anchorEntityPosition.y, this.anchorEntityPosition.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocationWithinPrefabBoundingBox(@Nonnull Vector3i location) {
/* 299 */     return (location.x >= (getMinPoint()).x && location.x <= (getMaxPoint()).x && location.y >= 
/* 300 */       (getMinPoint()).y && location.y <= (getMaxPoint()).y && location.z >= 
/* 301 */       (getMinPoint()).z && location.z <= (getMaxPoint()).z);
/*     */   }
/*     */   
/*     */   void setMinPoint(Vector3i minPoint) {
/* 305 */     this.minPoint = minPoint;
/*     */   }
/*     */   
/*     */   void setMaxPoint(Vector3i maxPoint) {
/* 309 */     this.maxPoint = maxPoint;
/*     */   }
/*     */   
/*     */   public Vector3i getAnchorPoint() {
/* 313 */     return this.anchorPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3i getPastePosition() {
/* 320 */     return this.pastePosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3i getOriginalFileAnchor() {
/* 328 */     return this.originalFileAnchor;
/*     */   }
/*     */   
/*     */   public Path getPrefabPath() {
/* 332 */     return this.prefabPath;
/*     */   }
/*     */   
/*     */   public Vector3i getMinPoint() {
/* 336 */     return this.minPoint;
/*     */   }
/*     */   
/*     */   public Vector3i getMaxPoint() {
/* 340 */     return this.maxPoint;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UUID getAnchorEntityUuid() {
/* 345 */     return this.anchorEntityUuid;
/*     */   }
/*     */   
/*     */   public Vector3i getAnchorEntityPosition() {
/* 349 */     return this.anchorEntityPosition;
/*     */   }
/*     */   
/*     */   public UUID getUuid() {
/* 353 */     return this.uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 360 */     return this.dirty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 369 */     this.dirty = dirty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 376 */     return (this.prefabPath != null && this.prefabPath.getFileSystem() != FileSystems.getDefault());
/*     */   }
/*     */   
/*     */   private PrefabEditingMetadata() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabEditingMetadata.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */