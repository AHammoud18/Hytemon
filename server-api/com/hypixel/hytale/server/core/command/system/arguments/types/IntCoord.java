/*     */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntCoord
/*     */ {
/*     */   private final int value;
/*     */   private final boolean height;
/*     */   private final boolean relative;
/*     */   private final boolean chunk;
/*     */   
/*     */   public IntCoord(int value, boolean height, boolean relative, boolean chunk) {
/*  21 */     this.value = value;
/*  22 */     this.height = height;
/*  23 */     this.relative = relative;
/*  24 */     this.chunk = chunk;
/*     */   }
/*     */   
/*     */   public int getValue() {
/*  28 */     return this.value;
/*     */   }
/*     */   
/*     */   public boolean isNotBase() {
/*  32 */     return (!this.height && !this.relative && !this.chunk);
/*     */   }
/*     */   
/*     */   public boolean isHeight() {
/*  36 */     return this.height;
/*     */   }
/*     */   
/*     */   public boolean isRelative() {
/*  40 */     return this.relative;
/*     */   }
/*     */   
/*     */   public boolean isChunk() {
/*  44 */     return this.chunk;
/*     */   }
/*     */   
/*     */   public int resolveXZ(int base) {
/*  48 */     return resolve(base);
/*     */   }
/*     */   
/*     */   public int resolveYAtWorldCoords(int base, @Nonnull ChunkStore chunkStore, int x, int z) {
/*  52 */     if (this.height) {
/*  53 */       long chunkIndex = ChunkUtil.indexChunkFromBlock(x, z);
/*  54 */       Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*     */       
/*  56 */       if (chunkRef == null || !chunkRef.isValid()) {
/*  57 */         return resolve(base);
/*     */       }
/*     */       
/*  60 */       WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getStore().getComponent(chunkRef, WorldChunk.getComponentType());
/*  61 */       assert worldChunkComponent != null;
/*     */       
/*  63 */       return resolve(worldChunkComponent.getHeight(x, z) + 1);
/*     */     } 
/*  65 */     return resolve(base);
/*     */   }
/*     */   
/*     */   protected int resolve(int base) {
/*  69 */     int val = this.chunk ? (this.value * 32) : this.value;
/*  70 */     return this.relative ? (val + base) : val;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static IntCoord parse(@Nonnull String str) {
/*  75 */     boolean height = false;
/*  76 */     boolean relative = false;
/*  77 */     boolean chunk = false;
/*     */     
/*  79 */     int index = 0;
/*     */     
/*     */     while (true) {
/*  82 */       switch (str.charAt(index)) {
/*     */         case '_':
/*  84 */           index++;
/*  85 */           height = true;
/*  86 */           if (str.length() == index) return new IntCoord(0, true, relative, chunk); 
/*     */           continue;
/*     */         case '~':
/*  89 */           index++;
/*  90 */           relative = true;
/*  91 */           if (str.length() == index) return new IntCoord(0, height, true, chunk); 
/*     */           continue;
/*     */         case 'c':
/*  94 */           index++;
/*  95 */           chunk = true;
/*  96 */           if (str.length() == index) return new IntCoord(0, height, relative, true);
/*     */           
/*     */           continue;
/*     */       } 
/*     */       
/*     */       break;
/*     */     } 
/* 103 */     String rest = str.substring(index);
/* 104 */     return new IntCoord(Integer.parseInt(rest), height, relative, chunk);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\IntCoord.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */