/*     */ package com.hypixel.hytale.server.core.prefab.selection.buffer;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.server.core.util.io.FileUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionException;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class PrefabBufferUtil
/*     */ {
/*  36 */   public static final Path CACHE_PATH = (Path)Options.getOrDefault(Options.PREFAB_CACHE_DIRECTORY, 
/*     */       
/*  38 */       Options.getOptionSet(), 
/*  39 */       Path.of(".cache/prefabs", new String[0]));
/*     */   
/*     */   public static final String LPF_FILE_SUFFIX = ".lpf";
/*     */   
/*     */   public static final String JSON_FILE_SUFFIX = ".prefab.json";
/*     */   
/*     */   public static final String JSON_LPF_FILE_SUFFIX = ".prefab.json.lpf";
/*     */   public static final String FILE_SUFFIX_REGEX = "((!\\.prefab\\.json)\\.lpf|\\.prefab\\.json)$";
/*  47 */   public static final Pattern FILE_SUFFIX_PATTERN = Pattern.compile("((!\\.prefab\\.json)\\.lpf|\\.prefab\\.json)$");
/*  48 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */   
/*  51 */   private static final Map<Path, WeakReference<CachedEntry>> CACHE = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static IPrefabBuffer getCached(@Nonnull Path path) {
/*  62 */     WeakReference<CachedEntry> reference = CACHE.get(path);
/*  63 */     CachedEntry cachedPrefab = (reference != null) ? reference.get() : null;
/*  64 */     if (cachedPrefab != null) {
/*  65 */       long l = cachedPrefab.lock.readLock();
/*     */       try {
/*  67 */         if (cachedPrefab.buffer != null) {
/*  68 */           return (IPrefabBuffer)cachedPrefab.buffer.newAccess();
/*     */         }
/*     */       } finally {
/*  71 */         cachedPrefab.lock.unlockRead(l);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     cachedPrefab = getOrCreateCacheEntry(path);
/*     */     
/*  77 */     long stamp = cachedPrefab.lock.writeLock();
/*     */     try {
/*  79 */       if (cachedPrefab.buffer != null) {
/*  80 */         return (IPrefabBuffer)cachedPrefab.buffer.newAccess();
/*     */       }
/*     */       
/*  83 */       cachedPrefab.buffer = loadBuffer(path);
/*  84 */       return (IPrefabBuffer)cachedPrefab.buffer.newAccess();
/*     */     } finally {
/*  86 */       cachedPrefab.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static PrefabBuffer loadBuffer(@Nonnull Path path) {
/*     */     Path cachedLpfPath;
/*     */     AssetPack pack;
/*  98 */     String fileNameStr = path.getFileName().toString();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     String fileName = fileNameStr.replace(".prefab.json.lpf", "").replace(".prefab.json", "");
/*     */ 
/*     */     
/* 106 */     Path lpfPath = path.resolveSibling(fileName + ".lpf");
/* 107 */     if (Files.exists(lpfPath, new java.nio.file.LinkOption[0])) {
/* 108 */       return loadFromLPF(path, lpfPath);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (AssetModule.get().isAssetPathImmutable(path)) {
/* 116 */       Path lpfConvertedPath = path.resolveSibling(fileName + ".prefab.json.lpf");
/* 117 */       if (Files.exists(lpfConvertedPath, new java.nio.file.LinkOption[0])) {
/* 118 */         return loadFromLPF(path, lpfConvertedPath);
/*     */       }
/*     */       
/* 121 */       pack = AssetModule.get().findAssetPackForPath(path);
/* 122 */       if (pack != null) {
/* 123 */         String safePackName = FileUtil.INVALID_FILENAME_CHARACTERS.matcher(pack.getName()).replaceAll("_");
/* 124 */         cachedLpfPath = CACHE_PATH.resolve(safePackName).resolve(pack.getRoot().relativize(lpfConvertedPath).toString());
/* 125 */       } else if (lpfConvertedPath.getRoot() != null) {
/* 126 */         cachedLpfPath = CACHE_PATH.resolve(lpfConvertedPath.subpath(1, lpfConvertedPath.getNameCount()).toString());
/*     */       } else {
/* 128 */         cachedLpfPath = CACHE_PATH.resolve(lpfConvertedPath.toString());
/*     */       } 
/*     */     } else {
/* 131 */       cachedLpfPath = path.resolveSibling(fileName + ".prefab.json.lpf");
/* 132 */       pack = null;
/*     */     } 
/*     */ 
/*     */     
/* 136 */     Path jsonPath = path.resolveSibling(fileName + ".prefab.json");
/* 137 */     if (!Files.exists(jsonPath, new java.nio.file.LinkOption[0])) {
/*     */       
/*     */       try {
/* 140 */         Files.deleteIfExists(cachedLpfPath);
/* 141 */       } catch (IOException iOException) {}
/*     */       
/* 143 */       throw new Error("Error loading Prefab from " + String.valueOf(jsonPath.toAbsolutePath()) + " (.lpf and .prefab.json) File NOT found!");
/*     */     } 
/*     */     
/*     */     try {
/* 147 */       return loadFromJson(pack, path, cachedLpfPath, jsonPath);
/* 148 */     } catch (IOException e) {
/* 149 */       throw SneakyThrow.sneakyThrow(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<Void> writeToFileAsync(@Nonnull PrefabBuffer prefab, @Nonnull Path path) {
/* 155 */     return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> { SeekableByteChannel channel = Files.newByteChannel(path, FileUtil.DEFAULT_WRITE_OPTIONS, (FileAttribute<?>[])new FileAttribute[0]); try { channel.write(BinaryPrefabBufferCodec.INSTANCE.serialize(prefab).nioBuffer()); if (channel != null)
/* 156 */                 channel.close();  } catch (Throwable t$) { if (channel != null) try { channel.close(); } catch (Throwable x2)
/*     */                 { t$.addSuppressed(x2); }
/*     */                  
/*     */               throw t$; }
/*     */           
/*     */           }));
/*     */   } public static PrefabBuffer readFromFile(@Nonnull Path path) {
/* 163 */     return readFromFileAsync(path).join();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<PrefabBuffer> readFromFileAsync(@Nonnull Path path) {
/* 168 */     return CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> { SeekableByteChannel channel = Files.newByteChannel(path, new java.nio.file.OpenOption[0]); try { int size = (int)channel.size(); ByteBuf buf = Unpooled.buffer(size); buf.writerIndex(size); if (channel.read(buf.internalNioBuffer(0, size)) != size) throw new IOException("Didn't read full file!");  PrefabBuffer prefabBuffer = BinaryPrefabBufferCodec.INSTANCE.deserialize(path, buf); if (channel != null)
/* 169 */                 channel.close();  return prefabBuffer; } catch (Throwable t$) { if (channel != null) try { channel.close(); } catch (Throwable x2)
/*     */                 { t$.addSuppressed(x2); }
/*     */               
/*     */               
/*     */               throw t$; }
/*     */           
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static PrefabBuffer loadFromLPF(@Nonnull Path path, @Nonnull Path realPath) {
/*     */     try {
/* 185 */       return readFromFile(realPath);
/* 186 */     } catch (Exception e) {
/* 187 */       throw new Error("Error while loading prefab " + String.valueOf(path.toAbsolutePath()) + " from " + String.valueOf(realPath.toAbsolutePath()), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PrefabBuffer loadFromJson(@Nullable AssetPack pack, Path path, @Nonnull Path cachedLpfPath, @Nonnull Path jsonPath) throws IOException {
/*     */     FileTime targetModifiedTime;
/* 194 */     BasicFileAttributes cachedAttr = null;
/*     */     try {
/* 196 */       cachedAttr = Files.readAttributes(cachedLpfPath, BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/* 197 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (pack == null || !pack.isImmutable()) {
/* 202 */       targetModifiedTime = Files.<BasicFileAttributes>readAttributes(jsonPath, BasicFileAttributes.class, new java.nio.file.LinkOption[0]).lastModifiedTime();
/*     */     } else {
/* 204 */       targetModifiedTime = Files.<BasicFileAttributes>readAttributes(pack.getPackLocation(), BasicFileAttributes.class, new java.nio.file.LinkOption[0]).lastModifiedTime();
/*     */     } 
/*     */     
/* 207 */     if (cachedAttr != null && 
/* 208 */       targetModifiedTime.compareTo(cachedAttr.lastModifiedTime()) <= 0) {
/*     */       try {
/* 210 */         return readFromFile(cachedLpfPath);
/* 211 */       } catch (CompletionException e) {
/* 212 */         if (!Options.getOptionSet().has(Options.VALIDATE_PREFABS)) {
/* 213 */           if (e.getCause() instanceof UpdateBinaryPrefabException) {
/*     */             
/* 215 */             LOGGER.at(Level.FINE).log("Ignoring LPF %s due to: %s", path, e.getMessage());
/*     */           } else {
/* 217 */             ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause((Throwable)new SkipSentryException(e))).log("Failed to load %s", cachedLpfPath);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 227 */       PrefabBuffer buffer = BsonPrefabBufferDeserializer.INSTANCE.deserialize(jsonPath, BsonUtil.readDocument(jsonPath, false).join());
/*     */       
/* 229 */       if (!Options.getOptionSet().has(Options.DISABLE_CPB_BUILD)) {
/*     */         try {
/* 231 */           Files.createDirectories(cachedLpfPath.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 232 */           writeToFileAsync(buffer, cachedLpfPath)
/* 233 */             .thenRun(() -> {
/*     */                 try {
/*     */                   Files.setLastModifiedTime(cachedLpfPath, targetModifiedTime);
/* 236 */                 } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 241 */               }).exceptionally(throwable -> {
/*     */                 ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.FINE).withCause((Throwable)new SkipSentryException(throwable))).log("Failed to save prefab cache %s", cachedLpfPath);
/*     */ 
/*     */                 
/*     */                 return null;
/*     */               });
/* 247 */         } catch (IOException e) {
/*     */           
/* 249 */           LOGGER.at(Level.FINE).log("Cannot create cache directory for %s: %s", cachedLpfPath, e.getMessage());
/*     */         } 
/*     */       }
/*     */       
/* 253 */       return buffer;
/* 254 */     } catch (Exception e) {
/* 255 */       throw new Error("Error while loading Prefab from " + String.valueOf(jsonPath.toAbsolutePath()), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static CachedEntry getOrCreateCacheEntry(Path path) {
/* 263 */     CachedEntry[] temp = new CachedEntry[1];
/* 264 */     CACHE.compute(path, (p, ref) -> {
/*     */           if (ref != null) {
/*     */             CachedEntry cached = ref.get();
/*     */             temp[0] = cached;
/*     */             if (cached != null) {
/*     */               return ref;
/*     */             }
/*     */           } 
/*     */           return new WeakReference<>(temp[0] = new CachedEntry());
/*     */         });
/* 274 */     return temp[0];
/*     */   }
/*     */   
/*     */   private static class CachedEntry {
/* 278 */     private final StampedLock lock = new StampedLock();
/*     */     private PrefabBuffer buffer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\PrefabBufferUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */