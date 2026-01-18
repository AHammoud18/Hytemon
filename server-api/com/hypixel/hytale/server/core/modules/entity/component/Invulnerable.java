/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ public class Invulnerable
/*    */   implements Component<EntityStore>
/*    */ {
/* 13 */   public static final Invulnerable INSTANCE = new Invulnerable();
/*    */   
/* 15 */   public static final BuilderCodec<Invulnerable> CODEC = BuilderCodec.builder(Invulnerable.class, () -> INSTANCE)
/* 16 */     .build();
/*    */   
/*    */   public static ComponentType<EntityStore, Invulnerable> getComponentType() {
/* 19 */     return EntityModule.get().getInvulnerableComponentType();
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\Invulnerable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */