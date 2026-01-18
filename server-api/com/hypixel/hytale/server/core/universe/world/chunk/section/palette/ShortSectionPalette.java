/*    */ package com.hypixel.hytale.server.core.universe.world.chunk.section.palette;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.world.PaletteType;
/*    */ import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ShortMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import it.unimi.dsi.fastutil.shorts.Short2IntMap;
/*    */ import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
/*    */ import it.unimi.dsi.fastutil.shorts.Short2ShortOpenHashMap;
/*    */ import java.util.BitSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShortSectionPalette
/*    */   extends AbstractShortSectionPalette
/*    */ {
/*    */   private static final int KEY_MASK = 65535;
/*    */   public static final int MAX_SIZE = 65536;
/*    */   public static final int DEMOTE_SIZE = 251;
/*    */   
/*    */   public ShortSectionPalette() {
/* 27 */     super(new short[32768]);
/*    */   }
/*    */   
/*    */   public ShortSectionPalette(Int2ShortMap externalToInternal, Short2IntMap internalToExternal, BitSet internalIdSet, Short2ShortMap internalIdCount, short[] blocks) {
/* 31 */     super(externalToInternal, internalToExternal, internalIdSet, internalIdCount, blocks);
/*    */   }
/*    */   
/*    */   public ShortSectionPalette(@Nonnull int[] data, int[] unique, int count) {
/* 35 */     super(new short[32768], data, unique, count);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PaletteType getPaletteType() {
/* 41 */     return PaletteType.Short;
/*    */   }
/*    */ 
/*    */   
/*    */   protected short get0(int idx) {
/* 46 */     return this.blocks[idx];
/*    */   }
/*    */ 
/*    */   
/*    */   protected void set0(int idx, short s) {
/* 51 */     this.blocks[idx] = s;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldDemote() {
/* 56 */     return (count() <= 251);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ByteSectionPalette demote() {
/* 62 */     return ByteSectionPalette.fromShortPalette(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ISectionPalette promote() {
/* 67 */     throw new UnsupportedOperationException("Short palette cannot be promoted.");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isValidInternalId(int internalId) {
/* 72 */     return ((internalId & 0xFFFF) == internalId);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ShortSectionPalette fromBytePalette(@Nonnull ByteSectionPalette section) {
/* 77 */     Int2ShortOpenHashMap int2ShortOpenHashMap = new Int2ShortOpenHashMap();
/* 78 */     Short2IntOpenHashMap short2IntOpenHashMap = new Short2IntOpenHashMap();
/* 79 */     BitSet shortInternalIdSet = new BitSet(section.internalToExternal.size());
/* 80 */     Short2ShortOpenHashMap short2ShortOpenHashMap = new Short2ShortOpenHashMap();
/* 81 */     for (ObjectIterator<Byte2IntMap.Entry> objectIterator = section.internalToExternal.byte2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2IntMap.Entry entry = objectIterator.next();
/* 82 */       short internal = (short)(entry.getByteKey() & 0xFF);
/* 83 */       int external = entry.getIntValue();
/*    */       
/* 85 */       short2IntOpenHashMap.put(internal, external);
/* 86 */       int2ShortOpenHashMap.put(external, internal);
/* 87 */       shortInternalIdSet.set(internal);
/* 88 */       short2ShortOpenHashMap.put(internal, section.internalIdCount.get(entry.getByteKey())); }
/*    */ 
/*    */     
/* 91 */     short[] shortBlocks = new short[32768];
/* 92 */     for (int i = 0; i < 32768; i++) {
/* 93 */       shortBlocks[i] = (short)(section.blocks[i] & 0xFF);
/*    */     }
/*    */     
/* 96 */     return new ShortSectionPalette((Int2ShortMap)int2ShortOpenHashMap, (Short2IntMap)short2IntOpenHashMap, shortInternalIdSet, (Short2ShortMap)short2ShortOpenHashMap, shortBlocks);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\palette\ShortSectionPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */