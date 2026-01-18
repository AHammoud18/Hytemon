/*     */ package com.hypixel.hytale.builtin.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.DrawType;
/*     */ import com.hypixel.hytale.protocol.Opacity;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BlockColorIndex
/*     */ {
/*  22 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  24 */   private final List<BlockColorEntry> entries = new ArrayList<>();
/*     */   private boolean initialized = false;
/*     */   
/*     */   private void ensureInitialized() {
/*  28 */     if (this.initialized)
/*     */       return; 
/*  30 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*  31 */     Set<String> keys = assetMap.getAssetMap().keySet();
/*     */     
/*  33 */     for (String key : keys) {
/*  34 */       BlockType blockType = (BlockType)assetMap.getAsset(key);
/*  35 */       if (blockType == null)
/*     */         continue; 
/*  37 */       if (!isSolidCube(blockType))
/*     */         continue; 
/*  39 */       Color particleColor = blockType.getParticleColor();
/*  40 */       if (particleColor == null)
/*     */         continue; 
/*  42 */       int blockId = assetMap.getIndex(key);
/*  43 */       int r = particleColor.red & 0xFF;
/*  44 */       int g = particleColor.green & 0xFF;
/*  45 */       int b = particleColor.blue & 0xFF;
/*  46 */       double[] lab = rgbToLab(r, g, b);
/*     */       
/*  48 */       this.entries.add(new BlockColorEntry(blockId, key, r, g, b, lab[0], lab[1], lab[2]));
/*     */     } 
/*     */ 
/*     */     
/*  52 */     this.entries.sort(Comparator.comparingDouble(e -> e.labL));
/*     */     
/*  54 */     this.initialized = true;
/*  55 */     LOGGER.at(Level.INFO).log("BlockColorIndex initialized with %d solid cube blocks", this.entries.size());
/*     */   }
/*     */   
/*     */   private boolean isSolidCube(@Nonnull BlockType blockType) {
/*  59 */     DrawType drawType = blockType.getDrawType();
/*  60 */     Opacity opacity = blockType.getOpacity();
/*  61 */     return (drawType == DrawType.Cube && opacity == Opacity.Solid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int findClosestBlock(int r, int g, int b) {
/*  70 */     ensureInitialized();
/*  71 */     if (this.entries.isEmpty()) return -1;
/*     */     
/*  73 */     double[] lab = rgbToLab(r, g, b);
/*  74 */     double targetL = lab[0], targetA = lab[1], targetB = lab[2];
/*     */     
/*  76 */     double minDist = Double.MAX_VALUE;
/*  77 */     int bestId = -1;
/*     */     
/*  79 */     for (BlockColorEntry entry : this.entries) {
/*  80 */       double dist = colorDistanceLab(targetL, targetA, targetB, entry.labL, entry.labA, entry.labB);
/*  81 */       if (dist < minDist) {
/*  82 */         minDist = dist;
/*  83 */         bestId = entry.blockId;
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     return bestId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int findDarkerVariant(int blockId, float darkenAmount) {
/*  98 */     ensureInitialized();
/*  99 */     BlockColorEntry source = findEntry(blockId);
/* 100 */     if (source == null) return blockId;
/*     */ 
/*     */     
/* 103 */     double targetL = source.labL * (1.0D - darkenAmount);
/* 104 */     double targetA = source.labA;
/* 105 */     double targetB = source.labB;
/*     */     
/* 107 */     double minDist = Double.MAX_VALUE;
/* 108 */     int bestId = blockId;
/*     */     
/* 110 */     for (BlockColorEntry entry : this.entries) {
/*     */       
/* 112 */       if (entry.labL > source.labL)
/*     */         continue; 
/* 114 */       double dist = colorDistanceLab(targetL, targetA, targetB, entry.labL, entry.labA, entry.labB);
/* 115 */       if (dist < minDist) {
/* 116 */         minDist = dist;
/* 117 */         bestId = entry.blockId;
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return bestId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockColor(int blockId) {
/* 130 */     ensureInitialized();
/* 131 */     BlockColorEntry entry = findEntry(blockId);
/* 132 */     if (entry == null) return -1; 
/* 133 */     return entry.r << 16 | entry.g << 8 | entry.b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int findBlockForLerpedColor(int rA, int gA, int bA, int rB, int gB, int bB, float t) {
/* 142 */     ensureInitialized();
/* 143 */     double[] labA = rgbToLab(rA, gA, bA);
/* 144 */     double[] labB = rgbToLab(rB, gB, bB);
/*     */     
/* 146 */     double l = labA[0] + (labB[0] - labA[0]) * t;
/* 147 */     double a = labA[1] + (labB[1] - labA[1]) * t;
/* 148 */     double b = labA[2] + (labB[2] - labA[2]) * t;
/*     */     
/* 150 */     int[] rgb = labToRgb(l, a, b);
/* 151 */     return findClosestBlock(rgb[0], rgb[1], rgb[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 158 */     ensureInitialized();
/* 159 */     return this.entries.isEmpty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockColorEntry findEntry(int blockId) {
/* 164 */     for (BlockColorEntry entry : this.entries) {
/* 165 */       if (entry.blockId == blockId) return entry; 
/*     */     } 
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static double colorDistanceLab(double l1, double a1, double b1, double l2, double a2, double b2) {
/* 172 */     double dL = l1 - l2;
/* 173 */     double dA = a1 - a2;
/* 174 */     double dB = b1 - b2;
/* 175 */     return dL * dL + dA * dA + dB * dB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] rgbToLab(int r, int g, int b) {
/* 181 */     double rn = r / 255.0D;
/* 182 */     double gn = g / 255.0D;
/* 183 */     double bn = b / 255.0D;
/*     */ 
/*     */     
/* 186 */     rn = (rn > 0.04045D) ? Math.pow((rn + 0.055D) / 1.055D, 2.4D) : (rn / 12.92D);
/* 187 */     gn = (gn > 0.04045D) ? Math.pow((gn + 0.055D) / 1.055D, 2.4D) : (gn / 12.92D);
/* 188 */     bn = (bn > 0.04045D) ? Math.pow((bn + 0.055D) / 1.055D, 2.4D) : (bn / 12.92D);
/*     */ 
/*     */     
/* 191 */     double x = rn * 0.4124564D + gn * 0.3575761D + bn * 0.1804375D;
/* 192 */     double y = rn * 0.2126729D + gn * 0.7151522D + bn * 0.072175D;
/* 193 */     double z = rn * 0.0193339D + gn * 0.119192D + bn * 0.9503041D;
/*     */ 
/*     */     
/* 196 */     x /= 0.95047D;
/* 197 */     y /= 1.0D;
/* 198 */     z /= 1.08883D;
/*     */     
/* 200 */     x = (x > 0.008856D) ? Math.cbrt(x) : (7.787D * x + 0.13793103448275862D);
/* 201 */     y = (y > 0.008856D) ? Math.cbrt(y) : (7.787D * y + 0.13793103448275862D);
/* 202 */     z = (z > 0.008856D) ? Math.cbrt(z) : (7.787D * z + 0.13793103448275862D);
/*     */     
/* 204 */     double labL = 116.0D * y - 16.0D;
/* 205 */     double labA = 500.0D * (x - y);
/* 206 */     double labB = 200.0D * (y - z);
/*     */     
/* 208 */     return new double[] { labL, labA, labB };
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] labToRgb(double labL, double labA, double labB) {
/* 213 */     double y = (labL + 16.0D) / 116.0D;
/* 214 */     double x = labA / 500.0D + y;
/* 215 */     double z = y - labB / 200.0D;
/*     */     
/* 217 */     double x3 = x * x * x;
/* 218 */     double y3 = y * y * y;
/* 219 */     double z3 = z * z * z;
/*     */     
/* 221 */     x = (x3 > 0.008856D) ? x3 : ((x - 0.13793103448275862D) / 7.787D);
/* 222 */     y = (y3 > 0.008856D) ? y3 : ((y - 0.13793103448275862D) / 7.787D);
/* 223 */     z = (z3 > 0.008856D) ? z3 : ((z - 0.13793103448275862D) / 7.787D);
/*     */     
/* 225 */     x *= 0.95047D;
/* 226 */     y *= 1.0D;
/* 227 */     z *= 1.08883D;
/*     */ 
/*     */     
/* 230 */     double rn = x * 3.2404542D + y * -1.5371385D + z * -0.4985314D;
/* 231 */     double gn = x * -0.969266D + y * 1.8760108D + z * 0.041556D;
/* 232 */     double bn = x * 0.0556434D + y * -0.2040259D + z * 1.0572252D;
/*     */ 
/*     */     
/* 235 */     rn = (rn > 0.0031308D) ? (1.055D * Math.pow(rn, 0.4166666666666667D) - 0.055D) : (12.92D * rn);
/* 236 */     gn = (gn > 0.0031308D) ? (1.055D * Math.pow(gn, 0.4166666666666667D) - 0.055D) : (12.92D * gn);
/* 237 */     bn = (bn > 0.0031308D) ? (1.055D * Math.pow(bn, 0.4166666666666667D) - 0.055D) : (12.92D * bn);
/*     */     
/* 239 */     int r = Math.max(0, Math.min(255, (int)Math.round(rn * 255.0D)));
/* 240 */     int g = Math.max(0, Math.min(255, (int)Math.round(gn * 255.0D)));
/* 241 */     int b = Math.max(0, Math.min(255, (int)Math.round(bn * 255.0D)));
/*     */     
/* 243 */     return new int[] { r, g, b };
/*     */   }
/*     */   private static final class BlockColorEntry extends Record { private final int blockId; private final String key; private final int r; private final int g; private final int b; private final double labL; private final double labA; private final double labB;
/* 246 */     private BlockColorEntry(int blockId, String key, int r, int g, int b, double labL, double labA, double labB) { this.blockId = blockId; this.key = key; this.r = r; this.g = g; this.b = b; this.labL = labL; this.labA = labA; this.labB = labB; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/BlockColorIndex$BlockColorEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #246	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 246 */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/BlockColorIndex$BlockColorEntry; } public int blockId() { return this.blockId; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/BlockColorIndex$BlockColorEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #246	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/BlockColorIndex$BlockColorEntry; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/BlockColorIndex$BlockColorEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #246	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/BlockColorIndex$BlockColorEntry;
/* 246 */       //   0	8	1	o	Ljava/lang/Object; } public String key() { return this.key; } public int r() { return this.r; } public int g() { return this.g; } public int b() { return this.b; } public double labL() { return this.labL; } public double labA() { return this.labA; } public double labB() { return this.labB; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\BlockColorIndex.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */