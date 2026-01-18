/*     */ package com.hypixel.hytale.server.core.asset.type.buildertool.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import java.util.stream.Stream;
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
/*     */ public class PrefabReference
/*     */ {
/*     */   public static final BuilderCodec<PrefabReference> CODEC;
/*     */   public PrefabListAsset.PrefabRootDirectory rootDirectory;
/*     */   public String unprocessedPrefabPath;
/*     */   
/*     */   static {
/* 151 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PrefabReference.class, PrefabReference::new).append(new KeyedCodec("RootDirectory", (Codec)new EnumCodec(PrefabListAsset.PrefabRootDirectory.class), true), (prefabReference, rootDirectory) -> prefabReference.rootDirectory = rootDirectory, prefabReference -> prefabReference.rootDirectory).add()).append(new KeyedCodec("Path", (Codec)Codec.STRING, true), (prefabReference, unprocessedPrefabPath) -> prefabReference.unprocessedPrefabPath = unprocessedPrefabPath, prefabReference -> prefabReference.unprocessedPrefabPath).add()).append(new KeyedCodec("Recursive", (Codec)Codec.BOOLEAN, false), (prefabReference, recursive) -> prefabReference.recursive = recursive.booleanValue(), prefabReference -> Boolean.valueOf(prefabReference.recursive)).add()).afterDecode(PrefabReference::processPrefabPath)).build();
/*     */   }
/*     */   
/*     */   public boolean recursive = false;
/*     */   @Nonnull
/* 156 */   public List<Path> prefabPaths = (List<Path>)new ObjectArrayList();
/*     */ 
/*     */   
/*     */   public void processPrefabPath() {
/* 160 */     if (this.unprocessedPrefabPath == null) {
/*     */       return;
/*     */     }
/* 163 */     this.unprocessedPrefabPath = this.unprocessedPrefabPath.replace('\\', '/');
/* 164 */     if (this.unprocessedPrefabPath.endsWith("/")) {
/*     */       
/* 166 */       try { Stream<Path> walk = Files.walk(this.rootDirectory.getPrefabPath().resolve(this.unprocessedPrefabPath), this.recursive ? 5 : 1, new java.nio.file.FileVisitOption[0]);
/*     */ 
/*     */         
/* 169 */         try { Objects.requireNonNull(this.prefabPaths); walk.filter(x$0 -> Files.isRegularFile(x$0, new java.nio.file.LinkOption[0])).filter(path -> path.toString().endsWith(".prefab.json")).forEach(this.prefabPaths::add);
/* 170 */           if (walk != null) walk.close();  } catch (Throwable throwable) { if (walk != null) try { walk.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 171 */       { ((HytaleLogger.Api)PrefabListAsset.getAssetStore().getLogger().at(Level.SEVERE).withCause(e)).log("Failed to process prefab path: %s", this.unprocessedPrefabPath); }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 177 */     if (!this.unprocessedPrefabPath.endsWith(".prefab.json")) {
/* 178 */       this.unprocessedPrefabPath += ".prefab.json";
/*     */     }
/* 180 */     this.prefabPaths.add(this.rootDirectory.getPrefabPath().resolve(this.unprocessedPrefabPath));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\buildertool\config\PrefabListAsset$PrefabReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */