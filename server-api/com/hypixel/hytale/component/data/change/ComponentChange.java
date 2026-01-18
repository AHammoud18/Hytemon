/*    */ package com.hypixel.hytale.component.data.change;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ComponentChange<ECS_TYPE, T extends Component<ECS_TYPE>>
/*    */   implements DataChange {
/*    */   private final ChangeType type;
/*    */   private final ComponentType<ECS_TYPE, T> componentType;
/*    */   
/*    */   public ComponentChange(ChangeType type, ComponentType<ECS_TYPE, T> componentType) {
/* 13 */     this.type = type;
/* 14 */     this.componentType = componentType;
/*    */   }
/*    */   
/*    */   public ChangeType getType() {
/* 18 */     return this.type;
/*    */   }
/*    */   
/*    */   public ComponentType<ECS_TYPE, T> getComponentType() {
/* 22 */     return this.componentType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 28 */     return "ComponentChange{type=" + String.valueOf(this.type) + ", componentType=" + String.valueOf(this.componentType) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\data\change\ComponentChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */