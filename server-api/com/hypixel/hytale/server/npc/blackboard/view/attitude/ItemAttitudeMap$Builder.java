/*    */ package com.hypixel.hytale.server.npc.blackboard.view.attitude;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.npc.config.ItemAttitudeGroup;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/* 59 */   private final Map<String, Attitude>[] map = (Map<String, Attitude>[])new HashMap[ItemAttitudeGroup.getAssetMap().getNextIndex()];
/*    */   
/*    */   public void addAttitudeGroups(@Nonnull Map<String, ItemAttitudeGroup> groups) {
/* 62 */     groups.forEach((id, group) -> addAttitudeGroup(group));
/*    */   }
/*    */   
/*    */   private void addAttitudeGroup(@Nonnull ItemAttitudeGroup group) {
/* 66 */     String key = group.getId();
/* 67 */     int index = ItemAttitudeGroup.getAssetMap().getIndex(key);
/* 68 */     if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 69 */     this.map[index] = createGroupMap(group);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private static Map<String, Attitude> createGroupMap(@Nonnull ItemAttitudeGroup group) {
/* 74 */     HashMap<String, Attitude> groupMap = new HashMap<>();
/* 75 */     for (Attitude attitude : Attitude.VALUES) {
/* 76 */       putGroups((String[])group.getAttitudes().get(attitude), attitude, groupMap);
/*    */     }
/* 78 */     return groupMap;
/*    */   }
/*    */   
/*    */   private static void putGroups(@Nullable String[] group, Attitude targetAttitude, @Nonnull HashMap<String, Attitude> targetMap) {
/* 82 */     if (group == null)
/*    */       return; 
/* 84 */     for (String item : group) {
/* 85 */       Set<String> set = Item.getAssetMap().getKeysForTag(AssetRegistry.getOrCreateTagIndex(item));
/* 86 */       if (set != null)
/* 87 */         set.forEach(k -> targetMap.put(k, targetAttitude)); 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ItemAttitudeMap build() {
/* 93 */     return new ItemAttitudeMap(this.map);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\attitude\ItemAttitudeMap$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */