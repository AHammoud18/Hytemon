/*     */ package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nullable;
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
/*     */ public interface BlockMaskConstants
/*     */ {
/*     */   public static final int ID_IS_BYTE = 1;
/*     */   public static final int ID_IS_SHORT = 2;
/*     */   public static final int ID_IS_INT = 3;
/*     */   public static final int ID_MASK = 3;
/*     */   public static final int HAS_CHANCE = 4;
/*     */   public static final int OFFSET_IS_BYTE = 8;
/*     */   public static final int OFFSET_IS_SHORT = 16;
/*     */   public static final int OFFSET_IS_INT = 24;
/*     */   public static final int OFFSET_MASK = 24;
/*     */   public static final int HAS_COMPONENTS = 32;
/*     */   public static final int FLUID_IS_BYTE = 64;
/*     */   public static final int FLUID_IS_SHORT = 128;
/*     */   public static final int FLUID_IS_INT = 192;
/*     */   public static final int FLUID_MASK = 192;
/*     */   public static final int SUPPORT_MASK = 3840;
/*     */   public static final int SUPPORT_OFFSET = 8;
/*     */   public static final int HAS_FILLER = 4096;
/*     */   public static final int HAS_ROTATION = 8192;
/*     */   
/*     */   static int getBlockMask(int blockBytes, int fluidBytes, boolean chance, int offsetBytes, @Nullable Holder<ChunkStore> holder, byte supportValue, int rotation, int filler) {
/* 116 */     int mask = 0;
/*     */ 
/*     */     
/* 119 */     switch (blockBytes) {
/*     */       case 0:
/*     */         break;
/*     */       case 1:
/* 123 */         mask |= 0x1;
/*     */         break;
/*     */       case 2:
/* 126 */         mask |= 0x2;
/*     */         break;
/*     */       case 4:
/* 129 */         mask |= 0x3;
/*     */         break;
/*     */       default:
/* 132 */         throw new IllegalArgumentException("Unsupported amount of bytes for blocks (0, 1, 2, 4). Given: " + blockBytes);
/*     */     } 
/*     */ 
/*     */     
/* 136 */     if (chance) mask |= 0x4;
/*     */ 
/*     */     
/* 139 */     switch (offsetBytes) {
/*     */       case 0:
/*     */         break;
/*     */       case 1:
/* 143 */         mask |= 0x8;
/*     */         break;
/*     */       case 2:
/* 146 */         mask |= 0x10;
/*     */         break;
/*     */       case 4:
/* 149 */         mask |= 0x18;
/*     */         break;
/*     */       default:
/* 152 */         throw new IllegalArgumentException("Unsupported amount of bytes for offset (0, 1, 2, 4). Given: " + offsetBytes);
/*     */     } 
/*     */ 
/*     */     
/* 156 */     if (holder != null) mask |= 0x20;
/*     */ 
/*     */     
/* 159 */     switch (fluidBytes) {
/*     */       case 0:
/*     */         break;
/*     */       case 1:
/* 163 */         mask |= 0x40;
/*     */         break;
/*     */       case 2:
/* 166 */         mask |= 0x80;
/*     */         break;
/*     */       case 4:
/* 169 */         mask |= 0xC0;
/*     */         break;
/*     */       default:
/* 172 */         throw new IllegalArgumentException("Unsupported amount of bytes for fluids (0, 1, 2, 4). Given: " + fluidBytes);
/*     */     } 
/*     */     
/* 175 */     mask |= supportValue << 8 & 0xF00;
/*     */     
/* 177 */     if (filler != 0) {
/* 178 */       mask |= 0x1000;
/*     */     }
/*     */     
/* 181 */     if (rotation != 0) {
/* 182 */       mask |= 0x2000;
/*     */     }
/*     */     
/* 185 */     return mask;
/*     */   }
/*     */   
/*     */   static int getSkipBytes(int mask) {
/* 189 */     int bytes = 0;
/* 190 */     bytes += getBlockBytes(mask);
/* 191 */     bytes += getOffsetBytes(mask);
/* 192 */     if (hasChance(mask)) bytes += 4; 
/* 193 */     bytes += getFluidBytes(mask);
/* 194 */     if (hasFiller(mask)) bytes += 2; 
/* 195 */     if (hasRotation(mask)) bytes++; 
/* 196 */     return bytes;
/*     */   }
/*     */   
/*     */   static boolean hasChance(int mask) {
/* 200 */     return ((mask & 0x4) == 4);
/*     */   }
/*     */   
/*     */   static boolean hasFiller(int mask) {
/* 204 */     return ((mask & 0x1000) == 4096);
/*     */   }
/*     */   
/*     */   static boolean hasRotation(int mask) {
/* 208 */     return ((mask & 0x2000) == 8192);
/*     */   }
/*     */   
/*     */   static int getBlockBytes(int mask) {
/* 212 */     switch (mask & 0x3) { case 1: case 2: case 3:  }  return 
/*     */ 
/*     */ 
/*     */       
/* 216 */       0;
/*     */   }
/*     */ 
/*     */   
/*     */   static int getOffsetBytes(int mask) {
/* 221 */     switch (mask & 0x18) { case 8: case 16: case 24:  }  return 
/*     */ 
/*     */ 
/*     */       
/* 225 */       0;
/*     */   }
/*     */ 
/*     */   
/*     */   static int getFluidBytes(int mask) {
/* 230 */     switch (mask & 0xC0) { case 64: case 128: case 192:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 235 */       0;
/*     */   }
/*     */ 
/*     */   
/*     */   static int getSupportValue(int mask) {
/* 240 */     return (mask & 0xF00) >> 8;
/*     */   }
/*     */   
/*     */   static boolean hasComponents(int mask) {
/* 244 */     return ((mask & 0x20) == 32);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\PrefabBuffer$BlockMaskConstants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */