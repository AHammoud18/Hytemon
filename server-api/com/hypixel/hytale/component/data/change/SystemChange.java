/*    */ package com.hypixel.hytale.component.data.change;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SystemChange<ECS_TYPE>
/*    */   implements DataChange {
/*    */   private final ChangeType type;
/*    */   private final ISystem<ECS_TYPE> system;
/*    */   
/*    */   public SystemChange(ChangeType type, ISystem<ECS_TYPE> system) {
/* 12 */     this.type = type;
/* 13 */     this.system = system;
/*    */   }
/*    */   
/*    */   public ChangeType getType() {
/* 17 */     return this.type;
/*    */   }
/*    */   
/*    */   public ISystem<ECS_TYPE> getSystem() {
/* 21 */     return this.system;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 27 */     return "SystemChange{type=" + String.valueOf(this.type) + ", system=" + String.valueOf(this.system) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\data\change\SystemChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */