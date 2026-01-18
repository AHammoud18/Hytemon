/*    */ package com.hypixel.hytale.server.core.inventory.container.filter;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.inventory.ResourceQuantity;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceFilter
/*    */   implements ItemSlotFilter
/*    */ {
/*    */   private final ResourceQuantity resource;
/*    */   
/*    */   public ResourceFilter(ResourceQuantity resource) {
/* 20 */     this.resource = resource;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable Item item) {
/* 25 */     return (item == null || this.resource.getResourceType(item) != null);
/*    */   }
/*    */   
/*    */   public ResourceQuantity getResource() {
/* 29 */     return this.resource;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\filter\ResourceFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */