/*    */ package com.hypixel.hytale.builtin.buildertools.objimport;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjImportCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public ObjImportCommand() {
/* 22 */     super("importobj", "server.commands.importobj.desc");
/* 23 */     addAliases(new String[] { "obj" });
/* 24 */     setPermissionGroup(GameMode.Creative);
/* 25 */     requirePermission("hytale.editor.selection.clipboard");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 34 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 35 */     if (playerComponent == null) {
/*    */       return;
/*    */     }
/* 38 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new ObjImportPage(playerRef));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\objimport\ObjImportCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */