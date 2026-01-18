/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkLightData
/*     */ {
/*  16 */   public static final ChunkLightData EMPTY = new ChunkLightData(null, (short)0);
/*     */   
/*     */   public static final int TREE_SIZE = 8;
/*     */   
/*     */   public static final int TREE_MASK = 7;
/*     */   
/*     */   public static final int DEPTH_MAGIC = 12;
/*     */   
/*     */   public static final int SIZE_MAGIC = 17;
/*     */   
/*     */   public static final int INITIAL_CAPACITY = 128;
/*     */   
/*     */   public static final byte MAX_VALUE = 15;
/*     */   
/*     */   public static final int CHANNEL_COUNT = 4;
/*     */   
/*     */   public static final int BITS_PER_CHANNEL = 4;
/*     */   
/*     */   public static final int CHANNEL_MASK = 15;
/*     */   
/*     */   public static final int RED_CHANNEL = 0;
/*     */   
/*     */   public static final int GREEN_CHANNEL = 1;
/*     */   
/*     */   public static final int BLUE_CHANNEL = 2;
/*     */   
/*     */   public static final int SKY_CHANNEL = 3;
/*     */   
/*     */   public static final int RED_CHANNEL_BIT = 0;
/*     */   
/*     */   public static final int GREEN_CHANNEL_BIT = 4;
/*     */   
/*     */   public static final int BLUE_CHANNEL_BIT = 8;
/*     */   
/*     */   public static final int SKY_CHANNEL_BIT = 12;
/*     */   
/*     */   public static final int RGB_MASK = -61441;
/*     */   
/*     */   protected final short changeId;
/*     */   
/*     */   ByteBuf light;
/*     */ 
/*     */   
/*     */   public ChunkLightData(ByteBuf light, short changeId) {
/*  60 */     this.light = light;
/*  61 */     this.changeId = changeId;
/*     */   }
/*     */   
/*     */   public short getChangeId() {
/*  65 */     return this.changeId;
/*     */   }
/*     */   
/*     */   public byte getRedBlockLight(int x, int y, int z) {
/*  69 */     return getRedBlockLight(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public byte getRedBlockLight(int index) {
/*  73 */     if (this.light == null) return 0; 
/*  74 */     return getLight(index, 0);
/*     */   }
/*     */   
/*     */   public byte getGreenBlockLight(int x, int y, int z) {
/*  78 */     return getGreenBlockLight(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public byte getGreenBlockLight(int index) {
/*  82 */     if (this.light == null) return 0; 
/*  83 */     return getLight(index, 1);
/*     */   }
/*     */   
/*     */   public byte getBlueBlockLight(int x, int y, int z) {
/*  87 */     return getBlueBlockLight(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public byte getBlueBlockLight(int index) {
/*  91 */     if (this.light == null) return 0; 
/*  92 */     return getLight(index, 2);
/*     */   }
/*     */   
/*     */   public byte getBlockLightIntensity(int x, int y, int z) {
/*  96 */     return getBlockLightIntensity(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public byte getBlockLightIntensity(int index) {
/* 100 */     if (this.light == null) return 0; 
/* 101 */     byte r = getLight(index, 0);
/* 102 */     byte g = getLight(index, 1);
/* 103 */     byte b = getLight(index, 2);
/* 104 */     return (byte)(MathUtil.maxValue(b, g, r) & 0xF);
/*     */   }
/*     */   
/*     */   public short getBlockLight(int x, int y, int z) {
/* 108 */     return getBlockLight(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public short getBlockLight(int index) {
/* 112 */     if (this.light == null) return 0; 
/* 113 */     return (short)(getLightRaw(index) & 0xFFFF0FFF);
/*     */   }
/*     */   
/*     */   public byte getSkyLight(int x, int y, int z) {
/* 117 */     return getSkyLight(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public byte getSkyLight(int index) {
/* 121 */     if (this.light == null) return 0; 
/* 122 */     return getLight(index, 3);
/*     */   }
/*     */   
/*     */   public byte getLight(int index, int channel) {
/* 126 */     if (channel < 0 || channel >= 4) throw new IllegalArgumentException(); 
/* 127 */     if (this.light == null) return 0; 
/* 128 */     short value = getLightRaw(index);
/* 129 */     return (byte)(value >> channel * 4 & 0xF);
/*     */   }
/*     */   
/*     */   public short getLightRaw(int x, int y, int z) {
/* 133 */     return getLightRaw(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public short getLightRaw(int index) {
/* 137 */     if (this.light == null) return 0; 
/* 138 */     if (index < 0 || index >= 32768) throw new IllegalArgumentException("Index " + index + " is outside of the bounds!"); 
/* 139 */     return getTraverse(this.light, index, 0, 0);
/*     */   }
/*     */   
/*     */   protected static short getTraverse(@Nonnull ByteBuf local, int index, int pointer, int depth) {
/* 143 */     int loc = -1;
/* 144 */     int result = -1;
/*     */     try {
/* 146 */       int position = pointer * 17;
/* 147 */       byte mask = local.getByte(position);
/* 148 */       int innerIndex = index >> 12 - depth & 0x7;
/* 149 */       loc = innerIndex * 2 + position + 1;
/* 150 */       result = local.getUnsignedShort(loc);
/* 151 */       if ((mask >> innerIndex & 0x1) == 1) {
/* 152 */         return getTraverse(local, index, result, depth + 3);
/*     */       }
/* 154 */       return (short)result;
/*     */     }
/* 156 */     catch (Throwable t) {
/* 157 */       throw new RuntimeException("Failed with " + index + ", " + pointer + ", " + depth + ". Result: " + result + " from " + loc, t);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 162 */     buf.writeShort(this.changeId);
/*     */     
/* 164 */     boolean hasLight = (this.light != null);
/* 165 */     buf.writeBoolean(hasLight);
/* 166 */     if (hasLight) {
/* 167 */       buf.ensureWritable(this.light.readableBytes());
/*     */       
/* 169 */       int before = buf.writerIndex();
/* 170 */       buf.writeInt(0);
/*     */       
/* 172 */       serializeOctree(buf, 0);
/*     */       
/* 174 */       int after = buf.writerIndex();
/* 175 */       buf.writerIndex(before);
/* 176 */       buf.writeInt(after - before - 4);
/* 177 */       buf.writerIndex(after);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void serializeOctree(@Nonnull ByteBuf buf, int position) {
/* 182 */     int mask = this.light.getByte(position * 17);
/* 183 */     buf.writeByte(mask);
/* 184 */     for (int i = 0; i < 8; i++) {
/* 185 */       int val = this.light.getUnsignedShort(position * 17 + i * 2 + 1);
/* 186 */       if ((mask >> i & 0x1) == 1) {
/* 187 */         serializeOctree(buf, val);
/*     */       } else {
/* 189 */         buf.writeShort(val);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeForPacket(@Nonnull ByteBuf buf) {
/* 195 */     boolean hasLight = (this.light != null);
/* 196 */     buf.writeBoolean(hasLight);
/* 197 */     if (hasLight) {
/* 198 */       buf.ensureWritable(this.light.readableBytes());
/* 199 */       serializeOctreeForPacket(buf, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void serializeOctreeForPacket(@Nonnull ByteBuf buf, int position) {
/* 204 */     int mask = this.light.getByte(position * 17);
/* 205 */     buf.writeByte(mask);
/* 206 */     for (int i = 0; i < 8; i++) {
/* 207 */       int val = this.light.getUnsignedShort(position * 17 + i * 2 + 1);
/* 208 */       if ((mask >> i & 0x1) == 1) {
/* 209 */         serializeOctreeForPacket(buf, val);
/*     */       } else {
/* 211 */         buf.writeShortLE(val);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @Nonnull
/*     */   public static ChunkLightData deserialize(@Nonnull ByteBuf buf, int version) {
/*     */     ChunkLightData chunkLightData;
/* 218 */     short changeId = buf.readShort();
/*     */ 
/*     */     
/* 221 */     boolean hasLight = buf.readBoolean();
/* 222 */     if (hasLight) {
/* 223 */       int length = buf.readInt();
/* 224 */       ByteBuf from = buf.readSlice(length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       int estSize = length * 23 / 20;
/*     */       
/* 232 */       ByteBuf buffer = Unpooled.buffer(estSize);
/* 233 */       buffer.writerIndex(17);
/* 234 */       deserializeOctree(from, buffer, 0, 0);
/* 235 */       chunkLightData = new ChunkLightData(buffer.copy(), changeId);
/*     */     } else {
/* 237 */       chunkLightData = new ChunkLightData(null, changeId);
/*     */     } 
/*     */     
/* 240 */     return chunkLightData;
/*     */   }
/*     */   
/*     */   private static int deserializeOctree(@Nonnull ByteBuf from, @Nonnull ByteBuf to, int position, int segmentIndex) {
/* 244 */     int mask = from.readByte();
/* 245 */     to.setByte(position * 17, mask);
/* 246 */     for (int i = 0; i < 8; i++) {
/*     */       int val;
/* 248 */       if ((mask >> i & 0x1) == 1) {
/* 249 */         int nextSegmentIndex = ++segmentIndex;
/* 250 */         to.writerIndex((nextSegmentIndex + 1) * 17);
/*     */         
/* 252 */         val = nextSegmentIndex;
/* 253 */         segmentIndex = deserializeOctree(from, to, val, nextSegmentIndex);
/*     */       } else {
/* 255 */         val = from.readShort();
/*     */       } 
/* 257 */       to.setShort(position * 17 + i * 2 + 1, val);
/*     */     } 
/* 259 */     return segmentIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String octreeToString() {
/* 264 */     if (this.light == null) {
/* 265 */       return "NULL";
/*     */     }
/* 267 */     return ChunkLightDataBuilder.octreeToString(this.light);
/*     */   }
/*     */   
/*     */   public static short combineLightValues(byte red, byte green, byte blue, byte sky) {
/* 271 */     return (short)(sky << 12 | blue << 8 | green << 4 | red << 0);
/*     */   }
/*     */   
/*     */   public static short combineLightValues(byte red, byte green, byte blue) {
/* 275 */     return (short)(blue << 8 | green << 4 | red << 0);
/*     */   }
/*     */   
/*     */   public static byte getLightValue(short value, int channel) {
/* 279 */     return (byte)(value >> channel * 4 & 0xF);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\ChunkLightData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */