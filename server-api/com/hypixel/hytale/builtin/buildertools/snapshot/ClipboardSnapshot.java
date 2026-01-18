/*    */ package com.hypixel.hytale.builtin.buildertools.snapshot;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ClipboardSnapshot<T extends SelectionSnapshot<?>>
/*    */   extends SelectionSnapshot<T>
/*    */ {
/*    */   static {
/* 19 */     if (null.$assertionsDisabled);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   default T restore(Ref<EntityStore> ref, @Nonnull Player player, World world, ComponentAccessor<EntityStore> componentAccessor) {
/* 25 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 26 */     if (!null.$assertionsDisabled && playerRefComponent == null) throw new AssertionError();
/*    */     
/* 28 */     BuilderToolsPlugin.BuilderState state = BuilderToolsPlugin.getState(player, playerRefComponent);
/* 29 */     if (state == null) return null;
/*    */     
/* 31 */     return restoreClipboard(ref, player, world, state, componentAccessor);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   T restoreClipboard(Ref<EntityStore> paramRef, Player paramPlayer, World paramWorld, BuilderToolsPlugin.BuilderState paramBuilderState, ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\snapshot\ClipboardSnapshot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */