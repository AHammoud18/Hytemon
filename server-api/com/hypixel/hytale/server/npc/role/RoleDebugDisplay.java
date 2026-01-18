/*     */ package com.hypixel.hytale.server.npc.role;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.AnimationSlot;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ActiveAnimationComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockMembership;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.support.MarkedEntitySupport;
/*     */ import com.hypixel.hytale.server.npc.util.InventoryHelper;
/*     */ import com.hypixel.hytale.server.spawning.util.LightRangePredicate;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RoleDebugDisplay {
/*     */   protected boolean debugDisplayState;
/*     */   protected boolean debugDisplayTime;
/*     */   protected boolean debugDisplayFlock;
/*     */   protected boolean debugDisplayAnim;
/*     */   protected boolean debugDisplayLockedTarget;
/*     */   protected boolean debugDisplayLightLevel;
/*     */   protected boolean debugDisplayFreeSlots;
/*     */   @Nonnull
/*  46 */   protected StringBuilder debugDisplay = new StringBuilder(20);
/*     */   
/*     */   protected boolean debugDisplayCustom;
/*     */   protected boolean debugDisplayPathFinder;
/*     */   protected boolean debugDisplayHP;
/*     */   
/*     */   public void display(@Nonnull Role role, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  53 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, NPCEntity.getComponentType());
/*  54 */     assert npcComponent != null;
/*     */     
/*  56 */     if (this.debugDisplayInternalId) this.debugDisplay.append("ID-").append(archetypeChunk.getReferenceTo(index).getIndex()).append(" "); 
/*  57 */     if (this.debugDisplayName) this.debugDisplay.append(" Role(").append(role.getRoleName()).append(")"); 
/*  58 */     if (this.debugDisplayState) role.getStateSupport().appendStateName(this.debugDisplay);
/*     */     
/*  60 */     if (this.debugDisplayFlock) {
/*  61 */       FlockMembership flockMembershipComponent = (FlockMembership)archetypeChunk.getComponent(index, FlockMembership.getComponentType());
/*  62 */       if (flockMembershipComponent != null) {
/*  63 */         if (flockMembershipComponent.getMembershipType().isActingAsLeader()) {
/*  64 */           this.debugDisplay.append(" LDR");
/*     */         } else {
/*  66 */           this.debugDisplay.append(" FLK");
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  71 */     WorldTimeResource worldTimeResource = (WorldTimeResource)commandBuffer.getResource(WorldTimeResource.getResourceType());
/*  72 */     if (this.debugDisplayTime) {
/*  73 */       double dayProgress = (24 * worldTimeResource.getGameDateTime().get(ChronoField.SECOND_OF_DAY)) / WorldTimeResource.SECONDS_PER_DAY;
/*  74 */       this.debugDisplay.append(' ').append((int)(100.0D * dayProgress) / 100.0D);
/*     */     } 
/*     */     
/*  77 */     if (this.debugDisplayAnim) {
/*  78 */       ActiveAnimationComponent activeAnimationComponent = (ActiveAnimationComponent)archetypeChunk.getComponent(index, ActiveAnimationComponent.getComponentType());
/*  79 */       assert activeAnimationComponent != null;
/*     */       
/*  81 */       String[] activeAnimations = activeAnimationComponent.getActiveAnimations();
/*     */       
/*  83 */       MovementStates movementStates = ((MovementStatesComponent)archetypeChunk.getComponent(index, MovementStatesComponent.getComponentType())).getMovementStates();
/*  84 */       this.debugDisplay.append(" M:");
/*  85 */       this.debugDisplay.append(movementStates.idle ? 73 : 45);
/*  86 */       this.debugDisplay.append(movementStates.horizontalIdle ? 72 : 45);
/*  87 */       this.debugDisplay.append(movementStates.running ? 82 : 45);
/*  88 */       this.debugDisplay.append(movementStates.climbing ? 67 : 45);
/*  89 */       this.debugDisplay.append(movementStates.jumping ? 74 : 45);
/*  90 */       this.debugDisplay.append(movementStates.falling ? 70 : 45);
/*  91 */       this.debugDisplay.append(movementStates.crouching ? 99 : 45);
/*  92 */       this.debugDisplay.append(movementStates.flying ? 102 : 45);
/*  93 */       this.debugDisplay.append(movementStates.swimming ? 115 : 45);
/*  94 */       this.debugDisplay.append(movementStates.swimJumping ? 83 : 45);
/*  95 */       this.debugDisplay.append(movementStates.onGround ? 111 : 45);
/*  96 */       this.debugDisplay.append(movementStates.inFluid ? 119 : 45);
/*     */       
/*  98 */       String animationId = activeAnimations[AnimationSlot.Status.ordinal()];
/*  99 */       this.debugDisplay.append(" S:").append((animationId != null) ? animationId : "-");
/* 100 */       animationId = activeAnimations[AnimationSlot.Action.ordinal()];
/* 101 */       this.debugDisplay.append(" A:").append((animationId != null) ? animationId : "-");
/* 102 */       animationId = activeAnimations[AnimationSlot.Face.ordinal()];
/* 103 */       this.debugDisplay.append(" F:").append((animationId != null) ? animationId : "-");
/*     */     } 
/*     */     
/* 106 */     if (this.debugDisplayLockedTarget) {
/* 107 */       MarkedEntitySupport markedEntitySupport = role.getMarkedEntitySupport();
/* 108 */       int targetSlotCount = markedEntitySupport.getMarkedEntitySlotCount();
/*     */       
/* 110 */       for (int i = 0; i < targetSlotCount; i++) {
/* 111 */         String slotName = markedEntitySupport.getSlotName(i);
/* 112 */         Ref<EntityStore> targetRef = markedEntitySupport.getMarkedEntityRef(i);
/*     */         
/* 114 */         if (targetRef == null) {
/* 115 */           this.debugDisplay.append(" T(").append(slotName).append("):-");
/*     */         } else {
/* 117 */           PlayerRef targetPlayerRefComponent = (PlayerRef)commandBuffer.getComponent(targetRef, PlayerRef.getComponentType());
/* 118 */           NPCEntity targetNpcComponent = (NPCEntity)commandBuffer.getComponent(targetRef, NPCEntity.getComponentType());
/*     */           
/* 120 */           if (targetPlayerRefComponent != null) {
/* 121 */             this.debugDisplay.append(" TP(").append(slotName).append("):").append(targetPlayerRefComponent.getUsername());
/* 122 */           } else if (targetNpcComponent != null) {
/* 123 */             String roleName = targetNpcComponent.getRoleName();
/* 124 */             if (roleName == null || roleName.isEmpty()) roleName = "???"; 
/* 125 */             this.debugDisplay.append(" T(").append(slotName).append("):").append(roleName);
/*     */           } else {
/* 127 */             this.debugDisplay.append(" T(").append(slotName).append("):?");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     if (this.debugDisplayLightLevel) {
/* 134 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 135 */       assert transformComponent != null;
/*     */       
/* 137 */       Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/* 138 */       if (chunkRef != null && chunkRef.isValid()) {
/* 139 */         World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 140 */         Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */         
/* 142 */         BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 143 */         assert blockChunkComponent != null;
/*     */         
/* 145 */         Vector3d position = transformComponent.getPosition();
/* 146 */         int x = MathUtil.floor(position.getX());
/* 147 */         int y = MathUtil.floor(position.getY());
/* 148 */         int z = MathUtil.floor(position.getZ());
/* 149 */         double sunlightFactor = worldTimeResource.getSunlightFactor();
/* 150 */         this.debugDisplay.append(" LL:").append(LightRangePredicate.lightToPrecentage(LightRangePredicate.calculateLightValue(blockChunkComponent, x, y, z, sunlightFactor)))
/* 151 */           .append('/').append(LightRangePredicate.lightToPrecentage(blockChunkComponent.getSkyLight(x, y, z)))
/* 152 */           .append('/').append(LightRangePredicate.lightToPrecentage((byte)(int)(blockChunkComponent.getSkyLight(x, y, z) * sunlightFactor)))
/* 153 */           .append('/').append(LightRangePredicate.lightToPrecentage(blockChunkComponent.getRedBlockLight(x, y, z)))
/* 154 */           .append('/').append(LightRangePredicate.lightToPrecentage(blockChunkComponent.getGreenBlockLight(x, y, z)))
/* 155 */           .append('/').append(LightRangePredicate.lightToPrecentage(blockChunkComponent.getBlueBlockLight(x, y, z)));
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     String displayPathfinderString = role.getDebugSupport().pollDisplayPathfinderString();
/* 160 */     if (this.debugDisplayPathFinder && displayPathfinderString != null && !displayPathfinderString.isEmpty()) {
/* 161 */       this.debugDisplay.append(!this.debugDisplay.isEmpty() ? " PF:" : "PF:").append(displayPathfinderString);
/*     */     }
/*     */     
/* 164 */     String customString = role.getDebugSupport().pollDisplayCustomString();
/* 165 */     if (this.debugDisplayCustom && customString != null && !customString.isEmpty()) {
/* 166 */       if (!this.debugDisplay.isEmpty()) this.debugDisplay.append(' '); 
/* 167 */       this.debugDisplay.append(customString);
/*     */     } 
/*     */     
/* 170 */     if (this.debugDisplayFreeSlots) {
/* 171 */       Inventory inventory = npcComponent.getInventory();
/* 172 */       int hotbarFreeSlots = InventoryHelper.countFreeSlots(inventory.getHotbar());
/* 173 */       int inventoryFreeSlots = InventoryHelper.countFreeSlots(inventory.getStorage());
/* 174 */       this.debugDisplay.append(" FS:").append(hotbarFreeSlots).append('/').append(inventoryFreeSlots);
/*     */     } 
/*     */     
/* 177 */     if (this.debugDisplayHP) {
/* 178 */       EntityStatMap entityStatsComponent = (EntityStatMap)archetypeChunk.getComponent(index, EntityStatMap.getComponentType());
/* 179 */       assert entityStatsComponent != null;
/*     */       
/* 181 */       EntityStatValue healthValue = entityStatsComponent.get(DefaultEntityStatTypes.getHealth());
/* 182 */       if (healthValue == null) {
/* 183 */         this.debugDisplay.append(" HP: N/A");
/*     */       } else {
/* 185 */         this.debugDisplay.append(" HP:").append(healthValue.get()).append('/').append(healthValue.getMax());
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     if (this.debugDisplayStamina) {
/* 190 */       EntityStatMap entityStatsComponent = (EntityStatMap)archetypeChunk.getComponent(index, EntityStatMap.getComponentType());
/* 191 */       assert entityStatsComponent != null;
/*     */       
/* 193 */       EntityStatValue staminaValue = entityStatsComponent.get(DefaultEntityStatTypes.getStamina());
/* 194 */       if (staminaValue == null) {
/* 195 */         this.debugDisplay.append(" Stamina: N/A");
/*     */       } else {
/* 197 */         this.debugDisplay.append(" Stamina:").append(staminaValue.get()).append('/').append(staminaValue.getMax());
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     if (this.debugDisplaySpeed) {
/* 202 */       Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/* 203 */       assert velocityComponent != null;
/* 204 */       this.debugDisplay.append(" SPD:").append(MathUtil.round(velocityComponent.getSpeed(), 1));
/*     */     } 
/*     */     
/* 207 */     if (!this.debugDisplay.isEmpty()) {
/*     */       
/* 209 */       Nameplate nameplateComponent = (Nameplate)archetypeChunk.getComponent(index, Nameplate.getComponentType());
/* 210 */       if (nameplateComponent != null) {
/* 211 */         nameplateComponent.setText(this.debugDisplay.toString());
/*     */       } else {
/* 213 */         Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 214 */         commandBuffer.addComponent(ref, Nameplate.getComponentType(), (Component)new Nameplate(this.debugDisplay.toString()));
/*     */       } 
/* 216 */       this.debugDisplay.setLength(0);
/*     */     } 
/*     */   }
/*     */   protected boolean debugDisplayStamina; protected boolean debugDisplaySpeed; protected boolean debugDisplayInternalId; protected boolean debugDisplayName;
/*     */   @Nullable
/*     */   public static RoleDebugDisplay create(@Nonnull EnumSet<RoleDebugFlags> debugFlags) {
/* 222 */     boolean debugDisplayState = debugFlags.contains(RoleDebugFlags.DisplayState);
/* 223 */     boolean debugDisplayTime = debugFlags.contains(RoleDebugFlags.DisplayTime);
/* 224 */     boolean debugDisplayFlock = debugFlags.contains(RoleDebugFlags.DisplayFlock);
/* 225 */     boolean debugDisplayAnim = debugFlags.contains(RoleDebugFlags.DisplayAnim);
/* 226 */     boolean debugDisplayLockedTarget = debugFlags.contains(RoleDebugFlags.DisplayTarget);
/* 227 */     boolean debugDisplayLightLevel = debugFlags.contains(RoleDebugFlags.DisplayLightLevel);
/* 228 */     boolean debugDisplayCustom = debugFlags.contains(RoleDebugFlags.DisplayCustom);
/* 229 */     boolean debugDisplayFreeSlots = debugFlags.contains(RoleDebugFlags.DisplayFreeSlots);
/* 230 */     boolean debugDisplayPathFinder = debugFlags.contains(RoleDebugFlags.Pathfinder);
/* 231 */     boolean debugDisplayHP = debugFlags.contains(RoleDebugFlags.DisplayHP);
/* 232 */     boolean debugDisplayStamina = debugFlags.contains(RoleDebugFlags.DisplayStamina);
/* 233 */     boolean debugDisplaySpeed = debugFlags.contains(RoleDebugFlags.DisplaySpeed);
/* 234 */     boolean debugDisplayName = debugFlags.contains(RoleDebugFlags.DisplayName);
/* 235 */     boolean debugDisplayInternalId = debugFlags.contains(RoleDebugFlags.DisplayInternalId);
/* 236 */     if (!debugDisplayInternalId && !debugDisplayState && !debugDisplayFlock && !debugDisplayTime && !debugDisplayAnim && !debugDisplayLockedTarget && !debugDisplayLightLevel && !debugDisplayCustom && !debugDisplayFreeSlots && !debugDisplayPathFinder && !debugDisplayHP && !debugDisplaySpeed && !debugDisplayName && !debugDisplayStamina)
/*     */     {
/*     */       
/* 239 */       return null;
/*     */     }
/*     */     
/* 242 */     RoleDebugDisplay debugDisplay = new RoleDebugDisplay();
/* 243 */     debugDisplay.debugDisplayState = debugDisplayState;
/* 244 */     debugDisplay.debugDisplayTime = debugDisplayTime;
/* 245 */     debugDisplay.debugDisplayFlock = debugDisplayFlock;
/* 246 */     debugDisplay.debugDisplayAnim = debugDisplayAnim;
/* 247 */     debugDisplay.debugDisplayLockedTarget = debugDisplayLockedTarget;
/* 248 */     debugDisplay.debugDisplayLightLevel = debugDisplayLightLevel;
/* 249 */     debugDisplay.debugDisplayCustom = debugDisplayCustom;
/* 250 */     debugDisplay.debugDisplayFreeSlots = debugDisplayFreeSlots;
/* 251 */     debugDisplay.debugDisplayPathFinder = debugDisplayPathFinder;
/* 252 */     debugDisplay.debugDisplayHP = debugDisplayHP;
/* 253 */     debugDisplay.debugDisplayStamina = debugDisplayStamina;
/* 254 */     debugDisplay.debugDisplaySpeed = debugDisplaySpeed;
/* 255 */     debugDisplay.debugDisplayInternalId = debugDisplayInternalId;
/* 256 */     debugDisplay.debugDisplayName = debugDisplayName;
/* 257 */     return debugDisplay;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\RoleDebugDisplay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */