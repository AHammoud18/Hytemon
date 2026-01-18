/*    */ package com.hypixel.hytale.component.query;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Query<ECS_TYPE>
/*    */ {
/*    */   @Nonnull
/*    */   static <ECS_TYPE> AnyQuery<ECS_TYPE> any() {
/* 17 */     return (AnyQuery)AnyQuery.INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   static <ECS_TYPE> NotQuery<ECS_TYPE> not(Query<ECS_TYPE> query) {
/* 22 */     return new NotQuery<>(query);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   @SafeVarargs
/*    */   static <ECS_TYPE> AndQuery<ECS_TYPE> and(Query<ECS_TYPE>... queries) {
/* 28 */     return new AndQuery<>(queries);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   @SafeVarargs
/*    */   static <ECS_TYPE> OrQuery<ECS_TYPE> or(Query<ECS_TYPE>... queries) {
/* 34 */     return new OrQuery<>(queries);
/*    */   }
/*    */   
/*    */   boolean test(Archetype<ECS_TYPE> paramArchetype);
/*    */   
/*    */   boolean requiresComponentType(ComponentType<ECS_TYPE, ?> paramComponentType);
/*    */   
/*    */   void validateRegistry(ComponentRegistry<ECS_TYPE> paramComponentRegistry);
/*    */   
/*    */   void validate();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\query\Query.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */