/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
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
/*    */ public class UpdateSelectionCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 29 */   private final RequiredArg<Integer> xMinArg = withRequiredArg("xMin", "server.commands.updateselection.xMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 35 */   private final RequiredArg<Integer> yMinArg = withRequiredArg("yMin", "server.commands.updateselection.yMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 41 */   private final RequiredArg<Integer> zMinArg = withRequiredArg("zMin", "server.commands.updateselection.zMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 47 */   private final RequiredArg<Integer> xMaxArg = withRequiredArg("xMax", "server.commands.updateselection.xMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 53 */   private final RequiredArg<Integer> yMaxArg = withRequiredArg("yMax", "server.commands.updateselection.yMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 59 */   private final RequiredArg<Integer> zMaxArg = withRequiredArg("zMax", "server.commands.updateselection.zMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UpdateSelectionCommand() {
/* 65 */     super("updateselection", "server.commands.updateselection.desc");
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
/* 78 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 80 */     int xMin = ((Integer)this.xMinArg.get(context)).intValue();
/* 81 */     int yMin = ((Integer)this.yMinArg.get(context)).intValue();
/* 82 */     int zMin = ((Integer)this.zMinArg.get(context)).intValue();
/* 83 */     int xMax = ((Integer)this.xMaxArg.get(context)).intValue();
/* 84 */     int yMax = ((Integer)this.yMaxArg.get(context)).intValue();
/* 85 */     int zMax = ((Integer)this.zMaxArg.get(context)).intValue();
/*    */     
/* 87 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.update(xMin, yMin, zMin, xMax, yMax, zMax));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\UpdateSelectionCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */