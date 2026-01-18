/*    */ package com.hypixel.hytale.server.npc.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.instructions.RoleStateChange;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISensorEntityCollector
/*    */   extends RoleStateChange
/*    */ {
/* 18 */   public static final ISensorEntityCollector DEFAULT = new ISensorEntityCollector()
/*    */     {
/*    */       public void init(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*    */ 
/*    */ 
/*    */       
/*    */       public void collectMatching(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*    */ 
/*    */ 
/*    */       
/*    */       public void collectNonMatching(@Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*    */ 
/*    */ 
/*    */       
/*    */       public boolean terminateOnFirstMatch() {
/* 33 */         return true;
/*    */       }
/*    */       
/*    */       public void cleanup() {}
/*    */     };
/*    */   
/*    */   void init(@Nonnull Ref<EntityStore> paramRef, @Nonnull Role paramRole, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */   
/*    */   void collectMatching(@Nonnull Ref<EntityStore> paramRef1, @Nonnull Ref<EntityStore> paramRef2, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */   
/*    */   void collectNonMatching(@Nonnull Ref<EntityStore> paramRef, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */   
/*    */   boolean terminateOnFirstMatch();
/*    */   
/*    */   void cleanup();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\ISensorEntityCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */