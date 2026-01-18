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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PreventItemMerging
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 21 */   public static final PreventItemMerging INSTANCE = new PreventItemMerging();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 27 */   public static final BuilderCodec<PreventItemMerging> CODEC = BuilderCodec.builder(PreventItemMerging.class, () -> INSTANCE)
/* 28 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, PreventItemMerging> getComponentType() {
/* 35 */     return EntityModule.get().getPreventItemMergingType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 46 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\PreventItemMerging.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */