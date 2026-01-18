/*    */ package com.hypixel.hytale.component.data.change;
/*    */ 
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SystemGroupChange<ECS_TYPE>
/*    */   implements DataChange {
/*    */   private final ChangeType type;
/*    */   private final SystemGroup<ECS_TYPE> systemGroup;
/*    */   
/*    */   public SystemGroupChange(ChangeType type, SystemGroup<ECS_TYPE> systemGroup) {
/* 12 */     this.type = type;
/* 13 */     this.systemGroup = systemGroup;
/*    */   }
/*    */   
/*    */   public ChangeType getType() {
/* 17 */     return this.type;
/*    */   }
/*    */   
/*    */   public SystemGroup<ECS_TYPE> getSystemGroup() {
/* 21 */     return this.systemGroup;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 27 */     return "SystemGroupChange{type=" + String.valueOf(this.type) + ", systemGroup=" + String.valueOf(this.systemGroup) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\data\change\SystemGroupChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */