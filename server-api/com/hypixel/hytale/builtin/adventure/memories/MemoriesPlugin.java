/*     */ package com.hypixel.hytale.builtin.adventure.memories;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.commands.MemoriesCommand;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.component.PlayerMemories;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.interactions.MemoriesConditionInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.interactions.SetMemoriesCapacityInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.memories.Memory;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.memories.MemoryProvider;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.memories.npc.NPCMemory;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.memories.npc.NPCMemoryProvider;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.page.MemoriesPage;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.page.MemoriesPageSupplier;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.temple.ForgottenTempleConfig;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.server.core.Constants;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.server.npc.AllNPCsLoadedEvent;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class MemoriesPlugin extends JavaPlugin {
/*     */   public static MemoriesPlugin get() {
/*  57 */     return instance;
/*     */   }
/*     */   private static MemoriesPlugin instance;
/*  60 */   private final Config<MemoriesPluginConfig> config = withConfig(MemoriesPluginConfig.CODEC);
/*     */   
/*  62 */   private final List<MemoryProvider<?>> providers = (List<MemoryProvider<?>>)new ObjectArrayList();
/*     */   
/*  64 */   private final Map<String, Set<Memory>> allMemories = (Map<String, Set<Memory>>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   private ComponentType<EntityStore, PlayerMemories> playerMemoriesComponentType;
/*     */   @Nullable
/*     */   private RecordedMemories recordedMemories;
/*     */   private boolean hasInitializedMemories;
/*     */   
/*     */   public MemoriesPlugin(@Nonnull JavaPluginInit init) {
/*  72 */     super(init);
/*  73 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  78 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*     */     
/*  80 */     getCommandRegistry().registerCommand((AbstractCommand)new MemoriesCommand());
/*     */     
/*  82 */     OpenCustomUIInteraction.registerCustomPageSupplier((PluginBase)this, MemoriesPage.class, "Memories", (OpenCustomUIInteraction.CustomPageSupplier)new MemoriesPageSupplier());
/*  83 */     Window.CLIENT_REQUESTABLE_WINDOW_TYPES.put(WindowType.Memories, com.hypixel.hytale.builtin.adventure.memories.window.MemoriesWindow::new);
/*     */     
/*  85 */     this.playerMemoriesComponentType = entityStoreRegistry.registerComponent(PlayerMemories.class, "PlayerMemories", PlayerMemories.CODEC);
/*     */ 
/*     */     
/*  88 */     NPCMemoryProvider npcMemoryProvider = new NPCMemoryProvider();
/*  89 */     registerMemoryProvider((MemoryProvider<Memory>)npcMemoryProvider);
/*  90 */     entityStoreRegistry.registerSystem((ISystem)new NPCMemory.GatherMemoriesSystem(npcMemoryProvider.getCollectionRadius()));
/*     */ 
/*     */ 
/*     */     
/*  94 */     for (MemoryProvider<?> provider : this.providers) {
/*  95 */       BuilderCodec<? extends Memory> codec = provider.getCodec();
/*  96 */       getCodecRegistry((StringCodecMapCodec)Memory.CODEC).register(provider.getId(), codec.getInnerClass(), (Codec)codec);
/*     */     } 
/*     */ 
/*     */     
/* 100 */     getEventRegistry().register(AllNPCsLoadedEvent.class, event -> onAssetsLoad());
/*     */     
/* 102 */     entityStoreRegistry.registerSystem((ISystem)new PlayerAddedSystem());
/*     */     
/* 104 */     getCodecRegistry(Interaction.CODEC).register("SetMemoriesCapacity", SetMemoriesCapacityInteraction.class, SetMemoriesCapacityInteraction.CODEC);
/* 105 */     getCodecRegistry(GameplayConfig.PLUGIN_CODEC).register(MemoriesGameplayConfig.class, "Memories", (Codec)MemoriesGameplayConfig.CODEC);
/*     */     
/* 107 */     getCodecRegistry(Interaction.CODEC).register("MemoriesCondition", MemoriesConditionInteraction.class, MemoriesConditionInteraction.CODEC);
/*     */     
/* 109 */     entityStoreRegistry.registerSystem((ISystem)new TempleRespawnPlayersSystem());
/* 110 */     getCodecRegistry(GameplayConfig.PLUGIN_CODEC).register(ForgottenTempleConfig.class, "ForgottenTemple", (Codec)ForgottenTempleConfig.CODEC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void start() {
/*     */     try {
/* 119 */       Path path = Constants.UNIVERSE_PATH.resolve("memories.json");
/* 120 */       if (Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 121 */         this.recordedMemories = (RecordedMemories)RawJsonReader.readSync(path, (Codec)RecordedMemories.CODEC, getLogger());
/*     */       } else {
/* 123 */         this.recordedMemories = new RecordedMemories();
/*     */       } 
/* 125 */     } catch (IOException e) {
/* 126 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 129 */     this.hasInitializedMemories = true;
/* 130 */     onAssetsLoad();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void shutdown() {
/* 135 */     this.recordedMemories.lock.readLock().lock();
/*     */     try {
/* 137 */       BsonUtil.writeSync(Constants.UNIVERSE_PATH.resolve("memories.json"), (Codec)RecordedMemories.CODEC, this.recordedMemories, getLogger());
/* 138 */     } catch (IOException e) {
/* 139 */       throw new RuntimeException(e);
/*     */     } finally {
/* 141 */       this.recordedMemories.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onAssetsLoad() {
/* 146 */     if (!this.hasInitializedMemories)
/*     */       return; 
/* 148 */     this.allMemories.clear();
/* 149 */     for (MemoryProvider<?> provider : this.providers) {
/* 150 */       for (Map.Entry<String, Set<Memory>> entry : (Iterable<Map.Entry<String, Set<Memory>>>)provider.getAllMemories().entrySet()) {
/* 151 */         ((Set)this.allMemories.computeIfAbsent(entry.getKey(), k -> new HashSet())).addAll(entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public MemoriesPluginConfig getConfig() {
/* 157 */     return (MemoriesPluginConfig)this.config.get();
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, PlayerMemories> getPlayerMemoriesComponentType() {
/* 161 */     return this.playerMemoriesComponentType;
/*     */   }
/*     */   
/*     */   public <T extends Memory> void registerMemoryProvider(MemoryProvider<T> memoryProvider) {
/* 165 */     this.providers.add(memoryProvider);
/*     */   }
/*     */   
/*     */   public Map<String, Set<Memory>> getAllMemories() {
/* 169 */     return this.allMemories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMemoriesLevel(@Nonnull GameplayConfig gameplayConfig) {
/* 179 */     MemoriesGameplayConfig config = MemoriesGameplayConfig.get(gameplayConfig);
/* 180 */     int memoriesLevel = 1;
/* 181 */     if (config == null) {
/* 182 */       return memoriesLevel;
/*     */     }
/* 184 */     int recordedMemoriesCount = getRecordedMemories().size();
/*     */     
/* 186 */     int[] memoriesAmountPerLevel = config.getMemoriesAmountPerLevel();
/* 187 */     for (int i = 0; i < memoriesAmountPerLevel.length && 
/* 188 */       recordedMemoriesCount >= memoriesAmountPerLevel[i]; i++)
/*     */     {
/* 190 */       memoriesLevel += i + 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     return memoriesLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMemoriesForNextLevel(@Nonnull GameplayConfig gameplayConfig) {
/* 207 */     MemoriesGameplayConfig memoriesConfig = MemoriesGameplayConfig.get(gameplayConfig);
/* 208 */     if (memoriesConfig == null) {
/* 209 */       return -1;
/*     */     }
/* 211 */     int memoriesLevel = getMemoriesLevel(gameplayConfig);
/* 212 */     int[] memoriesAmountPerLevel = memoriesConfig.getMemoriesAmountPerLevel();
/* 213 */     if (memoriesLevel >= memoriesAmountPerLevel.length) {
/* 214 */       return -1;
/*     */     }
/*     */     
/* 217 */     int recordedMemoriesCount = getRecordedMemories().size();
/* 218 */     return memoriesAmountPerLevel[memoriesLevel] - recordedMemoriesCount;
/*     */   }
/*     */   
/*     */   public boolean hasRecordedMemory(Memory memory) {
/* 222 */     this.recordedMemories.lock.readLock().lock();
/*     */     try {
/* 224 */       return this.recordedMemories.memories.contains(memory);
/*     */     } finally {
/* 226 */       this.recordedMemories.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean recordPlayerMemories(@Nonnull PlayerMemories playerMemories) {
/* 231 */     this.recordedMemories.lock.writeLock().lock();
/*     */     try {
/* 233 */       if (playerMemories.takeMemories(this.recordedMemories.memories)) {
/* 234 */         BsonUtil.writeSync(Constants.UNIVERSE_PATH.resolve("memories.json"), (Codec)RecordedMemories.CODEC, this.recordedMemories, getLogger());
/* 235 */         return true;
/*     */       } 
/* 237 */     } catch (IOException e) {
/* 238 */       throw new RuntimeException(e);
/*     */     } finally {
/* 240 */       this.recordedMemories.lock.writeLock().unlock();
/*     */     } 
/* 242 */     return false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<Memory> getRecordedMemories() {
/* 247 */     this.recordedMemories.lock.readLock().lock();
/*     */     try {
/* 249 */       return new HashSet<>(this.recordedMemories.memories);
/*     */     } finally {
/* 251 */       this.recordedMemories.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearRecordedMemories() {
/* 256 */     this.recordedMemories.lock.writeLock().lock();
/*     */     try {
/* 258 */       this.recordedMemories.memories.clear();
/* 259 */       BsonUtil.writeSync(Constants.UNIVERSE_PATH.resolve("memories.json"), (Codec)RecordedMemories.CODEC, this.recordedMemories, getLogger());
/* 260 */     } catch (IOException e) {
/* 261 */       throw new RuntimeException(e);
/*     */     } finally {
/* 263 */       this.recordedMemories.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void recordAllMemories() {
/* 268 */     this.recordedMemories.lock.writeLock().lock();
/*     */     try {
/* 270 */       for (Map.Entry<String, Set<Memory>> entry : this.allMemories.entrySet()) {
/* 271 */         this.recordedMemories.memories.addAll(entry.getValue());
/*     */       }
/* 273 */       BsonUtil.writeSync(Constants.UNIVERSE_PATH.resolve("memories.json"), (Codec)RecordedMemories.CODEC, this.recordedMemories, getLogger());
/* 274 */     } catch (IOException e) {
/* 275 */       throw new RuntimeException(e);
/*     */     } finally {
/* 277 */       this.recordedMemories.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MemoriesPluginConfig
/*     */   {
/*     */     public static final BuilderCodec<MemoriesPluginConfig> CODEC;
/*     */     
/*     */     private Object2DoubleMap<String> collectionRadius;
/*     */     
/*     */     static {
/* 289 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(MemoriesPluginConfig.class, MemoriesPluginConfig::new).append(new KeyedCodec("CollectionRadius", (Codec)new Object2DoubleMapCodec((Codec)Codec.STRING, it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap::new)), (config, map) -> config.collectionRadius = map, config -> config.collectionRadius).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Object2DoubleMap<String> getCollectionRadius() {
/* 299 */       return (this.collectionRadius != null) ? this.collectionRadius : (Object2DoubleMap<String>)Object2DoubleMaps.EMPTY_MAP;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RecordedMemories
/*     */   {
/*     */     public static final BuilderCodec<RecordedMemories> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 314 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(RecordedMemories.class, RecordedMemories::new).append(new KeyedCodec("Memories", (Codec)new ArrayCodec((Codec)Memory.CODEC, x$0 -> new Memory[x$0])), (recordedMemories, memories) -> { if (memories == null) return;  Collections.addAll(recordedMemories.memories, memories); }recordedMemories -> (Memory[])recordedMemories.memories.toArray(())).add()).build();
/*     */     }
/* 316 */     private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
/* 317 */     private final Set<Memory> memories = new HashSet<>();
/*     */   }
/*     */   
/*     */   public static class PlayerAddedSystem
/*     */     extends RefSystem<EntityStore> {
/*     */     @Nonnull
/* 323 */     private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, PlayerSystems.PlayerSpawnedSystem.class));
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public PlayerAddedSystem() {
/* 329 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)Player.getComponentType(), (Query)PlayerRef.getComponentType() });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 335 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 341 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 346 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 347 */       assert playerComponent != null;
/*     */       
/* 349 */       PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 350 */       assert playerRefComponent != null;
/*     */       
/* 352 */       PlayerMemories playerMemoriesComponent = (PlayerMemories)store.getComponent(ref, PlayerMemories.getComponentType());
/* 353 */       boolean isFeatureUnlockedByPlayer = (playerMemoriesComponent != null);
/*     */       
/* 355 */       PacketHandler playerConnection = playerRefComponent.getPacketHandler();
/* 356 */       playerConnection.writeNoCache((Packet)new UpdateMemoriesFeatureStatus(isFeatureUnlockedByPlayer));
/*     */     }
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\MemoriesPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */