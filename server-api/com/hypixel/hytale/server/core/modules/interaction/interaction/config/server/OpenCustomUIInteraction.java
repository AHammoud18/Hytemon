/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.PageManager;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class OpenCustomUIInteraction extends SimpleInstantInteraction {
/*  33 */   public static final CodecMapCodec<CustomPageSupplier> PAGE_CODEC = new CodecMapCodec();
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<OpenCustomUIInteraction> CODEC;
/*     */ 
/*     */   
/*     */   private CustomPageSupplier customPageSupplier;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OpenCustomUIInteraction.class, OpenCustomUIInteraction::new, SimpleInstantInteraction.CODEC).documentation("Opens a custom ui page.")).appendInherited(new KeyedCodec("Page", (Codec)PAGE_CODEC), (o, v) -> o.customPageSupplier = v, o -> o.customPageSupplier, (o, p) -> o.customPageSupplier = p.customPageSupplier).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  50 */     Ref<EntityStore> ref = context.getEntity();
/*  51 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*     */     
/*  53 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  54 */     if (playerComponent == null)
/*     */       return; 
/*  56 */     PageManager pageManager = playerComponent.getPageManager();
/*  57 */     if (pageManager.getCustomPage() != null)
/*     */       return; 
/*  59 */     PlayerRef playerRef = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/*  60 */     assert playerRef != null;
/*     */     
/*  62 */     CustomUIPage page = this.customPageSupplier.tryCreate(ref, (ComponentAccessor<EntityStore>)commandBuffer, playerRef, context);
/*  63 */     if (page != null) {
/*  64 */       Store<EntityStore> store = commandBuffer.getStore();
/*  65 */       pageManager.openCustomPage(ref, store, page);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <S extends CustomPageSupplier> void registerCustomPageSupplier(@Nonnull PluginBase plugin, Class<?> tClass, String id, @Nonnull S supplier) {
/*  75 */     plugin.getCodecRegistry((StringCodecMapCodec)PAGE_CODEC).register(id, supplier.getClass(), (Codec)BuilderCodec.builder(tClass, () -> supplier).build());
/*     */   }
/*     */   
/*     */   public static void registerSimple(@Nonnull PluginBase plugin, Class<?> tClass, String id, @Nonnull Function<PlayerRef, CustomUIPage> supplier) {
/*  79 */     registerCustomPageSupplier(plugin, tClass, id, (ref, componentAccessor, playerRef, context) -> (CustomUIPage)supplier.apply(playerRef));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static <T extends BlockState> void registerBlockCustomPage(@Nonnull PluginBase plugin, Class<?> tClass, String id, @Nonnull Class<T> stateClass, @Nonnull BlockCustomPageSupplier<T> blockSupplier) {
/*  87 */     registerBlockCustomPage(plugin, tClass, id, stateClass, blockSupplier, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static <T extends BlockState> void registerBlockCustomPage(@Nonnull PluginBase plugin, Class<?> tClass, String id, @Nonnull Class<T> stateClass, @Nonnull BlockCustomPageSupplier<T> blockSupplier, boolean createState) {
/*  95 */     CustomPageSupplier supplier = (ref, componentAccessor, playerRef, context) -> {
/*     */         BlockPosition targetBlock = context.getTargetBlock();
/*     */         
/*     */         if (targetBlock == null) {
/*     */           return null;
/*     */         }
/*     */         
/*     */         Store<EntityStore> store = ref.getStore();
/*     */         
/*     */         World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */         
/*     */         BlockState state = world.getState(targetBlock.x, targetBlock.y, targetBlock.z, true);
/*     */         
/*     */         if (state == null) {
/*     */           if (createState) {
/*     */             WorldChunk chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/*     */             
/*     */             state = BlockStateModule.get().createBlockState(stateClass, chunk, new Vector3i(targetBlock.x, targetBlock.y, targetBlock.z), chunk.getBlockType(targetBlock.x, targetBlock.y, targetBlock.z));
/*     */             chunk.setState(targetBlock.x, targetBlock.y, targetBlock.z, state);
/*     */           } 
/*     */           if (state == null) {
/*     */             return null;
/*     */           }
/*     */         } 
/*     */         return stateClass.isInstance(state) ? blockSupplier.tryCreate(playerRef, stateClass.cast(state)) : null;
/*     */       };
/* 121 */     registerCustomPageSupplier(plugin, tClass, id, supplier);
/*     */   }
/*     */   
/*     */   public static void registerBlockEntityCustomPage(@Nonnull PluginBase plugin, Class<?> tClass, String id, @Nonnull BlockEntityCustomPageSupplier blockSupplier) {
/* 125 */     CustomPageSupplier supplier = (ref, componentAccessor, playerRef, context) -> {
/*     */         BlockPosition targetBlock = context.getTargetBlock();
/*     */         
/*     */         if (targetBlock == null) {
/*     */           return null;
/*     */         }
/*     */         
/*     */         Store<EntityStore> store = ref.getStore();
/*     */         
/*     */         World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */         WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/*     */         if (chunk == null) {
/*     */           return null;
/*     */         }
/*     */         BlockPosition targetBaseBlock = world.getBaseBlock(targetBlock);
/*     */         Ref<ChunkStore> blockEntityRef = chunk.getBlockComponentEntity(targetBaseBlock.x, targetBaseBlock.y, targetBaseBlock.z);
/*     */         return (blockEntityRef == null) ? null : blockSupplier.tryCreate(playerRef, blockEntityRef);
/*     */       };
/* 143 */     registerCustomPageSupplier(plugin, tClass, id, supplier);
/*     */   }
/*     */   
/*     */   public static void registerBlockEntityCustomPage(@Nonnull PluginBase plugin, Class<?> tClass, String id, @Nonnull BlockEntityCustomPageSupplier blockSupplier, Supplier<Holder<ChunkStore>> creator) {
/* 147 */     CustomPageSupplier supplier = (ref, componentAccessor, playerRef, context) -> {
/*     */         BlockPosition targetBlock = context.getTargetBlock();
/*     */         if (targetBlock == null) {
/*     */           return null;
/*     */         }
/*     */         Store<EntityStore> store = ref.getStore();
/*     */         World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */         WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/*     */         if (chunk == null) {
/*     */           return null;
/*     */         }
/*     */         BlockPosition targetBaseBlock = world.getBaseBlock(targetBlock);
/*     */         BlockComponentChunk blockComponentChunk = chunk.getBlockComponentChunk();
/*     */         int index = ChunkUtil.indexBlockInColumn(targetBaseBlock.x, targetBaseBlock.y, targetBaseBlock.z);
/*     */         Ref<ChunkStore> blockEntityRef = blockComponentChunk.getEntityReference(index);
/*     */         if (blockEntityRef == null) {
/*     */           Holder<ChunkStore> holder = creator.get();
/*     */           holder.putComponent(BlockModule.BlockStateInfo.getComponentType(), (Component)new BlockModule.BlockStateInfo(index, chunk.getReference()));
/*     */           blockEntityRef = world.getChunkStore().getStore().addEntity(holder, AddReason.SPAWN);
/*     */         } 
/*     */         return blockSupplier.tryCreate(playerRef, blockEntityRef);
/*     */       };
/* 169 */     registerCustomPageSupplier(plugin, tClass, id, supplier);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface CustomPageSupplier {
/*     */     @Nullable
/*     */     CustomUIPage tryCreate(Ref<EntityStore> param1Ref, ComponentAccessor<EntityStore> param1ComponentAccessor, PlayerRef param1PlayerRef, InteractionContext param1InteractionContext);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BlockCustomPageSupplier<T extends BlockState> {
/*     */     CustomUIPage tryCreate(PlayerRef param1PlayerRef, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BlockEntityCustomPageSupplier {
/*     */     CustomUIPage tryCreate(PlayerRef param1PlayerRef, Ref<ChunkStore> param1Ref);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\OpenCustomUIInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */