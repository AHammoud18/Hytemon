/*    */ package com.hypixel.hytale.server.core.inventory.container.filter;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TagFilter
/*    */   implements ItemSlotFilter
/*    */ {
/*    */   private final int tagIndex;
/*    */   
/*    */   public TagFilter(int tagIndex) {
/* 16 */     this.tagIndex = tagIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable Item item) {
/* 21 */     return (item == null || item.getData().getExpandedTagIndexes().contains(this.tagIndex));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\filter\TagFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */