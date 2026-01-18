/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ApplyRandomSkinPersistedComponent
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, ApplyRandomSkinPersistedComponent> getComponentType() {
/* 13 */     return EntityModule.get().getApplyRandomSkinPersistedComponent();
/*    */   }
/*    */   
/* 16 */   public static final ApplyRandomSkinPersistedComponent INSTANCE = new ApplyRandomSkinPersistedComponent();
/*    */   
/* 18 */   public static final BuilderCodec<ApplyRandomSkinPersistedComponent> CODEC = BuilderCodec.builder(ApplyRandomSkinPersistedComponent.class, () -> INSTANCE)
/* 19 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 27 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\ApplyRandomSkinPersistedComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */