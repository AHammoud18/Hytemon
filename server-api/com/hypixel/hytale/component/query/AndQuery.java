/*    */ package com.hypixel.hytale.component.query;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AndQuery<ECS_TYPE>
/*    */   implements Query<ECS_TYPE>
/*    */ {
/*    */   private final Query<ECS_TYPE>[] queries;
/*    */   
/*    */   @SafeVarargs
/*    */   public AndQuery(Query<ECS_TYPE>... queries) {
/* 16 */     this.queries = queries;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(Archetype<ECS_TYPE> archetype) {
/* 22 */     for (Query<ECS_TYPE> query : this.queries) {
/* 23 */       if (!query.test(archetype)) return false; 
/*    */     } 
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresComponentType(ComponentType<ECS_TYPE, ?> componentType) {
/* 30 */     for (Query<ECS_TYPE> query : this.queries) {
/* 31 */       if (query.requiresComponentType(componentType)) return true; 
/*    */     } 
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validateRegistry(ComponentRegistry<ECS_TYPE> registry) {
/* 38 */     for (Query<ECS_TYPE> query : this.queries) query.validateRegistry(registry);
/*    */   
/*    */   }
/*    */   
/*    */   public void validate() {
/* 43 */     for (Query<ECS_TYPE> query : this.queries) query.validate(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\query\AndQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */