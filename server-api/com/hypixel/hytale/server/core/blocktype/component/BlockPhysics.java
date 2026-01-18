/*     */ package com.hypixel.hytale.server.core.blocktype.component;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.common.util.BitUtil;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.blocktype.BlockTypeModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockPhysics implements Component<ChunkStore> {
/*  27 */   public static final BuilderCodec<BlockPhysics> CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockPhysics.class, BlockPhysics::new)
/*  28 */     .versioned())
/*  29 */     .codecVersion(0))
/*  30 */     .append(new KeyedCodec("Data", Codec.BYTE_ARRAY), BlockPhysics::deserialize, BlockPhysics::serialize)
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  35 */     .add())
/*  36 */     .build(); public static final int VERSION = 0; public static final int SUPPORT_DATA_SIZE = 16384; public static final int IS_DECO_VALUE = 15; public static final int NULL_SUPPORT = 0;
/*     */   
/*     */   public static ComponentType<ChunkStore, BlockPhysics> getComponentType() {
/*  39 */     return BlockTypeModule.get().getBlockPhysicsComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private final StampedLock lock = new StampedLock(); @Nullable
/*  49 */   private byte[] supportData = null;
/*     */   
/*  51 */   private int nonZeroCount = 0;
/*     */   
/*     */   public boolean set(int index, int support) {
/*  54 */     long stamp = this.lock.writeLock();
/*     */     try {
/*  56 */       support &= 0xF;
/*  57 */       if (this.supportData == null) {
/*  58 */         if (support == 0) return false; 
/*  59 */         this.supportData = new byte[16384];
/*     */       } 
/*     */       
/*  62 */       byte previousValue = BitUtil.getAndSetNibble(this.supportData, index, (byte)support);
/*  63 */       if (previousValue == support) return false; 
/*  64 */       if (previousValue == 0) {
/*  65 */         this.nonZeroCount++;
/*  66 */       } else if (support == 0) {
/*  67 */         this.nonZeroCount--;
/*     */         
/*  69 */         if (this.nonZeroCount <= 0) {
/*  70 */           this.supportData = null;
/*     */         }
/*     */       } 
/*  73 */       return true;
/*     */     } finally {
/*  75 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean set(int x, int y, int z, int support) {
/*  80 */     return set(ChunkUtil.indexBlock(x, y, z), support);
/*     */   }
/*     */   
/*     */   public int get(int index) {
/*  84 */     long stamp = this.lock.readLock();
/*     */     try {
/*  86 */       if (this.supportData == null) {
/*  87 */         return 0;
/*     */       }
/*     */       
/*  90 */       return BitUtil.getNibble(this.supportData, index);
/*     */     } finally {
/*  92 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int get(int x, int y, int z) {
/*  97 */     return get(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public boolean isDeco(int x, int y, int z) {
/* 101 */     return isDeco(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public boolean isDeco(int index) {
/* 105 */     return (get(index) == 15);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/* 111 */     BlockPhysics decoBlocks = new BlockPhysics();
/* 112 */     if (this.supportData != null) {
/* 113 */       decoBlocks.supportData = Arrays.copyOf(this.supportData, this.supportData.length);
/* 114 */       decoBlocks.nonZeroCount = this.nonZeroCount;
/*     */     } 
/* 116 */     return decoBlocks;
/*     */   }
/*     */   
/*     */   private byte[] serialize(ExtraInfo extraInfo) {
/* 120 */     ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
/*     */     try {
/* 122 */       buf.writeBoolean((this.supportData != null));
/* 123 */       if (this.supportData != null) {
/* 124 */         buf.writeBytes(this.supportData);
/*     */       }
/* 126 */       return ByteBufUtil.getBytesRelease(buf);
/* 127 */     } catch (Throwable e) {
/* 128 */       buf.release();
/* 129 */       throw SneakyThrow.sneakyThrow(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deserialize(@Nonnull byte[] bytes, ExtraInfo extraInfo) {
/* 134 */     ByteBuf buf = Unpooled.wrappedBuffer(bytes);
/* 135 */     if (buf.readBoolean()) {
/* 136 */       this.supportData = new byte[16384];
/* 137 */       buf.readBytes(this.supportData);
/* 138 */       this.nonZeroCount = 0;
/* 139 */       for (int i = 0; i < 16384; i++) {
/* 140 */         byte v = this.supportData[i];
/* 141 */         if ((v & 0xF) != 0) this.nonZeroCount++; 
/* 142 */         if ((v & 0xF0) != 0) this.nonZeroCount++; 
/*     */       } 
/*     */     } else {
/* 145 */       this.supportData = null;
/* 146 */       this.nonZeroCount = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clear(@Nonnull Store<ChunkStore> store, @Nonnull Ref<ChunkStore> section, int x, int y, int z) {
/* 154 */     BlockPhysics blockPhysics = (BlockPhysics)store.getComponent(section, getComponentType());
/*     */     
/* 156 */     if (blockPhysics == null)
/* 157 */       return;  blockPhysics.set(ChunkUtil.indexBlock(x, y, z), 0);
/*     */   }
/*     */   
/*     */   public static void clear(@Nonnull Holder<ChunkStore> section, int x, int y, int z) {
/* 161 */     BlockPhysics blockPhysics = (BlockPhysics)section.getComponent(getComponentType());
/*     */     
/* 163 */     if (blockPhysics == null)
/* 164 */       return;  blockPhysics.set(ChunkUtil.indexBlock(x, y, z), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset(@Nonnull Store<ChunkStore> store, @Nonnull Ref<ChunkStore> section, int x, int y, int z) {
/* 172 */     BlockPhysics blockPhysics = (BlockPhysics)store.getComponent(section, getComponentType());
/* 173 */     if (blockPhysics == null) {
/* 174 */       blockPhysics = (BlockPhysics)store.ensureAndGetComponent(section, getComponentType());
/*     */     }
/* 176 */     blockPhysics.set(ChunkUtil.indexBlock(x, y, z), 0);
/*     */   }
/*     */   
/*     */   public static void reset(@Nonnull Holder<ChunkStore> section, int x, int y, int z) {
/* 180 */     setSupportValue(section, x, y, z, 0);
/*     */   }
/*     */   
/*     */   public static void markDeco(@Nonnull ComponentAccessor<ChunkStore> store, @Nonnull Ref<ChunkStore> section, int x, int y, int z) {
/* 184 */     BlockPhysics blockPhysics = (BlockPhysics)store.getComponent(section, getComponentType());
/* 185 */     if (blockPhysics == null) {
/* 186 */       blockPhysics = (BlockPhysics)store.ensureAndGetComponent(section, getComponentType());
/*     */     }
/* 188 */     blockPhysics.set(ChunkUtil.indexBlock(x, y, z), 15);
/*     */   }
/*     */   
/*     */   public static void setSupportValue(@Nonnull Store<ChunkStore> store, @Nonnull Ref<ChunkStore> section, int x, int y, int z, int value) {
/* 192 */     BlockPhysics blockPhysics = (BlockPhysics)store.getComponent(section, getComponentType());
/* 193 */     if (blockPhysics == null) {
/* 194 */       blockPhysics = (BlockPhysics)store.ensureAndGetComponent(section, getComponentType());
/*     */     }
/* 196 */     blockPhysics.set(ChunkUtil.indexBlock(x, y, z), value);
/*     */   }
/*     */   
/*     */   public static void setSupportValue(@Nonnull Holder<ChunkStore> section, int x, int y, int z, int value) {
/* 200 */     BlockPhysics blockPhysics = (BlockPhysics)section.getComponent(getComponentType());
/* 201 */     if (blockPhysics == null) {
/* 202 */       blockPhysics = (BlockPhysics)section.ensureAndGetComponent(getComponentType());
/*     */     }
/* 204 */     blockPhysics.set(ChunkUtil.indexBlock(x, y, z), value);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\blocktype\component\BlockPhysics.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */