/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import java.util.LinkedList;
/*     */ import java.util.concurrent.CompletableFuture;
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
/*     */ public class VoxelSpaceUtil
/*     */ {
/*     */   public static <V> void parallelCopy(@Nonnull VoxelSpace<V> source, @Nonnull VoxelSpace<V> destination, int concurrency) {
/*  31 */     if (concurrency < 1) {
/*  32 */       throw new IllegalArgumentException("negative concurrency");
/*     */     }
/*     */     
/*  35 */     int minX = source.minX();
/*  36 */     int minY = source.minY();
/*  37 */     int minZ = source.minZ();
/*  38 */     int sizeX = source.sizeX();
/*  39 */     int sizeY = source.sizeY();
/*  40 */     int sizeZ = source.sizeZ();
/*     */ 
/*     */ 
/*     */     
/*  44 */     LinkedList<CompletableFuture<Void>> tasks = new LinkedList<>();
/*  45 */     int bSize = source.sizeX() * source.sizeY() * source.sizeZ() / concurrency;
/*     */     
/*  47 */     for (int b = 0; b < concurrency; b++) {
/*  48 */       int finalB = b;
/*  49 */       tasks.add(CompletableFuture.runAsync(() -> {
/*     */               for (int i = finalB * bSize; i < (finalB + 1) * bSize; i++) {
/*     */                 int x = i % sizeX + minX;
/*     */                 
/*     */                 int y = i / sizeX % sizeY + minY;
/*     */                 
/*     */                 int z = i / sizeX * sizeY % sizeZ + minZ;
/*     */                 
/*     */                 if (source.isInsideSpace(x, y, z) && destination.isInsideSpace(x, y, z)) {
/*     */                   destination.set(source.getContent(x, y, z), x, y, z);
/*     */                 }
/*     */               } 
/*  61 */             }).handle((r, e) -> {
/*     */               if (e == null) {
/*     */                 return r;
/*     */               }
/*     */               
/*     */               LoggerUtil.logException("a VoxelSpace async process", e, LoggerUtil.getLogger());
/*     */               
/*     */               return null;
/*     */             }));
/*     */     } 
/*     */     try {
/*  72 */       for (; !tasks.isEmpty(); ((CompletableFuture)tasks.removeFirst()).get());
/*  73 */     } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
/*  74 */       Thread.currentThread().interrupt();
/*  75 */       String msg = "Exception thrown by HytaleGenerator while attempting an asynchronous copy of a VoxelSpace:\n";
/*  76 */       msg = msg + msg;
/*  77 */       LoggerUtil.getLogger().severe(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class BatchTransfer<T>
/*     */     implements Runnable
/*     */   {
/*     */     private final VoxelSpace<T> source;
/*     */     
/*     */     private final VoxelSpace<T> destination;
/*     */     
/*     */     private final int minX;
/*     */     
/*     */     private final int minY;
/*     */     
/*     */     private final int minZ;
/*     */     
/*     */     private final int maxX;
/*     */     
/*     */     private final int maxY;
/*     */     private final int maxZ;
/*     */     
/*     */     private BatchTransfer(VoxelSpace<T> source, VoxelSpace<T> destination, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/* 101 */       this.source = source;
/* 102 */       this.destination = destination;
/* 103 */       this.minX = minX;
/* 104 */       this.minY = minY;
/* 105 */       this.minZ = minZ;
/* 106 */       this.maxX = maxX;
/* 107 */       this.maxY = maxY;
/* 108 */       this.maxZ = maxZ;
/*     */     }
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
/*     */     public void run() {
/*     */       try {
/* 125 */         for (int x = this.minX; x < this.maxX; x++) {
/* 126 */           for (int y = this.minY; y < this.maxY; y++)
/* 127 */           { for (int z = this.minZ; z < this.maxZ; z++)
/* 128 */             { if (this.destination.isInsideSpace(x, y, z))
/*     */               {
/* 130 */                 this.destination.set(this.source.getContent(x, y, z), x, y, z); }  }  } 
/*     */         } 
/* 132 */       } catch (Exception e) {
/* 133 */         String msg = "Exception thrown by HytaleGenerator while attempting a BatchTransfer operation:\n";
/* 134 */         msg = msg + msg;
/* 135 */         LoggerUtil.getLogger().severe(msg);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\voxelspace\VoxelSpaceUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */