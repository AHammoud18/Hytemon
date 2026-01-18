/*     */ package com.hypixel.hytale.builtin.adventure.teleporter.component;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.teleporter.TeleporterPlugin;
/*     */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*     */ import com.hypixel.hytale.builtin.teleport.Warp;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.wordlist.WordList;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class Teleporter
/*     */   implements Component<ChunkStore>
/*     */ {
/*     */   public static final BuilderCodec<Teleporter> CODEC;
/*     */   @Nullable
/*     */   private UUID worldUuid;
/*     */   @Nullable
/*     */   private Transform transform;
/*     */   
/*     */   static {
/*  68 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Teleporter.class, Teleporter::new).append(new KeyedCodec("World", (Codec)Codec.UUID_BINARY), (teleporter, uuid) -> teleporter.worldUuid = uuid, teleporter -> teleporter.worldUuid).add()).append(new KeyedCodec("Transform", (Codec)Transform.CODEC), (teleporter, transform) -> teleporter.transform = transform, teleporter -> teleporter.transform).add()).append(new KeyedCodec("Relative", (Codec)Codec.BYTE), (teleporter, b) -> teleporter.relativeMask = b.byteValue(), teleporter -> Byte.valueOf(teleporter.relativeMask)).add()).append(new KeyedCodec("Warp", (Codec)Codec.STRING), (teleporter, s) -> teleporter.warp = s, teleporter -> teleporter.warp).add()).append(new KeyedCodec("OwnedWarp", (Codec)Codec.STRING), (teleporter, s) -> teleporter.ownedWarp = s, teleporter -> teleporter.ownedWarp).add()).append(new KeyedCodec("IsCustomName", (Codec)Codec.BOOLEAN), (teleporter, s) -> teleporter.isCustomName = s.booleanValue(), teleporter -> Boolean.valueOf(teleporter.isCustomName)).add()).append(new KeyedCodec("WarpNameWordList", (Codec)Codec.STRING), (teleporter, s) -> teleporter.warpNameWordListKey = s, teleporter -> teleporter.warpNameWordListKey).documentation("The ID of the Word list to select default warp names from").add()).build();
/*     */   }
/*     */   public static ComponentType<ChunkStore, Teleporter> getComponentType() {
/*  71 */     return TeleporterPlugin.get().getTeleporterComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private byte relativeMask = 0;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String warp;
/*     */   
/*     */   @Deprecated
/*     */   private String ownedWarp;
/*     */   
/*     */   private boolean isCustomName;
/*     */   
/*     */   private String warpNameWordListKey;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getWorldUuid() {
/*  97 */     return this.worldUuid;
/*     */   }
/*     */   
/*     */   public void setWorldUuid(@Nullable UUID worldUuid) {
/* 101 */     this.worldUuid = worldUuid;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Transform getTransform() {
/* 106 */     return this.transform;
/*     */   }
/*     */   
/*     */   public void setTransform(@Nullable Transform transform) {
/* 110 */     this.transform = transform;
/*     */   }
/*     */   
/*     */   public byte getRelativeMask() {
/* 114 */     return this.relativeMask;
/*     */   }
/*     */   
/*     */   public void setRelativeMask(byte relativeMask) {
/* 118 */     this.relativeMask = relativeMask;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getWarp() {
/* 123 */     return this.warp;
/*     */   }
/*     */   
/*     */   public void setWarp(@Nullable String warp) {
/* 127 */     this.warp = (warp == null || warp.isEmpty()) ? null : warp;
/*     */   }
/*     */   
/*     */   public String getOwnedWarp() {
/* 131 */     return this.ownedWarp;
/*     */   }
/*     */   
/*     */   public void setOwnedWarp(String ownedWarp) {
/* 135 */     this.ownedWarp = ownedWarp;
/*     */   }
/*     */   
/*     */   public boolean hasOwnedWarp() {
/* 139 */     return (this.ownedWarp != null && !this.ownedWarp.isEmpty());
/*     */   }
/*     */   
/*     */   public void setWarpNameWordListKey(String warpNameWordListKey) {
/* 143 */     this.warpNameWordListKey = warpNameWordListKey;
/*     */   }
/*     */   
/*     */   public boolean isCustomName() {
/* 147 */     return this.isCustomName;
/*     */   }
/*     */   
/*     */   public void setIsCustomName(boolean customName) {
/* 151 */     this.isCustomName = customName;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getWarpNameWordListKey() {
/* 156 */     return this.warpNameWordListKey;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WordList getWarpNameWordList() {
/* 161 */     return WordList.getWordList(this.warpNameWordListKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 166 */     if (this.warp != null && !this.warp.isEmpty()) return (TeleportPlugin.get().getWarps().get(this.warp.toLowerCase()) != null); 
/* 167 */     if (this.transform != null) {
/* 168 */       if (this.worldUuid != null) {
/* 169 */         return (Universe.get().getWorld(this.worldUuid) != null);
/*     */       }
/* 171 */       return true;
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/* 179 */     Teleporter teleporter = new Teleporter();
/* 180 */     teleporter.worldUuid = this.worldUuid;
/* 181 */     teleporter.transform = (this.transform != null) ? this.transform.clone() : null;
/* 182 */     teleporter.relativeMask = this.relativeMask;
/* 183 */     teleporter.warp = this.warp;
/* 184 */     teleporter.ownedWarp = this.ownedWarp;
/* 185 */     teleporter.isCustomName = this.isCustomName;
/* 186 */     teleporter.warpNameWordListKey = this.warpNameWordListKey;
/* 187 */     return teleporter;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Teleport toTeleport(@Nonnull Vector3d currentPosition, @Nonnull Vector3f currentRotation, @Nonnull Vector3i blockPosition) {
/* 193 */     if (this.warp != null && !this.warp.isEmpty()) {
/* 194 */       Warp targetWarp = (Warp)TeleportPlugin.get().getWarps().get(this.warp.toLowerCase());
/* 195 */       return (targetWarp != null) ? targetWarp.toTeleport() : null;
/*     */     } 
/*     */     
/* 198 */     if (this.transform != null) {
/* 199 */       if (this.worldUuid != null) {
/* 200 */         World world = Universe.get().getWorld(this.worldUuid);
/* 201 */         if (world != null) {
/* 202 */           if (this.relativeMask != 0) {
/* 203 */             Transform teleportTransform = this.transform.clone();
/* 204 */             Transform.applyMaskedRelativeTransform(teleportTransform, this.relativeMask, currentPosition, currentRotation, blockPosition);
/* 205 */             return new Teleport(world, teleportTransform);
/*     */           } 
/* 207 */           return new Teleport(world, this.transform);
/*     */         } 
/*     */       } 
/* 210 */       if (this.relativeMask != 0) {
/* 211 */         Transform teleportTransform = this.transform.clone();
/* 212 */         Transform.applyMaskedRelativeTransform(teleportTransform, this.relativeMask, currentPosition, currentRotation, blockPosition);
/* 213 */         return new Teleport(teleportTransform);
/*     */       } 
/* 215 */       return new Teleport(this.transform);
/*     */     } 
/* 217 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\teleporter\component\Teleporter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */