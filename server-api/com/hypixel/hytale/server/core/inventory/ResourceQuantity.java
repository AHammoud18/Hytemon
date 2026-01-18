/*    */ package com.hypixel.hytale.server.core.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.ItemResourceType;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class ResourceQuantity
/*    */ {
/*    */   protected String resourceId;
/*    */   protected int quantity;
/*    */   
/*    */   public ResourceQuantity(String resourceId, int quantity) {
/* 17 */     Objects.requireNonNull(resourceId, "resourceId cannot be null!");
/* 18 */     if (quantity <= 0) throw new IllegalArgumentException("quantity " + quantity + " must be >0!");
/*    */     
/* 20 */     this.resourceId = resourceId;
/* 21 */     this.quantity = quantity;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceQuantity() {}
/*    */   
/*    */   public String getResourceId() {
/* 28 */     return this.resourceId;
/*    */   }
/*    */   
/*    */   public int getQuantity() {
/* 32 */     return this.quantity;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ResourceQuantity clone(int quantity) {
/* 37 */     return new ResourceQuantity(this.resourceId, quantity);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public ItemResourceType getResourceType(@Nonnull Item item) {
/* 42 */     return ItemContainer.getMatchingResourceType(item, this.resourceId);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 47 */     if (this == o) return true; 
/* 48 */     if (o == null || getClass() != o.getClass()) return false; 
/* 49 */     ResourceQuantity itemStack = (ResourceQuantity)o;
/* 50 */     if (this.quantity != itemStack.quantity) return false; 
/* 51 */     return (this.resourceId != null) ? this.resourceId.equals(itemStack.resourceId) : ((itemStack.resourceId == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 56 */     int result = (this.resourceId != null) ? this.resourceId.hashCode() : 0;
/* 57 */     result = 31 * result + this.quantity;
/* 58 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 64 */     return "ResourceQuantity{resourceId='" + this.resourceId + "', quantity=" + this.quantity + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\ResourceQuantity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */