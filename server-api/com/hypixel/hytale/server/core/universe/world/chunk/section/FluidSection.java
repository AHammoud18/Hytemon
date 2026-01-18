/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.protocol.CachedPacket;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.world.SetFluids;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.palette.EmptySectionPalette;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.palette.ISectionPalette;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.palette.PaletteTypeEnum;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.io.ByteBufUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class FluidSection
/*     */   implements Component<ChunkStore> {
/*     */   public static ComponentType<ChunkStore, FluidSection> getComponentType() {
/*  36 */     return LegacyModule.get().getFluidSectionComponentType();
/*     */   }
/*     */   
/*     */   public static final int LEVEL_DATA_SIZE = 16384;
/*     */   public static final int VERSION = 0;
/*  41 */   public static final BuilderCodec<FluidSection> CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FluidSection.class, FluidSection::new)
/*  42 */     .versioned())
/*  43 */     .codecVersion(0))
/*  44 */     .append(new KeyedCodec("Data", Codec.BYTE_ARRAY), FluidSection::deserialize, FluidSection::serialize)
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     .add())
/*  50 */     .build();
/*     */   
/*  52 */   private final StampedLock lock = new StampedLock();
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*     */   private boolean loaded = false;
/*  57 */   private ISectionPalette typePalette = (ISectionPalette)EmptySectionPalette.INSTANCE; @Nullable
/*  58 */   private byte[] levelData = null;
/*     */   
/*  60 */   private int nonZeroLevels = 0;
/*     */   @Nonnull
/*  62 */   private IntOpenHashSet changedPositions = new IntOpenHashSet(0);
/*     */   
/*     */   @Nonnull
/*  65 */   private IntOpenHashSet swapChangedPositions = new IntOpenHashSet(0);
/*     */   
/*     */   @Nullable
/*  68 */   private transient SoftReference<CompletableFuture<CachedPacket<SetFluids>>> cachedPacket = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public void preload(int x, int y, int z) {
/*  73 */     this.x = x;
/*  74 */     this.y = y;
/*  75 */     this.z = z;
/*     */   }
/*     */   
/*     */   public void load(int x, int y, int z) {
/*  79 */     this.x = x;
/*  80 */     this.y = y;
/*  81 */     this.z = z;
/*  82 */     this.loaded = true;
/*     */   }
/*     */   
/*     */   private boolean setFluidRaw(int x, int y, int z, int fluidId) {
/*  86 */     return setFluidRaw(ChunkUtil.indexBlock(x, y, z), fluidId);
/*     */   }
/*     */   
/*     */   private boolean setFluidRaw(int index, int fluidId) {
/*  90 */     ISectionPalette.SetResult result = this.typePalette.set(index, fluidId);
/*  91 */     if (result == ISectionPalette.SetResult.REQUIRES_PROMOTE) {
/*  92 */       this.typePalette = this.typePalette.promote();
/*  93 */       result = this.typePalette.set(index, fluidId);
/*  94 */       if (result != ISectionPalette.SetResult.ADDED_OR_REMOVED) {
/*  95 */         throw new IllegalStateException("Promoted fluid section failed to correctly add the new fluid");
/*     */       }
/*     */     }
/*  98 */     else if (this.typePalette.shouldDemote()) {
/*  99 */       this.typePalette = this.typePalette.demote();
/*     */     } 
/*     */     
/* 102 */     return (result != ISectionPalette.SetResult.UNCHANGED);
/*     */   }
/*     */   
/*     */   public boolean setFluid(int x, int y, int z, @Nonnull Fluid fluid, byte level) {
/* 106 */     return setFluid(ChunkUtil.indexBlock(x, y, z), Fluid.getAssetMap().getIndex(fluid.getId()), level);
/*     */   }
/*     */   
/*     */   public boolean setFluid(int x, int y, int z, int fluidId, byte level) {
/* 110 */     return setFluid(ChunkUtil.indexBlock(x, y, z), fluidId, level);
/*     */   }
/*     */   
/*     */   public boolean setFluid(int index, @Nonnull Fluid fluid, byte level) {
/* 114 */     return setFluid(index, Fluid.getAssetMap().getIndex(fluid.getId()), level);
/*     */   }
/*     */   
/*     */   public boolean setFluid(int index, int fluidId, byte level) {
/* 118 */     level = (byte)(level & 0xF);
/* 119 */     if (level == 0) fluidId = 0; 
/* 120 */     if (fluidId == 0) level = 0; 
/* 121 */     long stamp = this.lock.writeLock();
/*     */     try {
/* 123 */       boolean changed = setFluidRaw(index, fluidId);
/* 124 */       changed |= setFluidLevel(index, level);
/* 125 */       if (changed && this.loaded) {
/* 126 */         this.cachedPacket = null;
/* 127 */         this.changedPositions.add(index);
/*     */       } 
/* 129 */       return changed;
/*     */     } finally {
/* 131 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean setFluidRaw(int x, int y, int z, @Nonnull Fluid fluid) {
/* 136 */     return setFluidRaw(ChunkUtil.indexBlock(x, y, z), fluid);
/*     */   }
/*     */   
/*     */   private boolean setFluidRaw(int index, @Nonnull Fluid fluid) {
/* 140 */     IndexedLookupTableAssetMap<String, Fluid> assetMap = Fluid.getAssetMap();
/* 141 */     return setFluidRaw(index, assetMap.getIndex(fluid.getId()));
/*     */   }
/*     */   
/*     */   public int getFluidId(int x, int y, int z) {
/* 145 */     return getFluidId(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public int getFluidId(int index) {
/* 149 */     long stamp = this.lock.tryOptimisticRead();
/* 150 */     int currentId = this.typePalette.get(index);
/*     */     
/* 152 */     if (!this.lock.validate(stamp)) {
/* 153 */       stamp = this.lock.readLock();
/*     */       try {
/* 155 */         currentId = this.typePalette.get(index);
/*     */       } finally {
/* 157 */         this.lock.unlockRead(stamp);
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     return currentId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Fluid getFluid(int x, int y, int z) {
/* 166 */     return getFluid(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Fluid getFluid(int index) {
/* 171 */     IndexedLookupTableAssetMap<String, Fluid> assetMap = Fluid.getAssetMap();
/* 172 */     return (Fluid)assetMap.getAsset(getFluidId(index));
/*     */   }
/*     */   
/*     */   private boolean setFluidLevel(int x, int y, int z, byte level) {
/* 176 */     return setFluidLevel(ChunkUtil.indexBlock(x, y, z), level);
/*     */   }
/*     */   
/*     */   private boolean setFluidLevel(int index, byte level) {
/* 180 */     level = (byte)(level & 0xF);
/* 181 */     if (this.levelData == null) {
/* 182 */       if (level == 0) return false; 
/* 183 */       this.levelData = new byte[16384];
/*     */     } 
/*     */ 
/*     */     
/* 187 */     int byteIndex = index >> 1;
/* 188 */     byte byteValue = this.levelData[byteIndex];
/* 189 */     int value = byteValue >> (index & 0x1) * 4 & 0xF;
/* 190 */     if (value == level) return false; 
/* 191 */     if (value == 0) {
/* 192 */       this.nonZeroLevels++;
/* 193 */     } else if (level == 0) {
/* 194 */       this.nonZeroLevels--;
/*     */       
/* 196 */       if (this.nonZeroLevels <= 0) {
/* 197 */         this.levelData = null;
/* 198 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     if ((index & 0x1) == 0) {
/* 203 */       this.levelData[byteIndex] = (byte)(byteValue & 0xF0 | level);
/*     */     } else {
/* 205 */       this.levelData[byteIndex] = (byte)(byteValue & 0xF | level << 4);
/*     */     } 
/* 207 */     return true;
/*     */   }
/*     */   
/*     */   public byte getFluidLevel(int x, int y, int z) {
/* 211 */     return getFluidLevel(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public byte getFluidLevel(int index) {
/* 215 */     long stamp = this.lock.tryOptimisticRead();
/*     */ 
/*     */     
/* 218 */     byte[] localData = this.levelData;
/* 219 */     byte result = 0;
/*     */ 
/*     */     
/* 222 */     if (localData != null) {
/* 223 */       int byteIndex = index >> 1;
/* 224 */       byte byteValue = localData[byteIndex];
/* 225 */       result = (byte)(byteValue >> (index & 0x1) * 4 & 0xF);
/*     */     } 
/*     */     
/* 228 */     if (!this.lock.validate(stamp)) {
/* 229 */       stamp = this.lock.readLock();
/*     */       try {
/* 231 */         if (this.levelData == null) {
/* 232 */           return 0;
/*     */         }
/* 234 */         int byteIndex = index >> 1;
/* 235 */         byte byteValue = this.levelData[byteIndex];
/* 236 */         return (byte)(byteValue >> (index & 0x1) * 4 & 0xF);
/*     */       } finally {
/* 238 */         this.lock.unlockRead(stamp);
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     return result;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 246 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 250 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 254 */     return this.z;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IntOpenHashSet getAndClearChangedPositions() {
/* 259 */     long stamp = this.lock.writeLock();
/*     */     try {
/* 261 */       this.swapChangedPositions.clear();
/*     */       
/* 263 */       IntOpenHashSet tmp = this.changedPositions;
/* 264 */       this.changedPositions = this.swapChangedPositions;
/* 265 */       this.swapChangedPositions = tmp;
/* 266 */       return tmp;
/*     */     } finally {
/* 268 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/* 276 */     return this;
/*     */   }
/*     */   
/*     */   private void serializeForPacket(@Nonnull ByteBuf buf) {
/* 280 */     long stamp = this.lock.readLock();
/*     */     try {
/* 282 */       buf.writeByte(this.typePalette.getPaletteType().ordinal());
/* 283 */       this.typePalette.serializeForPacket(buf);
/*     */       
/* 285 */       if (this.levelData != null) {
/* 286 */         buf.writeBoolean(true);
/* 287 */         buf.writeBytes(this.levelData);
/*     */       } else {
/* 289 */         buf.writeBoolean(false);
/*     */       } 
/*     */     } finally {
/* 292 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] serialize(ExtraInfo extraInfo) {
/* 297 */     ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
/* 298 */     long stamp = this.lock.readLock();
/*     */     try {
/* 300 */       buf.writeByte(this.typePalette.getPaletteType().ordinal());
/* 301 */       this.typePalette.serialize(Fluid.KEY_SERIALIZER, buf);
/*     */       
/* 303 */       if (this.levelData != null) {
/* 304 */         buf.writeBoolean(true);
/* 305 */         buf.writeBytes(this.levelData);
/*     */       } else {
/* 307 */         buf.writeBoolean(false);
/*     */       } 
/* 309 */       return ByteBufUtil.getBytesRelease(buf);
/* 310 */     } catch (Throwable e) {
/* 311 */       buf.release();
/* 312 */       throw SneakyThrow.sneakyThrow(e);
/*     */     } finally {
/* 314 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deserialize(@Nonnull byte[] bytes, ExtraInfo extraInfo) {
/* 319 */     ByteBuf buf = Unpooled.wrappedBuffer(bytes);
/*     */     
/* 321 */     PaletteTypeEnum type = PaletteTypeEnum.get(buf.readByte());
/* 322 */     this.typePalette = type.getConstructor().get();
/* 323 */     this.typePalette.deserialize(Fluid.KEY_DESERIALIZER, buf, 0);
/*     */     
/* 325 */     if (buf.readBoolean()) {
/* 326 */       this.levelData = new byte[16384];
/* 327 */       buf.readBytes(this.levelData);
/* 328 */       this.nonZeroLevels = 0;
/* 329 */       for (int i = 0; i < 16384; i++) {
/* 330 */         byte v = this.levelData[i];
/* 331 */         if ((v & 0xF) != 0) this.nonZeroLevels++; 
/* 332 */         if ((v & 0xF0) != 0) this.nonZeroLevels++; 
/*     */       } 
/*     */     } else {
/* 335 */       this.levelData = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<CachedPacket<SetFluids>> getCachedPacket() {
/* 341 */     SoftReference<CompletableFuture<CachedPacket<SetFluids>>> ref = this.cachedPacket;
/* 342 */     CompletableFuture<CachedPacket<SetFluids>> future = (ref != null) ? ref.get() : null;
/* 343 */     if (future != null) return future;
/*     */     
/* 345 */     future = CompletableFuture.supplyAsync(() -> {
/*     */           ByteBuf buf = Unpooled.buffer(65536);
/*     */           
/*     */           serializeForPacket(buf);
/*     */           byte[] data = ByteBufUtil.getBytesRelease(buf);
/*     */           SetFluids packet = new SetFluids(this.x, this.y, this.z, data);
/*     */           return CachedPacket.cache((Packet)packet);
/*     */         });
/* 353 */     this.cachedPacket = new SoftReference<>(future);
/* 354 */     return future;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 358 */     return (this.typePalette.isSolid(0) && this.nonZeroLevels == 0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\FluidSection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */