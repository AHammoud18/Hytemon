/*     */ package com.hypixel.hytale.server.core.universe.world.storage.resources;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.component.ComponentRegistry;
/*     */ import com.hypixel.hytale.component.IResourceStorage;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.server.core.util.io.FileUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class DiskResourceStorageProvider implements IResourceStorageProvider {
/*     */   public static final String ID = "Disk";
/*     */   
/*     */   static {
/*  34 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DiskResourceStorageProvider.class, DiskResourceStorageProvider::new).append(new KeyedCodec("Path", (Codec)Codec.STRING), (o, s) -> o.path = s, o -> o.path).add()).build();
/*     */   } public static final BuilderCodec<DiskResourceStorageProvider> CODEC; @Nonnull
/*  36 */   private String path = "resources";
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getPath() {
/*  41 */     return this.path;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends com.hypixel.hytale.server.core.universe.world.WorldProvider> IResourceStorage getResourceStorage(@Nonnull World world) {
/*  47 */     return new DiskResourceStorage(world.getSavePath().resolve(this.path));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  53 */     return "DiskResourceStorageProvider{path=" + this.path + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public static void migrateFiles(@Nonnull World world) {
/*  60 */     Path resourcesPath = world.getSavePath().resolve("resources");
/*  61 */     Path chunkStorePath = resourcesPath.resolve("chunkstore");
/*  62 */     if (Files.exists(chunkStorePath, new java.nio.file.LinkOption[0])) {
/*     */       try {
/*  64 */         FileUtil.moveDirectoryContents(chunkStorePath, resourcesPath, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*  65 */         FileUtil.deleteDirectory(chunkStorePath);
/*  66 */       } catch (IOException e) {
/*  67 */         throw new RuntimeException("Failed to migrate old chunkstore resources!", e);
/*     */       } 
/*     */     }
/*  70 */     Path entityStorePath = resourcesPath.resolve("entitystore");
/*  71 */     if (Files.exists(entityStorePath, new java.nio.file.LinkOption[0]))
/*     */       try {
/*  73 */         FileUtil.moveDirectoryContents(entityStorePath, resourcesPath, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*  74 */         FileUtil.deleteDirectory(entityStorePath);
/*  75 */       } catch (IOException e) {
/*  76 */         throw new RuntimeException("Failed to migrate old entitystore resources!", e);
/*     */       }  
/*     */   }
/*     */   
/*     */   public static class DiskResourceStorage
/*     */     implements IResourceStorage {
/*  82 */     private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */     
/*     */     public static final String FILE_EXTENSION = ".json";
/*     */     
/*     */     @Nonnull
/*     */     private final Path path;
/*     */     
/*     */     public DiskResourceStorage(@Nonnull Path path) {
/*  90 */       this.path = path;
/*  91 */       if (!Options.getOptionSet().has(Options.BARE)) {
/*     */         try {
/*  93 */           Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
/*  94 */         } catch (IOException e) {
/*  95 */           throw new RuntimeException("Failed to create Resources directory", e);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<T> load(@Nonnull Store<ECS_TYPE> store, @Nonnull ComponentRegistry.Data<ECS_TYPE> data, @Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 103 */       BuilderCodec<T> codec = data.getResourceCodec(resourceType);
/*     */       
/* 105 */       if (codec == null) return CompletableFuture.completedFuture((T)data.createResource(resourceType));
/*     */ 
/*     */       
/* 108 */       return CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> {
/*     */               BasicFileAttributes attributes;
/*     */               
/*     */               String id = data.getResourceId(resourceType);
/*     */               Path file = this.path.resolve(id + ".json");
/*     */               try {
/*     */                 attributes = Files.readAttributes(file, BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/* 115 */               } catch (IOException ignored) {
/*     */                 LOGGER.at(Level.FINE).log("File '%s' was not found, using the default file", file);
/*     */                 
/*     */                 return data.createResource(resourceType);
/*     */               } 
/*     */               
/*     */               if (attributes.size() == 0L) {
/*     */                 LOGGER.at(Level.WARNING).log("Error loading file %s, file was found to be entirely empty, using the default file", file);
/*     */                 
/*     */                 return data.createResource(resourceType);
/*     */               } 
/*     */               
/*     */               try {
/*     */                 Resource resource = (Resource)RawJsonReader.readSync(file, (Codec)codec, LOGGER);
/*     */                 return (resource != null) ? resource : data.createResource(resourceType);
/* 130 */               } catch (IOException e) {
/*     */                 ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to load resource from %s, using default", file);
/*     */                 return data.createResource(resourceType);
/*     */               } 
/*     */             }));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<Void> save(@Nonnull Store<ECS_TYPE> store, @Nonnull ComponentRegistry.Data<ECS_TYPE> data, @Nonnull ResourceType<ECS_TYPE, T> resourceType, T resource) {
/* 140 */       BuilderCodec<T> codec = data.getResourceCodec(resourceType);
/*     */       
/* 142 */       if (codec == null) return CompletableFuture.completedFuture(null);
/*     */       
/* 144 */       String id = data.getResourceId(resourceType);
/* 145 */       Path file = this.path.resolve(id + ".json");
/*     */       
/* 147 */       ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 148 */       BsonDocument document = codec.encode(resource, extraInfo).asDocument();
/* 149 */       extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*     */       
/* 151 */       return BsonUtil.writeDocument(file, document);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<Void> remove(@Nonnull Store<ECS_TYPE> store, @Nonnull ComponentRegistry.Data<ECS_TYPE> data, @Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 157 */       String id = data.getResourceId(resourceType);
/*     */       
/* 159 */       if (id == null) return CompletableFuture.completedFuture(null);
/*     */       
/* 161 */       Path file = this.path.resolve(id + ".json");
/*     */       try {
/* 163 */         Files.deleteIfExists(file);
/* 164 */         return CompletableFuture.completedFuture(null);
/* 165 */       } catch (IOException e) {
/* 166 */         return CompletableFuture.failedFuture(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\resources\DiskResourceStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */