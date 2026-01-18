/*    */ package com.hypixel.hytale.builtin.adventure.objectives.admin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.BasicCustomUIPage;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ObjectiveAdminPanelPage extends BasicCustomUIPage {
/*    */   public ObjectiveAdminPanelPage(@Nonnull PlayerRef playerRef) {
/* 16 */     super(playerRef, CustomPageLifetime.CanDismiss);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(@Nonnull UICommandBuilder commandBuilder) {
/* 21 */     commandBuilder.append("Pages/ObjectiveAdminPanelPage.ui");
/*    */     
/* 23 */     Collection<Objective> objectives = ObjectivePlugin.get().getObjectiveDataStore().getObjectiveCollection();
/* 24 */     int index = 0;
/* 25 */     for (Objective objective : objectives) {
/* 26 */       String selector = "#ObjectiveList[" + index + "]";
/*    */       
/* 28 */       commandBuilder.append("#ObjectiveList", "Pages/ObjectiveAdminPanelDataSlot.ui");
/* 29 */       commandBuilder.set(selector + " #Id.Text", objective.getObjectiveId());
/* 30 */       commandBuilder.set(selector + " #UUID.Text", "Objective UUID: " + objective.getObjectiveUUID().toString());
/*    */       
/* 32 */       StringBuilder stringBuilder = new StringBuilder();
/* 33 */       Universe universe = Universe.get();
/* 34 */       for (UUID playerUUID : objective.getActivePlayerUUIDs()) {
/* 35 */         PlayerRef player = universe.getPlayer(playerUUID);
/* 36 */         if (player == null)
/* 37 */           continue;  if (!stringBuilder.isEmpty()) stringBuilder.append(", "); 
/* 38 */         stringBuilder.append(player.getUsername());
/*    */       } 
/*    */       
/* 41 */       commandBuilder.set(selector + " #CurrentPlayers.Text", "Current players: " + stringBuilder.toString());
/* 42 */       commandBuilder.set(selector + " #AllTimePlayers.Text", "All time players: " + Arrays.toString(objective.getPlayerUUIDs().toArray()));
/*    */       
/* 44 */       index++;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\admin\ObjectiveAdminPanelPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */