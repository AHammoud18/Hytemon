/*     */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeChunkPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.EntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ChunkInfoCommand extends AbstractWorldCommand {
/*     */   @Nonnull
/*  25 */   private static final Message MESSAGE_GENERAL_CHUNK_NOT_LOADED = Message.translation("server.general.chunkNotLoaded");
/*     */   @Nonnull
/*  27 */   private static final Message MESSAGE_COMMANDS_CHUNKINFO_LOAD_USAGE = Message.translation("server.commands.chunkinfo.load.usage");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  33 */   private final RequiredArg<RelativeChunkPosition> chunkPosArg = withRequiredArg("x z", "server.commands.chunk.info.position.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkInfoCommand() {
/*  39 */     super("info", "server.commands.chunk.info.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  44 */     RelativeChunkPosition chunkPosition = (RelativeChunkPosition)this.chunkPosArg.get(context);
/*  45 */     Vector2i position = chunkPosition.getChunkPosition(context, (ComponentAccessor)store);
/*     */     
/*  47 */     ChunkStore chunkStore = world.getChunkStore();
/*  48 */     Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/*  49 */     long chunkIndex = ChunkUtil.indexChunk(position.x, position.y);
/*  50 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*     */ 
/*     */     
/*  53 */     if (chunkRef == null || !chunkRef.isValid()) {
/*  54 */       context.sendMessage(MESSAGE_GENERAL_CHUNK_NOT_LOADED
/*  55 */           .param("chunkX", position.x)
/*  56 */           .param("chunkZ", position.y));
/*     */ 
/*     */       
/*  59 */       context.sendMessage(MESSAGE_COMMANDS_CHUNKINFO_LOAD_USAGE
/*  60 */           .param("chunkX", position.x)
/*  61 */           .param("chunkZ", position.y));
/*     */       
/*     */       return;
/*     */     } 
/*  65 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStoreStore.getComponent(chunkRef, WorldChunk.getComponentType());
/*  66 */     assert worldChunkComponent != null;
/*     */     
/*  68 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStoreStore.getComponent(chunkRef, BlockChunk.getComponentType());
/*  69 */     assert blockChunkComponent != null;
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
/*  82 */     Message msg = Message.translation("server.commands.chunkinfo.chunk").param("chunkX", position.x).param("chunkZ", position.y).param("startInit", worldChunkComponent.is(ChunkFlag.START_INIT)).param("init", worldChunkComponent.is(ChunkFlag.INIT)).param("newlyGenerated", worldChunkComponent.is(ChunkFlag.NEWLY_GENERATED)).param("onDisk", worldChunkComponent.is(ChunkFlag.ON_DISK)).param("ticking", worldChunkComponent.is(ChunkFlag.TICKING)).param("keepLoaded", worldChunkComponent.shouldKeepLoaded()).param("saving", worldChunkComponent.getNeedsSaving()).param("savingChunk", blockChunkComponent.getNeedsSaving());
/*     */     
/*  84 */     for (int i = 0; i < 10; i++) {
/*  85 */       BlockSection section = blockChunkComponent.getSectionAtIndex(i);
/*  86 */       msg.insert(Message.translation("server.commands.chunkinfo.section").param("index", i));
/*  87 */       if (section instanceof BlockSection) { BlockSection blockSection = section;
/*  88 */         msg.insert(Message.translation("server.commands.chunkinfo.dataType")
/*  89 */             .param("data", blockSection.getChunkSection().getClass().getSimpleName())); }
/*     */       
/*  91 */       msg.insert(Message.translation("server.commands.chunkinfo.sectionInfo")
/*  92 */           .param("ticking", section.hasTicking())
/*  93 */           .param("solidAir", section.isSolidAir())
/*  94 */           .param("count", section.count())
/*  95 */           .param("counts", section.valueCounts().toString()));
/*     */     } 
/*     */     
/*  98 */     BlockComponentChunk blockStateChunk = (BlockComponentChunk)chunkStoreStore.getComponent(chunkRef, BlockComponentChunk.getComponentType());
/*  99 */     EntityChunk entityChunk = (EntityChunk)chunkStoreStore.getComponent(chunkRef, EntityChunk.getComponentType());
/* 100 */     if (blockStateChunk != null && entityChunk != null) {
/* 101 */       msg.insert(Message.translation("server.commands.chunkinfo.blockStateChunk")
/* 102 */           .param("saving", blockStateChunk.getNeedsSaving())
/* 103 */           .param("countStates", blockStateChunk.getEntityHolders().size() + blockStateChunk.getEntityReferences().size())
/* 104 */           .param("savingEntity", entityChunk.getNeedsSaving())
/* 105 */           .param("countEntities", entityChunk.getEntityHolders().size() + entityChunk.getEntityReferences().size()));
/*     */     }
/*     */     
/* 108 */     context.sendMessage(msg);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkInfoCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */