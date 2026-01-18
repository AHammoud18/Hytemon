/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FromPrefab
/*    */   implements Component<EntityStore>
/*    */ {
/* 17 */   public static final FromPrefab INSTANCE = new FromPrefab();
/*    */   
/* 19 */   public static final BuilderCodec<FromPrefab> CODEC = BuilderCodec.builder(FromPrefab.class, () -> INSTANCE)
/* 20 */     .build();
/*    */   
/*    */   public static ComponentType<EntityStore, FromPrefab> getComponentType() {
/* 23 */     return EntityModule.get().getFromPrefabComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 31 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\FromPrefab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */