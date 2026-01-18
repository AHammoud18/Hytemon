/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section.palette;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.BitUtil;
/*     */ import com.hypixel.hytale.protocol.packets.world.PaletteType;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.BitSet;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HalfByteSectionPalette
/*     */   extends AbstractByteSectionPalette
/*     */ {
/*     */   private static final int KEY_MASK = 15;
/*     */   public static final int MAX_SIZE = 16;
/*     */   
/*     */   public HalfByteSectionPalette() {
/*  25 */     super(new byte[16384]);
/*     */   }
/*     */   
/*     */   public HalfByteSectionPalette(Int2ByteMap externalToInternal, Byte2IntMap internalToExternal, BitSet internalIdSet, Byte2ShortMap internalIdCount, byte[] blocks) {
/*  29 */     super(externalToInternal, internalToExternal, internalIdSet, internalIdCount, blocks);
/*     */   }
/*     */   
/*     */   public HalfByteSectionPalette(@Nonnull int[] data, int[] unique, int count) {
/*  33 */     super(new byte[16384], data, unique, count);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PaletteType getPaletteType() {
/*  39 */     return PaletteType.HalfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void set0(int idx, byte b) {
/*  44 */     BitUtil.setNibble(this.blocks, idx, b);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte get0(int idx) {
/*  49 */     return BitUtil.getNibble(this.blocks, idx);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDemote() {
/*  54 */     return isSolid(0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ISectionPalette demote() {
/*  60 */     return EmptySectionPalette.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ByteSectionPalette promote() {
/*  66 */     return ByteSectionPalette.fromHalfBytePalette(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValidInternalId(int internalId) {
/*  71 */     return ((internalId & 0xF) == internalId);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int unsignedInternalId(byte internalId) {
/*  76 */     return internalId & 0xF;
/*     */   }
/*     */   
/*     */   private static int sUnsignedInternalId(byte internalId) {
/*  80 */     return internalId & 0xF;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static HalfByteSectionPalette fromBytePalette(@Nonnull ByteSectionPalette section) {
/*  85 */     if (section.count() > 16) {
/*  86 */       throw new IllegalStateException("Cannot demote byte palette to half byte palette. Too many blocks! Count: " + section.count());
/*     */     }
/*  88 */     HalfByteSectionPalette halfByteSection = new HalfByteSectionPalette();
/*     */     
/*  90 */     Byte2ByteOpenHashMap byte2ByteOpenHashMap = new Byte2ByteOpenHashMap();
/*     */ 
/*     */     
/*  93 */     halfByteSection.internalToExternal.clear();
/*  94 */     halfByteSection.externalToInternal.clear();
/*     */ 
/*     */     
/*  97 */     halfByteSection.internalIdSet.clear(); ObjectIterator<Byte2IntMap.Entry> objectIterator;
/*  98 */     for (objectIterator = section.internalToExternal.byte2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2IntMap.Entry entry = objectIterator.next();
/*  99 */       byte oldInternalId = entry.getByteKey();
/* 100 */       int externalId = entry.getIntValue();
/*     */       
/* 102 */       byte newInternalId = (byte)halfByteSection.internalIdSet.nextClearBit(0);
/* 103 */       halfByteSection.internalIdSet.set(sUnsignedInternalId(newInternalId));
/* 104 */       byte2ByteOpenHashMap.put(oldInternalId, newInternalId);
/* 105 */       halfByteSection.internalToExternal.put(newInternalId, externalId);
/* 106 */       halfByteSection.externalToInternal.put(externalId, newInternalId); }
/*     */ 
/*     */ 
/*     */     
/* 110 */     halfByteSection.internalIdCount.clear();
/* 111 */     for (objectIterator = section.internalIdCount.byte2ShortEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2ShortMap.Entry entry = (Byte2ShortMap.Entry)objectIterator.next();
/* 112 */       byte internalId = entry.getByteKey();
/* 113 */       short count = entry.getShortValue();
/*     */       
/* 115 */       byte newInternalId = byte2ByteOpenHashMap.get(internalId);
/* 116 */       halfByteSection.internalIdCount.put(newInternalId, count); }
/*     */ 
/*     */ 
/*     */     
/* 120 */     for (int i = 0; i < section.blocks.length; i++) {
/* 121 */       byte internalId = section.blocks[i];
/* 122 */       byte byteInternalId = byte2ByteOpenHashMap.get(internalId);
/* 123 */       halfByteSection.set0(i, byteInternalId);
/*     */     } 
/* 125 */     return halfByteSection;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\palette\HalfByteSectionPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */