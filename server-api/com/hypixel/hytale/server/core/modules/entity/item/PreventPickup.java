/*    */ package com.hypixel.hytale.server.core.modules.entity.item;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PreventPickup
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 18 */   public static final PreventPickup INSTANCE = new PreventPickup();
/*    */   
/*    */   @Nonnull
/* 21 */   public static final BuilderCodec<PreventPickup> CODEC = BuilderCodec.builder(PreventPickup.class, () -> INSTANCE)
/* 22 */     .build();
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, PreventPickup> getComponentType() {
/* 26 */     return EntityModule.get().getPreventPickupComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 34 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\PreventPickup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */