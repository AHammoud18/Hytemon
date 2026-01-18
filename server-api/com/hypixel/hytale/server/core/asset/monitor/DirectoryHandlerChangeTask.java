/*     */ package com.hypixel.hytale.server.core.asset.monitor;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.nio.file.Path;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class DirectoryHandlerChangeTask
/*     */   implements Runnable
/*     */ {
/*  22 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private static final long ACCUMULATION_DELAY_MILLIS = 1000L;
/*     */   
/*     */   private final AssetMonitor assetMonitor;
/*     */   private final Path parent;
/*     */   private final AssetMonitorHandler handler;
/*     */   @Nonnull
/*     */   private final ScheduledFuture<?> task;
/*  31 */   private final AtomicBoolean changed = new AtomicBoolean(true);
/*  32 */   private final Map<Path, PathEvent> paths = (Map<Path, PathEvent>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   public DirectoryHandlerChangeTask(AssetMonitor assetMonitor, Path parent, AssetMonitorHandler handler) {
/*  35 */     this.assetMonitor = assetMonitor;
/*  36 */     this.parent = parent;
/*  37 */     this.handler = handler;
/*  38 */     this.task = AssetMonitor.runTask(this, 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  44 */     if (!this.changed.getAndSet(false)) {
/*  45 */       cancelSchedule();
/*     */       try {
/*  47 */         LOGGER.at(Level.FINER).log("run: %s", this.paths);
/*     */ 
/*     */ 
/*     */         
/*  51 */         ObjectArrayList<Map.Entry<Path, PathEvent>> entries = new ObjectArrayList(this.paths.size());
/*  52 */         for (Map.Entry<Path, PathEvent> entry : this.paths.entrySet()) {
/*  53 */           entries.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
/*     */         }
/*  55 */         this.paths.clear();
/*  56 */         entries.sort(Comparator.comparingLong(value -> ((PathEvent)value.getValue()).getTimestamp()));
/*     */         
/*  58 */         Set<String> fileNames = new HashSet<>();
/*  59 */         Object2ObjectOpenHashMap<Path, EventKind> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*     */ 
/*     */         
/*  62 */         for (ObjectListIterator<Map.Entry<Path, PathEvent>> objectListIterator = entries.iterator(); objectListIterator.hasNext(); ) { Map.Entry<Path, PathEvent> entry = objectListIterator.next();
/*  63 */           if (!fileNames.add(((Path)entry.getKey()).getFileName().toString())) {
/*     */             
/*  65 */             LOGGER.at(Level.FINER).log("run handler.accept(%s)", object2ObjectOpenHashMap);
/*  66 */             this.handler.accept((Map<Path, EventKind>)object2ObjectOpenHashMap);
/*  67 */             object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*  68 */             fileNames.clear();
/*     */           } 
/*     */           
/*  71 */           object2ObjectOpenHashMap.put(entry.getKey(), ((PathEvent)entry.getValue()).getEventKind()); }
/*     */ 
/*     */ 
/*     */         
/*  75 */         if (!object2ObjectOpenHashMap.isEmpty()) {
/*  76 */           LOGGER.at(Level.FINER).log("run handler.accept(%s)", object2ObjectOpenHashMap);
/*  77 */           this.handler.accept((Map<Path, EventKind>)object2ObjectOpenHashMap);
/*     */         } 
/*  79 */       } catch (Exception e) {
/*  80 */         ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to run: %s", this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public AssetMonitor getAssetMonitor() {
/*  86 */     return this.assetMonitor;
/*     */   }
/*     */   
/*     */   public Path getParent() {
/*  90 */     return this.parent;
/*     */   }
/*     */   
/*     */   public AssetMonitorHandler getHandler() {
/*  94 */     return this.handler;
/*     */   }
/*     */   
/*     */   public void addPath(Path path, PathEvent pathEvent) {
/*  98 */     LOGGER.at(Level.FINEST).log("addPath(%s, %s): %s", path, pathEvent, this);
/*  99 */     this.paths.put(path, pathEvent);
/* 100 */     this.changed.set(true);
/*     */   }
/*     */   
/*     */   public void removePath(Path path) {
/* 104 */     LOGGER.at(Level.FINEST).log("removePath(%s, %s): %s", path, this);
/* 105 */     this.paths.remove(path);
/* 106 */     if (this.paths.isEmpty()) {
/* 107 */       cancelSchedule();
/*     */     } else {
/* 109 */       this.changed.set(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void markChanged() {
/* 114 */     AssetMonitor.LOGGER.at(Level.FINEST).log("markChanged(): %s", this);
/* 115 */     this.changed.set(true);
/*     */   }
/*     */   
/*     */   public void cancelSchedule() {
/* 119 */     LOGGER.at(Level.FINEST).log("cancelSchedule(): %s", this);
/* 120 */     this.assetMonitor.removeHookChangeTask(this);
/* 121 */     if (this.task != null && !this.task.isDone()) this.task.cancel(false);
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 127 */     return "DirectoryHandlerChangeTask{parent=" + String.valueOf(this.parent) + ", handler=" + String.valueOf(this.handler) + ", changed=" + String.valueOf(this.changed) + ", paths=" + String.valueOf(this.paths) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\monitor\DirectoryHandlerChangeTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */