/*     */ package com.hypixel.hytale.builtin.adventure.stash;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ListTransaction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class StashPlugin extends JavaPlugin {
/*  33 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   public StashPlugin(@Nonnull JavaPluginInit init) {
/*  36 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  41 */     getChunkStoreRegistry().registerSystem((ISystem)new StashSystem(BlockStateModule.get().getComponentType(ItemContainerState.class)));
/*  42 */     getCodecRegistry(GameplayConfig.PLUGIN_CODEC).register(StashGameplayConfig.class, "Stash", (Codec)StashGameplayConfig.CODEC);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ListTransaction<ItemStackTransaction> stash(@Nonnull ItemContainerState containerState, boolean clearDropList) {
/*  47 */     String droplist = containerState.getDroplist();
/*  48 */     if (droplist == null) return null;
/*     */     
/*  50 */     List<ItemStack> stacks = ItemModule.get().getRandomItemDrops(droplist);
/*     */ 
/*     */     
/*  53 */     if (!stacks.isEmpty()) {
/*  54 */       ItemContainer itemContainer = containerState.getItemContainer();
/*  55 */       short capacity = itemContainer.getCapacity();
/*  56 */       ShortArrayList slots = new ShortArrayList(capacity); short s;
/*  57 */       for (s = 0; s < capacity; s = (short)(s + 1)) {
/*  58 */         slots.add(s);
/*     */       }
/*     */ 
/*     */       
/*  62 */       Vector3i blockPosition = containerState.getBlockPosition();
/*  63 */       long positionHash = blockPosition.hashCode();
/*  64 */       Random rnd = new Random(positionHash);
/*  65 */       ShortLists.shuffle((ShortList)slots, rnd);
/*     */       
/*  67 */       boolean anySucceeded = false;
/*     */ 
/*     */       
/*  70 */       for (int idx = 0; idx < stacks.size() && idx < slots.size(); idx++) {
/*  71 */         short slot = slots.getShort(idx);
/*  72 */         ItemStackSlotTransaction transaction = itemContainer.addItemStackToSlot(slot, stacks.get(idx));
/*     */         
/*  74 */         if (transaction.getRemainder() == null || transaction.getRemainder().isEmpty()) {
/*  75 */           anySucceeded = true;
/*     */         } else {
/*  77 */           LOGGER.at(Level.WARNING).log("Could not add Item to Stash at %d, %d, %d: %s", Integer.valueOf(blockPosition.x), Integer.valueOf(blockPosition.y), Integer.valueOf(blockPosition.z), transaction.getRemainder());
/*     */         } 
/*     */       } 
/*     */       
/*  81 */       if (clearDropList && anySucceeded) {
/*  82 */         containerState.setDroplist(null);
/*     */       }
/*     */       
/*  85 */       return new ListTransaction(anySucceeded, (List)new ObjectArrayList());
/*     */     } 
/*     */     
/*  88 */     return ListTransaction.getEmptyTransaction(true);
/*     */   }
/*     */   
/*     */   private static class StashSystem extends RefSystem<ChunkStore> {
/*     */     private final ComponentType<ChunkStore, ItemContainerState> componentType;
/*     */     @Nonnull
/*     */     private final Set<Dependency<ChunkStore>> dependencies;
/*     */     
/*     */     public StashSystem(ComponentType<ChunkStore, ItemContainerState> componentType) {
/*  97 */       this.componentType = componentType;
/*  98 */       this.dependencies = Set.of(new SystemDependency(Order.AFTER, BlockStateModule.LegacyBlockStateRefSystem.class));
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<ChunkStore> getQuery() {
/* 103 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 108 */       World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 109 */       if (world.getWorldConfig().getGameMode() == GameMode.Creative)
/*     */         return; 
/* 111 */       StashGameplayConfig stashGameplayConfig = StashGameplayConfig.getOrDefault(world.getGameplayConfig());
/*     */       
/* 113 */       boolean clearContainerDropList = stashGameplayConfig.isClearContainerDropList();
/* 114 */       StashPlugin.stash((ItemContainerState)store.getComponent(ref, this.componentType), clearContainerDropList);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<ChunkStore>> getDependencies() {
/* 124 */       return this.dependencies;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\stash\StashPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */