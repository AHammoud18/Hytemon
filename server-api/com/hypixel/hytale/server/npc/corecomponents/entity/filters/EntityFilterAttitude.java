/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterAttitude;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import java.util.Arrays;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityFilterAttitude
/*    */   extends EntityFilterBase {
/*    */   public static final String TYPE = "Attitude";
/*    */   public static final int COST = 0;
/*    */   protected final EnumSet<Attitude> attitudes;
/*    */   
/*    */   public EntityFilterAttitude(@Nonnull BuilderEntityFilterAttitude builder, @Nonnull BuilderSupport support) {
/* 23 */     this.attitudes = builder.getAttitudes(support);
/*    */   }
/*    */   
/*    */   public EntityFilterAttitude(Attitude[] attitudes) {
/* 27 */     this.attitudes = EnumSet.noneOf(Attitude.class);
/* 28 */     this.attitudes.addAll(Arrays.asList(attitudes));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 33 */     Attitude attitude = role.getWorldSupport().getAttitude(ref, targetRef, (ComponentAccessor)store);
/* 34 */     return (attitude != null && this.attitudes.contains(attitude));
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 39 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerWithSupport(@Nonnull Role role) {
/* 44 */     role.getWorldSupport().requireAttitudeCache();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterAttitude.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */