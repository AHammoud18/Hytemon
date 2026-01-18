/*    */ package com.hypixel.hytale.builtin.mounts;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MountedByComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, MountedByComponent> getComponentType() {
/* 15 */     return MountPlugin.getInstance().getMountedByComponentType();
/*    */   }
/*    */   @Nonnull
/* 18 */   private final List<Ref<EntityStore>> passengers = (List<Ref<EntityStore>>)new ObjectArrayList();
/*    */ 
/*    */   
/*    */   public void removeInvalid() {
/* 22 */     this.passengers.removeIf(v -> !v.isValid());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<Ref<EntityStore>> getPassengers() {
/* 27 */     removeInvalid();
/* 28 */     return this.passengers;
/*    */   }
/*    */   
/*    */   public void addPassenger(Ref<EntityStore> passenger) {
/* 32 */     this.passengers.add(passenger);
/*    */   }
/*    */   
/*    */   public void removePassenger(Ref<EntityStore> ref) {
/* 36 */     this.passengers.remove(ref);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public MountedByComponent withPassenger(Ref<EntityStore> passenger) {
/* 41 */     this.passengers.add(passenger);
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 48 */     return new MountedByComponent();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\MountedByComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */