/*     */ package com.hypixel.hytale.server.core.asset.monitor;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class FileChangeTask
/*     */   implements Runnable
/*     */ {
/*  17 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private static final long FILE_SIZE_CHECK_DELAY_MILLIS = 200L;
/*     */   
/*     */   private final AssetMonitor assetMonitor;
/*     */   @Nonnull
/*     */   private final Path path;
/*     */   @Nonnull
/*     */   private final PathEvent pathEvent;
/*     */   private final boolean createdOrModified;
/*     */   @Nonnull
/*     */   private final ScheduledFuture<?> task;
/*     */   private long lastSize;
/*     */   
/*     */   public FileChangeTask(AssetMonitor assetMonitor, @Nonnull Path path, @Nonnull PathEvent pathEvent) throws IOException {
/*  32 */     this.assetMonitor = assetMonitor;
/*  33 */     this.path = path;
/*  34 */     this.pathEvent = pathEvent;
/*  35 */     this.createdOrModified = (pathEvent.getEventKind() == EventKind.ENTRY_CREATE || pathEvent.getEventKind() == EventKind.ENTRY_MODIFY);
/*     */     
/*  37 */     long size = 0L;
/*  38 */     if (this.createdOrModified) {
/*  39 */       BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/*  40 */       if (!fileAttributes.isDirectory()) {
/*  41 */         size = fileAttributes.size();
/*     */       }
/*     */     } 
/*  44 */     this.lastSize = size;
/*     */     
/*  46 */     this.task = AssetMonitor.runTask(this, 200L);
/*     */   }
/*     */   
/*     */   public AssetMonitor getAssetMonitor() {
/*  50 */     return this.assetMonitor;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Path getPath() {
/*  55 */     return this.path;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PathEvent getPathEvent() {
/*  60 */     return this.pathEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  66 */       if (this.createdOrModified) {
/*  67 */         if (!Files.exists(this.path, new java.nio.file.LinkOption[0])) {
/*  68 */           LOGGER.at(Level.WARNING).log("The asset file '%s' was deleted before we could load/update it!", this.path);
/*  69 */           cancelSchedule();
/*     */           
/*     */           return;
/*     */         } 
/*  73 */         BasicFileAttributes fileAttributes = Files.readAttributes(this.path, BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/*  74 */         if (!fileAttributes.isDirectory()) {
/*  75 */           long size = fileAttributes.size();
/*  76 */           if (size > this.lastSize) {
/*  77 */             LOGGER.at(Level.FINEST).log("File increased in size: %s, %s, %d > %d", this.path, this.pathEvent, Long.valueOf(size), Long.valueOf(this.lastSize));
/*  78 */             this.lastSize = size;
/*  79 */             this.assetMonitor.markChanged(this.path);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*  85 */       cancelSchedule();
/*     */       
/*  87 */       this.assetMonitor.onDelayedChange(this.path, this.pathEvent);
/*  88 */     } catch (NoSuchFileException|java.io.FileNotFoundException e) {
/*  89 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause((Throwable)new SkipSentryException(e))).log("The asset file '%s' was deleted before we could load/update it!", this.path);
/*  90 */     } catch (Throwable e) {
/*  91 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to handle file change %s", this.path);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cancelSchedule() {
/*  96 */     LOGGER.at(Level.FINEST).log("cancelSchedule(): %s", this);
/*  97 */     this.assetMonitor.removeFileChangeTask(this);
/*  98 */     if (this.task != null && !this.task.isDone()) this.task.cancel(false);
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 104 */     return "FileChangeTask{path=" + String.valueOf(this.path) + ", eventKind=" + String.valueOf(this.pathEvent) + ", lastSize=" + this.lastSize + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\monitor\FileChangeTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */