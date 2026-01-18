/*    */ package com.hypixel.hytale.component.query;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ 
/*    */ 
/*    */ 
/*    */ class AnyQuery<ECS_TYPE>
/*    */   implements Query<ECS_TYPE>
/*    */ {
/* 12 */   static final AnyQuery<?> INSTANCE = new AnyQuery();
/*    */ 
/*    */   
/*    */   public boolean test(Archetype<ECS_TYPE> archetype) {
/* 16 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresComponentType(ComponentType<ECS_TYPE, ?> componentType) {
/* 21 */     return false;
/*    */   }
/*    */   
/*    */   public void validateRegistry(ComponentRegistry<ECS_TYPE> registry) {}
/*    */   
/*    */   public void validate() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\query\AnyQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */