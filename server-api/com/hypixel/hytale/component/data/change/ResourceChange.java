/*    */ package com.hypixel.hytale.component.data.change;
/*    */ 
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ResourceChange<ECS_TYPE, T extends Resource<ECS_TYPE>>
/*    */   implements DataChange {
/*    */   private final ChangeType type;
/*    */   private final ResourceType<ECS_TYPE, T> resourceType;
/*    */   
/*    */   public ResourceChange(ChangeType type, ResourceType<ECS_TYPE, T> resourceType) {
/* 13 */     this.type = type;
/* 14 */     this.resourceType = resourceType;
/*    */   }
/*    */   
/*    */   public ChangeType getType() {
/* 18 */     return this.type;
/*    */   }
/*    */   
/*    */   public ResourceType<ECS_TYPE, T> getResourceType() {
/* 22 */     return this.resourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 28 */     return "ResourceChange{type=" + String.valueOf(this.type) + ", resourceChange=" + String.valueOf(this.resourceType) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\data\change\ResourceChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */