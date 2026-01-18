/*    */ package com.hypixel.hytale.server.core.entity;
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
/*    */ 
/*    */ public class Frozen
/*    */   implements Component<EntityStore>
/*    */ {
/* 18 */   public static final BuilderCodec<Frozen> CODEC = BuilderCodec.builder(Frozen.class, Frozen::get)
/* 19 */     .build();
/*    */   
/*    */   public static ComponentType<EntityStore, Frozen> getComponentType() {
/* 22 */     return EntityModule.get().getFrozenComponentType();
/*    */   }
/*    */   
/* 25 */   private static final Frozen INSTANCE = new Frozen();
/*    */   
/*    */   public static Frozen get() {
/* 28 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 36 */     return get();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\Frozen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */