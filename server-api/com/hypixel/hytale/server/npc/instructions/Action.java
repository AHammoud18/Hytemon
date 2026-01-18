/*    */ package com.hypixel.hytale.server.npc.instructions;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*    */ import com.hypixel.hytale.server.npc.util.IComponentExecutionControl;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface Action
/*    */   extends RoleStateChange, IAnnotatedComponent, IComponentExecutionControl {
/* 15 */   public static final Action[] EMPTY_ARRAY = new Action[0];
/*    */   
/*    */   boolean canExecute(@Nonnull Ref<EntityStore> paramRef, @Nonnull Role paramRole, @Nullable InfoProvider paramInfoProvider, double paramDouble, @Nonnull Store<EntityStore> paramStore);
/*    */   
/*    */   boolean execute(@Nonnull Ref<EntityStore> paramRef, @Nonnull Role paramRole, @Nullable InfoProvider paramInfoProvider, double paramDouble, @Nonnull Store<EntityStore> paramStore);
/*    */   
/*    */   void activate(Role paramRole, InfoProvider paramInfoProvider);
/*    */   
/*    */   void deactivate(Role paramRole, InfoProvider paramInfoProvider);
/*    */   
/*    */   boolean isActivated();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\instructions\Action.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */