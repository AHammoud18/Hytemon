/*    */ package com.hypixel.hytale.server.core.entity.entities.player.pages;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class BasicCustomUIPage
/*    */   extends CustomUIPage {
/*    */   public BasicCustomUIPage(@Nonnull PlayerRef playerRef, @Nonnull CustomPageLifetime lifetime) {
/* 15 */     super(playerRef, lifetime);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder, @Nonnull Store<EntityStore> store) {
/* 20 */     build(commandBuilder);
/*    */   }
/*    */   
/*    */   public abstract void build(UICommandBuilder paramUICommandBuilder);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\BasicCustomUIPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */