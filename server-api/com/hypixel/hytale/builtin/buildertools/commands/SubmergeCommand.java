/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.builtin.buildertools.utils.FluidPatternHelper;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
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
/*    */ public class SubmergeCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 32 */   private final RequiredArg<String> fluidItemArg = withRequiredArg("fluid-item", "server.commands.submerge.fluidType.desc", (ArgumentType)ArgTypes.BLOCK_TYPE_KEY);
/*    */   
/*    */   public SubmergeCommand() {
/* 35 */     super("submerge", "server.commands.submerge.desc");
/* 36 */     setPermissionGroup(GameMode.Creative);
/* 37 */     addAliases(new String[] { "flood" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 46 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 47 */     assert playerComponent != null;
/*    */     
/* 49 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 51 */     String fluidItemKey = (String)this.fluidItemArg.get(context);
/*    */ 
/*    */     
/* 54 */     if (!FluidPatternHelper.isFluidItem(fluidItemKey)) {
/* 55 */       context.sendMessage(Message.translation("server.builderTools.invalidBlockType")
/* 56 */           .param("name", fluidItemKey)
/* 57 */           .param("key", fluidItemKey));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 62 */     BlockPattern pattern = BlockPattern.parse(fluidItemKey);
/*    */     
/* 64 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.set(pattern, componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\SubmergeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */