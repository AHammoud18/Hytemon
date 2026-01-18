/*     */ package com.hypixel.hytale.builtin.blocktick;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.builtin.blocktick.procedure.BasicChanceBlockGrowthProcedure;
/*     */ import com.hypixel.hytale.builtin.blocktick.procedure.SplitChanceBlockGrowthProcedure;
/*     */ import com.hypixel.hytale.builtin.blocktick.system.ChunkBlockTickSystem;
/*     */ import com.hypixel.hytale.builtin.blocktick.system.MergeWaitingBlocksSystem;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.event.EventPriority;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickManager;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.IBlockTickProvider;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.config.TickProcedure;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ChunkPreLoadProcessEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BlockTickPlugin
/*     */   extends JavaPlugin implements IBlockTickProvider {
/*     */   public static BlockTickPlugin get() {
/*  29 */     return instance;
/*     */   }
/*     */   private static BlockTickPlugin instance;
/*     */   public BlockTickPlugin(@Nonnull JavaPluginInit init) {
/*  33 */     super(init);
/*  34 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  39 */     TickProcedure.CODEC.register("BasicChance", BasicChanceBlockGrowthProcedure.class, (Codec)BasicChanceBlockGrowthProcedure.CODEC);
/*  40 */     TickProcedure.CODEC.register("SplitChance", SplitChanceBlockGrowthProcedure.class, (Codec)SplitChanceBlockGrowthProcedure.CODEC);
/*     */     
/*  42 */     getEventRegistry().registerGlobal(EventPriority.EARLY, ChunkPreLoadProcessEvent.class, this::discoverTickingBlocks);
/*     */     
/*  44 */     ChunkStore.REGISTRY.registerSystem((ISystem)new ChunkBlockTickSystem.PreTick());
/*  45 */     ChunkStore.REGISTRY.registerSystem((ISystem)new ChunkBlockTickSystem.Ticking());
/*  46 */     ChunkStore.REGISTRY.registerSystem((ISystem)new MergeWaitingBlocksSystem());
/*     */     
/*  48 */     BlockTickManager.setBlockTickProvider(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public TickProcedure getTickProcedure(int blockId) {
/*  53 */     return ((BlockType)BlockType.getAssetMap().getAsset(blockId)).getTickProcedure();
/*     */   }
/*     */   
/*     */   private void discoverTickingBlocks(@Nonnull ChunkPreLoadProcessEvent event) {
/*  57 */     if (!event.isNewlyGenerated())
/*  58 */       return;  discoverTickingBlocks(event.getHolder(), event.getChunk());
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
/*     */   public int discoverTickingBlocks(@Nonnull Holder<ChunkStore> holder, @Nonnull WorldChunk chunk) {
/*  71 */     if (!isEnabled()) return 0;
/*     */     
/*  73 */     BlockChunk bc = chunk.getBlockChunk();
/*  74 */     if (!bc.consumeNeedsPhysics()) return 0;
/*     */     
/*  76 */     ChunkColumn column = (ChunkColumn)holder.getComponent(ChunkColumn.getComponentType());
/*  77 */     if (column == null) return 0;
/*     */     
/*  79 */     Holder[] arrayOfHolder = column.getSectionHolders();
/*  80 */     if (arrayOfHolder == null) return 0;
/*     */     
/*  82 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */ 
/*     */     
/*  85 */     int count = 0;
/*  86 */     for (int i = 0; i < arrayOfHolder.length; i++) {
/*  87 */       Holder<ChunkStore> sectionHolder = arrayOfHolder[i];
/*  88 */       BlockSection section = (BlockSection)sectionHolder.ensureAndGetComponent(BlockSection.getComponentType());
/*     */       
/*  90 */       if (!section.isSolidAir())
/*  91 */         for (int blockIdx = 0; blockIdx < 32768; blockIdx++) {
/*  92 */           int blockId = section.get(blockIdx);
/*  93 */           BlockType blockType = (BlockType)assetMap.getAsset(blockId);
/*  94 */           if (blockType != null && blockType.getTickProcedure() != null) {
/*     */             
/*  96 */             section.setTicking(blockIdx, true);
/*  97 */             bc.markNeedsSaving();
/*  98 */             count++;
/*     */           } 
/*     */         }  
/* 101 */     }  return count;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blocktick\BlockTickPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */