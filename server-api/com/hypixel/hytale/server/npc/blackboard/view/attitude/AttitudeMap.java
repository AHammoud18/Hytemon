/*     */ package com.hypixel.hytale.server.npc.blackboard.view.attitude;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.tagset.TagSetPlugin;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*     */ import com.hypixel.hytale.server.npc.config.AttitudeGroup;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttitudeMap
/*     */ {
/*     */   @Nullable
/*  35 */   private static final ComponentType<EntityStore, NPCEntity> NPC_COMPONENT_TYPE = NPCEntity.getComponentType();
/*  36 */   private static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
/*     */   
/*     */   private final Int2ObjectMap<Attitude>[] map;
/*     */   
/*     */   private AttitudeMap(Int2ObjectMap<Attitude>[] map) {
/*  41 */     this.map = map;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Attitude getAttitude(@Nonnull Role role, @Nonnull Ref<EntityStore> target, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  46 */     int targetId, group = role.getWorldSupport().getAttitudeGroup();
/*     */     
/*  48 */     if (group == Integer.MIN_VALUE) return null;
/*     */ 
/*     */     
/*  51 */     Int2ObjectMap<Attitude> attitudeMap = this.map[group];
/*  52 */     if (attitudeMap == null) return null;
/*     */ 
/*     */     
/*  55 */     NPCEntity npc = (NPCEntity)componentAccessor.getComponent(target, NPC_COMPONENT_TYPE);
/*  56 */     if (npc != null) {
/*  57 */       targetId = npc.getRoleIndex();
/*     */     } else {
/*  59 */       if (!componentAccessor.getArchetype(target).contains(PLAYER_COMPONENT_TYPE)) return null; 
/*  60 */       targetId = BuilderManager.getPlayerGroupID();
/*     */     } 
/*     */ 
/*     */     
/*  64 */     if (targetId == role.getRoleIndex()) return (Attitude)attitudeMap.get(BuilderManager.getSelfGroupID());
/*     */     
/*  66 */     return (Attitude)attitudeMap.get(targetId);
/*     */   }
/*     */   
/*     */   public int getAttitudeGroupCount() {
/*  70 */     return this.map.length;
/*     */   }
/*     */   
/*     */   public void updateAttitudeGroup(int id, @Nonnull AttitudeGroup group) {
/*  74 */     Int2ObjectMap<Attitude> groupMap = Builder.createGroupMap(group);
/*  75 */     this.map[id] = groupMap;
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */   {
/*  80 */     private final Int2ObjectMap<Attitude>[] map = (Int2ObjectMap<Attitude>[])new Int2ObjectMap[AttitudeGroup.getAssetMap().getNextIndex()];
/*     */     
/*     */     public void addAttitudeGroups(@Nonnull Map<String, AttitudeGroup> groups) {
/*  83 */       groups.forEach((id, group) -> addAttitudeGroup(group));
/*     */     }
/*     */     
/*     */     private void addAttitudeGroup(@Nonnull AttitudeGroup group) {
/*  87 */       String key = group.getId();
/*  88 */       int index = AttitudeGroup.getAssetMap().getIndex(key);
/*  89 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/*  90 */       this.map[index] = createGroupMap(group);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private static Int2ObjectMap<Attitude> createGroupMap(@Nonnull AttitudeGroup group) {
/*  95 */       TagSetPlugin.TagSetLookup npcGroups = TagSetPlugin.get(NPCGroup.class);
/*  96 */       Int2ObjectOpenHashMap<Attitude> groupMap = new Int2ObjectOpenHashMap();
/*  97 */       for (Attitude attitude : Attitude.VALUES) {
/*  98 */         putGroups(group.getId(), npcGroups, (String[])group.getAttitudeGroups().get(attitude), attitude, (Int2ObjectMap<Attitude>)groupMap);
/*     */       }
/* 100 */       groupMap.trim();
/* 101 */       return (Int2ObjectMap<Attitude>)groupMap;
/*     */     }
/*     */     
/*     */     private static void putGroups(String attitudeGroup, @Nonnull TagSetPlugin.TagSetLookup npcGroupLookup, @Nullable String[] group, Attitude targetAttitude, @Nonnull Int2ObjectMap<Attitude> targetMap) {
/* 105 */       if (group == null)
/*     */         return; 
/* 107 */       for (String item : group) {
/* 108 */         int index = NPCGroup.getAssetMap().getIndex(item);
/* 109 */         if (index == Integer.MIN_VALUE) {
/* 110 */           NPCPlugin.get().getLogger().at(Level.SEVERE).log("Creating attitude groups: NPC Group '%s' does not exist in attitude group '%s'!", item, attitudeGroup);
/*     */         } else {
/*     */           
/* 113 */           IntSet set = npcGroupLookup.getSet(index);
/* 114 */           if (set != null)
/* 115 */             set.forEach(i -> targetMap.put(i, targetAttitude)); 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     @Nonnull
/*     */     public AttitudeMap build() {
/* 121 */       return new AttitudeMap(this.map);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\attitude\AttitudeMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */