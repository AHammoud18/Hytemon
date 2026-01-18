/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.server.core.meta.DynamicMetaStore;
/*     */ import com.hypixel.hytale.server.core.meta.IMetaRegistry;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InteractionEntry {
/*  16 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private final int index;
/*     */   
/*     */   @Nonnull
/*     */   private final DynamicMetaStore<Interaction> metaStore;
/*     */   private long timestamp;
/*     */   private long simulationTimestamp;
/*  24 */   private final InteractionSyncData serverState = new InteractionSyncData();
/*     */   
/*     */   private InteractionSyncData simulationState;
/*     */   
/*     */   @Nullable
/*     */   private InteractionSyncData clientState;
/*     */   
/*     */   private long waitingForSyncData;
/*     */   private long waitingForServerFinished;
/*     */   private long waitingForClientFinished;
/*     */   private boolean useSimulationState;
/*     */   private boolean desynced;
/*     */   private boolean shouldSendInitial = true;
/*     */   
/*     */   public InteractionEntry(int index, int counter, int rootInteraction) {
/*  39 */     this.index = index;
/*     */     
/*  41 */     this.metaStore = new DynamicMetaStore(null, (IMetaRegistry)Interaction.META_REGISTRY);
/*  42 */     this.serverState.operationCounter = counter;
/*  43 */     this.serverState.rootInteraction = rootInteraction;
/*  44 */     this.serverState.state = InteractionState.NotFinished;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  48 */     return this.index;
/*     */   }
/*     */   
/*     */   public int nextForkId() {
/*  52 */     return this.serverState.totalForks++;
/*     */   }
/*     */   
/*     */   public int getNextForkId() {
/*  56 */     return this.serverState.totalForks;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public InteractionSyncData getState() {
/*  61 */     return this.useSimulationState ? getSimulationState() : getServerState();
/*     */   }
/*     */   
/*     */   public void setUseSimulationState(boolean useSimulationState) {
/*  65 */     this.useSimulationState = useSimulationState;
/*     */   }
/*     */   
/*     */   public float getTimeInSeconds(long tickTime) {
/*  69 */     long timestamp = getTimestamp();
/*  70 */     if (timestamp == 0L) return 0.0F; 
/*  71 */     long diff = tickTime - timestamp;
/*  72 */     return (float)diff / 1.0E9F;
/*     */   }
/*     */   
/*     */   public void setTimestamp(long timestamp, float shift) {
/*  76 */     timestamp -= (long)(shift * 1.0E9F);
/*  77 */     if (this.useSimulationState) {
/*  78 */       this.simulationTimestamp = timestamp;
/*     */       return;
/*     */     } 
/*  81 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   public long getTimestamp() {
/*  85 */     return this.useSimulationState ? this.simulationTimestamp : this.timestamp;
/*     */   }
/*     */   
/*     */   public boolean isUseSimulationState() {
/*  89 */     return this.useSimulationState;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InteractionSyncData getClientState() {
/*  94 */     return this.clientState;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DynamicMetaStore<Interaction> getMetaStore() {
/*  99 */     return this.metaStore;
/*     */   }
/*     */   
/*     */   public int getServerDataHashCode() {
/* 103 */     InteractionSyncData serverData = getState();
/* 104 */     float progress = serverData.progress;
/* 105 */     serverData.progress = (int)progress;
/* 106 */     int hashCode = serverData.hashCode();
/* 107 */     serverData.progress = progress;
/* 108 */     return hashCode;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public InteractionSyncData getServerState() {
/* 113 */     return this.serverState;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public InteractionSyncData getSimulationState() {
/* 118 */     if (this.simulationState == null) {
/* 119 */       this.simulationState = new InteractionSyncData();
/* 120 */       this.simulationState.operationCounter = this.serverState.operationCounter;
/* 121 */       this.simulationState.rootInteraction = this.serverState.rootInteraction;
/* 122 */       this.simulationState.state = InteractionState.NotFinished;
/*     */     } 
/* 124 */     return this.simulationState;
/*     */   }
/*     */   
/*     */   public boolean setClientState(@Nullable InteractionSyncData clientState) {
/* 128 */     if (clientState != null && (clientState.operationCounter != this.serverState.operationCounter || clientState.rootInteraction != this.serverState.rootInteraction)) {
/* 129 */       HytaleLogger.Api ctx = LOGGER.at(Level.WARNING);
/* 130 */       if (ctx.isEnabled()) {
/* 131 */         String info; RootInteraction root = (RootInteraction)RootInteraction.getAssetMap().getAsset(this.serverState.rootInteraction);
/* 132 */         Operation op = root.getOperation(this.serverState.operationCounter);
/*     */         
/* 134 */         Operation innerOp = op.getInnerOperation();
/* 135 */         if (innerOp instanceof Interaction) { Interaction interaction = (Interaction)innerOp;
/* 136 */           info = interaction.getId() + " (" + interaction.getId() + ")"; }
/*     */         else
/* 138 */         { info = String.valueOf(op) + " (" + String.valueOf(op) + ")"; }
/*     */         
/* 140 */         ctx.log("%d: Client/Server desync %d != %d, %d != %d (for %s)", 
/* 141 */             Integer.valueOf(this.index), Integer.valueOf(this.serverState.operationCounter), 
/* 142 */             Integer.valueOf(clientState.operationCounter), Integer.valueOf(this.serverState.rootInteraction), 
/* 143 */             Integer.valueOf(clientState.rootInteraction), info);
/*     */       } 
/* 145 */       return false;
/*     */     } 
/* 147 */     this.clientState = clientState;
/* 148 */     return true;
/*     */   }
/*     */   
/*     */   public long getWaitingForSyncData() {
/* 152 */     return this.waitingForSyncData;
/*     */   }
/*     */   
/*     */   public void setWaitingForSyncData(long waitingForSyncData) {
/* 156 */     this.waitingForSyncData = waitingForSyncData;
/*     */   }
/*     */   
/*     */   public long getWaitingForServerFinished() {
/* 160 */     return this.waitingForServerFinished;
/*     */   }
/*     */   
/*     */   public void setWaitingForServerFinished(long waitingForServerFinished) {
/* 164 */     this.waitingForServerFinished = waitingForServerFinished;
/*     */   }
/*     */   
/*     */   public long getWaitingForClientFinished() {
/* 168 */     return this.waitingForClientFinished;
/*     */   }
/*     */   
/*     */   public void setWaitingForClientFinished(long waitingForClientFinished) {
/* 172 */     this.waitingForClientFinished = waitingForClientFinished;
/*     */   }
/*     */   
/*     */   public boolean consumeDesyncFlag() {
/* 176 */     boolean flag = this.desynced;
/* 177 */     this.desynced = false;
/* 178 */     return flag;
/*     */   }
/*     */   
/*     */   public void flagDesync() {
/* 182 */     this.desynced = true;
/*     */   }
/*     */   
/*     */   public boolean consumeSendInitial() {
/* 186 */     boolean flag = this.shouldSendInitial;
/* 187 */     this.shouldSendInitial = false;
/* 188 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 194 */     return "InteractionEntry{index=" + this.index + ", metaStore=" + String.valueOf(this.metaStore) + ", timestamp=" + this.timestamp + ", getTimeInSeconds()=" + 
/*     */ 
/*     */ 
/*     */       
/* 198 */       getTimeInSeconds(System.nanoTime()) + ", simulationTimestamp=" + this.simulationTimestamp + ", serverState=" + String.valueOf(this.serverState) + ", simulationState=" + String.valueOf(this.simulationState) + ", clientState=" + String.valueOf(this.clientState) + ", waitingForSyncData=" + this.waitingForSyncData + ", waitingForServerFinished=" + this.waitingForServerFinished + ", waitingForClientFinished=" + this.waitingForClientFinished + ", useSimulationState=" + this.useSimulationState + ", desynced=" + this.desynced + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\InteractionEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */