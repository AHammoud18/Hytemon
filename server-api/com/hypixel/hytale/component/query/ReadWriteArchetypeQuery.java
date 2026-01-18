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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ReadWriteArchetypeQuery<ECS_TYPE>
/*    */   extends Query<ECS_TYPE>
/*    */ {
/*    */   Archetype<ECS_TYPE> getReadArchetype();
/*    */   
/*    */   Archetype<ECS_TYPE> getWriteArchetype();
/*    */   
/*    */   default boolean test(@Nonnull Archetype<ECS_TYPE> archetype) {
/* 32 */     return (archetype.contains(getReadArchetype()) && archetype.contains(getWriteArchetype()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean requiresComponentType(@Nonnull ComponentType<ECS_TYPE, ?> componentType) {
/* 42 */     return (getReadArchetype().contains(componentType) || getWriteArchetype().contains(componentType));
/*    */   }
/*    */ 
/*    */   
/*    */   default void validateRegistry(ComponentRegistry<ECS_TYPE> registry) {
/* 47 */     getReadArchetype().validateRegistry(registry);
/* 48 */     getWriteArchetype().validateRegistry(registry);
/*    */   }
/*    */ 
/*    */   
/*    */   default void validate() {
/* 53 */     getReadArchetype().validate();
/* 54 */     getWriteArchetype().validate();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\query\ReadWriteArchetypeQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */