/*     */ package com.hypixel.hytale.server.npc.blackboard.view.attitude;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.tagset.TagSetPlugin;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.config.AttitudeGroup;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*  80 */   private final Int2ObjectMap<Attitude>[] map = (Int2ObjectMap<Attitude>[])new Int2ObjectMap[AttitudeGroup.getAssetMap().getNextIndex()];
/*     */   
/*     */   public void addAttitudeGroups(@Nonnull Map<String, AttitudeGroup> groups) {
/*  83 */     groups.forEach((id, group) -> addAttitudeGroup(group));
/*     */   }
/*     */   
/*     */   private void addAttitudeGroup(@Nonnull AttitudeGroup group) {
/*  87 */     String key = group.getId();
/*  88 */     int index = AttitudeGroup.getAssetMap().getIndex(key);
/*  89 */     if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/*  90 */     this.map[index] = createGroupMap(group);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static Int2ObjectMap<Attitude> createGroupMap(@Nonnull AttitudeGroup group) {
/*  95 */     TagSetPlugin.TagSetLookup npcGroups = TagSetPlugin.get(NPCGroup.class);
/*  96 */     Int2ObjectOpenHashMap<Attitude> groupMap = new Int2ObjectOpenHashMap();
/*  97 */     for (Attitude attitude : Attitude.VALUES) {
/*  98 */       putGroups(group.getId(), npcGroups, (String[])group.getAttitudeGroups().get(attitude), attitude, (Int2ObjectMap<Attitude>)groupMap);
/*     */     }
/* 100 */     groupMap.trim();
/* 101 */     return (Int2ObjectMap<Attitude>)groupMap;
/*     */   }
/*     */   
/*     */   private static void putGroups(String attitudeGroup, @Nonnull TagSetPlugin.TagSetLookup npcGroupLookup, @Nullable String[] group, Attitude targetAttitude, @Nonnull Int2ObjectMap<Attitude> targetMap) {
/* 105 */     if (group == null)
/*     */       return; 
/* 107 */     for (String item : group) {
/* 108 */       int index = NPCGroup.getAssetMap().getIndex(item);
/* 109 */       if (index == Integer.MIN_VALUE) {
/* 110 */         NPCPlugin.get().getLogger().at(Level.SEVERE).log("Creating attitude groups: NPC Group '%s' does not exist in attitude group '%s'!", item, attitudeGroup);
/*     */       } else {
/*     */         
/* 113 */         IntSet set = npcGroupLookup.getSet(index);
/* 114 */         if (set != null)
/* 115 */           set.forEach(i -> targetMap.put(i, targetAttitude)); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @Nonnull
/*     */   public AttitudeMap build() {
/* 121 */     return new AttitudeMap(this.map);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\attitude\AttitudeMap$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */