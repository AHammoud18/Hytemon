/*    */ package com.hypixel.hytale.server.npc.navigation;
/*    */ 
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AStarNodePoolProviderSimple
/*    */   implements AStarNodePoolProvider, Resource<EntityStore>
/*    */ {
/*    */   public static ResourceType<EntityStore, AStarNodePoolProviderSimple> getResourceType() {
/* 18 */     return NPCPlugin.get().getAStarNodePoolProviderSimpleResourceType();
/*    */   }
/*    */   @Nonnull
/* 21 */   protected Int2ObjectMap<AStarNodePoolSimple> nodePools = (Int2ObjectMap<AStarNodePoolSimple>)new Int2ObjectOpenHashMap();
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AStarNodePool getPool(int childNodeCount) {
/* 27 */     return (AStarNodePool)this.nodePools.computeIfAbsent(childNodeCount, AStarNodePoolSimple::new);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 34 */     return new AStarNodePoolProviderSimple();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\navigation\AStarNodePoolProviderSimple.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */