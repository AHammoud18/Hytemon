/*    */ package com.hypixel.hytale.builtin.adventure.npcreputation;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.ReputationGroupComponent;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCReputationPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   public NPCReputationPlugin(@Nonnull JavaPluginInit init) {
/* 17 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 22 */     getEntityStoreRegistry().registerSystem((ISystem)new ReputationAttitudeSystem());
/* 23 */     getEntityStoreRegistry().registerSystem((ISystem)new NPCReputationHolderSystem(ReputationGroupComponent.getComponentType(), NPCEntity.getComponentType()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcreputation\NPCReputationPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */