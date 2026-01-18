/*     */ package com.hypixel.hytale.builtin.path;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameplateHolderSystem
/*     */   extends HolderSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 152 */     return (Query<EntityStore>)Archetype.of(PatrolPathMarkerEntity.getComponentType());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdd(@NonNullDecl Holder<EntityStore> holder, @NonNullDecl AddReason reason, @NonNullDecl Store<EntityStore> store) {
/* 157 */     PatrolPathMarkerEntity patrolPathMarkerComponent = (PatrolPathMarkerEntity)holder.getComponent(PatrolPathMarkerEntity.getComponentType());
/* 158 */     assert patrolPathMarkerComponent != null;
/*     */     
/* 160 */     DisplayNameComponent displayNameComponent = (DisplayNameComponent)holder.getComponent(DisplayNameComponent.getComponentType());
/* 161 */     String displayName = "";
/* 162 */     if (displayNameComponent == null) {
/* 163 */       String legacyDisplayName = patrolPathMarkerComponent.getLegacyDisplayName();
/* 164 */       displayName = (legacyDisplayName != null) ? legacyDisplayName : "Path Marker";
/*     */       
/* 166 */       Message legacyDisplayNameMessage = Message.raw(displayName);
/* 167 */       displayNameComponent = new DisplayNameComponent(legacyDisplayNameMessage);
/* 168 */       holder.putComponent(DisplayNameComponent.getComponentType(), (Component)displayNameComponent);
/*     */     } 
/*     */     
/* 171 */     Nameplate nameplateComponent = (Nameplate)holder.getComponent(Nameplate.getComponentType());
/* 172 */     if (nameplateComponent == null)
/* 173 */       holder.putComponent(Nameplate.getComponentType(), (Component)new Nameplate(displayName)); 
/*     */   }
/*     */   
/*     */   public void onEntityRemoved(@NonNullDecl Holder<EntityStore> holder, @NonNullDecl RemoveReason reason, @NonNullDecl Store<EntityStore> store) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\PrefabPathSystems$NameplateHolderSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */