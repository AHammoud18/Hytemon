/*    */ package com.hypixel.hytale.component.data.change;
/*    */ 
/*    */ import com.hypixel.hytale.component.SystemType;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SystemTypeChange<ECS_TYPE, T extends ISystem<ECS_TYPE>>
/*    */   implements DataChange {
/*    */   private final ChangeType type;
/*    */   private final SystemType<ECS_TYPE, T> systemType;
/*    */   
/*    */   public SystemTypeChange(ChangeType type, SystemType<ECS_TYPE, T> systemType) {
/* 13 */     this.type = type;
/* 14 */     this.systemType = systemType;
/*    */   }
/*    */   
/*    */   public ChangeType getType() {
/* 18 */     return this.type;
/*    */   }
/*    */   
/*    */   public SystemType<ECS_TYPE, T> getSystemType() {
/* 22 */     return this.systemType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 28 */     return "SystemTypeChange{type=" + String.valueOf(this.type) + ", systemType=" + String.valueOf(this.systemType) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\data\change\SystemTypeChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */