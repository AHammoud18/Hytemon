/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropComponent
/*    */   implements Component<EntityStore>
/*    */ {
/* 17 */   public static final BuilderCodec<PropComponent> CODEC = BuilderCodec.builder(PropComponent.class, PropComponent::new).build();
/*    */   
/* 19 */   private static final PropComponent INSTANCE = new PropComponent();
/*    */   
/*    */   public static ComponentType<EntityStore, PropComponent> getComponentType() {
/* 22 */     return EntityModule.get().getPropComponentType();
/*    */   }
/*    */   
/*    */   public static PropComponent get() {
/* 26 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 32 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\PropComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */