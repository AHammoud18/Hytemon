/*     */ package com.hypixel.hytale.builtin.teleport.components;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class TeleportHistory
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   private static final int MAX_TELEPORT_HISTORY = 100;
/*  34 */   private static final Message MESSAGE_COMMANDS_TELEPORT_NOT_FURTHER = Message.translation("server.commands.teleport.notFurther");
/*  35 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WORLD_NOT_LOADED = Message.translation("server.commands.teleport.worldNotLoaded");
/*  36 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_FORWARD_TO_WAYPOINT = Message.translation("server.commands.teleport.teleportedForwardToWaypoint");
/*  37 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_BACK_TO_WAYPOINT = Message.translation("server.commands.teleport.teleportedBackToWaypoint");
/*  38 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_FORWARD_TO_COORDINATES = Message.translation("server.commands.teleport.teleportedForwardToCoordinates");
/*  39 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_BACK_TO_COORDINATES = Message.translation("server.commands.teleport.teleportedBackToCoordinates");
/*     */   
/*     */   @Nonnull
/*     */   private final Deque<Waypoint> back;
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, TeleportHistory> getComponentType() {
/*  46 */     return TeleportPlugin.get().getTeleportHistoryComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Deque<Waypoint> forward;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeleportHistory() {
/*  65 */     this.back = new ArrayDeque<>();
/*  66 */     this.forward = new ArrayDeque<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forward(@Nonnull Ref<EntityStore> ref, int count) {
/*  76 */     Store<EntityStore> store = ref.getStore();
/*  77 */     go(store, ref, this.forward, this.back, count, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void back(@Nonnull Ref<EntityStore> ref, int count) {
/*  87 */     Store<EntityStore> store = ref.getStore();
/*  88 */     go(store, ref, this.back, this.forward, count, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getForwardSize() {
/*  95 */     return this.forward.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBackSize() {
/* 102 */     return this.back.size();
/*     */   }
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
/*     */   private static void go(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Deque<Waypoint> from, @Nonnull Deque<Waypoint> to, int count, boolean isForward) {
/* 121 */     if (count <= 0) throw new IllegalArgumentException(String.valueOf(count));
/*     */     
/* 123 */     PlayerRef playerRef = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 124 */     assert playerRef != null;
/*     */     
/* 126 */     Waypoint point = null;
/* 127 */     int i = 0;
/* 128 */     for (; i < count; i++) {
/* 129 */       if (from.isEmpty()) {
/* 130 */         if (point == null) {
/* 131 */           playerRef.sendMessage(MESSAGE_COMMANDS_TELEPORT_NOT_FURTHER);
/*     */           
/*     */           return;
/*     */         } 
/*     */         break;
/*     */       } 
/* 137 */       point = from.pop();
/* 138 */       to.push(point);
/*     */     } 
/*     */ 
/*     */     
/* 142 */     if (point == null) throw new NullPointerException(to.toString());
/*     */     
/* 144 */     World targetWorld = Universe.get().getWorld(point.world);
/* 145 */     if (targetWorld == null) {
/* 146 */       playerRef.sendMessage(MESSAGE_COMMANDS_TELEPORT_WORLD_NOT_LOADED);
/*     */     } else {
/* 148 */       to.push(point);
/* 149 */       store.addComponent(ref, Teleport.getComponentType(), (Component)new Teleport(targetWorld, point.position, point.rotation));
/*     */ 
/*     */       
/* 152 */       Vector3d pos = point.position;
/* 153 */       int remainingInDirection = from.size();
/* 154 */       int totalInOtherDirection = to.size() - 1;
/*     */       
/* 156 */       if (point.message != null && !point.message.isEmpty()) {
/* 157 */         playerRef.sendMessage(isForward ? MESSAGE_COMMANDS_TELEPORT_TELEPORTED_FORWARD_TO_WAYPOINT : 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 163 */             MESSAGE_COMMANDS_TELEPORT_TELEPORTED_BACK_TO_WAYPOINT.param("name", point.message).param("x", pos.getX()).param("y", pos.getY()).param("z", pos.getZ()).param("remaining", remainingInDirection).param("otherDirection", totalInOtherDirection));
/*     */       } else {
/* 165 */         playerRef.sendMessage(isForward ? MESSAGE_COMMANDS_TELEPORT_TELEPORTED_FORWARD_TO_COORDINATES : 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 170 */             MESSAGE_COMMANDS_TELEPORT_TELEPORTED_BACK_TO_COORDINATES.param("x", pos.getX()).param("y", pos.getY()).param("z", pos.getZ()).param("remaining", remainingInDirection).param("otherDirection", totalInOtherDirection));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(@Nonnull World world, @Nonnull Vector3d pos, @Nonnull Vector3f rotation, @Nonnull String key) {
/* 184 */     this.back.push(new Waypoint(world.getName(), pos, rotation, key));
/* 185 */     this.forward.clear();
/* 186 */     while (this.back.size() > 100) {
/* 187 */       this.back.removeLast();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 194 */     return "TeleportHistory{back=" + String.valueOf(this.back) + ", forward=" + String.valueOf(this.forward) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 203 */     TeleportHistory cloned = new TeleportHistory();
/* 204 */     cloned.back.addAll(this.back);
/* 205 */     cloned.forward.addAll(this.forward);
/* 206 */     return cloned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Waypoint
/*     */   {
/*     */     private final String world;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Vector3d position;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Vector3f rotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String message;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Waypoint(@Nonnull String world, @Nonnull Vector3d position, @Nonnull Vector3f rotation, @Nonnull String message) {
/* 243 */       this.world = world;
/* 244 */       this.position = position;
/* 245 */       this.rotation = rotation;
/* 246 */       this.message = message;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 252 */       return "Waypoint{world='" + this.world + "', position=" + String.valueOf(this.position) + ", rotation=" + String.valueOf(this.rotation) + ", message='" + this.message + "'}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\components\TeleportHistory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */