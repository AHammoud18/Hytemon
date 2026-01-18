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
/*    */ public class ExactArchetypeQuery<ECS_TYPE>
/*    */   implements Query<ECS_TYPE>
/*    */ {
/*    */   private final Archetype<ECS_TYPE> archetype;
/*    */   
/*    */   public ExactArchetypeQuery(Archetype<ECS_TYPE> archetype) {
/* 17 */     this.archetype = archetype;
/*    */   }
/*    */   
/*    */   public Archetype<ECS_TYPE> getArchetype() {
/* 21 */     return this.archetype;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nonnull Archetype<ECS_TYPE> archetype) {
/* 26 */     return archetype.equals(this.archetype);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresComponentType(@Nonnull ComponentType<ECS_TYPE, ?> componentType) {
/* 31 */     return this.archetype.requiresComponentType(componentType);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validateRegistry(ComponentRegistry<ECS_TYPE> registry) {
/* 36 */     this.archetype.validateRegistry(registry);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate() {
/* 41 */     this.archetype.validate();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\query\ExactArchetypeQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */