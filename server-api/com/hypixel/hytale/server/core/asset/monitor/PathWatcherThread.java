/*     */ package com.hypixel.hytale.server.core.asset.monitor;
/*     */ import com.hypixel.hytale.common.util.SystemUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.util.io.FileUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import com.sun.nio.file.SensitivityWatchEventModifier;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardWatchEventKinds;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PathWatcherThread implements Runnable {
/*  19 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  21 */   public static final boolean HAS_FILE_TREE_SUPPORT = (SystemUtil.TYPE == SystemUtil.SystemType.WINDOWS);
/*  22 */   public static final WatchEvent.Kind<?>[] WATCH_EVENT_KINDS = new WatchEvent.Kind[] { StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY };
/*     */   
/*     */   private final BiConsumer<Path, EventKind> consumer;
/*     */   
/*     */   @Nonnull
/*     */   private final Thread thread;
/*     */   private final WatchService service;
/*  29 */   private final Map<Path, WatchKey> registered = new ConcurrentHashMap<>();
/*     */   
/*     */   public PathWatcherThread(BiConsumer<Path, EventKind> consumer) throws IOException {
/*  32 */     this.consumer = consumer;
/*     */     
/*  34 */     this.thread = new Thread(this, "PathWatcher");
/*  35 */     this.thread.setDaemon(true);
/*     */     
/*  37 */     this.service = FileSystems.getDefault().newWatchService();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void run() {
/*     */     try {
/*  43 */       while (!Thread.interrupted())
/*  44 */       { WatchKey key = this.service.take();
/*     */         
/*  46 */         Path directory = (Path)key.watchable();
/*  47 */         for (WatchEvent<?> event : key.pollEvents()) {
/*  48 */           WatchEvent.Kind<?> kind = event.kind();
/*  49 */           if (kind == StandardWatchEventKinds.OVERFLOW) {
/*  50 */             LOGGER.at(Level.WARNING).log("Event Overflow, Unable to detect all file changed! This may cause server instability!! More than AbstractWatchKey.MAX_EVENT_LIST_SIZE queued events (512)");
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*  55 */           WatchEvent<Path> pathEvent = (WatchEvent)event;
/*  56 */           Path path = directory.resolve(pathEvent.context());
/*     */ 
/*     */ 
/*     */           
/*  60 */           if (!HAS_FILE_TREE_SUPPORT && pathEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE && Files.isDirectory(path, new java.nio.file.LinkOption[0])) {
/*  61 */             addPath(path);
/*     */           }
/*     */           
/*  64 */           this.consumer.accept(path, EventKind.parse(pathEvent.kind()));
/*     */         } 
/*     */         
/*  67 */         if (!key.reset())
/*     */           break;  } 
/*  69 */     } catch (InterruptedException ignored) {
/*  70 */       Thread.currentThread().interrupt();
/*  71 */     } catch (Throwable t) {
/*  72 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(t)).log("Exception occurred when polling:");
/*     */     } 
/*  74 */     LOGGER.at(Level.INFO).log("Stopped polling for changes in assets. Server will need to be rebooted to load changes!");
/*     */     
/*     */     try {
/*  77 */       this.service.close();
/*  78 */     } catch (IOException iOException) {}
/*     */     
/*  80 */     this.registered.clear();
/*     */   }
/*     */   
/*     */   public void start() {
/*  84 */     this.thread.start();
/*     */   }
/*     */   
/*     */   public void shutdown() {
/*  88 */     this.thread.interrupt();
/*     */     try {
/*  90 */       this.thread.join(1000L);
/*     */       
/*     */       try {
/*  93 */         this.service.close();
/*  94 */       } catch (IOException iOException) {}
/*     */       
/*  96 */       this.registered.clear();
/*  97 */     } catch (InterruptedException ignored) {
/*  98 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addPath(Path path) throws IOException {
/* 103 */     path = path.toAbsolutePath();
/* 104 */     if (Files.isRegularFile(path, new java.nio.file.LinkOption[0])) path = path.getParent();
/*     */ 
/*     */     
/* 107 */     Path parent = path;
/*     */     do {
/* 109 */       WatchKey keys = this.registered.get(parent);
/* 110 */       if (keys != null) {
/* 111 */         if (!HAS_FILE_TREE_SUPPORT) watchPath(path); 
/*     */         return;
/*     */       } 
/* 114 */     } while ((parent = parent.getParent()) != null);
/*     */     
/* 116 */     watchPath(path);
/*     */   }
/*     */   
/*     */   private void watchPath(@Nonnull Path path) throws IOException {
/* 120 */     if (HAS_FILE_TREE_SUPPORT) {
/* 121 */       this.registered.put(path, path.register(this.service, WATCH_EVENT_KINDS, new WatchEvent.Modifier[] { SensitivityWatchEventModifier.HIGH, ExtendedWatchEventModifier.FILE_TREE }));
/* 122 */       LOGGER.at(Level.FINEST).log("Register path: %s", path);
/*     */     } else {
/* 124 */       WatchEvent.Modifier[] modifiers = { SensitivityWatchEventModifier.HIGH };
/* 125 */       Stream<Path> stream = Files.walk(path, FileUtil.DEFAULT_WALK_TREE_OPTIONS_ARRAY); 
/* 126 */       try { stream.forEach(SneakyThrow.sneakyConsumer(childPath -> {
/*     */                 if (Files.isDirectory(childPath, new java.nio.file.LinkOption[0])) {
/*     */                   this.registered.put(childPath, childPath.register(this.service, WATCH_EVENT_KINDS, modifiers));
/*     */                   LOGGER.at(Level.FINEST).log("Register path: %s", childPath);
/*     */                 } 
/*     */               }));
/* 132 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*     */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */     
/* 135 */     }  LOGGER.at(Level.FINER).log("Watching path: %s", path);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\monitor\PathWatcherThread.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */