/*     */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkLoader;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
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
/*     */ public class MigrationChunkStorageProvider
/*     */   implements IChunkStorageProvider
/*     */ {
/*     */   public static final String ID = "Migration";
/*     */   @Nonnull
/*     */   public static final BuilderCodec<MigrationChunkStorageProvider> CODEC;
/*     */   private IChunkStorageProvider[] from;
/*     */   private IChunkStorageProvider to;
/*     */   
/*     */   static {
/*  55 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MigrationChunkStorageProvider.class, MigrationChunkStorageProvider::new).documentation("A provider that combines multiple storage providers in a chain to assist with migrating worlds between storage formats.\n\nCan also be used to set storage to load chunks but block saving them if combined with the **Empty** storage provider")).append(new KeyedCodec("Loaders", (Codec)new ArrayCodec((Codec)IChunkStorageProvider.CODEC, x$0 -> new IChunkStorageProvider[x$0])), (migration, o) -> migration.from = o, migration -> migration.from).documentation("A list of storage providers to use as chunk loaders.\n\nEach loader will be tried in order to load a chunk, returning the chunk if found otherwise trying the next loaded until found or none are left.").add()).append(new KeyedCodec("Saver", (Codec)IChunkStorageProvider.CODEC), (migration, o) -> migration.to = o, migration -> migration.to).documentation("The storage provider to use to save chunks.").add()).build();
/*     */   }
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
/*     */   public MigrationChunkStorageProvider() {}
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
/*     */   public MigrationChunkStorageProvider(@Nonnull IChunkStorageProvider[] from, @Nonnull IChunkStorageProvider to) {
/*  82 */     this.from = from;
/*  83 */     this.to = to;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IChunkLoader getLoader(@NonNullDecl Store<ChunkStore> store) throws IOException {
/*  89 */     IChunkLoader[] loaders = new IChunkLoader[this.from.length];
/*  90 */     for (int i = 0; i < this.from.length; i++) {
/*  91 */       loaders[i] = this.from[i].getLoader(store);
/*     */     }
/*  93 */     return new MigrationChunkLoader(loaders);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IChunkSaver getSaver(@NonNullDecl Store<ChunkStore> store) throws IOException {
/*  98 */     return this.to.getSaver(store);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 105 */     return "MigrationChunkStorageProvider{from=" + Arrays.toString((Object[])this.from) + ", to=" + String.valueOf(this.to) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MigrationChunkLoader
/*     */     implements IChunkLoader
/*     */   {
/*     */     @Nonnull
/*     */     private final IChunkLoader[] loaders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MigrationChunkLoader(@Nonnull IChunkLoader... loaders) {
/* 127 */       this.loaders = loaders;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 132 */       IOException exception = null;
/* 133 */       for (IChunkLoader loader : this.loaders) {
/*     */         try {
/* 135 */           loader.close();
/* 136 */         } catch (Exception e) {
/* 137 */           if (exception == null) exception = new IOException("Failed to close one or more loaders!"); 
/* 138 */           exception.addSuppressed(e);
/*     */         } 
/*     */       } 
/* 141 */       if (exception != null) throw exception;
/*     */     
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<Holder<ChunkStore>> loadHolder(int x, int z) {
/* 147 */       CompletableFuture<Holder<ChunkStore>> future = this.loaders[0].loadHolder(x, z);
/* 148 */       for (int i = 1; i < this.loaders.length; i++) {
/*     */         
/* 150 */         IChunkLoader loader = this.loaders[i];
/* 151 */         CompletableFuture<Holder<ChunkStore>> previous = future;
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
/* 167 */         future = previous.handle((worldChunk, throwable) -> (throwable != null) ? loader.loadHolder(x, z).exceptionally(()) : ((worldChunk == null) ? loader.loadHolder(x, z) : previous)).thenCompose(Function.identity());
/*     */       } 
/* 169 */       return future;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public LongSet getIndexes() throws IOException {
/* 175 */       LongOpenHashSet indexes = new LongOpenHashSet();
/* 176 */       for (IChunkLoader loader : this.loaders) {
/* 177 */         indexes.addAll((LongCollection)loader.getIndexes());
/*     */       }
/* 179 */       return (LongSet)indexes;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\MigrationChunkStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */