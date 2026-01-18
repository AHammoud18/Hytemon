/*     */ package com.hypixel.hytale.builtin.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import java.util.concurrent.Executor;
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
/*     */ public class SetCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  52 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK = Message.translation("server.commands.errors.playerNotLookingAtBlock");
/*     */   @Nonnull
/*  54 */   private static final Message MESSAGE_COMMANDS_SET_UNKNOWN_FLUID = Message.translation("server.commands.set.unknownFluid");
/*     */   @Nonnull
/*  56 */   private static final Message MESSAGE_COMMANDS_NO_SECTION_COMPONENT = Message.translation("server.commands.noSectionComponent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  62 */   private final RequiredArg<Fluid> fluid = withRequiredArg("fluid", "", (ArgumentType)FluidCommand.FLUID_ARG);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  68 */   private final RequiredArg<Integer> level = withRequiredArg("level", "", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  74 */   private final OptionalArg<RelativeIntPosition> targetOffset = withOptionalArg("offset", "", ArgTypes.RELATIVE_BLOCK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SetCommand() {
/*  80 */     super("set", "Changes the fluid at the target position");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  85 */     RelativeIntPosition offset = (RelativeIntPosition)this.targetOffset.get(context);
/*  86 */     Vector3i blockTarget = TargetUtil.getTargetBlock(ref, 8.0D, (ComponentAccessor)store);
/*  87 */     if (blockTarget == null) {
/*  88 */       playerRef.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK);
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     ChunkStore chunkStore = world.getChunkStore();
/*     */ 
/*     */     
/*  95 */     Vector3i pos = (offset == null) ? blockTarget : offset.getBlockPosition(blockTarget.toVector3d(), chunkStore);
/*     */     
/*  97 */     Fluid fluid = (Fluid)this.fluid.get(context);
/*  98 */     if (fluid == null) {
/*  99 */       playerRef.sendMessage(MESSAGE_COMMANDS_SET_UNKNOWN_FLUID);
/*     */       
/*     */       return;
/*     */     } 
/* 103 */     Integer level = (Integer)this.level.get(context);
/* 104 */     if (level.intValue() > fluid.getMaxFluidLevel()) {
/* 105 */       level = Integer.valueOf(fluid.getMaxFluidLevel());
/* 106 */       playerRef.sendMessage(Message.translation("server.commands.set.maxFluidLevelClamped")
/* 107 */           .param("level", fluid.getMaxFluidLevel()));
/*     */     } 
/*     */     
/* 110 */     Integer finalLevel = level;
/* 111 */     chunkStore.getChunkSectionReferenceAsync(ChunkUtil.chunkCoordinate(pos.x), ChunkUtil.chunkCoordinate(pos.y), ChunkUtil.chunkCoordinate(pos.z))
/* 112 */       .thenAcceptAsync(section -> {
/*     */           Store<ChunkStore> sectionStore = section.getStore();
/*     */           FluidSection fluidSection = (FluidSection)sectionStore.getComponent(section, FluidSection.getComponentType());
/*     */           if (fluidSection == null) {
/*     */             playerRef.sendMessage(MESSAGE_COMMANDS_NO_SECTION_COMPONENT);
/*     */             return;
/*     */           } 
/*     */           int index = ChunkUtil.indexBlock(pos.x, pos.y, pos.z);
/*     */           fluidSection.setFluid(index, fluid, finalLevel.byteValue());
/*     */           playerRef.sendMessage(Message.translation("server.commands.set.success").param("x", pos.x).param("y", pos.y).param("z", pos.z).param("id", fluid.getId()).param("level", finalLevel.intValue()));
/*     */           ChunkSection chunkSection = (ChunkSection)sectionStore.getComponent(section, ChunkSection.getComponentType());
/*     */           WorldChunk worldChunk = (WorldChunk)sectionStore.getComponent(chunkSection.getChunkColumnReference(), WorldChunk.getComponentType());
/*     */           worldChunk.markNeedsSaving();
/*     */           worldChunk.setTicking(pos.x, pos.y, pos.z, true);
/*     */         }(Executor)world);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidCommand$SetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */