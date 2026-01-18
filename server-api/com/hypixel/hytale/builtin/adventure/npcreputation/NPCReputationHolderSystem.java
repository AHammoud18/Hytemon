/*    */ package com.hypixel.hytale.builtin.adventure.npcreputation;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.ReputationGroupComponent;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationGroup;
/*    */ import com.hypixel.hytale.builtin.tagset.TagSetPlugin;
/*    */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NPCReputationHolderSystem extends HolderSystem<EntityStore> {
/*    */   private final ComponentType<EntityStore, ReputationGroupComponent> reputationGroupComponentType;
/*    */   private final ComponentType<EntityStore, NPCEntity> npcEntityComponentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public NPCReputationHolderSystem(ComponentType<EntityStore, ReputationGroupComponent> reputationGroupComponentType, ComponentType<EntityStore, NPCEntity> npcEntityComponentType) {
/* 27 */     this.reputationGroupComponentType = reputationGroupComponentType;
/* 28 */     this.npcEntityComponentType = npcEntityComponentType;
/* 29 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcEntityComponentType, (Query)Query.not((Query)reputationGroupComponentType) });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 35 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 40 */     NPCEntity npcEntity = (NPCEntity)holder.getComponent(this.npcEntityComponentType);
/* 41 */     int npcTypeIndex = npcEntity.getNPCTypeIndex();
/*    */     
/* 43 */     for (Map.Entry<String, ReputationGroup> reputationEntry : (Iterable<Map.Entry<String, ReputationGroup>>)ReputationGroup.getAssetMap().getAssetMap().entrySet()) {
/* 44 */       String[] arrayOfString; int i; byte b; for (arrayOfString = ((ReputationGroup)reputationEntry.getValue()).getNpcGroups(), i = arrayOfString.length, b = 0; b < i; ) { String npcGroup = arrayOfString[b];
/* 45 */         int index = NPCGroup.getAssetMap().getIndex(npcGroup);
/* 46 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown npc group! " + npcGroup);
/*    */         
/* 48 */         if (!TagSetPlugin.get(NPCGroup.class).tagInSet(index, npcTypeIndex)) {
/*    */           b++; continue;
/* 50 */         }  holder.addComponent(this.reputationGroupComponentType, (Component)new ReputationGroupComponent(reputationEntry.getKey()));
/*    */         return; }
/*    */     
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcreputation\NPCReputationHolderSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */