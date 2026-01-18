/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.BitSet;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkLightDataBuilder
/*     */   extends ChunkLightData
/*     */ {
/*     */   static boolean DEBUG = false;
/*     */   protected BitSet currentSegments;
/*     */   
/*     */   public ChunkLightDataBuilder(short changeId) {
/*  23 */     super(null, changeId);
/*     */   }
/*     */   
/*     */   public ChunkLightDataBuilder(@Nonnull ChunkLightData lightData, short changeId) {
/*  27 */     super((lightData.light != null) ? lightData.light.copy() : null, changeId);
/*  28 */     if (lightData instanceof ChunkLightDataBuilder) throw new IllegalArgumentException("ChunkLightDataBuilder light data isn't compacted so we can't read this cleanly atm");
/*     */     
/*  30 */     if (this.light != null) {
/*  31 */       this.currentSegments = new BitSet();
/*  32 */       this.currentSegments.set(0);
/*  33 */       findSegments(this.light, 0, this.currentSegments);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void findSegments(@Nonnull ByteBuf light, int position, @Nonnull BitSet currentSegments) {
/*  38 */     int mask = light.getByte(position * 17);
/*  39 */     for (int i = 0; i < 8; i++) {
/*  40 */       int val = light.getUnsignedShort(position * 17 + i * 2 + 1);
/*  41 */       if ((mask >> i & 0x1) == 1) {
/*  42 */         currentSegments.set(val);
/*  43 */         findSegments(light, val, currentSegments);
/*     */       } 
/*     */     } 
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
/*     */   public void setBlockLight(int x, int y, int z, byte red, byte green, byte blue) {
/*  59 */     setBlockLight(ChunkUtil.indexBlock(x, y, z), red, green, blue);
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
/*     */   public void setBlockLight(int index, byte red, byte green, byte blue) {
/*  71 */     byte sky = getLight(index, 3);
/*  72 */     setLightRaw(index, combineLightValues(red, green, blue, sky));
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
/*     */   public void setSkyLight(int x, int y, int z, byte light) {
/*  85 */     setSkyLight(ChunkUtil.indexBlock(x, y, z), light);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkyLight(int index, byte light) {
/*  96 */     setLight(index, 3, light);
/*     */   }
/*     */   
/*     */   public void setLight(int index, int channel, byte value) {
/* 100 */     if (channel < 0 || channel >= 4) throw new IllegalArgumentException(); 
/* 101 */     int current = getLightRaw(index);
/* 102 */     current &= 15 << channel * 4 ^ 0xFFFFFFFF;
/* 103 */     current |= (value & 0xF) << channel * 4;
/* 104 */     setLightRaw(index, (short)current);
/*     */   }
/*     */   
/*     */   public void setLightRaw(int index, short value) {
/* 108 */     if (index < 0 || index >= 32768) throw new IllegalArgumentException("Index " + index + " is outside of the bounds!"); 
/* 109 */     if (this.light == null) {
/* 110 */       this.light = Unpooled.buffer(2176);
/*     */     }
/* 112 */     if (this.currentSegments == null) {
/* 113 */       this.currentSegments = new BitSet();
/* 114 */       this.currentSegments.set(0);
/*     */     } 
/* 116 */     setTraverse(this.light, this.currentSegments, index, 0, 0, value);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ChunkLightData build() {
/* 121 */     if (this.light == null) return new ChunkLightData(null, this.changeId);
/*     */     
/* 123 */     ByteBuf buffer = Unpooled.buffer(this.currentSegments.cardinality() * 17);
/* 124 */     buffer.writerIndex(17);
/* 125 */     serializeOctree(buffer, 0, 0);
/* 126 */     return new ChunkLightData(buffer, this.changeId);
/*     */   }
/*     */   
/*     */   private int serializeOctree(@Nonnull ByteBuf to, int position, int segmentIndex) {
/* 130 */     int toPosition = segmentIndex;
/*     */     
/* 132 */     int mask = this.light.getByte(position * 17);
/* 133 */     to.setByte(toPosition * 17, mask);
/* 134 */     for (int i = 0; i < 8; i++) {
/* 135 */       int val = this.light.getUnsignedShort(position * 17 + i * 2 + 1);
/* 136 */       if ((mask >> i & 0x1) == 1) {
/*     */         
/* 138 */         to.ensureWritable(17);
/* 139 */         int nextSegmentIndex = ++segmentIndex;
/* 140 */         to.writerIndex((nextSegmentIndex + 1) * 17);
/*     */         
/* 142 */         int from = val;
/* 143 */         val = nextSegmentIndex;
/* 144 */         segmentIndex = serializeOctree(to, from, nextSegmentIndex);
/*     */       } 
/* 146 */       to.setShort(toPosition * 17 + i * 2 + 1, val);
/*     */     } 
/* 148 */     return segmentIndex;
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
/*     */   @Nullable
/*     */   private static Res setTraverse(@Nonnull ByteBuf local, @Nonnull BitSet currentSegments, int index, int pointer, int depth, short value) {
/* 163 */     int headerLocation = pointer * 17;
/* 164 */     byte i = local.getByte(headerLocation);
/* 165 */     int innerIndex = index >> 12 - depth & 0x7;
/* 166 */     int position = innerIndex * 2 + headerLocation + 1;
/* 167 */     short currentValue = local.getShort(position);
/*     */     try {
/* 169 */       if ((i >> innerIndex & 0x1) == 1) {
/* 170 */         int currentValueMasked = currentValue & 0xFFFF;
/* 171 */         if (depth == 12) {
/* 172 */           throw new RuntimeException("Discovered branch at deepest point in octree! Mask " + i + " innerIndex " + innerIndex + " depth " + depth + " setValue " + value + " currentValue " + currentValue + " at index " + index + " pointer " + pointer);
/*     */         }
/* 174 */         if (setTraverse(local, currentSegments, index, currentValueMasked, depth + 3, value) != null) {
/* 175 */           currentSegments.clear(currentValueMasked);
/* 176 */           local.setShort(position, value);
/* 177 */           int mask = 1 << innerIndex ^ 0xFFFFFFFF;
/* 178 */           i = (byte)(i & mask);
/* 179 */           local.setByte(headerLocation, i);
/* 180 */           if (i == 0) {
/* 181 */             for (int j = 0; j < 8; j++) {
/* 182 */               short s = local.getShort(j * 2 + headerLocation + 1);
/* 183 */               if (s != value) {
/* 184 */                 return null;
/*     */               }
/*     */             } 
/* 187 */             return Res.INSTANCE;
/*     */           }
/*     */         
/*     */         } 
/* 191 */       } else if (value != currentValue) {
/* 192 */         if (depth > 12) {
/* 193 */           throw new IllegalStateException("Somehow have invalid octree state: " + octreeToString(local) + " when setTraverse(" + index + ", " + pointer + ", " + depth + ", " + value + ");");
/*     */         }
/*     */         
/* 196 */         if (depth == 12) {
/* 197 */           byte[] bytes = null;
/* 198 */           if (DEBUG) {
/* 199 */             bytes = new byte[17];
/* 200 */             local.getBytes(headerLocation, bytes, 0, bytes.length);
/*     */           } 
/*     */           
/* 203 */           local.setShort(position, value);
/* 204 */           for (int j = 0; j < 8; j++) {
/* 205 */             short s = local.getShort(j * 2 + headerLocation + 1);
/* 206 */             if (s != value) {
/* 207 */               return null;
/*     */             }
/*     */           } 
/*     */           
/* 211 */           return DEBUG ? new Res(ByteBufUtil.hexDump(bytes)) : Res.INSTANCE;
/*     */         } 
/* 213 */         i = (byte)(i | 1 << innerIndex);
/* 214 */         local.setByte(headerLocation, i);
/*     */         
/* 216 */         int newSegmentIndex = growSegment(local, currentSegments, currentValue);
/* 217 */         local.setShort(position, newSegmentIndex);
/* 218 */         Res out = setTraverse(local, currentSegments, index, newSegmentIndex, depth + 3, value);
/* 219 */         if (out != null) {
/* 220 */           throw new RuntimeException("Created new segment that instantly collapsed with (" + index + ", " + pointer + ", " + depth + ", " + value + "): with currentValue mismatch " + currentValue + " res " + String.valueOf(out));
/*     */         }
/* 222 */         return null;
/*     */       }
/*     */     
/*     */     }
/* 226 */     catch (Throwable t) {
/* 227 */       throw new RuntimeException("Failed to setTraverse(" + index + ", " + pointer + ", " + depth + ", " + value + ") with i " + i + ", innerIndex " + innerIndex + ", position " + position + ", currentValue " + currentValue, t);
/*     */     } 
/* 229 */     return null;
/*     */   }
/*     */   
/*     */   protected static int growSegment(@Nonnull ByteBuf local, @Nonnull BitSet currentSegments, short val) {
/* 233 */     int newSegmentIndex = currentSegments.nextClearBit(0);
/* 234 */     currentSegments.set(newSegmentIndex);
/* 235 */     int currentCapacity = local.capacity();
/* 236 */     if (currentCapacity <= (newSegmentIndex + 1) * 17) {
/* 237 */       int newCap = currentCapacity + 1088;
/* 238 */       local.capacity(newCap);
/*     */     } 
/* 240 */     local.setByte(newSegmentIndex * 17, 0);
/* 241 */     for (int j = 0; j < 8; j++) {
/* 242 */       local.setShort(newSegmentIndex * 17 + j * 2 + 1, val);
/*     */     }
/* 244 */     return newSegmentIndex;
/*     */   }
/*     */   
/*     */   private static class Res {
/* 248 */     public static final Res INSTANCE = new Res(null);
/*     */     
/*     */     private final String segment;
/*     */     
/*     */     private Res(String segment) {
/* 253 */       this.segment = segment;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 259 */       return "Res{segment='" + this.segment + "'}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toStringOctree() {
/* 267 */     if (this.light == null) return "NULL"; 
/* 268 */     return octreeToString(this.light);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String octreeToString(@Nonnull ByteBuf buffer) {
/* 273 */     StringBuffer out = new StringBuffer();
/*     */     try {
/* 275 */       octreeToString(buffer, 0, out, 0);
/* 276 */     } catch (Throwable t) {
/* 277 */       throw new RuntimeException("Failed at " + String.valueOf(out), t);
/*     */     } 
/* 279 */     return out.toString();
/*     */   }
/*     */   
/*     */   public static void octreeToString(@Nonnull ByteBuf buffer, int pointer, @Nonnull StringBuffer out, int recursion) {
/* 283 */     int i = buffer.getByte(pointer * 17);
/* 284 */     for (int j = 0; j < 8; j++) {
/* 285 */       int loc = pointer * 17 + j * 2 + 1;
/* 286 */       int s = buffer.getUnsignedShort(loc);
/* 287 */       out.append("\t".repeat(Math.max(0, recursion)));
/* 288 */       if ((i & 1 << j) != 0) {
/* 289 */         out.append("SUBTREE AT ").append(j).append('\n');
/* 290 */         octreeToString(buffer, s, out, recursion + 1);
/*     */       } else {
/* 292 */         out.append("INDEX ").append(j).append(" VALUE: ").append(s);
/*     */       } 
/* 294 */       if (j != 7)
/* 295 */         out.append('\n'); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\ChunkLightDataBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */