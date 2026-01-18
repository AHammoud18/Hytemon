/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ public class Intangible
/*    */   implements Component<EntityStore>
/*    */ {
/* 13 */   public static final Intangible INSTANCE = new Intangible();
/*    */   
/* 15 */   public static final BuilderCodec<Intangible> CODEC = BuilderCodec.builder(Intangible.class, () -> INSTANCE)
/* 16 */     .build();
/*    */   
/*    */   public static ComponentType<EntityStore, Intangible> getComponentType() {
/* 19 */     return EntityModule.get().getIntangibleComponentType();
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\Intangible.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */