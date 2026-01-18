/*     */ package com.hypixel.hytale.server.npc.role.support;
/*     */ 
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugDisplay;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import com.hypixel.hytale.server.npc.role.builders.BuilderRole;
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class DebugSupport
/*     */ {
/*     */   protected final NPCEntity parent;
/*     */   @Nullable
/*     */   protected RoleDebugDisplay debugDisplay;
/*     */   protected boolean debugRoleSteering;
/*     */   protected boolean debugMotionSteering;
/*     */   protected EnumSet<RoleDebugFlags> debugFlags;
/*     */   @Nullable
/*     */   protected String displayCustomString;
/*     */   @Nullable
/*     */   protected String displayPathfinderString;
/*     */   protected boolean traceSuccess;
/*     */   protected boolean traceFail;
/*     */   protected boolean traceSensorFails;
/*     */   protected Sensor lastFailingSensor;
/*     */   
/*     */   public DebugSupport(NPCEntity parent, @Nonnull BuilderRole builder) {
/*  40 */     this.parent = parent;
/*  41 */     this.debugFlags = builder.getDebugFlags();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RoleDebugDisplay getDebugDisplay() {
/*  46 */     return this.debugDisplay;
/*     */   }
/*     */   
/*     */   public boolean isTraceSuccess() {
/*  50 */     return this.traceSuccess;
/*     */   }
/*     */   
/*     */   public boolean isTraceFail() {
/*  54 */     return this.traceFail;
/*     */   }
/*     */   
/*     */   public boolean isTraceSensorFails() {
/*  58 */     return this.traceSensorFails;
/*     */   }
/*     */   
/*     */   public void setLastFailingSensor(Sensor sensor) {
/*  62 */     this.lastFailingSensor = sensor;
/*     */   }
/*     */   
/*     */   public Sensor getLastFailingSensor() {
/*  66 */     return this.lastFailingSensor;
/*     */   }
/*     */   
/*     */   public boolean isDebugRoleSteering() {
/*  70 */     return this.debugRoleSteering;
/*     */   }
/*     */   
/*     */   public boolean isDebugMotionSteering() {
/*  74 */     return this.debugMotionSteering;
/*     */   }
/*     */   
/*     */   public void setDisplayCustomString(String displayCustomString) {
/*  78 */     this.displayCustomString = displayCustomString;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String pollDisplayCustomString() {
/*  83 */     String ret = this.displayCustomString;
/*  84 */     this.displayCustomString = null;
/*  85 */     return ret;
/*     */   }
/*     */   
/*     */   public void setDisplayPathfinderString(String displayPathfinderString) {
/*  89 */     this.displayPathfinderString = displayPathfinderString;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String pollDisplayPathfinderString() {
/*  94 */     String ret = this.displayPathfinderString;
/*  95 */     this.displayPathfinderString = null;
/*  96 */     return ret;
/*     */   }
/*     */   
/*     */   public EnumSet<RoleDebugFlags> getDebugFlags() {
/* 100 */     return this.debugFlags;
/*     */   }
/*     */   
/*     */   public void setDebugFlags(EnumSet<RoleDebugFlags> debugFlags) {
/* 104 */     this.debugFlags = debugFlags;
/* 105 */     activate();
/*     */   }
/*     */   
/*     */   public boolean isDebugFlagSet(RoleDebugFlags flag) {
/* 109 */     return this.debugFlags.contains(flag);
/*     */   }
/*     */   
/*     */   public boolean isAnyDebugFlagSet(@Nonnull EnumSet<RoleDebugFlags> flags) {
/* 113 */     for (RoleDebugFlags d : flags) {
/* 114 */       if (this.debugFlags.contains(d)) return true; 
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   public void activate() {
/* 120 */     this.debugRoleSteering = isDebugFlagSet(RoleDebugFlags.SteeringRole);
/* 121 */     this.debugMotionSteering = isDebugFlagSet(RoleDebugFlags.MotionControllerSteer);
/* 122 */     this.traceFail = isDebugFlagSet(RoleDebugFlags.TraceFail);
/* 123 */     this.traceSuccess = isDebugFlagSet(RoleDebugFlags.TraceSuccess);
/* 124 */     this.traceSensorFails = isDebugFlagSet(RoleDebugFlags.TraceSensorFailures);
/* 125 */     this.debugDisplay = RoleDebugDisplay.create(this.debugFlags);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\DebugSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */