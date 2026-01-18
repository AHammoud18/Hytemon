/*     */ package com.hypixel.hytale.builtin.buildertools.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ class ExtendFaceWithRegionCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/* 160 */   private final RequiredArg<Integer> xArg = withRequiredArg("x", "server.commands.extendface.x.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 166 */   private final RequiredArg<Integer> yArg = withRequiredArg("y", "server.commands.extendface.y.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 172 */   private final RequiredArg<Integer> zArg = withRequiredArg("z", "server.commands.extendface.z.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 178 */   private final RequiredArg<Integer> normalXArg = withRequiredArg("normalX", "server.commands.extendface.normalX.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 184 */   private final RequiredArg<Integer> normalYArg = withRequiredArg("normalY", "server.commands.extendface.normalY.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 190 */   private final RequiredArg<Integer> normalZArg = withRequiredArg("normalZ", "server.commands.extendface.normalZ.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 196 */   private final RequiredArg<Integer> toolParamArg = withRequiredArg("toolParam", "server.commands.extendface.toolParam.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 202 */   private final RequiredArg<Integer> shapeRangeArg = withRequiredArg("shapeRange", "server.commands.extendface.shapeRange.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 208 */   private final RequiredArg<String> blockTypeArg = withRequiredArg("blockType", "server.commands.extendface.blockType.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 214 */   private final RequiredArg<Integer> xMinArg = withRequiredArg("xMin", "server.commands.extendface.xMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 220 */   private final RequiredArg<Integer> yMinArg = withRequiredArg("yMin", "server.commands.extendface.yMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 226 */   private final RequiredArg<Integer> zMinArg = withRequiredArg("zMin", "server.commands.extendface.zMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 232 */   private final RequiredArg<Integer> xMaxArg = withRequiredArg("xMax", "server.commands.extendface.xMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 238 */   private final RequiredArg<Integer> yMaxArg = withRequiredArg("yMax", "server.commands.extendface.yMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 244 */   private final RequiredArg<Integer> zMaxArg = withRequiredArg("zMax", "server.commands.extendface.zMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendFaceWithRegionCommand() {
/* 250 */     super("server.commands.extendface.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 259 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 260 */     assert playerComponent != null;
/*     */     
/* 262 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */       return; 
/* 264 */     int x = ((Integer)this.xArg.get(context)).intValue();
/* 265 */     int y = ((Integer)this.yArg.get(context)).intValue();
/* 266 */     int z = ((Integer)this.zArg.get(context)).intValue();
/* 267 */     int normalX = ((Integer)this.normalXArg.get(context)).intValue();
/* 268 */     int normalY = ((Integer)this.normalYArg.get(context)).intValue();
/* 269 */     int normalZ = ((Integer)this.normalZArg.get(context)).intValue();
/* 270 */     int toolParam = ((Integer)this.toolParamArg.get(context)).intValue();
/* 271 */     int shapeRange = ((Integer)this.shapeRangeArg.get(context)).intValue();
/* 272 */     String key = (String)this.blockTypeArg.get(context);
/*     */     
/* 274 */     if (BlockType.getAssetMap().getAsset(key) == null) {
/* 275 */       context.sendMessage(Message.translation("server.builderTools.invalidBlockType")
/* 276 */           .param("name", key)
/* 277 */           .param("key", key));
/*     */       
/*     */       return;
/*     */     } 
/* 281 */     int index = BlockType.getAssetMap().getIndex(key);
/* 282 */     if (index == Integer.MIN_VALUE) {
/* 283 */       context.sendMessage(Message.translation("server.builderTools.invalidBlockType")
/* 284 */           .param("name", key)
/* 285 */           .param("key", key));
/*     */       
/*     */       return;
/*     */     } 
/* 289 */     int xMin = ((Integer)this.xMinArg.get(context)).intValue();
/* 290 */     int yMin = ((Integer)this.yMinArg.get(context)).intValue();
/* 291 */     int zMin = ((Integer)this.zMinArg.get(context)).intValue();
/* 292 */     int xMax = ((Integer)this.xMaxArg.get(context)).intValue();
/* 293 */     int yMax = ((Integer)this.yMaxArg.get(context)).intValue();
/* 294 */     int zMax = ((Integer)this.zMaxArg.get(context)).intValue();
/*     */     
/* 296 */     Vector3i min = new Vector3i(xMin, yMin, zMin);
/* 297 */     Vector3i max = new Vector3i(xMax, yMax, zMax);
/*     */     
/* 299 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.extendFace(x, y, z, normalX, normalY, normalZ, toolParam, shapeRange, index, min, max, componentAccessor));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\ExtendFaceCommand$ExtendFaceWithRegionCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */