/*     */ package com.hypixel.hytale.builtin.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
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
/*     */ public class FluidCommand
/*     */   extends AbstractCommandCollection {
/*  35 */   private static final SingleArgumentType<Fluid> FLUID_ARG = (SingleArgumentType<Fluid>)new AssetArgumentType("Fluid", Fluid.class, "");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidCommand() {
/*  41 */     super("fluid", "Fluid debug commands");
/*  42 */     addSubCommand((AbstractCommand)new SetCommand());
/*  43 */     addSubCommand((AbstractCommand)new GetCommand());
/*  44 */     addSubCommand((AbstractCommand)new SetRadiusCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SetCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  52 */     private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK = Message.translation("server.commands.errors.playerNotLookingAtBlock");
/*     */     @Nonnull
/*  54 */     private static final Message MESSAGE_COMMANDS_SET_UNKNOWN_FLUID = Message.translation("server.commands.set.unknownFluid");
/*     */     @Nonnull
/*  56 */     private static final Message MESSAGE_COMMANDS_NO_SECTION_COMPONENT = Message.translation("server.commands.noSectionComponent");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  62 */     private final RequiredArg<Fluid> fluid = withRequiredArg("fluid", "", (ArgumentType)FluidCommand.FLUID_ARG);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  68 */     private final RequiredArg<Integer> level = withRequiredArg("level", "", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  74 */     private final OptionalArg<RelativeIntPosition> targetOffset = withOptionalArg("offset", "", ArgTypes.RELATIVE_BLOCK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SetCommand() {
/*  80 */       super("set", "Changes the fluid at the target position");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  85 */       RelativeIntPosition offset = (RelativeIntPosition)this.targetOffset.get(context);
/*  86 */       Vector3i blockTarget = TargetUtil.getTargetBlock(ref, 8.0D, (ComponentAccessor)store);
/*  87 */       if (blockTarget == null) {
/*  88 */         playerRef.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK);
/*     */         
/*     */         return;
/*     */       } 
/*  92 */       ChunkStore chunkStore = world.getChunkStore();
/*     */ 
/*     */       
/*  95 */       Vector3i pos = (offset == null) ? blockTarget : offset.getBlockPosition(blockTarget.toVector3d(), chunkStore);
/*     */       
/*  97 */       Fluid fluid = (Fluid)this.fluid.get(context);
/*  98 */       if (fluid == null) {
/*  99 */         playerRef.sendMessage(MESSAGE_COMMANDS_SET_UNKNOWN_FLUID);
/*     */         
/*     */         return;
/*     */       } 
/* 103 */       Integer level = (Integer)this.level.get(context);
/* 104 */       if (level.intValue() > fluid.getMaxFluidLevel()) {
/* 105 */         level = Integer.valueOf(fluid.getMaxFluidLevel());
/* 106 */         playerRef.sendMessage(Message.translation("server.commands.set.maxFluidLevelClamped")
/* 107 */             .param("level", fluid.getMaxFluidLevel()));
/*     */       } 
/*     */       
/* 110 */       Integer finalLevel = level;
/* 111 */       chunkStore.getChunkSectionReferenceAsync(ChunkUtil.chunkCoordinate(pos.x), ChunkUtil.chunkCoordinate(pos.y), ChunkUtil.chunkCoordinate(pos.z))
/* 112 */         .thenAcceptAsync(section -> {
/*     */             Store<ChunkStore> sectionStore = section.getStore();
/*     */             FluidSection fluidSection = (FluidSection)sectionStore.getComponent(section, FluidSection.getComponentType());
/*     */             if (fluidSection == null) {
/*     */               playerRef.sendMessage(MESSAGE_COMMANDS_NO_SECTION_COMPONENT);
/*     */               return;
/*     */             } 
/*     */             int index = ChunkUtil.indexBlock(pos.x, pos.y, pos.z);
/*     */             fluidSection.setFluid(index, fluid, finalLevel.byteValue());
/*     */             playerRef.sendMessage(Message.translation("server.commands.set.success").param("x", pos.x).param("y", pos.y).param("z", pos.z).param("id", fluid.getId()).param("level", finalLevel.intValue()));
/*     */             ChunkSection chunkSection = (ChunkSection)sectionStore.getComponent(section, ChunkSection.getComponentType());
/*     */             WorldChunk worldChunk = (WorldChunk)sectionStore.getComponent(chunkSection.getChunkColumnReference(), WorldChunk.getComponentType());
/*     */             worldChunk.markNeedsSaving();
/*     */             worldChunk.setTicking(pos.x, pos.y, pos.z, true);
/*     */           }(Executor)world);
/*     */     }
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
/*     */   public static class GetCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 142 */     private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK = Message.translation("server.commands.errors.playerNotLookingAtBlock");
/*     */     @Nonnull
/* 144 */     private static final Message MESSAGE_COMMANDS_NO_SECTION_COMPONENT = Message.translation("server.commands.noSectionComponent");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 150 */     private final OptionalArg<RelativeIntPosition> targetOffset = withOptionalArg("offset", "", ArgTypes.RELATIVE_BLOCK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GetCommand() {
/* 156 */       super("get", "Gets the fluid at the target position");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 162 */       RelativeIntPosition offset = (RelativeIntPosition)this.targetOffset.get(context);
/* 163 */       Vector3i blockTarget = TargetUtil.getTargetBlock(ref, 8.0D, (ComponentAccessor)store);
/* 164 */       if (blockTarget == null) {
/* 165 */         playerRef.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK);
/*     */         
/*     */         return;
/*     */       } 
/* 169 */       ChunkStore chunkStore = world.getChunkStore();
/*     */ 
/*     */       
/* 172 */       Vector3i pos = (offset == null) ? blockTarget : offset.getBlockPosition(blockTarget.toVector3d(), chunkStore);
/*     */       
/* 174 */       chunkStore.getChunkSectionReferenceAsync(ChunkUtil.chunkCoordinate(pos.x), ChunkUtil.chunkCoordinate(pos.y), ChunkUtil.chunkCoordinate(pos.z))
/* 175 */         .thenAcceptAsync(section -> {
/*     */             Store<ChunkStore> sectionStore = section.getStore();
/*     */             FluidSection fluidSection = (FluidSection)sectionStore.getComponent(section, FluidSection.getComponentType());
/*     */             if (fluidSection == null) {
/*     */               playerRef.sendMessage(MESSAGE_COMMANDS_NO_SECTION_COMPONENT);
/*     */               return;
/*     */             } 
/*     */             int index = ChunkUtil.indexBlock(pos.x, pos.y, pos.z);
/*     */             Fluid fluid = fluidSection.getFluid(index);
/*     */             byte level = fluidSection.getFluidLevel(index);
/*     */             playerRef.sendMessage(Message.translation("server.commands.get.success").param("x", pos.x).param("y", pos.y).param("z", pos.z).param("id", fluid.getId()).param("level", level));
/*     */           }(Executor)world);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SetRadiusCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 197 */     private static final Message MESSAGE_COMMANDS_SET_UNKNOWN_FLUID = Message.translation("server.commands.set.unknownFluid");
/*     */     @Nonnull
/* 199 */     private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK = Message.translation("server.commands.errors.playerNotLookingAtBlock");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 205 */     private final RequiredArg<Integer> radius = withRequiredArg("radius", "", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 211 */     private final RequiredArg<Fluid> fluid = withRequiredArg("fluid", "", (ArgumentType)FluidCommand.FLUID_ARG);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 217 */     private final RequiredArg<Integer> level = withRequiredArg("level", "", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 223 */     private final OptionalArg<RelativeIntPosition> targetOffset = withOptionalArg("offset", "", ArgTypes.RELATIVE_BLOCK_POSITION);
/*     */     
/*     */     public SetRadiusCommand() {
/* 226 */       super("setradius", "Changes the fluid at the player position in a given radius");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 231 */       RelativeIntPosition offset = (RelativeIntPosition)this.targetOffset.get(context);
/* 232 */       Vector3i blockTarget = TargetUtil.getTargetBlock(ref, 8.0D, (ComponentAccessor)store);
/* 233 */       if (blockTarget == null) {
/* 234 */         playerRef.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_LOOKING_AT_BLOCK);
/*     */         
/*     */         return;
/*     */       } 
/* 238 */       ChunkStore chunkStore = world.getChunkStore();
/*     */ 
/*     */       
/* 241 */       Vector3i pos = (offset == null) ? blockTarget : offset.getBlockPosition(blockTarget.toVector3d(), chunkStore);
/*     */       
/* 243 */       Fluid fluid = (Fluid)this.fluid.get(context);
/* 244 */       if (fluid == null) {
/* 245 */         playerRef.sendMessage(MESSAGE_COMMANDS_SET_UNKNOWN_FLUID);
/*     */         
/*     */         return;
/*     */       } 
/* 249 */       Integer level = (Integer)this.level.get(context);
/* 250 */       if (level.intValue() > fluid.getMaxFluidLevel()) {
/* 251 */         level = Integer.valueOf(fluid.getMaxFluidLevel());
/* 252 */         playerRef.sendMessage(Message.translation("server.commands.set.maxFluidLevelClamped")
/* 253 */             .param("level", fluid.getMaxFluidLevel()));
/*     */       } 
/*     */       
/* 256 */       Integer radius = (Integer)this.radius.get(context);
/*     */       
/* 258 */       int minX = pos.x - radius.intValue();
/* 259 */       int maxX = pos.x + radius.intValue();
/* 260 */       int minY = pos.y - radius.intValue();
/* 261 */       int maxY = pos.y + radius.intValue();
/* 262 */       int minZ = pos.z - radius.intValue();
/* 263 */       int maxZ = pos.z + radius.intValue();
/*     */       
/* 265 */       int minCX = ChunkUtil.chunkCoordinate(minX);
/* 266 */       int maxCX = ChunkUtil.chunkCoordinate(maxX);
/* 267 */       int minCY = ChunkUtil.chunkCoordinate(minY);
/* 268 */       int maxCY = ChunkUtil.chunkCoordinate(maxY);
/* 269 */       int minCZ = ChunkUtil.chunkCoordinate(minZ);
/* 270 */       int maxCZ = ChunkUtil.chunkCoordinate(maxZ);
/*     */       
/* 272 */       Integer finalLevel = level;
/*     */       
/* 274 */       for (int cx = minCX; cx <= maxCX; cx++) {
/* 275 */         for (int cz = minCZ; cz <= maxCZ; cz++) {
/*     */           
/* 277 */           int relMinX = MathUtil.clamp(minX - ChunkUtil.minBlock(cx), 0, 32);
/* 278 */           int relMaxX = MathUtil.clamp(maxX - ChunkUtil.minBlock(cx), 0, 32);
/* 279 */           int relMinZ = MathUtil.clamp(minZ - ChunkUtil.minBlock(cz), 0, 32);
/* 280 */           int relMaxZ = MathUtil.clamp(maxZ - ChunkUtil.minBlock(cz), 0, 32);
/*     */           
/* 282 */           for (int cy = minCY; cy <= maxCY; cy++) {
/* 283 */             chunkStore.getChunkSectionReferenceAsync(cx, cy, cz)
/* 284 */               .thenAcceptAsync(section -> {
/*     */                   Store<ChunkStore> sectionStore = section.getStore();
/*     */                   FluidSection fluidSection = (FluidSection)sectionStore.getComponent(section, FluidSection.getComponentType());
/*     */                   if (fluidSection == null)
/*     */                     return; 
/*     */                   int relMinY = MathUtil.clamp(minY - ChunkUtil.minBlock(fluidSection.getY()), 0, 32);
/*     */                   int relMaxY = MathUtil.clamp(maxY - ChunkUtil.minBlock(fluidSection.getY()), 0, 32);
/*     */                   ChunkSection sectionComp = (ChunkSection)sectionStore.getComponent(section, ChunkSection.getComponentType());
/*     */                   WorldChunk worldChunk = (WorldChunk)sectionStore.getComponent(sectionComp.getChunkColumnReference(), WorldChunk.getComponentType());
/*     */                   for (int y = relMinY; y < relMaxY; y++) {
/*     */                     for (int z = relMinZ; z < relMaxZ; z++) {
/*     */                       for (int x = relMinX; x < relMaxX; x++) {
/*     */                         int index = ChunkUtil.indexBlock(x, y, z);
/*     */                         fluidSection.setFluid(index, fluid, finalLevel.byteValue());
/*     */                         worldChunk.setTicking(pos.x, pos.y, pos.z, true);
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                   worldChunk.markNeedsSaving();
/*     */                 }(Executor)world);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */