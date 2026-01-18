/*    */ package com.hypixel.hytale.server.core.command.commands.utility.sound;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.audio.PlaySoundPage;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public SoundCommand() {
/* 24 */     super("sound", "server.commands.sound.desc");
/* 25 */     addSubCommand((AbstractCommand)new SoundPlay2DCommand());
/* 26 */     addSubCommand((AbstractCommand)new SoundPlay3DCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 31 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 32 */     assert playerComponent != null;
/*    */     
/* 34 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 35 */     assert playerRefComponent != null;
/*    */     
/* 37 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new PlaySoundPage(playerRefComponent));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\sound\SoundCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */