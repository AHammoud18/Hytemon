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
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
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
/*     */ public class GetCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/* 142 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK = Message.translation("server.commands.errors.playerNotLookingAtBlock");
/*     */   @Nonnull
/* 144 */   private static final Message MESSAGE_COMMANDS_NO_SECTION_COMPONENT = Message.translation("server.commands.noSectionComponent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 150 */   private final OptionalArg<RelativeIntPosition> targetOffset = withOptionalArg("offset", "", ArgTypes.RELATIVE_BLOCK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GetCommand() {
/* 156 */     super("get", "Gets the fluid at the target position");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 162 */     RelativeIntPosition offset = (RelativeIntPosition)this.targetOffset.get(context);
/* 163 */     Vector3i blockTarget = TargetUtil.getTargetBlock(ref, 8.0D, (ComponentAccessor)store);
/* 164 */     if (blockTarget == null) {
/* 165 */       playerRef.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK);
/*     */       
/*     */       return;
/*     */     } 
/* 169 */     ChunkStore chunkStore = world.getChunkStore();
/*     */ 
/*     */     
/* 172 */     Vector3i pos = (offset == null) ? blockTarget : offset.getBlockPosition(blockTarget.toVector3d(), chunkStore);
/*     */     
/* 174 */     chunkStore.getChunkSectionReferenceAsync(ChunkUtil.chunkCoordinate(pos.x), ChunkUtil.chunkCoordinate(pos.y), ChunkUtil.chunkCoordinate(pos.z))
/* 175 */       .thenAcceptAsync(section -> {
/*     */           Store<ChunkStore> sectionStore = section.getStore();
/*     */           FluidSection fluidSection = (FluidSection)sectionStore.getComponent(section, FluidSection.getComponentType());
/*     */           if (fluidSection == null) {
/*     */             playerRef.sendMessage(MESSAGE_COMMANDS_NO_SECTION_COMPONENT);
/*     */             return;
/*     */           } 
/*     */           int index = ChunkUtil.indexBlock(pos.x, pos.y, pos.z);
/*     */           Fluid fluid = fluidSection.getFluid(index);
/*     */           byte level = fluidSection.getFluidLevel(index);
/*     */           playerRef.sendMessage(Message.translation("server.commands.get.success").param("x", pos.x).param("y", pos.y).param("z", pos.z).param("id", fluid.getId()).param("level", level));
/*     */         }(Executor)world);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidCommand$GetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */