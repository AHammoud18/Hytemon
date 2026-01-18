/*     */ package com.hypixel.hytale.server.core.universe.datastore;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class DiskDataStore<T> implements DataStore<T> {
/*     */   private static final String EXTENSION = ".json";
/*  24 */   private static final int EXTENSION_LEN = ".json".length();
/*     */   
/*     */   private static final String EXTENSION_BACKUP = ".json.bak";
/*     */   private static final String GLOB = "*.json";
/*     */   private static final String GLOB_WITH_BACKUP = "*.{json,json.bak}";
/*     */   @Nonnull
/*     */   private final HytaleLogger logger;
/*     */   @Nonnull
/*     */   private final Path path;
/*     */   private final BuilderCodec<T> codec;
/*     */   
/*     */   public DiskDataStore(@Nonnull String path, BuilderCodec<T> codec) {
/*  36 */     this.logger = HytaleLogger.get("DataStore|" + path);
/*  37 */     this.path = Universe.get().getPath().resolve(path);
/*  38 */     this.codec = codec;
/*     */     
/*  40 */     if (Files.isDirectory(this.path, new java.nio.file.LinkOption[0])) {
/*  41 */       try { DirectoryStream<Path> paths = Files.newDirectoryStream(this.path, "*.bson"); 
/*  42 */         try { for (Path oldPath : paths) {
/*  43 */             Path newPath = getPathFromId(this.path, getIdFromPath(oldPath));
/*     */             try {
/*  45 */               Files.move(oldPath, newPath, new java.nio.file.CopyOption[0]);
/*  46 */             } catch (IOException iOException) {}
/*     */           } 
/*     */           
/*  49 */           if (paths != null) paths.close();  } catch (Throwable throwable) { if (paths != null) try { paths.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  50 */       { ((HytaleLogger.Api)this.logger.at(Level.SEVERE).withCause(e)).log("Failed to migrate files form .bson to .json!"); }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Path getPath() {
/*  57 */     return this.path;
/*     */   }
/*     */ 
/*     */   
/*     */   public BuilderCodec<T> getCodec() {
/*  62 */     return this.codec;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T load(String id) throws IOException {
/*  68 */     Path filePath = getPathFromId(this.path, id);
/*  69 */     return Files.exists(filePath, new java.nio.file.LinkOption[0]) ? load0(filePath) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(String id, T value) {
/*  75 */     ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  76 */     BsonDocument bsonValue = this.codec.encode(value, extraInfo);
/*  77 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(this.logger);
/*     */     
/*  79 */     BsonUtil.writeDocument(getPathFromId(this.path, id), bsonValue.asDocument()).join();
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(String id) throws IOException {
/*  84 */     Files.deleteIfExists(getPathFromId(this.path, id));
/*  85 */     Files.deleteIfExists(getBackupPathFromId(this.path, id));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<String> list() throws IOException {
/*  91 */     ObjectArrayList<String> objectArrayList = new ObjectArrayList();
/*  92 */     DirectoryStream<Path> paths = Files.newDirectoryStream(this.path, "*.json"); 
/*  93 */     try { for (Path path : paths) {
/*  94 */         objectArrayList.add(getIdFromPath(path));
/*     */       }
/*  96 */       if (paths != null) paths.close();  } catch (Throwable throwable) { if (paths != null)
/*  97 */         try { paths.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return (List<String>)objectArrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, T> loadAll() throws IOException {
/* 103 */     Object2ObjectOpenHashMap<String, T> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 104 */     DirectoryStream<Path> paths = Files.newDirectoryStream(this.path, "*.json"); 
/* 105 */     try { for (Path path : paths) {
/* 106 */         object2ObjectOpenHashMap.put(getIdFromPath(path), load0(path));
/*     */       }
/* 108 */       if (paths != null) paths.close();  } catch (Throwable throwable) { if (paths != null)
/* 109 */         try { paths.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return (Map<String, T>)object2ObjectOpenHashMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAll() throws IOException {
/* 114 */     DirectoryStream<Path> paths = Files.newDirectoryStream(this.path, "*.{json,json.bak}"); 
/* 115 */     try { for (Path path : paths) Files.delete(path); 
/* 116 */       if (paths != null) paths.close();  }
/*     */     catch (Throwable throwable) { if (paths != null)
/*     */         try { paths.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 121 */      } @Nullable protected T load0(@Nonnull Path path) throws IOException { return (T)RawJsonReader.readSync(path, (Codec)this.codec, this.logger); }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected static Path getPathFromId(@Nonnull Path path, String id) {
/* 126 */     return path.resolve(id + ".json");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected static Path getBackupPathFromId(@Nonnull Path path, String id) {
/* 131 */     return path.resolve(id + ".json.bak");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected static String getIdFromPath(@Nonnull Path path) {
/* 136 */     String fileName = path.getFileName().toString();
/* 137 */     return fileName.substring(0, fileName.length() - EXTENSION_LEN);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\datastore\DiskDataStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */