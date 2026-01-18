/*    */ package com.hypixel.hytale.component.query;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotQuery<ECS_TYPE>
/*    */   implements Query<ECS_TYPE>
/*    */ {
/*    */   private final Query<ECS_TYPE> query;
/*    */   
/*    */   public NotQuery(Query<ECS_TYPE> query) {
/* 15 */     this.query = query;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(Archetype<ECS_TYPE> archetype) {
/* 21 */     return !this.query.test(archetype);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean requiresComponentType(ComponentType<ECS_TYPE, ?> componentType) {
/* 27 */     return this.query.requiresComponentType(componentType);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validateRegistry(ComponentRegistry<ECS_TYPE> registry) {
/* 32 */     this.query.validateRegistry(registry);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate() {
/* 37 */     this.query.validate();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\query\NotQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */