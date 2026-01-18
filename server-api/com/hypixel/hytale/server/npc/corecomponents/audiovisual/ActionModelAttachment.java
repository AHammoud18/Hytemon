/*    */ package com.hypixel.hytale.server.npc.corecomponents.audiovisual;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionModelAttachment;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionModelAttachment
/*    */   extends ActionBase
/*    */ {
/*    */   @Nonnull
/*    */   protected final String slot;
/*    */   @Nonnull
/*    */   protected final String attachment;
/*    */   
/*    */   public ActionModelAttachment(@Nonnull BuilderActionModelAttachment builder, @Nonnull BuilderSupport support) {
/* 46 */     super((BuilderActionBase)builder);
/* 47 */     this.slot = builder.getSlot(support);
/* 48 */     this.attachment = builder.getAttachment(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 53 */     super.execute(ref, role, sensorInfo, dt, store);
/* 54 */     setModelAttachment(ref, this.slot, this.attachment, (ComponentAccessor<EntityStore>)store);
/* 55 */     return true;
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
/*    */ 
/*    */   
/*    */   private static void setModelAttachment(@Nonnull Ref<EntityStore> ref, @Nonnull String slot, @Nullable String attachment, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 70 */     if (slot.isEmpty()) throw new IllegalArgumentException("Slot must be specified!");
/*    */     
/* 72 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/* 73 */     assert modelComponent != null;
/*    */     
/* 75 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/* 76 */     assert npcComponent != null;
/*    */     
/* 78 */     Model model = modelComponent.getModel();
/*    */     
/* 80 */     float scale = model.getScale();
/* 81 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(model.getModelAssetId());
/* 82 */     Map<String, String> randomAttachments = (model.getRandomAttachmentIds() != null) ? new HashMap<>(model.getRandomAttachmentIds()) : new HashMap<>();
/* 83 */     if (attachment == null || attachment.isEmpty()) {
/* 84 */       randomAttachments.remove(slot);
/*    */     } else {
/* 86 */       randomAttachments.put(slot, attachment);
/*    */     } 
/*    */     
/* 89 */     model = Model.createScaledModel(modelAsset, scale, randomAttachments);
/* 90 */     componentAccessor.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*    */     
/* 92 */     Role role = npcComponent.getRole();
/* 93 */     if (role != null)
/* 94 */       role.updateMotionControllers(ref, model, model.getBoundingBox(), componentAccessor); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\audiovisual\ActionModelAttachment.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */