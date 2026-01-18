/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BrushOrigin;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BrushShape;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeVector3i;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EditLineCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 32 */   private final RequiredArg<RelativeVector3i> startArg = withRequiredArg("start", "server.commands.editline.start.desc", ArgTypes.RELATIVE_VECTOR3I);
/*    */   
/*    */   @Nonnull
/* 35 */   private final RequiredArg<RelativeVector3i> endArg = withRequiredArg("end", "server.commands.editline.end.desc", ArgTypes.RELATIVE_VECTOR3I);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 41 */   private final RequiredArg<String> materialArg = withRequiredArg("material", "server.commands.editline.material.desc", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   @Nonnull
/* 44 */   private final DefaultArg<Integer> widthArg = withDefaultArg("width", "server.commands.editline.width.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(1), "1");
/*    */   
/*    */   @Nonnull
/* 47 */   private final DefaultArg<Integer> heightArg = withDefaultArg("height", "server.commands.editline.height.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(1), "1");
/*    */   
/*    */   @Nonnull
/* 50 */   private final DefaultArg<Integer> wallThicknessArg = withDefaultArg("wallThickness", "server.commands.editline.wallThickness.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(0), "0");
/*    */   
/*    */   @Nonnull
/* 53 */   private final DefaultArg<String> shapeArg = withDefaultArg("shape", "server.commands.editline.shape.desc", (ArgumentType)ArgTypes.STRING, "Cube", "Cube");
/*    */   
/*    */   @Nonnull
/* 56 */   private final DefaultArg<String> originArg = withDefaultArg("origin", "server.commands.editline.origin.desc", (ArgumentType)ArgTypes.STRING, "Center", "Center");
/*    */   
/*    */   @Nonnull
/* 59 */   private final DefaultArg<Integer> spacingArg = withDefaultArg("spacing", "server.commands.editline.spacing.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(1), "1");
/*    */   
/*    */   @Nonnull
/* 62 */   private final DefaultArg<Integer> densityArg = withDefaultArg("density", "server.commands.editline.density.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(100), "100");
/*    */   
/*    */   public EditLineCommand() {
/* 65 */     super("editline", "server.commands.editline.desc");
/* 66 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 75 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 76 */     assert playerComponent != null;
/*    */ 
/*    */     
/* 79 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 80 */     assert transformComponent != null;
/* 81 */     Vector3d playerPos = transformComponent.getPosition();
/* 82 */     int baseX = MathUtil.floor(playerPos.getX());
/* 83 */     int baseY = MathUtil.floor(playerPos.getY());
/* 84 */     int baseZ = MathUtil.floor(playerPos.getZ());
/*    */ 
/*    */     
/* 87 */     Vector3i start = ((RelativeVector3i)this.startArg.get(context)).resolve(baseX, baseY, baseZ);
/* 88 */     Vector3i end = ((RelativeVector3i)this.endArg.get(context)).resolve(baseX, baseY, baseZ);
/*    */     
/* 90 */     BlockPattern material = BlockPattern.parse((String)this.materialArg.get(context));
/* 91 */     int width = ((Integer)this.widthArg.get(context)).intValue();
/* 92 */     int height = ((Integer)this.heightArg.get(context)).intValue();
/* 93 */     int wallThickness = ((Integer)this.wallThicknessArg.get(context)).intValue();
/* 94 */     BrushShape shape = BrushShape.valueOf((String)this.shapeArg.get(context));
/* 95 */     BrushOrigin origin = BrushOrigin.valueOf((String)this.originArg.get(context));
/* 96 */     int spacing = ((Integer)this.spacingArg.get(context)).intValue();
/* 97 */     int density = ((Integer)this.densityArg.get(context)).intValue();
/*    */     
/* 99 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.editLine(start.x, start.y, start.z, end.x, end.y, end.z, material, width, height, wallThickness, shape, origin, spacing, density, s.getGlobalMask(), componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\EditLineCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */