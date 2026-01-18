/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
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
/*    */ public class ClearEditHistory
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public ClearEditHistory() {
/* 25 */     super("clearEditHistory", "server.commands.clearhistory.desc");
/* 26 */     setPermissionGroup(GameMode.Creative);
/* 27 */     addAliases(new String[] { "clearHistory", "clearToolHistory" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 32 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 33 */     assert playerComponent != null;
/*    */     
/* 35 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, c) -> s.clearHistory(r, c));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\ClearEditHistory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */