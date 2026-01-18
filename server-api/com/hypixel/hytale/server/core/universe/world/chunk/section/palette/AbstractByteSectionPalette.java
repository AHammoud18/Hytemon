/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section.palette;
/*     */ import com.hypixel.hytale.math.util.NumberUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ByteMap;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.BitSet;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class AbstractByteSectionPalette implements ISectionPalette {
/*     */   protected final Int2ByteMap externalToInternal;
/*     */   protected final Byte2IntMap internalToExternal;
/*     */   
/*     */   protected AbstractByteSectionPalette(byte[] blocks) {
/*  26 */     this((Int2ByteMap)new Int2ByteOpenHashMap(), (Byte2IntMap)new Byte2IntOpenHashMap(), new BitSet(), (Byte2ShortMap)new Byte2ShortOpenHashMap(), blocks);
/*     */ 
/*     */     
/*  29 */     this.externalToInternal.put(0, (byte)0);
/*  30 */     this.internalToExternal.put((byte)0, 0);
/*  31 */     this.internalIdSet.set(0);
/*  32 */     this.internalIdCount.put((byte)0, -32768);
/*     */   }
/*     */   protected final BitSet internalIdSet; protected final Byte2ShortMap internalIdCount; protected final byte[] blocks;
/*     */   public AbstractByteSectionPalette(byte[] blocks, @Nonnull int[] data, int[] unique, int count) {
/*  36 */     this((Int2ByteMap)new Int2ByteOpenHashMap(count), (Byte2IntMap)new Byte2IntOpenHashMap(count), new BitSet(count), (Byte2ShortMap)new Byte2ShortOpenHashMap(count), blocks);
/*     */ 
/*     */ 
/*     */     
/*  40 */     for (int internalId = 0; internalId < count; internalId++) {
/*  41 */       int blockId = unique[internalId];
/*  42 */       this.internalToExternal.put((byte)internalId, blockId);
/*  43 */       this.externalToInternal.put(blockId, (byte)internalId);
/*  44 */       this.internalIdSet.set(unsignedInternalId((byte)internalId));
/*  45 */       this.internalIdCount.put((byte)internalId, (short)0);
/*     */     } 
/*     */ 
/*     */     
/*  49 */     for (int index = 0; index < data.length; index++) {
/*  50 */       int id = data[index];
/*  51 */       byte b = this.externalToInternal.get(id);
/*     */ 
/*     */       
/*  54 */       incrementBlockCount(b);
/*  55 */       set0(index, b);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected AbstractByteSectionPalette(Int2ByteMap externalToInternal, Byte2IntMap internalToExternal, BitSet internalIdSet, Byte2ShortMap internalIdCount, byte[] blocks) {
/*  60 */     this.externalToInternal = externalToInternal;
/*  61 */     this.internalToExternal = internalToExternal;
/*  62 */     this.internalIdSet = internalIdSet;
/*  63 */     this.internalIdCount = internalIdCount;
/*  64 */     this.blocks = blocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(int index) {
/*  69 */     byte internalId = get0(index);
/*  70 */     return this.internalToExternal.get(internalId);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ISectionPalette.SetResult set(int index, int id) {
/*  76 */     byte oldInternalId = get0(index);
/*  77 */     if (this.externalToInternal.containsKey(id)) {
/*  78 */       byte b = this.externalToInternal.get(id);
/*     */ 
/*     */       
/*  81 */       if (b == oldInternalId) return ISectionPalette.SetResult.UNCHANGED;
/*     */ 
/*     */       
/*  84 */       boolean removed = decrementBlockCount(oldInternalId);
/*     */ 
/*     */       
/*  87 */       incrementBlockCount(b);
/*  88 */       set0(index, b);
/*     */       
/*  90 */       if (removed) return ISectionPalette.SetResult.ADDED_OR_REMOVED; 
/*  91 */       return ISectionPalette.SetResult.CHANGED;
/*     */     } 
/*     */ 
/*     */     
/*  95 */     int nextInternalId = nextInternalId(oldInternalId);
/*  96 */     if (!isValidInternalId(nextInternalId)) return ISectionPalette.SetResult.REQUIRES_PROMOTE;
/*     */ 
/*     */ 
/*     */     
/* 100 */     decrementBlockCount(oldInternalId);
/*     */     
/* 102 */     byte newInternalId = (byte)nextInternalId;
/* 103 */     createBlockId(newInternalId, id);
/* 104 */     set0(index, newInternalId);
/* 105 */     return ISectionPalette.SetResult.ADDED_OR_REMOVED;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract byte get0(int paramInt);
/*     */   
/*     */   protected abstract void set0(int paramInt, byte paramByte);
/*     */   
/*     */   public boolean contains(int id) {
/* 114 */     return this.externalToInternal.containsKey(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAny(@Nonnull IntList ids) {
/* 119 */     for (int i = 0; i < ids.size(); i++) {
/* 120 */       if (this.externalToInternal.containsKey(ids.getInt(i))) return true; 
/*     */     } 
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int count() {
/* 127 */     return this.internalIdCount.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int count(int id) {
/* 132 */     if (this.externalToInternal.containsKey(id)) {
/* 133 */       byte internalId = this.externalToInternal.get(id);
/* 134 */       return this.internalIdCount.get(internalId);
/*     */     } 
/* 136 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IntSet values() {
/* 142 */     return (IntSet)new IntOpenHashSet((IntCollection)this.externalToInternal.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachValue(IntConsumer consumer) {
/* 147 */     this.externalToInternal.keySet().forEach(consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Int2ShortMap valueCounts() {
/* 153 */     Int2ShortOpenHashMap int2ShortOpenHashMap = new Int2ShortOpenHashMap();
/* 154 */     for (ObjectIterator<Byte2ShortMap.Entry> objectIterator = this.internalIdCount.byte2ShortEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2ShortMap.Entry entry = objectIterator.next();
/* 155 */       byte internalId = entry.getByteKey();
/* 156 */       short count = entry.getShortValue();
/*     */       
/* 158 */       int externalId = this.internalToExternal.get(internalId);
/* 159 */       int2ShortOpenHashMap.put(externalId, count); }
/*     */     
/* 161 */     return (Int2ShortMap)int2ShortOpenHashMap;
/*     */   }
/*     */   
/*     */   private void createBlockId(byte internalId, int blockId) {
/* 165 */     this.internalToExternal.put(internalId, blockId);
/* 166 */     this.externalToInternal.put(blockId, internalId);
/* 167 */     this.internalIdSet.set(unsignedInternalId(internalId));
/* 168 */     this.internalIdCount.put(internalId, (short)1);
/*     */   }
/*     */   
/*     */   private boolean decrementBlockCount(byte internalId) {
/* 172 */     short oldCount = this.internalIdCount.get(internalId);
/* 173 */     if (oldCount == 1) {
/* 174 */       this.internalIdCount.remove(internalId);
/* 175 */       int externalId = this.internalToExternal.remove(internalId);
/* 176 */       this.externalToInternal.remove(externalId);
/* 177 */       this.internalIdSet.clear(unsignedInternalId(internalId));
/* 178 */       return true;
/*     */     } 
/* 180 */     this.internalIdCount.mergeShort(internalId, (short)1, NumberUtil::subtract);
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void incrementBlockCount(byte internalId) {
/* 186 */     this.internalIdCount.mergeShort(internalId, (short)1, NumberUtil::sum);
/*     */   }
/*     */ 
/*     */   
/*     */   private int nextInternalId(byte oldInternalId) {
/* 191 */     if (this.internalIdCount.get(oldInternalId) == 1)
/*     */     {
/* 193 */       return unsignedInternalId(oldInternalId);
/*     */     }
/*     */     
/* 196 */     return this.internalIdSet.nextClearBit(0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean isValidInternalId(int paramInt);
/*     */ 
/*     */   
/*     */   protected abstract int unsignedInternalId(byte paramByte);
/*     */   
/*     */   public void serializeForPacket(@Nonnull ByteBuf buf) {
/* 206 */     buf.writeShortLE(this.internalToExternal.size());
/* 207 */     for (ObjectIterator<Byte2IntMap.Entry> objectIterator = this.internalToExternal.byte2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2IntMap.Entry entry = objectIterator.next();
/* 208 */       byte internalId = entry.getByteKey();
/* 209 */       int externalId = entry.getIntValue();
/*     */       
/* 211 */       buf.writeByte(internalId);
/* 212 */       buf.writeIntLE(externalId);
/* 213 */       buf.writeShortLE(this.internalIdCount.get(internalId)); }
/*     */ 
/*     */     
/* 216 */     buf.writeBytes(this.blocks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ISectionPalette.KeySerializer keySerializer, @Nonnull ByteBuf buf) {
/* 222 */     buf.writeShort(this.internalToExternal.size());
/* 223 */     for (ObjectIterator<Byte2IntMap.Entry> objectIterator = this.internalToExternal.byte2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2IntMap.Entry entry = objectIterator.next();
/* 224 */       byte internalId = entry.getByteKey();
/* 225 */       int externalId = entry.getIntValue();
/*     */ 
/*     */       
/* 228 */       buf.writeByte(internalId);
/* 229 */       keySerializer.serialize(buf, externalId);
/*     */       
/* 231 */       buf.writeShort(this.internalIdCount.get(internalId)); }
/*     */ 
/*     */ 
/*     */     
/* 235 */     buf.writeBytes(this.blocks);
/*     */   }
/*     */   
/*     */   public void deserialize(@Nonnull ToIntFunction<ByteBuf> deserializer, @Nonnull ByteBuf buf, int version) {
/*     */     Byte2ByteOpenHashMap byte2ByteOpenHashMap;
/* 240 */     this.externalToInternal.clear();
/* 241 */     this.internalToExternal.clear();
/* 242 */     this.internalIdSet.clear();
/* 243 */     this.internalIdCount.clear();
/*     */     
/* 245 */     Byte2ByteMap internalIdRemapping = null;
/*     */ 
/*     */     
/* 248 */     int blockCount = buf.readShort(); int i;
/* 249 */     for (i = 0; i < blockCount; i++) {
/*     */       
/* 251 */       byte internalId = buf.readByte();
/* 252 */       int externalId = deserializer.applyAsInt(buf);
/*     */       
/* 254 */       short count = buf.readShort();
/*     */ 
/*     */       
/* 257 */       if (this.externalToInternal.containsKey(externalId)) {
/* 258 */         byte existingInternalId = this.externalToInternal.get(externalId);
/*     */         
/* 260 */         if (internalIdRemapping == null) byte2ByteOpenHashMap = new Byte2ByteOpenHashMap(); 
/* 261 */         byte2ByteOpenHashMap.put(internalId, existingInternalId);
/*     */         
/* 263 */         this.internalIdCount.mergeShort(existingInternalId, count, NumberUtil::sum);
/*     */       }
/*     */       else {
/*     */         
/* 267 */         this.externalToInternal.put(externalId, internalId);
/* 268 */         this.internalToExternal.put(internalId, externalId);
/* 269 */         this.internalIdSet.set(unsignedInternalId(internalId));
/* 270 */         this.internalIdCount.put(internalId, count);
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     buf.readBytes(this.blocks);
/*     */ 
/*     */     
/* 277 */     if (byte2ByteOpenHashMap != null) {
/* 278 */       for (i = 0; i < 32768; i++) {
/* 279 */         byte oldInternalId = get0(i);
/* 280 */         if (byte2ByteOpenHashMap.containsKey(oldInternalId)) {
/* 281 */           set0(i, byte2ByteOpenHashMap.get(oldInternalId));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void find(@Nonnull IntList ids, @Nonnull IntSet internalIdHolder, @Nonnull IntConsumer indexConsumer) {
/*     */     int i;
/* 289 */     for (i = 0; i < ids.size(); i++) {
/* 290 */       byte internal = this.externalToInternal.getOrDefault(ids.getInt(i), -128);
/* 291 */       if (internal != Byte.MIN_VALUE) {
/* 292 */         internalIdHolder.add(internal);
/*     */       }
/*     */     } 
/*     */     
/* 296 */     for (i = 0; i < 32768; i++) {
/* 297 */       byte type = get0(i);
/* 298 */       if (internalIdHolder.contains(type))
/* 299 */         indexConsumer.accept(i); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\palette\AbstractByteSectionPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */