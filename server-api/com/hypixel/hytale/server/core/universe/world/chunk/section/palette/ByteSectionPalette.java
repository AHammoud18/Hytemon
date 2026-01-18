/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section.palette;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.BitSetUtil;
/*     */ import com.hypixel.hytale.protocol.packets.world.PaletteType;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2IntMap;
/*     */ import java.util.BitSet;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteSectionPalette
/*     */   extends AbstractByteSectionPalette
/*     */ {
/*     */   private static final int KEY_MASK = 255;
/*     */   public static final int MAX_SIZE = 256;
/*     */   public static final int DEMOTE_SIZE = 14;
/*     */   
/*     */   public ByteSectionPalette() {
/*  27 */     super(new byte[32768]);
/*     */   }
/*     */   
/*     */   public ByteSectionPalette(Int2ByteMap externalToInternal, Byte2IntMap internalToExternal, BitSet internalIdSet, Byte2ShortMap internalIdCount, byte[] blocks) {
/*  31 */     super(externalToInternal, internalToExternal, internalIdSet, internalIdCount, blocks);
/*     */   }
/*     */   
/*     */   public ByteSectionPalette(@Nonnull int[] data, int[] unique, int count) {
/*  35 */     super(new byte[32768], data, unique, count);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PaletteType getPaletteType() {
/*  41 */     return PaletteType.Byte;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte get0(int idx) {
/*  46 */     return this.blocks[idx];
/*     */   }
/*     */ 
/*     */   
/*     */   protected void set0(int idx, byte b) {
/*  51 */     this.blocks[idx] = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDemote() {
/*  56 */     return (count() <= 14);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public HalfByteSectionPalette demote() {
/*  62 */     return HalfByteSectionPalette.fromBytePalette(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ShortSectionPalette promote() {
/*  68 */     return ShortSectionPalette.fromBytePalette(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValidInternalId(int internalId) {
/*  73 */     return ((internalId & 0xFF) == internalId);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int unsignedInternalId(byte internalId) {
/*  78 */     return internalId & 0xFF;
/*     */   }
/*     */   
/*     */   private static int sUnsignedInternalId(byte internalId) {
/*  82 */     return internalId & 0xFF;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ByteSectionPalette fromHalfBytePalette(@Nonnull HalfByteSectionPalette section) {
/*  87 */     ByteSectionPalette byteSection = new ByteSectionPalette();
/*  88 */     byteSection.externalToInternal.clear();
/*  89 */     byteSection.externalToInternal.putAll((Map)section.externalToInternal);
/*     */     
/*  91 */     byteSection.internalToExternal.clear();
/*  92 */     byteSection.internalToExternal.putAll((Map)section.internalToExternal);
/*     */     
/*  94 */     BitSetUtil.copyValues(section.internalIdSet, byteSection.internalIdSet);
/*     */     
/*  96 */     byteSection.internalIdCount.clear();
/*  97 */     byteSection.internalIdCount.putAll((Map)section.internalIdCount);
/*     */     
/*  99 */     for (int i = 0; i < byteSection.blocks.length; i++) {
/* 100 */       byteSection.blocks[i] = section.get0(i);
/*     */     }
/*     */     
/* 103 */     return byteSection;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ByteSectionPalette fromShortPalette(@Nonnull ShortSectionPalette section) {
/* 108 */     if (section.count() > 256) {
/* 109 */       throw new IllegalStateException("Cannot demote short palette to byte palette. Too many blocks! Count: " + section.count());
/*     */     }
/* 111 */     ByteSectionPalette byteSection = new ByteSectionPalette();
/*     */ 
/*     */     
/* 114 */     Short2ByteOpenHashMap short2ByteOpenHashMap = new Short2ByteOpenHashMap();
/*     */ 
/*     */     
/* 117 */     byteSection.internalToExternal.clear();
/* 118 */     byteSection.externalToInternal.clear();
/*     */ 
/*     */     
/* 121 */     byteSection.internalIdSet.clear();
/* 122 */     byteSection.internalIdCount.clear();
/* 123 */     for (ObjectIterator<Short2IntMap.Entry> objectIterator = section.internalToExternal.short2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Short2IntMap.Entry entry = objectIterator.next();
/* 124 */       short oldInternalId = entry.getShortKey();
/* 125 */       int externalId = entry.getIntValue();
/*     */       
/* 127 */       byte newInternalId = (byte)byteSection.internalIdSet.nextClearBit(0);
/* 128 */       byteSection.internalIdSet.set(sUnsignedInternalId(newInternalId));
/* 129 */       short2ByteOpenHashMap.put(oldInternalId, newInternalId);
/* 130 */       byteSection.internalToExternal.put(newInternalId, externalId);
/* 131 */       byteSection.externalToInternal.put(externalId, newInternalId);
/* 132 */       byteSection.internalIdCount.put(newInternalId, section.internalIdCount.get(oldInternalId)); }
/*     */ 
/*     */ 
/*     */     
/* 136 */     for (int i = 0; i < 32768; i++) {
/* 137 */       short internalId = section.blocks[i];
/* 138 */       byte byteInternalId = short2ByteOpenHashMap.get(internalId);
/* 139 */       byteSection.blocks[i] = byteInternalId;
/*     */     } 
/*     */     
/* 142 */     return byteSection;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\palette\ByteSectionPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */