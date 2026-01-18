/*    */ package com.hypixel.hytale.server.core.entity.entities.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.ClientCameraView;
/*    */ import com.hypixel.hytale.protocol.MouseButtonState;
/*    */ import com.hypixel.hytale.protocol.MouseButtonType;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.camera.SetServerCamera;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CameraManager
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, CameraManager> getComponentType() {
/* 22 */     return EntityModule.get().getCameraManagerComponentType();
/*    */   }
/*    */   
/* 25 */   private final Map<MouseButtonType, MouseButtonState> mouseStates = new EnumMap<>(MouseButtonType.class);
/* 26 */   private final Map<MouseButtonType, Vector3i> mousePressedPosition = new EnumMap<>(MouseButtonType.class);
/* 27 */   private final Map<MouseButtonType, Vector3i> mouseReleasedPosition = new EnumMap<>(MouseButtonType.class);
/*    */   
/* 29 */   private Vector2d lastScreenPoint = Vector2d.ZERO;
/*    */   
/*    */   private Vector3i lastTargetBlock;
/*    */ 
/*    */   
/*    */   public CameraManager(@Nonnull CameraManager other) {
/* 35 */     this();
/* 36 */     this.lastScreenPoint = other.lastScreenPoint;
/* 37 */     this.lastTargetBlock = other.lastTargetBlock;
/*    */   }
/*    */   
/*    */   public void resetCamera(@Nonnull PlayerRef ref) {
/* 41 */     ref.getPacketHandler().writeNoCache((Packet)new SetServerCamera(ClientCameraView.Custom, false, null));
/* 42 */     this.mouseStates.clear();
/*    */   }
/*    */   
/*    */   public void handleMouseButtonState(MouseButtonType mouseButtonType, MouseButtonState state, Vector3i targetBlock) {
/* 46 */     this.mouseStates.put(mouseButtonType, state);
/* 47 */     if (state == MouseButtonState.Pressed) this.mousePressedPosition.put(mouseButtonType, targetBlock); 
/* 48 */     if (state == MouseButtonState.Released) this.mouseReleasedPosition.put(mouseButtonType, targetBlock); 
/*    */   }
/*    */   
/*    */   public MouseButtonState getMouseButtonState(MouseButtonType mouseButtonType) {
/* 52 */     return this.mouseStates.getOrDefault(mouseButtonType, MouseButtonState.Released);
/*    */   }
/*    */   
/*    */   public Vector3i getLastMouseButtonPressedPosition(MouseButtonType mouseButtonType) {
/* 56 */     return this.mousePressedPosition.get(mouseButtonType);
/*    */   }
/*    */   
/*    */   public Vector3i getLastMouseButtonReleasedPosition(MouseButtonType mouseButtonType) {
/* 60 */     return this.mouseReleasedPosition.get(mouseButtonType);
/*    */   }
/*    */   
/*    */   public void setLastScreenPoint(Vector2d lastScreenPoint) {
/* 64 */     this.lastScreenPoint = lastScreenPoint;
/*    */   }
/*    */   
/*    */   public Vector2d getLastScreenPoint() {
/* 68 */     return this.lastScreenPoint;
/*    */   }
/*    */   
/*    */   public void setLastBlockPosition(Vector3i targetBlock) {
/* 72 */     this.lastTargetBlock = targetBlock;
/*    */   }
/*    */   
/*    */   public Vector3i getLastTargetBlock() {
/* 76 */     return this.lastTargetBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 82 */     return new CameraManager(this);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 88 */     return "CameraManager{mouseStates=" + String.valueOf(this.mouseStates) + ", mousePressedPosition=" + String.valueOf(this.mousePressedPosition) + ", mouseReleasedPosition=" + String.valueOf(this.mouseReleasedPosition) + ", lastScreenPoint=" + String.valueOf(this.lastScreenPoint) + ", lastTargetBlock=" + String.valueOf(this.lastTargetBlock) + "}";
/*    */   }
/*    */   
/*    */   public CameraManager() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\CameraManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */