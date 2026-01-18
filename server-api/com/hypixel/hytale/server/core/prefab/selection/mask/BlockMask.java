/*     */ package com.hypixel.hytale.server.core.prefab.selection.mask;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.function.FunctionCodec;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockMask
/*     */ {
/*  17 */   public static final BlockMask EMPTY = new BlockMask(BlockFilter.EMPTY_ARRAY);
/*  18 */   public static final Codec<BlockMask> CODEC = (Codec<BlockMask>)new FunctionCodec((Codec)Codec.STRING, BlockMask::parse, BlockMask::toString);
/*     */   
/*     */   public static final String MASK_SEPARATOR = ",";
/*     */   
/*     */   public static final String ALT_MASK_SEPARATOR = ";";
/*     */   
/*     */   public static final String EMPTY_MASK_CHARACTER = "-";
/*     */   private final BlockFilter[] filters;
/*     */   private boolean inverted;
/*     */   
/*     */   public BlockMask(BlockFilter[] filters) {
/*  29 */     this.filters = filters;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockMask withOptions(@Nonnull BlockFilter.FilterType filterType, boolean inverted) {
/*  34 */     if (this == EMPTY) return this;
/*     */ 
/*     */     
/*  37 */     BlockFilter[] filters = this.filters;
/*  38 */     for (BlockFilter filter : filters) {
/*  39 */       if (filter.getBlockFilterType() != filterType || filter.isInverted() != inverted) {
/*  40 */         filters = new BlockFilter[filters.length];
/*     */         break;
/*     */       } 
/*     */     } 
/*  44 */     if (filters == this.filters) return this;
/*     */ 
/*     */     
/*  47 */     for (int i = 0; i < filters.length; i++) {
/*  48 */       BlockFilter filter = this.filters[i];
/*  49 */       if (filter.getBlockFilterType() != filterType || filter.isInverted() != inverted) {
/*  50 */         filter = new BlockFilter(filterType, filter.getBlocks(), inverted);
/*     */       }
/*  52 */       filters[i] = filter;
/*     */     } 
/*     */     
/*  55 */     return new BlockMask(filters);
/*     */   }
/*     */   
/*     */   public BlockFilter[] getFilters() {
/*  59 */     return this.filters;
/*     */   }
/*     */   
/*     */   public void setInverted(boolean inverted) {
/*  63 */     this.inverted = inverted;
/*     */   }
/*     */   
/*     */   public boolean isInverted() {
/*  67 */     return this.inverted;
/*     */   }
/*     */   
/*     */   public boolean isExcluded(@Nonnull ChunkAccessor accessor, int x, int y, int z, Vector3i min, Vector3i max, int blockId) {
/*  71 */     return isExcluded(accessor, x, y, z, min, max, blockId, -1);
/*     */   }
/*     */   
/*     */   public boolean isExcluded(@Nonnull ChunkAccessor accessor, int x, int y, int z, Vector3i min, Vector3i max, int blockId, int fluidId) {
/*  75 */     boolean excluded = false;
/*  76 */     for (BlockFilter filter : this.filters) {
/*  77 */       if (filter.isExcluded(accessor, x, y, z, min, max, blockId, fluidId)) {
/*  78 */         excluded = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*  82 */     return (this.inverted != excluded);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  88 */     if (this.filters.length == 0) return "-"; 
/*  89 */     String base = joinElements(",", (Object[])this.filters);
/*  90 */     return this.inverted ? ("!" + base) : base;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String informativeToString() {
/*  95 */     if (this.filters.length == 0) return "-"; 
/*  96 */     StringBuilder builder = new StringBuilder();
/*     */     
/*  98 */     if (this.inverted) builder.append("NOT("); 
/*  99 */     if (this.filters.length > 1) builder.append("(");
/*     */     
/* 101 */     for (int i = 0; i < this.filters.length; i++) {
/* 102 */       builder.append(this.filters[i].informativeToString());
/* 103 */       if (i != this.filters.length - 1) builder.append(" AND ");
/*     */     
/*     */     } 
/* 106 */     if (this.filters.length > 1) builder.append(")"); 
/* 107 */     if (this.inverted) builder.append(")");
/*     */     
/* 109 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected static String joinElements(String separator, @Nonnull Object[] elements) {
/* 115 */     StringBuilder sb = new StringBuilder();
/* 116 */     for (Object o : elements) {
/* 117 */       if (!sb.isEmpty()) sb.append(separator); 
/* 118 */       sb.append(o);
/*     */     } 
/* 120 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static BlockMask parse(@Nonnull String masks) {
/* 124 */     if (masks.isEmpty() || masks.equals("-")) return EMPTY; 
/* 125 */     masks = masks.replace(";", ",");
/* 126 */     return parse(masks.split(","));
/*     */   }
/*     */   
/*     */   public static BlockMask parse(@Nonnull String[] masks) {
/* 130 */     if (masks.length == 0) return EMPTY; 
/* 131 */     if (masks.length == 1) return new BlockMask(new BlockFilter[] { BlockFilter.parse(masks[0]) });
/*     */     
/* 133 */     BlockFilter[] parsedFilters = new BlockFilter[masks.length];
/* 134 */     for (int i = 0; i < masks.length; i++) {
/* 135 */       parsedFilters[i] = BlockFilter.parse(masks[i]);
/*     */     }
/* 137 */     return groupFilters(parsedFilters);
/*     */   }
/*     */   
/*     */   public static BlockMask combine(@Nullable BlockMask... masks) {
/* 141 */     if (masks == null || masks.length == 0) return EMPTY;
/*     */     
/* 143 */     int totalFilters = 0;
/* 144 */     for (BlockMask mask : masks) {
/* 145 */       if (mask != null && mask != EMPTY) {
/* 146 */         totalFilters += (mask.getFilters()).length;
/*     */       }
/*     */     } 
/* 149 */     if (totalFilters == 0) return EMPTY;
/*     */     
/* 151 */     BlockFilter[] allFilters = new BlockFilter[totalFilters];
/* 152 */     int idx = 0;
/* 153 */     for (BlockMask mask : masks) {
/* 154 */       if (mask != null && mask != EMPTY)
/* 155 */         for (BlockFilter filter : mask.getFilters()) {
/* 156 */           allFilters[idx++] = filter;
/*     */         } 
/*     */     } 
/* 159 */     return groupFilters(allFilters);
/*     */   }
/*     */   
/*     */   private static BlockMask groupFilters(@Nonnull BlockFilter[] inputFilters) {
/* 163 */     if (inputFilters.length == 0) return EMPTY; 
/* 164 */     if (inputFilters.length == 1) return new BlockMask(inputFilters);
/*     */     
/* 166 */     Int2ObjectLinkedOpenHashMap<List<String>> groups = new Int2ObjectLinkedOpenHashMap();
/* 167 */     for (BlockFilter filter : inputFilters) {
/* 168 */       int key = filter.getBlockFilterType().ordinal() << 1 | (filter.isInverted() ? 1 : 0);
/* 169 */       List<String> list = (List<String>)groups.computeIfAbsent(key, k -> new ArrayList());
/* 170 */       for (String block : filter.getBlocks()) {
/* 171 */         list.add(block);
/*     */       }
/*     */     } 
/*     */     
/* 175 */     if (groups.size() == inputFilters.length) {
/* 176 */       return new BlockMask(inputFilters);
/*     */     }
/*     */     
/* 179 */     BlockFilter[] filters = new BlockFilter[groups.size()];
/* 180 */     int i = 0;
/* 181 */     for (ObjectBidirectionalIterator<Int2ObjectMap.Entry<List<String>>> objectBidirectionalIterator = groups.int2ObjectEntrySet().iterator(); objectBidirectionalIterator.hasNext(); ) { Int2ObjectMap.Entry<List<String>> entry = objectBidirectionalIterator.next();
/* 182 */       int key = entry.getIntKey();
/* 183 */       BlockFilter.FilterType filterType = BlockFilter.FilterType.values()[key >> 1];
/* 184 */       boolean inverted = ((key & 0x1) != 0);
/* 185 */       String[] blocks = (String[])((List)entry.getValue()).toArray((Object[])new String[0]);
/* 186 */       filters[i++] = new BlockFilter(filterType, blocks, inverted); }
/*     */     
/* 188 */     return new BlockMask(filters);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\mask\BlockMask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */