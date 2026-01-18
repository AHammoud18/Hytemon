/*    */ package com.hypixel.hytale.server.core.modules.migrations;
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.SystemType;
/*    */ import com.hypixel.hytale.server.core.Options;
/*    */ import com.hypixel.hytale.server.core.event.events.BootEvent;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkLoader;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.component.ChunkSavingSystems;
/*    */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.nio.file.Path;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import java.util.function.Function;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import joptsimple.OptionSet;
/*    */ 
/*    */ public class MigrationModule extends JavaPlugin {
/* 28 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(MigrationModule.class)
/* 29 */     .build();
/*    */   
/*    */   protected static MigrationModule instance;
/*    */   @Nonnull
/* 33 */   private final Map<String, Function<Path, Migration>> migrationCtors = (Map<String, Function<Path, Migration>>)new Object2ObjectOpenHashMap();
/*    */   
/*    */   private SystemType<ChunkStore, ChunkColumnMigrationSystem> chunkColumnMigrationSystem;
/*    */   private SystemType<ChunkStore, ChunkSectionMigrationSystem> chunkSectionMigrationSystem;
/*    */   
/*    */   public MigrationModule(@Nonnull JavaPluginInit init) {
/* 39 */     super(init);
/* 40 */     instance = this;
/*    */   }
/*    */   
/*    */   public static MigrationModule get() {
/* 44 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 49 */     getEventRegistry().register(BootEvent.class, event -> {
/*    */           if (!Options.getOptionSet().has(Options.MIGRATIONS))
/*    */             return; 
/*    */           runMigrations();
/*    */           HytaleServer.get().shutdownServer();
/*    */         });
/* 55 */     this.chunkColumnMigrationSystem = getChunkStoreRegistry().registerSystemType(ChunkColumnMigrationSystem.class);
/* 56 */     this.chunkSectionMigrationSystem = getChunkStoreRegistry().registerSystemType(ChunkSectionMigrationSystem.class);
/*    */   }
/*    */   
/*    */   public SystemType<ChunkStore, ChunkColumnMigrationSystem> getChunkColumnMigrationSystem() {
/* 60 */     return this.chunkColumnMigrationSystem;
/*    */   }
/*    */   
/*    */   public SystemType<ChunkStore, ChunkSectionMigrationSystem> getChunkSectionMigrationSystem() {
/* 64 */     return this.chunkSectionMigrationSystem;
/*    */   }
/*    */   
/*    */   public void register(String id, Function<Path, Migration> migration) {
/* 68 */     this.migrationCtors.put(id, migration);
/*    */   }
/*    */   
/*    */   public void runMigrations() {
/* 72 */     OptionSet optionSet = Options.getOptionSet();
/* 73 */     List<String> worldsToMigrate = optionSet.has(Options.MIGRATE_WORLDS) ? optionSet.valuesOf(Options.MIGRATE_WORLDS) : null;
/* 74 */     Map<String, Path> migrationMap = (Map<String, Path>)Options.getOptionSet().valueOf(Options.MIGRATIONS);
/*    */     
/* 76 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 77 */     migrationMap.forEach((s, path) -> {
/*    */           Function<Path, Migration> migrationCtor = this.migrationCtors.get(s);
/*    */           if (migrationCtor == null)
/*    */             return; 
/*    */           migrations.add(migrationCtor.apply(path));
/*    */         });
/* 83 */     if (objectArrayList.isEmpty())
/*    */       return; 
/* 85 */     AtomicInteger worldsCount = new AtomicInteger();
/*    */     
/* 87 */     for (World world : Universe.get().getWorlds().values()) {
/* 88 */       String worldName = world.getName();
/* 89 */       if (worldsToMigrate != null && !worldsToMigrate.contains(worldName))
/*    */         continue; 
/* 91 */       worldsCount.incrementAndGet();
/* 92 */       getLogger().at(Level.INFO).log("Starting to migrate world '%s'...", worldName);
/*    */       
/* 94 */       ChunkStore chunkComponentStore = world.getChunkStore();
/* 95 */       IChunkSaver saver = chunkComponentStore.getSaver();
/* 96 */       IChunkLoader loader = chunkComponentStore.getLoader();
/*    */       
/* 98 */       world.execute(() -> {
/*    */             ChunkSavingSystems.Data data = (ChunkSavingSystems.Data)chunkComponentStore.getStore().getResource(ChunkStore.SAVE_RESOURCE);
/*    */             data.isSaving = false;
/*    */             data.waitForSavingChunks().whenComplete(());
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\migrations\MigrationModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */