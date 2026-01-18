/*     */ package com.hypixel.hytale.server.core.asset.monitor;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.server.core.util.concurrent.ThreadUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class AssetMonitor {
/*  22 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  24 */   private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor(ThreadUtil.daemon("AssetMonitor Thread"));
/*     */   
/*  26 */   private final Map<Path, List<AssetMonitorHandler>> directoryMonitors = new ConcurrentHashMap<>();
/*     */   
/*  28 */   private final Map<Path, FileChangeTask> fileChangeTasks = new ConcurrentHashMap<>();
/*  29 */   private final Map<Path, Map<AssetMonitorHandler, DirectoryHandlerChangeTask>> directoryHandlerChangeTasks = new ConcurrentHashMap<>();
/*     */   
/*     */   @Nonnull
/*     */   private final PathWatcherThread pathWatcherThread;
/*     */   
/*     */   public AssetMonitor() throws IOException {
/*  35 */     this.pathWatcherThread = new PathWatcherThread(this::onChange);
/*  36 */     this.pathWatcherThread.start();
/*     */   }
/*     */   
/*     */   public void shutdown() {
/*  40 */     this.pathWatcherThread.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void monitorDirectoryFiles(@Nonnull Path path, @Nonnull AssetMonitorHandler handler) {
/*  47 */     if (!Files.isDirectory(path, new java.nio.file.LinkOption[0])) throw new IllegalArgumentException(String.valueOf(path));
/*     */     
/*     */     try {
/*  50 */       Path normalize = path.toAbsolutePath().normalize();
/*  51 */       LOGGER.at(Level.FINE).log("Monitoring Directory: %s", normalize);
/*  52 */       ((List<AssetMonitorHandler>)this.directoryMonitors
/*  53 */         .computeIfAbsent(normalize, SneakyThrow.sneakyFunction(k -> {
/*     */               this.pathWatcherThread.addPath(k);
/*     */               
/*     */               return new ObjectArrayList();
/*  57 */             }))).add(handler);
/*  58 */     } catch (Exception e) {
/*  59 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause((Throwable)new SkipSentryException(e))).log("Failed to monitor directory: %s", path);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeMonitorDirectoryFiles(@Nonnull Path path, @Nonnull Object key) {
/*  64 */     if (!Files.isDirectory(path, new java.nio.file.LinkOption[0])) throw new IllegalArgumentException(String.valueOf(path));
/*     */     
/*     */     try {
/*  67 */       Path normalize = path.toAbsolutePath().normalize();
/*  68 */       LOGGER.at(Level.FINE).log("Monitoring Directory: %s", normalize);
/*  69 */       ((List)this.directoryMonitors
/*  70 */         .computeIfAbsent(normalize, SneakyThrow.sneakyFunction(k -> {
/*     */               this.pathWatcherThread.addPath(k);
/*     */               
/*     */               return new ObjectArrayList();
/*  74 */             }))).removeIf(v -> v.getKey().equals(key));
/*  75 */     } catch (Exception e) {
/*  76 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause((Throwable)new SkipSentryException(e))).log("Failed to monitor directory: %s", path);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onChange(@Nonnull Path file, EventKind eventKind) {
/*  81 */     LOGGER.at(Level.FINER).log("onChange: %s of %s", file, eventKind);
/*  82 */     Path path = file.toAbsolutePath().normalize();
/*     */ 
/*     */     
/*  85 */     FileChangeTask oldTask = this.fileChangeTasks.remove(path);
/*  86 */     if (oldTask != null) oldTask.cancelSchedule(); 
/*  87 */     for (Map<AssetMonitorHandler, DirectoryHandlerChangeTask> tasks : this.directoryHandlerChangeTasks.values()) {
/*  88 */       for (DirectoryHandlerChangeTask task : tasks.values()) {
/*  89 */         task.removePath(path);
/*     */       }
/*     */     } 
/*     */     
/*  93 */     boolean createdOrModified = (eventKind == EventKind.ENTRY_CREATE || eventKind == EventKind.ENTRY_MODIFY);
/*  94 */     if (createdOrModified && 
/*  95 */       !Files.exists(path, new java.nio.file.LinkOption[0])) {
/*  96 */       LOGGER.at(Level.WARNING).log("The asset file '%s' was deleted before we could load/update it!", path);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 103 */       this.fileChangeTasks.put(path, new FileChangeTask(this, path, new PathEvent(eventKind, System.nanoTime())));
/* 104 */     } catch (NoSuchFileException|java.io.FileNotFoundException|java.nio.file.AccessDeniedException e) {
/* 105 */       LOGGER.at(Level.WARNING).log("The asset file '%s' was deleted before we could load/update it!", path);
/* 106 */     } catch (IOException e) {
/* 107 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to queue asset to be reloaded %s", path);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onDelayedChange(@Nonnull Path path, @Nonnull PathEvent pathEvent) {
/* 112 */     LOGGER.at(Level.FINER).log("onDelayedChange: %s of %s", path, pathEvent);
/*     */     
/* 114 */     for (Map.Entry<Path, List<AssetMonitorHandler>> entry : this.directoryMonitors.entrySet()) {
/* 115 */       Path parent = entry.getKey();
/* 116 */       if (!path.startsWith(parent))
/*     */         continue; 
/* 118 */       Map<AssetMonitorHandler, DirectoryHandlerChangeTask> tasks = this.directoryHandlerChangeTasks.computeIfAbsent(parent, k -> new ConcurrentHashMap<>());
/* 119 */       for (AssetMonitorHandler directoryHandler : entry.getValue()) {
/*     */         try {
/* 121 */           if (directoryHandler.test(path, pathEvent.getEventKind())) {
/* 122 */             ((DirectoryHandlerChangeTask)tasks
/* 123 */               .computeIfAbsent(directoryHandler, handler -> new DirectoryHandlerChangeTask(this, parent, handler)))
/* 124 */               .addPath(path, pathEvent);
/*     */           }
/* 126 */         } catch (Exception e) {
/* 127 */           ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to run directoryHandler.test for parent: %s, %s of %s", parent, path, pathEvent);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeFileChangeTask(@Nonnull FileChangeTask fileChangeTask) {
/* 134 */     this.fileChangeTasks.remove(fileChangeTask.getPath());
/*     */   }
/*     */   
/*     */   public void markChanged(@Nonnull Path path) {
/* 138 */     for (Map.Entry<Path, Map<AssetMonitorHandler, DirectoryHandlerChangeTask>> entry : this.directoryHandlerChangeTasks.entrySet()) {
/* 139 */       Path parent = entry.getKey();
/* 140 */       if (!path.startsWith(parent))
/*     */         continue; 
/* 142 */       for (DirectoryHandlerChangeTask hookChangeTask : ((Map)entry.getValue()).values()) {
/* 143 */         hookChangeTask.markChanged();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeHookChangeTask(@Nonnull DirectoryHandlerChangeTask directoryHandlerChangeTask) {
/* 149 */     AssetMonitorHandler hook = directoryHandlerChangeTask.getHandler();
/* 150 */     this.directoryHandlerChangeTasks.compute(directoryHandlerChangeTask.getParent(), (k, map) -> {
/*     */           if (map == null)
/*     */             return null; 
/*     */           map.remove(hook);
/*     */           return map.isEmpty() ? null : map;
/*     */         });
/*     */   }
/*     */   @Nonnull
/*     */   public static ScheduledFuture<?> runTask(@Nonnull Runnable task, long millisDelay) {
/* 159 */     return EXECUTOR.scheduleWithFixedDelay(task, millisDelay, millisDelay, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\monitor\AssetMonitor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */