/*     */ package com.hypixel.hytale.builtin.mounts;
/*     */ import com.hypixel.hytale.builtin.mounts.commands.MountCommand;
/*     */ import com.hypixel.hytale.builtin.mounts.interactions.MountInteraction;
/*     */ import com.hypixel.hytale.builtin.mounts.interactions.SeatingInteraction;
/*     */ import com.hypixel.hytale.builtin.mounts.interactions.SpawnMinecartInteraction;
/*     */ import com.hypixel.hytale.builtin.mounts.minecart.MinecartComponent;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.interaction.DismountNPC;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
/*     */ import com.hypixel.hytale.server.core.io.ServerManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.systems.RoleChangeSystem;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class MountPlugin extends JavaPlugin {
/*     */   public static MountPlugin getInstance() {
/*  35 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private static MountPlugin instance;
/*     */   private ComponentType<ChunkStore, BlockMountComponent> blockMountComponentType;
/*     */   private ComponentType<EntityStore, NPCMountComponent> mountComponentType;
/*     */   
/*     */   public MountPlugin(@Nonnull JavaPluginInit init) {
/*  44 */     super(init);
/*     */   }
/*     */   private ComponentType<EntityStore, MountedComponent> mountedComponentType; private ComponentType<EntityStore, MountedByComponent> mountedByComponentType; private ComponentType<EntityStore, MinecartComponent> minecartComponentType;
/*     */   public ComponentType<EntityStore, NPCMountComponent> getMountComponentType() {
/*  48 */     return this.mountComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, MountedComponent> getMountedComponentType() {
/*  52 */     return this.mountedComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, MountedByComponent> getMountedByComponentType() {
/*  56 */     return this.mountedByComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, MinecartComponent> getMinecartComponentType() {
/*  60 */     return this.minecartComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  65 */     instance = this;
/*     */     
/*  67 */     this.blockMountComponentType = getChunkStoreRegistry().registerComponent(BlockMountComponent.class, BlockMountComponent::new);
/*     */     
/*  69 */     NPCPlugin.get().registerCoreComponentType("Mount", com.hypixel.hytale.builtin.mounts.npc.builders.BuilderActionMount::new);
/*     */     
/*  71 */     this.mountComponentType = getEntityStoreRegistry().registerComponent(NPCMountComponent.class, "Mount", NPCMountComponent.CODEC);
/*  72 */     this.mountedComponentType = getEntityStoreRegistry().registerComponent(MountedComponent.class, () -> {
/*     */           throw new UnsupportedOperationException("Mounted component cannot be default constructed");
/*     */         });
/*  75 */     this.mountedByComponentType = getEntityStoreRegistry().registerComponent(MountedByComponent.class, MountedByComponent::new);
/*  76 */     this.minecartComponentType = getEntityStoreRegistry().registerComponent(MinecartComponent.class, "Minecart", MinecartComponent.CODEC);
/*     */     
/*  78 */     getEntityStoreRegistry().registerSystem((ISystem)new NPCMountSystems.OnAdd(this.mountComponentType));
/*  79 */     getEntityStoreRegistry().registerSystem((ISystem)new NPCMountSystems.DismountOnPlayerDeath());
/*  80 */     getEntityStoreRegistry().registerSystem((ISystem)new NPCMountSystems.DismountOnMountDeath());
/*     */     
/*  82 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.TrackerUpdate());
/*  83 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.TrackerRemove());
/*  84 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.RemoveMountedBy());
/*  85 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.RemoveMounted());
/*  86 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.TeleportMountedEntity());
/*  87 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.MountedEntityDeath());
/*  88 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.PlayerMount());
/*  89 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.HandleMountInput());
/*  90 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.TrackedMounted());
/*  91 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.EnsureMinecartComponents());
/*  92 */     getEntityStoreRegistry().registerSystem((ISystem)new MountSystems.OnMinecartHit());
/*     */     
/*  94 */     getChunkStoreRegistry().registerSystem((ISystem)new MountSystems.RemoveBlockSeat());
/*     */     
/*  96 */     ServerManager.get().registerSubPacketHandlers(MountGamePacketHandler::new);
/*     */     
/*  98 */     getEventRegistry().register(PlayerDisconnectEvent.class, MountPlugin::onPlayerDisconnect);
/*     */     
/* 100 */     getCommandRegistry().registerCommand((AbstractCommand)new MountCommand());
/*     */     
/* 102 */     Interaction.CODEC.register("SpawnMinecart", SpawnMinecartInteraction.class, SpawnMinecartInteraction.CODEC);
/* 103 */     Interaction.CODEC.register("Mount", MountInteraction.class, MountInteraction.CODEC);
/* 104 */     Interaction.CODEC.register("Seating", SeatingInteraction.class, SeatingInteraction.CODEC);
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockMountComponent> getBlockMountComponentType() {
/* 108 */     return this.blockMountComponentType;
/*     */   }
/*     */   
/*     */   private static void onPlayerDisconnect(@Nonnull PlayerDisconnectEvent event) {
/* 112 */     PlayerRef playerRef = event.getPlayerRef();
/* 113 */     Ref<EntityStore> ref = playerRef.getReference();
/* 114 */     if (ref == null)
/*     */       return; 
/* 116 */     Store<EntityStore> store = ref.getStore();
/* 117 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 118 */     world.execute(() -> {
/*     */           if (!ref.isValid())
/*     */             return; 
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (playerComponent == null)
/*     */             return; 
/*     */           checkDismountNpc((ComponentAccessor<EntityStore>)store, playerComponent);
/*     */         });
/*     */   }
/*     */   public static void checkDismountNpc(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Player playerComponent) {
/* 128 */     int mountEntityId = playerComponent.getMountEntityId();
/* 129 */     if (mountEntityId == 0)
/*     */       return; 
/* 131 */     dismountNpc(store, mountEntityId);
/*     */   }
/*     */   
/*     */   public static void dismountNpc(@Nonnull ComponentAccessor<EntityStore> store, int mountEntityId) {
/* 135 */     Ref<EntityStore> entityReference = ((EntityStore)store.getExternalData()).getRefFromNetworkId(mountEntityId);
/* 136 */     if (entityReference == null || !entityReference.isValid())
/*     */       return; 
/* 138 */     NPCMountComponent mountComponent = (NPCMountComponent)store.getComponent(entityReference, NPCMountComponent.getComponentType());
/* 139 */     assert mountComponent != null;
/*     */     
/* 141 */     resetOriginalMountRole(entityReference, store, mountComponent);
/*     */     
/* 143 */     PlayerRef ownerPlayerRef = mountComponent.getOwnerPlayerRef();
/* 144 */     if (ownerPlayerRef != null) {
/* 145 */       resetOriginalPlayerMovementSettings(ownerPlayerRef, store);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void resetOriginalMountRole(@Nonnull Ref<EntityStore> entityReference, @Nonnull ComponentAccessor<EntityStore> store, @Nonnull NPCMountComponent mountComponent) {
/* 150 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(entityReference, NPCEntity.getComponentType());
/* 151 */     assert npcComponent != null;
/*     */     
/* 153 */     RoleChangeSystem.requestRoleChange(entityReference, npcComponent.getRole(), mountComponent.getOriginalRoleIndex(), false, "Idle", null, store);
/* 154 */     store.removeComponent(entityReference, NPCMountComponent.getComponentType());
/*     */   }
/*     */   
/*     */   public static void resetOriginalPlayerMovementSettings(@Nonnull PlayerRef playerRef, @Nonnull ComponentAccessor<EntityStore> store) {
/* 158 */     Ref<EntityStore> reference = playerRef.getReference();
/* 159 */     if (reference == null)
/*     */       return; 
/* 161 */     playerRef.getPacketHandler().write((Packet)new DismountNPC());
/*     */     
/* 163 */     MovementManager movementManagerComponent = (MovementManager)store.getComponent(reference, MovementManager.getComponentType());
/* 164 */     assert movementManagerComponent != null;
/*     */     
/* 166 */     movementManagerComponent.resetDefaultsAndUpdate(reference, store);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\MountPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */