/*     */ package com.hypixel.hytale.server.worldgen.container;
/*     */ 
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class CoverContainerEntryPart
/*     */ {
/*  90 */   public static final CoverContainerEntryPart[] EMPTY_ARRAY = new CoverContainerEntryPart[0];
/*     */   
/*     */   protected final BlockFluidEntry entry;
/*     */   protected final int offset;
/*     */   
/*     */   public CoverContainerEntryPart(BlockFluidEntry entry, int offset) {
/*  96 */     this.entry = entry;
/*  97 */     this.offset = offset;
/*     */   }
/*     */   
/*     */   public BlockFluidEntry getEntry() {
/* 101 */     return this.entry;
/*     */   }
/*     */   
/*     */   public int getOffset() {
/* 105 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 111 */     return "CoverContainerEntryPart{entry=" + String.valueOf(this.entry) + ", offset=" + this.offset + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\CoverContainer$CoverContainerEntry$CoverContainerEntryPart.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */