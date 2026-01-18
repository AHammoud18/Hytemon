/*    */ package com.hypixel.hytale.server.npc.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.instructions.RoleStateChange;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public interface IEntityFilter
/*    */   extends RoleStateChange, IAnnotatedComponent {
/* 15 */   public static final IEntityFilter[] EMPTY_ARRAY = new IEntityFilter[0];
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int MINIMAL_COST = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int LOW_COST = 100;
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int MID_COST = 200;
/*    */ 
/*    */   
/*    */   public static final int HIGH_COST = 300;
/*    */ 
/*    */   
/*    */   public static final int EXTREME_COST = 400;
/*    */ 
/*    */ 
/*    */   
/*    */   boolean matchesEntity(@Nonnull Ref<EntityStore> paramRef1, @Nonnull Ref<EntityStore> paramRef2, @Nonnull Role paramRole, @Nonnull Store<EntityStore> paramStore);
/*    */ 
/*    */ 
/*    */   
/*    */   int cost();
/*    */ 
/*    */ 
/*    */   
/*    */   static void prioritiseFilters(@Nonnull IEntityFilter[] filters) {
/* 46 */     Arrays.sort(filters, Comparator.comparingInt(IEntityFilter::cost));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\IEntityFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */