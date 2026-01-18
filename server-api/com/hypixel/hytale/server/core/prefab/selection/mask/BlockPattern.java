/*     */ package com.hypixel.hytale.server.core.prefab.selection.mask;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.function.FunctionCodec;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BlockTypeListAsset;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class BlockPattern
/*     */ {
/*  25 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */   
/*  28 */   public static final Codec<BlockPattern> CODEC = (Codec<BlockPattern>)new FunctionCodec((Codec)Codec.STRING, BlockPattern::parse, BlockPattern::toString);
/*  29 */   public static final BlockPattern EMPTY = new BlockPattern(parseBlockPattern(new String[] { "Empty" }));
/*  30 */   public static final BlockPattern[] EMPTY_ARRAY = new BlockPattern[0];
/*     */   
/*  32 */   private static final Pattern FILLER_TEMP_REMOVER_PATTERN = Pattern.compile("(Filler=-?\\d+),(-?\\d+),(-?\\d+)");
/*     */   
/*     */   private static final String BLOCK_SEPARATOR = ",";
/*     */   
/*     */   private static final String ALT_BLOCK_SEPARATOR = ";";
/*     */   
/*     */   private static final String CHANCE_SUFFIX = "%";
/*     */   
/*     */   private static final double DEFAULT_CHANCE = 100.0D;
/*     */   
/*     */   private final IWeightedMap<String> weightedMap;
/*     */   private final transient String toString0;
/*     */   private IWeightedMap<Integer> resolvedWeightedMap;
/*     */   private IWeightedMap<BlockEntry> resolvedWeightedMapBtk;
/*     */   
/*     */   public BlockPattern(IWeightedMap<String> weightedMap) {
/*  48 */     this.weightedMap = weightedMap;
/*  49 */     this.toString0 = toString0();
/*     */   }
/*     */   
/*     */   public Integer[] getResolvedKeys() {
/*  53 */     resolve();
/*  54 */     return (Integer[])this.resolvedWeightedMap.internalKeys();
/*     */   }
/*     */   
/*     */   public void resolve() {
/*  58 */     if (this.resolvedWeightedMap != null)
/*     */       return; 
/*  60 */     WeightedMap.Builder<Integer> mapBuilder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/*  61 */     WeightedMap.Builder<BlockEntry> mapBuilderKey = WeightedMap.builder((Object[])new BlockEntry[0]);
/*  62 */     this.weightedMap.forEachEntry((blockName, weight) -> {
/*     */           int blockId = parseBlock(blockName);
/*     */           
/*     */           BlockEntry key = tryParseBlockTypeKey(blockName);
/*     */           
/*     */           BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*     */           if (blockType != null && blockType.getBlockListAssetId() != null) {
/*     */             BlockTypeListAsset blockTypeListAsset = (BlockTypeListAsset)BlockTypeListAsset.getAssetMap().getAsset(blockType.getBlockListAssetId());
/*     */             if (blockTypeListAsset != null && blockTypeListAsset.getBlockPattern() != null) {
/*     */               for (String resolvedKey : blockTypeListAsset.getBlockTypeKeys()) {
/*     */                 int resolvedId = BlockType.getAssetMap().getIndex(resolvedKey);
/*     */                 if (resolvedId == Integer.MIN_VALUE) {
/*     */                   LOGGER.at(Level.WARNING).log("BlockTypeList '%s' contains invalid block '%s' - skipping", blockType.getBlockListAssetId(), resolvedKey);
/*     */                   continue;
/*     */                 } 
/*     */                 mapBuilder.put(Integer.valueOf(resolvedId), weight / blockTypeListAsset.getBlockTypeKeys().size());
/*     */               } 
/*     */               return;
/*     */             } 
/*     */           } 
/*     */           mapBuilder.put(Integer.valueOf(blockId), weight);
/*     */           if (key != null) {
/*     */             mapBuilderKey.put(key, weight);
/*     */           }
/*     */         });
/*  87 */     this.resolvedWeightedMap = mapBuilder.build();
/*  88 */     this.resolvedWeightedMapBtk = mapBuilderKey.build();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  92 */     return (this.weightedMap.size() == 0);
/*     */   }
/*     */   
/*     */   public int nextBlock(Random random) {
/*  96 */     resolve();
/*  97 */     return ((Integer)this.resolvedWeightedMap.get(random)).intValue();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockEntry nextBlockTypeKey(Random random) {
/* 102 */     resolve();
/* 103 */     return (BlockEntry)this.resolvedWeightedMapBtk.get(random);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int firstBlock() {
/* 109 */     resolve();
/* 110 */     return (this.resolvedWeightedMap.size() > 0) ? ((Integer[])this.resolvedWeightedMap.internalKeys())[0].intValue() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 115 */     return this.toString0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String toString0() {
/* 121 */     if (this.weightedMap.size() == 1) return ((String[])this.weightedMap.internalKeys())[0];
/*     */     
/* 123 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 124 */     this.weightedMap.forEachEntry((k, v) -> blocks.add("" + v + "%" + v));
/* 125 */     return String.join(",", (Iterable<? extends CharSequence>)objectArrayList);
/*     */   }
/*     */   
/*     */   public static BlockPattern parse(@Nonnull String str) {
/* 129 */     if (str.isEmpty() || str.equals("Empty")) return EMPTY; 
/* 130 */     if (str.toLowerCase().contains("filler="))
/* 131 */       str = FILLER_TEMP_REMOVER_PATTERN.matcher(str).replaceAll("$1;$2;$3"); 
/* 132 */     str = str.replace(";", ",");
/* 133 */     return new BlockPattern(parseBlockPattern(str.split(",")));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static IWeightedMap<String> parseBlockPattern(@Nonnull String... blocksArgs) {
/* 138 */     WeightedMap.Builder<String> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_STRING_ARRAY);
/*     */     
/* 140 */     for (String blockArg : blocksArgs) {
/* 141 */       if (!blockArg.isEmpty()) {
/*     */         
/* 143 */         double chance = 100.0D;
/* 144 */         String[] blockArr = blockArg.split("%");
/* 145 */         if (blockArr.length > 1) {
/*     */           try {
/* 147 */             chance = Double.parseDouble(blockArr[0]);
/* 148 */           } catch (NumberFormatException e) {
/* 149 */             throw new IllegalArgumentException("Invalid Chance Value: " + blockArr[0], e);
/*     */           } 
/*     */           
/* 152 */           blockArg = blockArr[1];
/*     */         } 
/*     */         
/* 155 */         builder.put(blockArg, chance);
/*     */       } 
/*     */     } 
/* 158 */     return builder.build();
/*     */   }
/*     */   
/*     */   public static int parseBlock(@Nonnull String blockText) {
/*     */     int blockId;
/*     */     try {
/* 164 */       blockId = Integer.parseInt(blockText);
/* 165 */       if (BlockType.getAssetMap().getAsset(blockId) == null) {
/* 166 */         throw new IllegalArgumentException("Block with id '" + blockText + "' doesn't exist!");
/*     */       }
/* 168 */     } catch (NumberFormatException ignored) {
/* 169 */       blockText = blockText.replace(";", ",");
/* 170 */       int oldData = blockText.indexOf('|');
/* 171 */       if (oldData != -1) blockText = blockText.substring(0, oldData); 
/* 172 */       blockId = BlockType.getAssetMap().getIndex(blockText);
/*     */     } 
/*     */     
/* 175 */     return blockId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockEntry tryParseBlockTypeKey(String blockText) {
/*     */     try {
/* 181 */       blockText = blockText.replace(";", ",");
/* 182 */       return BlockEntry.decode(blockText);
/* 183 */     } catch (Exception e) {
/* 184 */       return null;
/*     */     } 
/*     */   }
/*     */   public static final class BlockEntry extends Record { private final String blockTypeKey; private final int rotation; private final int filler;
/* 188 */     public BlockEntry(String blockTypeKey, int rotation, int filler) { this.blockTypeKey = blockTypeKey; this.rotation = rotation; this.filler = filler; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 188 */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry; } public String blockTypeKey() { return this.blockTypeKey; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;
/* 188 */       //   0	8	1	o	Ljava/lang/Object; } public int rotation() { return this.rotation; } public int filler() { return this.filler; }
/*     */     
/*     */     @Deprecated(forRemoval = true)
/* 191 */     public static Codec<BlockEntry> CODEC = (Codec<BlockEntry>)new FunctionCodec((Codec)Codec.STRING, BlockEntry::decode, BlockEntry::encode);
/*     */     
/*     */     @Deprecated(forRemoval = true)
/*     */     private String encode() {
/* 195 */       if (this.filler == 0 && this.rotation == 0) return this.blockTypeKey; 
/* 196 */       StringBuilder out = new StringBuilder(this.blockTypeKey);
/* 197 */       RotationTuple rot = RotationTuple.get(this.rotation);
/* 198 */       if (rot.yaw() != Rotation.None) {
/* 199 */         out.append("|Yaw=").append(rot.yaw().getDegrees());
/*     */       }
/* 201 */       if (rot.pitch() != Rotation.None) {
/* 202 */         out.append("|Pitch=").append(rot.pitch().getDegrees());
/*     */       }
/* 204 */       if (rot.roll() != Rotation.None) {
/* 205 */         out.append("|Roll=").append(rot.roll().getDegrees());
/*     */       }
/* 207 */       if (this.filler != 0) {
/* 208 */         int fillerX = FillerBlockUtil.unpackX(this.filler);
/* 209 */         int fillerY = FillerBlockUtil.unpackY(this.filler);
/* 210 */         int fillerZ = FillerBlockUtil.unpackZ(this.filler);
/* 211 */         out.append("|Filler=").append(fillerX).append(",").append(fillerY).append(",").append(fillerZ);
/*     */       } 
/* 213 */       return out.toString();
/*     */     }
/*     */     
/*     */     @Deprecated(forRemoval = true)
/*     */     public static BlockEntry decode(String key) {
/* 218 */       int filler = 0;
/* 219 */       if (key.contains("|Filler=")) {
/* 220 */         int start = key.indexOf("|Filler=") + "|Filler=".length();
/* 221 */         int firstComma = key.indexOf(',', start);
/* 222 */         if (firstComma == -1) throw new IllegalArgumentException("Invalid filler metadata! Missing comma"); 
/* 223 */         int secondComma = key.indexOf(',', firstComma + 1);
/* 224 */         if (secondComma == -1) throw new IllegalArgumentException("Invalid filler metadata! Missing second comma");
/*     */         
/* 226 */         int i = key.indexOf('|', start);
/* 227 */         if (i == -1) i = key.length();
/*     */         
/* 229 */         int fillerX = Integer.parseInt(key, start, firstComma, 10);
/* 230 */         int fillerY = Integer.parseInt(key, firstComma + 1, secondComma, 10);
/* 231 */         int fillerZ = Integer.parseInt(key, secondComma + 1, i, 10);
/*     */         
/* 233 */         filler = FillerBlockUtil.pack(fillerX, fillerY, fillerZ);
/*     */       } 
/*     */       
/* 236 */       Rotation rotationYaw = Rotation.None;
/* 237 */       Rotation rotationPitch = Rotation.None;
/* 238 */       Rotation rotationRoll = Rotation.None;
/*     */       
/* 240 */       if (key.contains("|Yaw=")) {
/* 241 */         int start = key.indexOf("|Yaw=") + "|Yaw=".length();
/* 242 */         int i = key.indexOf('|', start);
/* 243 */         if (i == -1) i = key.length(); 
/* 244 */         rotationYaw = Rotation.ofDegrees(Integer.parseInt(key, start, i, 10));
/*     */       } 
/* 246 */       if (key.contains("|Pitch=")) {
/* 247 */         int start = key.indexOf("|Pitch=") + "|Pitch=".length();
/* 248 */         int i = key.indexOf('|', start);
/* 249 */         if (i == -1) i = key.length(); 
/* 250 */         rotationPitch = Rotation.ofDegrees(Integer.parseInt(key, start, i, 10));
/*     */       } 
/* 252 */       if (key.contains("|Roll=")) {
/* 253 */         int start = key.indexOf("|Roll=") + "|Roll=".length();
/* 254 */         int i = key.indexOf('|', start);
/* 255 */         if (i == -1) i = key.length(); 
/* 256 */         rotationRoll = Rotation.ofDegrees(Integer.parseInt(key, start, i, 10));
/*     */       } 
/*     */ 
/*     */       
/* 260 */       int end = key.indexOf('|');
/* 261 */       if (end == -1) end = key.length(); 
/* 262 */       String name = key.substring(0, end);
/* 263 */       return new BlockEntry(name, RotationTuple.of(rotationYaw, rotationPitch, rotationRoll).index(), filler);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\mask\BlockPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */