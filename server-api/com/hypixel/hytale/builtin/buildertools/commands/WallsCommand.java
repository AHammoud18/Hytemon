/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WallsCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 34 */   private final RequiredArg<BlockPattern> patternArg = withRequiredArg("pattern", "server.commands.walls.blockType.desc", ArgTypes.BLOCK_PATTERN);
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 39 */   private final DefaultArg<Integer> thicknessArg = (DefaultArg<Integer>)
/* 40 */     withDefaultArg("thickness", "server.commands.walls.thickness.desc", (ArgumentType)ArgTypes.INTEGER, 
/* 41 */       Integer.valueOf(1), "Thickness of one")
/* 42 */     .addValidator(Validators.range(Integer.valueOf(1), Integer.valueOf(128)));
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 47 */   private final FlagArg floorArg = (FlagArg)
/* 48 */     withFlagArg("floor", "server.commands.walls.floor.desc")
/* 49 */     .addAliases(new String[] { "bottom" });
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 54 */   private final FlagArg roofArg = (FlagArg)
/* 55 */     withFlagArg("roof", "server.commands.walls.roof.desc")
/* 56 */     .addAliases(new String[] { "ceiling", "top" });
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 61 */   private final FlagArg perimeterArg = (FlagArg)
/* 62 */     withFlagArg("perimeter", "server.commands.walls.perimeter.desc")
/* 63 */     .addAliases(new String[] { "all" });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WallsCommand() {
/* 69 */     super("wall", "server.commands.walls.desc");
/* 70 */     setPermissionGroup(GameMode.Creative);
/* 71 */     addAliases(new String[] { "walls", "side", "sides" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 76 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 77 */     assert playerComponent != null;
/*    */     
/* 79 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 81 */     BlockPattern pattern = (BlockPattern)this.patternArg.get(context);
/*    */     
/* 83 */     if (pattern == null || pattern.isEmpty()) {
/* 84 */       context.sendMessage(Message.translation("server.builderTools.invalidBlockType")
/* 85 */           .param("name", "")
/* 86 */           .param("key", ""));
/*    */       
/*    */       return;
/*    */     } 
/* 90 */     Boolean floor = (Boolean)this.floorArg.get(context);
/* 91 */     Boolean roof = (Boolean)this.roofArg.get(context);
/* 92 */     Boolean perimeter = (Boolean)this.perimeterArg.get(context);
/*    */     
/* 94 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.walls(r, pattern, ((Integer)this.thicknessArg.get(context)).intValue(), 
/*    */           
/* 96 */           (roof.booleanValue() || perimeter.booleanValue()), (floor.booleanValue() || perimeter.booleanValue()), componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\WallsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */