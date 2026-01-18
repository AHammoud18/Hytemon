/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PersistentModel
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<PersistentModel> CODEC;
/*    */   private Model.ModelReference modelReference;
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, PersistentModel> getComponentType() {
/* 23 */     return EntityModule.get().getPersistentModelComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PersistentModel.class, PersistentModel::new).append(new KeyedCodec("Model", (Codec)Model.ModelReference.CODEC), (entity, model) -> entity.modelReference = model, entity -> entity.modelReference).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private PersistentModel() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PersistentModel(@Nonnull Model.ModelReference modelReference) {
/* 57 */     this.modelReference = modelReference;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Model.ModelReference getModelReference() {
/* 65 */     return this.modelReference;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setModelReference(@Nonnull Model.ModelReference modelReference) {
/* 74 */     this.modelReference = modelReference;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 81 */     PersistentModel modelComponent = new PersistentModel();
/* 82 */     modelComponent.modelReference = this.modelReference;
/* 83 */     return modelComponent;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\PersistentModel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */