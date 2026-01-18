/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section.palette;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.packets.world.PaletteType;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class EmptySectionPalette
/*     */   implements ISectionPalette
/*     */ {
/*     */   public static final int EMPTY_ID = 0;
/*  18 */   public static final EmptySectionPalette INSTANCE = new EmptySectionPalette();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PaletteType getPaletteType() {
/*  26 */     return PaletteType.Empty;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ISectionPalette.SetResult set(int index, int id) {
/*  32 */     return (id == 0) ? ISectionPalette.SetResult.UNCHANGED : ISectionPalette.SetResult.REQUIRES_PROMOTE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(int index) {
/*  37 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDemote() {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISectionPalette demote() {
/*  47 */     throw new UnsupportedOperationException("Cannot demote empty chunk section!");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ISectionPalette promote() {
/*  53 */     return new HalfByteSectionPalette();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int id) {
/*  58 */     return (id == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAny(@Nonnull IntList ids) {
/*  63 */     return ids.contains(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSolid(int id) {
/*  68 */     return (id == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int count() {
/*  73 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int count(int id) {
/*  78 */     return (id == 0) ? 32768 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IntSet values() {
/*  84 */     IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
/*  85 */     intOpenHashSet.add(0);
/*  86 */     return (IntSet)intOpenHashSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachValue(@Nonnull IntConsumer consumer) {
/*  91 */     consumer.accept(0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Int2ShortMap valueCounts() {
/*  97 */     Int2ShortOpenHashMap int2ShortOpenHashMap = new Int2ShortOpenHashMap();
/*  98 */     int2ShortOpenHashMap.put(0, -32768);
/*  99 */     return (Int2ShortMap)int2ShortOpenHashMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void find(@Nonnull IntList ids, IntSet internalIdHolder, @Nonnull IntConsumer indexConsumer) {
/* 104 */     if (ids.contains(0))
/* 105 */       for (int i = 0; i < 32768; i++)
/* 106 */         indexConsumer.accept(i);  
/*     */   }
/*     */   
/*     */   public void serializeForPacket(ByteBuf buf) {}
/*     */   
/*     */   public void serialize(ISectionPalette.KeySerializer keySerializer, ByteBuf buf) {}
/*     */   
/*     */   public void deserialize(ToIntFunction<ByteBuf> deserializer, ByteBuf buf, int version) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\palette\EmptySectionPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */