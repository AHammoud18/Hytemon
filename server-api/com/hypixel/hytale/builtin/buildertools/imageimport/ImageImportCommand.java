/*    */ package com.hypixel.hytale.builtin.buildertools.imageimport;
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
/*    */ public class ImageImportCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public ImageImportCommand() {
/* 21 */     super("importimage", "server.commands.importimage.desc");
/* 22 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 33 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 34 */     if (playerComponent == null)
/*    */       return; 
/* 36 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new ImageImportPage(playerRef));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\imageimport\ImageImportCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */