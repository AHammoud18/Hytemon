/*    */ package com.hypixel.hytale.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.query.ReadWriteArchetypeQuery;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReadWriteQuery<ECS_TYPE>
/*    */   implements ReadWriteArchetypeQuery<ECS_TYPE>
/*    */ {
/*    */   private final Archetype<ECS_TYPE> read;
/*    */   private final Archetype<ECS_TYPE> write;
/*    */   
/*    */   public ReadWriteQuery(Archetype<ECS_TYPE> read, Archetype<ECS_TYPE> write) {
/* 14 */     this.read = read;
/* 15 */     this.write = write;
/*    */   }
/*    */ 
/*    */   
/*    */   public Archetype<ECS_TYPE> getReadArchetype() {
/* 20 */     return this.read;
/*    */   }
/*    */ 
/*    */   
/*    */   public Archetype<ECS_TYPE> getWriteArchetype() {
/* 25 */     return this.write;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\ReadWriteQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */