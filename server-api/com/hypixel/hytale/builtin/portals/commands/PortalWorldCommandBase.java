/*    */ package com.hypixel.hytale.builtin.portals.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class PortalWorldCommandBase
/*    */   extends AbstractWorldCommand {
/*    */   public PortalWorldCommandBase(String name, String description) {
/* 15 */     super(name, description);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 20 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 21 */     if (!portalWorld.exists()) {
/* 22 */       context.sendMessage(Message.translation("server.commands.portals.notInPortal"));
/*    */       
/*    */       return;
/*    */     } 
/* 26 */     execute(context, world, portalWorld, store);
/*    */   }
/*    */   
/*    */   protected abstract void execute(@Nonnull CommandContext paramCommandContext, @Nonnull World paramWorld, @Nonnull PortalWorld paramPortalWorld, @Nonnull Store<EntityStore> paramStore);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\commands\PortalWorldCommandBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */