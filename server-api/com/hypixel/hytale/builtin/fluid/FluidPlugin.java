/*     */ package com.hypixel.hytale.builtin.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.lookup.Priority;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.event.EventPriority;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickStrategy;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.DefaultFluidTicker;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.FiniteFluidTicker;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.FluidTicker;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ChunkPreLoadProcessEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.time.Instant;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class FluidPlugin
/*     */   extends JavaPlugin {
/*  35 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private static FluidPlugin instance;
/*     */   
/*     */   public static FluidPlugin get() {
/*  40 */     return instance;
/*     */   }
/*     */   
/*     */   public FluidPlugin(@Nonnull JavaPluginInit init) {
/*  44 */     super(init);
/*  45 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  50 */     FluidTicker.CODEC.register(Priority.DEFAULT, "Default", DefaultFluidTicker.class, (Codec)DefaultFluidTicker.CODEC);
/*  51 */     FluidTicker.CODEC.register("Finite", FiniteFluidTicker.class, (Codec)FiniteFluidTicker.CODEC);
/*     */     
/*  53 */     getChunkStoreRegistry().registerSystem((ISystem)new FluidSystems.EnsureFluidSection());
/*  54 */     getChunkStoreRegistry().registerSystem((ISystem)new FluidSystems.MigrateFromColumn());
/*  55 */     getChunkStoreRegistry().registerSystem((ISystem)new FluidSystems.SetupSection());
/*  56 */     getChunkStoreRegistry().registerSystem((ISystem)new FluidSystems.LoadPacketGenerator());
/*  57 */     getChunkStoreRegistry().registerSystem((ISystem)new FluidSystems.ReplicateChanges());
/*  58 */     getChunkStoreRegistry().registerSystem((ISystem)new FluidSystems.Ticking());
/*     */     
/*  60 */     getEventRegistry().registerGlobal(EventPriority.FIRST, ChunkPreLoadProcessEvent.class, FluidPlugin::onChunkPreProcess);
/*     */     
/*  62 */     getCommandRegistry().registerCommand((AbstractCommand)new FluidCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void onChunkPreProcess(@Nonnull ChunkPreLoadProcessEvent event) {
/*  67 */     if (!event.isNewlyGenerated())
/*     */       return; 
/*  69 */     WorldChunk wc = event.getChunk();
/*  70 */     Holder<ChunkStore> holder = event.getHolder();
/*  71 */     ChunkColumn column = (ChunkColumn)holder.getComponent(ChunkColumn.getComponentType());
/*  72 */     if (column == null)
/*  73 */       return;  BlockChunk blockChunk = (BlockChunk)holder.getComponent(BlockChunk.getComponentType());
/*  74 */     if (blockChunk == null)
/*     */       return; 
/*  76 */     IndexedLookupTableAssetMap<String, Fluid> fluidMap = Fluid.getAssetMap();
/*  77 */     BlockTypeAssetMap<String, BlockType> blockMap = BlockType.getAssetMap();
/*     */     
/*  79 */     Holder[] arrayOfHolder = column.getSectionHolders();
/*  80 */     if (arrayOfHolder == null)
/*  81 */       return;  for (int i = 0; i < arrayOfHolder.length && 
/*  82 */       i < 10; i++) {
/*  83 */       Holder<ChunkStore> section = arrayOfHolder[i];
/*     */       
/*  85 */       FluidSection fluid = (FluidSection)section.getComponent(FluidSection.getComponentType());
/*  86 */       if (fluid != null && !fluid.isEmpty()) {
/*     */         
/*  88 */         BlockSection blockSection = (BlockSection)section.ensureAndGetComponent(BlockSection.getComponentType());
/*     */         
/*  90 */         for (int idx = 0; idx < 32768; idx++) {
/*  91 */           int fluidId = fluid.getFluidId(idx);
/*  92 */           if (fluidId == 0)
/*     */             continue; 
/*  94 */           Fluid fluidType = (Fluid)fluidMap.getAsset(fluidId);
/*  95 */           if (fluidType == null) {
/*  96 */             LOGGER.at(Level.WARNING).log("Invalid fluid found in chunk section: %d, %d %d with id %d", Integer.valueOf(fluid.getX()), Integer.valueOf(fluid.getY()), Integer.valueOf(fluid.getZ()), fluid);
/*  97 */             fluid.setFluid(idx, 0, (byte)0);
/*     */             
/*     */             continue;
/*     */           } 
/* 101 */           FluidTicker ticker = fluidType.getTicker();
/*     */ 
/*     */           
/* 104 */           if (FluidTicker.isSolid((BlockType)blockMap.getAsset(blockSection.get(idx)))) {
/* 105 */             fluid.setFluid(idx, 0, (byte)0);
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 111 */           if (!ticker.canDemote()) {
/* 112 */             int j, x = ChunkUtil.minBlock(fluid.getX()) + ChunkUtil.xFromIndex(idx);
/* 113 */             int y = ChunkUtil.minBlock(fluid.getY()) + ChunkUtil.yFromIndex(idx);
/* 114 */             int z = ChunkUtil.minBlock(fluid.getZ()) + ChunkUtil.zFromIndex(idx);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 119 */             boolean canSpread = (ChunkUtil.isBorderBlock(x, z) || (fluid.getFluidId(x - 1, y, z) == 0 && !FluidTicker.isSolid((BlockType)blockMap.getAsset(blockSection.get(x - 1, y, z)))) || (fluid.getFluidId(x + 1, y, z) == 0 && !FluidTicker.isSolid((BlockType)blockMap.getAsset(blockSection.get(x + 1, y, z)))) || (fluid.getFluidId(x, y, z - 1) == 0 && !FluidTicker.isSolid((BlockType)blockMap.getAsset(blockSection.get(x, y, z - 1)))) || (fluid.getFluidId(x, y, z + 1) == 0 && !FluidTicker.isSolid((BlockType)blockMap.getAsset(blockSection.get(x, y, z + 1)))));
/* 120 */             if (y > 0) {
/* 121 */               if (ChunkUtil.chunkCoordinate(y) == ChunkUtil.chunkCoordinate(y - 1)) {
/* 122 */                 j = canSpread | ((fluid.getFluidId(x, y - 1, z) == 0 && !FluidTicker.isSolid((BlockType)blockMap.getAsset(blockSection.get(x, y - 1, z)))) ? 1 : 0);
/*     */               } else {
/* 124 */                 FluidSection fluidSection2 = (FluidSection)arrayOfHolder[i - 1].getComponent(FluidSection.getComponentType());
/* 125 */                 j |= (fluidSection2.getFluidId(x, y - 1, z) == 0 && !FluidTicker.isSolid((BlockType)blockMap.getAsset(blockChunk.getBlock(x, y - 1, z)))) ? 1 : 0;
/*     */               } 
/*     */             }
/* 128 */             if (j == 0) {
/* 129 */               blockSection.setTicking(idx, false);
/*     */               
/*     */               continue;
/*     */             } 
/*     */           } 
/* 134 */           blockSection.setTicking(idx, true); continue;
/*     */         } 
/*     */       } 
/*     */     } 
/* 138 */     int tickingBlocks = blockChunk.getTickingBlocksCount();
/* 139 */     if (tickingBlocks == 0)
/*     */       return; 
/* 141 */     PreprocesorAccessor accessor = new PreprocesorAccessor(wc, blockChunk, (Holder<ChunkStore>[])arrayOfHolder);
/*     */     
/*     */     do {
/* 144 */       blockChunk.preTick(Instant.MIN);
/* 145 */       for (int j = 0; j < arrayOfHolder.length; j++) {
/* 146 */         Holder<ChunkStore> section = arrayOfHolder[j];
/* 147 */         FluidSection fluidSection = (FluidSection)section.getComponent(FluidSection.getComponentType());
/* 148 */         if (fluidSection != null && !fluidSection.isEmpty()) {
/* 149 */           BlockSection blockSection = (BlockSection)section.ensureAndGetComponent(BlockSection.getComponentType());
/*     */           
/* 151 */           fluidSection.preload(wc.getX(), j, wc.getZ());
/* 152 */           accessor.blockSection = blockSection;
/*     */           
/* 154 */           blockSection.forEachTicking(accessor, fluidSection, j, (preprocesorAccessor, fluidSection1, x, y, z, block) -> {
/*     */                 int fluidId = fluidSection1.getFluidId(x, y, z);
/*     */                 if (fluidId == 0) {
/*     */                   return BlockTickStrategy.IGNORED;
/*     */                 }
/*     */                 Fluid fluid = (Fluid)Fluid.getAssetMap().getAsset(fluidId);
/*     */                 int blockX = fluidSection1.getX() << 5 | x;
/*     */                 int blockY = y;
/*     */                 int blockZ = fluidSection1.getZ() << 5 | z;
/*     */                 return fluid.getTicker().process(preprocesorAccessor.worldChunk.getWorld(), preprocesorAccessor.tick, preprocesorAccessor, fluidSection1, accessor.blockSection, fluid, fluidId, blockX, blockY, blockZ);
/*     */               });
/*     */         } 
/*     */       } 
/* 167 */       tickingBlocks = blockChunk.getTickingBlocksCount();
/* 168 */       accessor.tick++;
/* 169 */     } while (tickingBlocks != 0 && accessor.tick <= 100L);
/*     */     
/* 171 */     blockChunk.mergeTickingBlocks();
/*     */   }
/*     */   
/*     */   public static class PreprocesorAccessor implements FluidTicker.Accessor {
/*     */     private final WorldChunk worldChunk;
/*     */     private final BlockChunk blockChunk;
/*     */     private final Holder<ChunkStore>[] sections;
/*     */     public long tick;
/*     */     public BlockSection blockSection;
/*     */     
/*     */     public PreprocesorAccessor(WorldChunk worldChunk, BlockChunk blockChunk, Holder<ChunkStore>[] sections) {
/* 182 */       this.worldChunk = worldChunk;
/* 183 */       this.blockChunk = blockChunk;
/* 184 */       this.sections = sections;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public FluidSection getFluidSection(int cx, int cy, int cz) {
/* 190 */       if (this.blockChunk.getX() == cx && this.blockChunk.getZ() == cz && cy >= 0 && cy < this.sections.length) {
/* 191 */         return (FluidSection)this.sections[cy].getComponent(FluidSection.getComponentType());
/*     */       }
/* 193 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public BlockSection getBlockSection(int cx, int cy, int cz) {
/* 199 */       if (cy < 0 || cy >= 10) return null; 
/* 200 */       if (this.blockChunk.getX() == cx && this.blockChunk.getZ() == cz) {
/* 201 */         return this.blockChunk.getSectionAtIndex(cy);
/*     */       }
/* 203 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setBlock(int x, int y, int z, int blockId) {
/* 208 */       if (this.worldChunk.getX() != ChunkUtil.chunkCoordinate(x) && this.worldChunk.getZ() != ChunkUtil.chunkCoordinate(z))
/* 209 */         return;  this.worldChunk.setBlock(x, y, z, blockId, 157);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */