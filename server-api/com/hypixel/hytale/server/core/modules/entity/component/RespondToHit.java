/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ public class RespondToHit
/*    */   implements Component<EntityStore>
/*    */ {
/* 13 */   public static final RespondToHit INSTANCE = new RespondToHit();
/*    */   
/* 15 */   public static final BuilderCodec<RespondToHit> CODEC = BuilderCodec.builder(RespondToHit.class, () -> INSTANCE)
/* 16 */     .build();
/*    */   
/*    */   public static ComponentType<EntityStore, RespondToHit> getComponentType() {
/* 19 */     return EntityModule.get().getRespondToHitComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 27 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\RespondToHit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */