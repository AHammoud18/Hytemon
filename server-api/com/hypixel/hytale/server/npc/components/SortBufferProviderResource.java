/*    */ package com.hypixel.hytale.server.npc.components;
/*    */ 
/*    */ import com.hypixel.hytale.common.collection.BucketList;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SortBufferProviderResource
/*    */   implements Resource<EntityStore>
/*    */ {
/*    */   public static ResourceType<EntityStore, SortBufferProviderResource> getResourceType() {
/* 17 */     return NPCPlugin.get().getSortBufferProviderResourceResourceType();
/*    */   }
/*    */   
/* 20 */   private final BucketList.SortBufferProvider sortBufferProvider = new BucketList.SortBufferProvider();
/*    */   
/*    */   @Nonnull
/*    */   public BucketList.SortBufferProvider getSortBufferProvider() {
/* 24 */     return this.sortBufferProvider;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 31 */     return new SortBufferProviderResource();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\SortBufferProviderResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */