/*    */ package com.hypixel.hytale.builtin.instances.command;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.page.InstanceListPage;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InstancesCommand extends AbstractPlayerCommand {
/*    */   public InstancesCommand() {
/* 19 */     super("instances", "server.commands.instances.desc");
/* 20 */     addAliases(new String[] { "instance", "inst" });
/*    */     
/* 22 */     addSubCommand((AbstractCommand)new InstancesEditCommand());
/* 23 */     addSubCommand((AbstractCommand)new InstanceSpawnCommand());
/* 24 */     addSubCommand((AbstractCommand)new InstanceExitCommand());
/* 25 */     addSubCommand((AbstractCommand)new InstanceMigrateCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 30 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 31 */     assert playerComponent != null;
/* 32 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new InstanceListPage(playerRef));
/*    */   }
/*    */   
/*    */   public static class InstancesEditCommand extends AbstractCommandCollection {
/*    */     public InstancesEditCommand() {
/* 37 */       super("edit", "server.commands.instances.edit.desc");
/* 38 */       addAliases(new String[] { "modify" });
/*    */       
/* 40 */       addSubCommand((AbstractCommand)new InstanceEditNewCommand());
/* 41 */       addSubCommand((AbstractCommand)new InstanceEditCopyCommand());
/* 42 */       addSubCommand((AbstractCommand)new InstanceEditLoadCommand());
/* 43 */       addSubCommand((AbstractCommand)new InstanceEditListCommand());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstancesCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */