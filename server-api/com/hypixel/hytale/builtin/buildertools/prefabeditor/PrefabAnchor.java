/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabAnchor
/*    */   implements Component<EntityStore>
/*    */ {
/* 14 */   public static final PrefabAnchor INSTANCE = new PrefabAnchor();
/*    */   
/* 16 */   public static final BuilderCodec<PrefabAnchor> CODEC = BuilderCodec.builder(PrefabAnchor.class, () -> INSTANCE)
/* 17 */     .build();
/*    */   
/*    */   public static ComponentType<EntityStore, PrefabAnchor> getComponentType() {
/* 20 */     return BuilderToolsPlugin.get().getPrefabAnchorComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 28 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabAnchor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */