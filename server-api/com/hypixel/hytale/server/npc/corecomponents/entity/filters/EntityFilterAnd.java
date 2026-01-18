/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityFilterAnd
/*    */   extends EntityFilterMany {
/*    */   public EntityFilterAnd(@Nonnull List<IEntityFilter> filters) {
/* 14 */     super(filters);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 19 */     for (IEntityFilter filter : this.filters) {
/* 20 */       if (!filter.matchesEntity(ref, targetRef, role, store)) return false; 
/*    */     } 
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterAnd.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */