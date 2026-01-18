/*    */ package com.hypixel.hytale.builtin.mounts;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class NPCMountComponent
/*    */   implements Component<EntityStore> {
/*    */   public static final BuilderCodec<NPCMountComponent> CODEC;
/*    */   private int originalRoleIndex;
/*    */   @Nullable
/*    */   private PlayerRef ownerPlayerRef;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(NPCMountComponent.class, NPCMountComponent::new).append(new KeyedCodec("OriginalRoleIndex", (Codec)Codec.INTEGER), (mountComponent, integer) -> mountComponent.originalRoleIndex = integer.intValue(), mountComponent -> Integer.valueOf(mountComponent.originalRoleIndex)).add()).build();
/*    */   } private float anchorX; private float anchorY; private float anchorZ;
/*    */   public static ComponentType<EntityStore, NPCMountComponent> getComponentType() {
/* 25 */     return MountPlugin.getInstance().getMountComponentType();
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
/*    */   public int getOriginalRoleIndex() {
/* 38 */     return this.originalRoleIndex;
/*    */   }
/*    */   
/*    */   public void setOriginalRoleIndex(int originalRoleIndex) {
/* 42 */     this.originalRoleIndex = originalRoleIndex;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public PlayerRef getOwnerPlayerRef() {
/* 47 */     return this.ownerPlayerRef;
/*    */   }
/*    */   
/*    */   public void setOwnerPlayerRef(PlayerRef ownerPlayerRef) {
/* 51 */     this.ownerPlayerRef = ownerPlayerRef;
/*    */   }
/*    */   
/*    */   public float getAnchorX() {
/* 55 */     return this.anchorX;
/*    */   }
/*    */   
/*    */   public float getAnchorY() {
/* 59 */     return this.anchorY;
/*    */   }
/*    */   
/*    */   public float getAnchorZ() {
/* 63 */     return this.anchorZ;
/*    */   }
/*    */   
/*    */   public void setAnchor(float x, float y, float z) {
/* 67 */     this.anchorX = x;
/* 68 */     this.anchorY = y;
/* 69 */     this.anchorZ = z;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 75 */     NPCMountComponent component = new NPCMountComponent();
/* 76 */     component.originalRoleIndex = this.originalRoleIndex;
/* 77 */     component.ownerPlayerRef = this.ownerPlayerRef;
/* 78 */     component.anchorX = this.anchorX;
/* 79 */     component.anchorY = this.anchorY;
/* 80 */     component.anchorZ = this.anchorZ;
/* 81 */     return component;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\NPCMountComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */