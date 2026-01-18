/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
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
/*    */ public class TintCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private final RequiredArg<String> colorArg = withRequiredArg("color", "server.commands.tint.color.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TintCommand() {
/* 36 */     super("tint", "server.commands.tint.desc");
/* 37 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*    */     int colorI;
/* 46 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 47 */     assert playerComponent != null;
/*    */     
/* 49 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 51 */     String color = (String)this.colorArg.get(context);
/*    */     
/*    */     try {
/* 54 */       if (color.charAt(0) == '#') {
/* 55 */         colorI = Integer.parseInt(color.substring(1), 16);
/*    */       } else {
/* 57 */         colorI = Integer.parseInt(color);
/*    */       } 
/* 59 */     } catch (NumberFormatException e) {
/* 60 */       context.sendMessage(Message.translation("server.builderTools.tint.colorFormatError")
/* 61 */           .param("color", color));
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.tint(r, colorI, componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\TintCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */