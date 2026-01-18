/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section.palette;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.NumberUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2IntMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ShortOpenHashMap;
/*     */ import java.util.BitSet;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class AbstractShortSectionPalette implements ISectionPalette {
/*     */   protected final Int2ShortMap externalToInternal;
/*     */   protected final Short2IntMap internalToExternal;
/*     */   protected final BitSet internalIdSet;
/*     */   protected final Short2ShortMap internalIdCount;
/*     */   protected final short[] blocks;
/*     */   
/*     */   public AbstractShortSectionPalette(short[] blocks) {
/*  29 */     this((Int2ShortMap)new Int2ShortOpenHashMap(), (Short2IntMap)new Short2IntOpenHashMap(), new BitSet(), (Short2ShortMap)new Short2ShortOpenHashMap(), blocks);
/*     */ 
/*     */     
/*  32 */     this.externalToInternal.put(0, (short)0);
/*  33 */     this.internalToExternal.put((short)0, 0);
/*  34 */     this.internalIdSet.set(0);
/*  35 */     this.internalIdCount.put((short)0, -32768);
/*     */   }
/*     */   
/*     */   public AbstractShortSectionPalette(short[] blocks, @Nonnull int[] data, int[] unique, int count) {
/*  39 */     this((Int2ShortMap)new Int2ShortOpenHashMap(count), (Short2IntMap)new Short2IntOpenHashMap(count), new BitSet(count), (Short2ShortMap)new Short2ShortOpenHashMap(count), blocks);
/*     */ 
/*     */ 
/*     */     
/*  43 */     for (int internalId = 0; internalId < count; internalId++) {
/*  44 */       int blockId = unique[internalId];
/*  45 */       this.internalToExternal.put((short)internalId, blockId);
/*  46 */       this.externalToInternal.put(blockId, (short)internalId);
/*  47 */       this.internalIdSet.set(internalId);
/*  48 */       this.internalIdCount.put((short)internalId, (short)0);
/*     */     } 
/*     */ 
/*     */     
/*  52 */     for (int index = 0; index < data.length; index++) {
/*  53 */       int id = data[index];
/*  54 */       short s = this.externalToInternal.get(id);
/*     */ 
/*     */       
/*  57 */       incrementBlockCount(s);
/*  58 */       set0(index, s);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected AbstractShortSectionPalette(Int2ShortMap externalToInternal, Short2IntMap internalToExternal, BitSet internalIdSet, Short2ShortMap internalIdCount, short[] blocks) {
/*  63 */     this.externalToInternal = externalToInternal;
/*  64 */     this.internalToExternal = internalToExternal;
/*  65 */     this.internalIdSet = internalIdSet;
/*  66 */     this.internalIdCount = internalIdCount;
/*  67 */     this.blocks = blocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(int index) {
/*  72 */     short internalId = get0(index);
/*  73 */     return this.internalToExternal.get(internalId);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ISectionPalette.SetResult set(int index, int id) {
/*  79 */     short oldInternalId = get0(index);
/*  80 */     if (this.externalToInternal.containsKey(id)) {
/*  81 */       short s = this.externalToInternal.get(id);
/*     */       
/*  83 */       if (s == oldInternalId) return ISectionPalette.SetResult.UNCHANGED;
/*     */ 
/*     */       
/*  86 */       boolean removed = decrementBlockCount(oldInternalId);
/*     */ 
/*     */       
/*  89 */       incrementBlockCount(s);
/*  90 */       set0(index, s);
/*     */       
/*  92 */       if (removed) return ISectionPalette.SetResult.ADDED_OR_REMOVED; 
/*  93 */       return ISectionPalette.SetResult.CHANGED;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     int nextInternalId = nextInternalId(oldInternalId);
/*  98 */     if (!isValidInternalId(nextInternalId)) return ISectionPalette.SetResult.REQUIRES_PROMOTE;
/*     */ 
/*     */ 
/*     */     
/* 102 */     decrementBlockCount(oldInternalId);
/*     */     
/* 104 */     short newInternalId = (short)nextInternalId;
/* 105 */     createBlockId(newInternalId, id);
/* 106 */     set0(index, newInternalId);
/* 107 */     return ISectionPalette.SetResult.ADDED_OR_REMOVED;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract short get0(int paramInt);
/*     */   
/*     */   protected abstract void set0(int paramInt, short paramShort);
/*     */   
/*     */   public boolean contains(int id) {
/* 116 */     return this.externalToInternal.containsKey(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAny(@Nonnull IntList ids) {
/* 121 */     for (int i = 0; i < ids.size(); i++) {
/* 122 */       if (this.externalToInternal.containsKey(ids.getInt(i))) return true; 
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int count() {
/* 129 */     return this.internalIdCount.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int count(int id) {
/* 134 */     if (this.externalToInternal.containsKey(id)) {
/* 135 */       short internalId = this.externalToInternal.get(id);
/* 136 */       return this.internalIdCount.get(internalId);
/*     */     } 
/* 138 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IntSet values() {
/* 144 */     return (IntSet)new IntOpenHashSet((IntCollection)this.externalToInternal.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachValue(IntConsumer consumer) {
/* 149 */     this.externalToInternal.keySet().forEach(consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Int2ShortMap valueCounts() {
/* 155 */     Int2ShortOpenHashMap int2ShortOpenHashMap = new Int2ShortOpenHashMap();
/* 156 */     for (ObjectIterator<Short2ShortMap.Entry> objectIterator = this.internalIdCount.short2ShortEntrySet().iterator(); objectIterator.hasNext(); ) { Short2ShortMap.Entry entry = objectIterator.next();
/* 157 */       short internalId = entry.getShortKey();
/* 158 */       short count = entry.getShortValue();
/*     */       
/* 160 */       int externalId = this.internalToExternal.get(internalId);
/* 161 */       int2ShortOpenHashMap.put(externalId, count); }
/*     */     
/* 163 */     return (Int2ShortMap)int2ShortOpenHashMap;
/*     */   }
/*     */   
/*     */   private void createBlockId(short internalId, int blockId) {
/* 167 */     this.internalToExternal.put(internalId, blockId);
/* 168 */     this.externalToInternal.put(blockId, internalId);
/* 169 */     this.internalIdSet.set(internalId);
/* 170 */     this.internalIdCount.put(internalId, (short)1);
/*     */   }
/*     */   
/*     */   private boolean decrementBlockCount(short internalId) {
/* 174 */     short oldCount = this.internalIdCount.get(internalId);
/* 175 */     if (oldCount == 1) {
/* 176 */       this.internalIdCount.remove(internalId);
/* 177 */       int externalId = this.internalToExternal.remove(internalId);
/* 178 */       this.externalToInternal.remove(externalId);
/* 179 */       this.internalIdSet.clear(internalId);
/* 180 */       return true;
/*     */     } 
/* 182 */     this.internalIdCount.mergeShort(internalId, (short)1, NumberUtil::subtract);
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void incrementBlockCount(short internalId) {
/* 188 */     this.internalIdCount.mergeShort(internalId, (short)1, NumberUtil::sum);
/*     */   }
/*     */ 
/*     */   
/*     */   private int nextInternalId(short oldInternalId) {
/* 193 */     if (this.internalIdCount.get(oldInternalId) == 1)
/*     */     {
/* 195 */       return oldInternalId;
/*     */     }
/*     */     
/* 198 */     return this.internalIdSet.nextClearBit(0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean isValidInternalId(int paramInt);
/*     */ 
/*     */   
/*     */   public void serializeForPacket(@Nonnull ByteBuf buf) {
/* 206 */     buf.writeShortLE(this.internalToExternal.size());
/* 207 */     for (ObjectIterator<Short2IntMap.Entry> objectIterator = this.internalToExternal.short2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Short2IntMap.Entry entry = objectIterator.next();
/* 208 */       short internalId = entry.getShortKey();
/* 209 */       int externalId = entry.getIntValue();
/* 210 */       buf.writeShortLE(internalId & 0xFFFF);
/* 211 */       buf.writeIntLE(externalId);
/* 212 */       buf.writeShortLE(this.internalIdCount.get(internalId)); }
/*     */ 
/*     */     
/* 215 */     for (int i = 0; i < this.blocks.length; i++) {
/* 216 */       buf.writeShortLE(this.blocks[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ISectionPalette.KeySerializer keySerializer, @Nonnull ByteBuf buf) {
/* 223 */     buf.writeShort(this.internalToExternal.size());
/* 224 */     for (ObjectIterator<Short2IntMap.Entry> objectIterator = this.internalToExternal.short2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Short2IntMap.Entry entry = objectIterator.next();
/* 225 */       short internalId = entry.getShortKey();
/* 226 */       int externalId = entry.getIntValue();
/*     */ 
/*     */       
/* 229 */       buf.writeShort(internalId & 0xFFFF);
/* 230 */       keySerializer.serialize(buf, externalId);
/*     */       
/* 232 */       buf.writeShort(this.internalIdCount.get(internalId)); }
/*     */ 
/*     */ 
/*     */     
/* 236 */     for (int i = 0; i < this.blocks.length; i++) {
/* 237 */       buf.writeShort(this.blocks[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void deserialize(@Nonnull ToIntFunction<ByteBuf> deserializer, @Nonnull ByteBuf buf, int version) {
/*     */     Short2ShortOpenHashMap short2ShortOpenHashMap;
/* 243 */     this.externalToInternal.clear();
/* 244 */     this.internalToExternal.clear();
/* 245 */     this.internalIdSet.clear();
/* 246 */     this.internalIdCount.clear();
/*     */     
/* 248 */     Short2ShortMap internalIdRemapping = null;
/*     */ 
/*     */     
/* 251 */     int blockCount = buf.readShort();
/*     */     int i;
/* 253 */     for (i = 0; i < blockCount; i++) {
/*     */       
/* 255 */       short internalId = buf.readShort();
/* 256 */       int externalId = deserializer.applyAsInt(buf);
/*     */       
/* 258 */       short count = buf.readShort();
/*     */ 
/*     */       
/* 261 */       if (this.externalToInternal.containsKey(externalId)) {
/* 262 */         short existingInternalId = this.externalToInternal.get(externalId);
/*     */         
/* 264 */         if (internalIdRemapping == null) short2ShortOpenHashMap = new Short2ShortOpenHashMap(); 
/* 265 */         short2ShortOpenHashMap.put(internalId, existingInternalId);
/*     */         
/* 267 */         this.internalIdCount.mergeShort(existingInternalId, count, NumberUtil::sum);
/*     */       }
/*     */       else {
/*     */         
/* 271 */         this.externalToInternal.put(externalId, internalId);
/* 272 */         this.internalToExternal.put(internalId, externalId);
/* 273 */         this.internalIdSet.set(internalId);
/* 274 */         this.internalIdCount.put(internalId, count);
/*     */       } 
/*     */     } 
/*     */     
/* 278 */     for (i = 0; i < this.blocks.length; i++) {
/* 279 */       this.blocks[i] = buf.readShort();
/*     */     }
/*     */ 
/*     */     
/* 283 */     if (short2ShortOpenHashMap != null) {
/* 284 */       for (i = 0; i < 32768; i++) {
/* 285 */         short oldInternalId = get0(i);
/* 286 */         if (short2ShortOpenHashMap.containsKey(oldInternalId)) {
/* 287 */           set0(i, short2ShortOpenHashMap.get(oldInternalId));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void find(@Nonnull IntList ids, @Nonnull IntSet internalIdHolder, @Nonnull IntConsumer indexConsumer) {
/*     */     int i;
/* 295 */     for (i = 0; i < ids.size(); i++) {
/* 296 */       short internal = this.externalToInternal.getOrDefault(ids.getInt(i), -32768);
/* 297 */       if (internal != Short.MIN_VALUE) {
/* 298 */         internalIdHolder.add(internal);
/*     */       }
/*     */     } 
/*     */     
/* 302 */     for (i = 0; i < 32768; i++) {
/* 303 */       short type = get0(i);
/* 304 */       if (internalIdHolder.contains(type))
/* 305 */         indexConsumer.accept(i); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\palette\AbstractShortSectionPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */