/*     */ package com.hypixel.hytale.builtin.buildertools;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.protocol.ModelTransform;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolAction;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolEntityAction;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolExtrudeAction;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolGeneralAction;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolLineAction;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolPasteClipboard;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolRotateClipboard;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolSelectionTransform;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolSelectionUpdate;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolSetEntityLight;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolSetEntityPickupEnabled;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolSetEntityScale;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolSetNPCDebug;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolStackArea;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.EntityToolAction;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BuilderTool;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PropComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.Interactions;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BuilderToolsPacketHandler implements SubPacketHandler {
/*  47 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private final IPacketHandler packetHandler;
/*     */   
/*     */   public BuilderToolsPacketHandler(IPacketHandler packetHandler) {
/*  52 */     this.packetHandler = packetHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerHandlers() {
/*  57 */     if (BuilderToolsPlugin.get().isDisabled()) {
/*  58 */       this.packetHandler.registerNoOpHandlers(new int[] { 400, 401, 412, 409, 403, 406, 407, 413, 414, 417 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.packetHandler.registerHandler(106, p -> handle((LoadHotbar)p));
/*  74 */     this.packetHandler.registerHandler(107, p -> handle((SaveHotbar)p));
/*  75 */     this.packetHandler.registerHandler(400, p -> handle((BuilderToolArgUpdate)p));
/*  76 */     this.packetHandler.registerHandler(401, p -> handle((BuilderToolEntityAction)p));
/*  77 */     this.packetHandler.registerHandler(412, p -> handle((BuilderToolGeneralAction)p));
/*  78 */     this.packetHandler.registerHandler(409, p -> handle((BuilderToolSelectionUpdate)p));
/*  79 */     this.packetHandler.registerHandler(403, p -> handle((BuilderToolExtrudeAction)p));
/*  80 */     this.packetHandler.registerHandler(406, p -> handle((BuilderToolRotateClipboard)p));
/*  81 */     this.packetHandler.registerHandler(407, p -> handle((BuilderToolPasteClipboard)p));
/*  82 */     this.packetHandler.registerHandler(413, p -> handle((BuilderToolOnUseInteraction)p));
/*  83 */     this.packetHandler.registerHandler(410, p -> handle((BuilderToolSelectionToolAskForClipboard)p));
/*  84 */     this.packetHandler.registerHandler(414, p -> handle((BuilderToolLineAction)p));
/*  85 */     this.packetHandler.registerHandler(402, p -> handle((BuilderToolSetEntityTransform)p));
/*  86 */     this.packetHandler.registerHandler(420, p -> handle((BuilderToolSetEntityScale)p));
/*  87 */     this.packetHandler.registerHandler(405, p -> handle((BuilderToolSelectionTransform)p));
/*  88 */     this.packetHandler.registerHandler(404, p -> handle((BuilderToolStackArea)p));
/*  89 */     this.packetHandler.registerHandler(408, p -> handle((BuilderToolSetTransformationModeState)p));
/*  90 */     this.packetHandler.registerHandler(417, p -> handle((PrefabUnselectPrefab)p));
/*  91 */     this.packetHandler.registerHandler(421, p -> handle((BuilderToolSetEntityPickupEnabled)p));
/*  92 */     this.packetHandler.registerHandler(422, p -> handle((BuilderToolSetEntityLight)p));
/*  93 */     this.packetHandler.registerHandler(423, p -> handle((BuilderToolSetNPCDebug)p));
/*     */   }
/*     */   
/*     */   static boolean hasPermission(@Nonnull Player player) {
/*  97 */     if (!player.hasPermission("hytale.editor.builderTools")) {
/*  98 */       player.sendMessage(Message.translation("server.builderTools.usageDenied"));
/*  99 */       return false;
/*     */     } 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean hasPermission(@Nonnull Player player, @Nonnull String permission) {
/* 107 */     if (player.hasPermission(permission) || player.hasPermission("hytale.editor.builderTools")) {
/* 108 */       return true;
/*     */     }
/* 110 */     player.sendMessage(Message.translation("server.builderTools.usageDenied"));
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolSetTransformationModeState packet) {
/* 115 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 116 */     Ref<EntityStore> ref = playerRef.getReference();
/* 117 */     if (ref == null || !ref.isValid()) {
/* 118 */       throw new RuntimeException("Unable to process BuilderToolSetTransformationModeState packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 121 */     Store<EntityStore> store = ref.getStore();
/* 122 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 124 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent))
/*     */             return; 
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           ToolOperation.getOrCreatePrototypeSettings(playerRef.getUuid()).setInSelectionTransformationMode(packet.enabled);
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolArgUpdate packet) {
/* 134 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 135 */     Ref<EntityStore> ref = playerRef.getReference();
/* 136 */     if (ref == null || !ref.isValid()) {
/* 137 */       throw new RuntimeException("Unable to process BuilderToolArgUpdate packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 140 */     Store<EntityStore> store = ref.getStore();
/* 141 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 143 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.brush.config"))
/*     */             return; 
/*     */           BuilderToolsPlugin.get().onToolArgUpdate(playerRef, playerComponent, packet);
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull LoadHotbar packet) {
/* 152 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 153 */     Ref<EntityStore> ref = playerRef.getReference();
/* 154 */     if (ref == null || !ref.isValid()) {
/* 155 */       throw new RuntimeException("Unable to process LoadHotbar packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 158 */     Store<EntityStore> store = ref.getStore();
/* 159 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 161 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           playerComponent.getHotbarManager().loadHotbar(ref, (short)packet.inventoryRow, (ComponentAccessor)store);
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull SaveHotbar packet) {
/* 169 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 170 */     Ref<EntityStore> ref = playerRef.getReference();
/* 171 */     if (ref == null || !ref.isValid()) {
/* 172 */       throw new RuntimeException("Unable to process SaveHotbar packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 175 */     Store<EntityStore> store = ref.getStore();
/* 176 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 178 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           playerComponent.getHotbarManager().saveHotbar(ref, (short)packet.inventoryRow, (ComponentAccessor)store);
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolEntityAction packet) {
/* 186 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 187 */     Ref<EntityStore> ref = playerRef.getReference();
/* 188 */     if (ref == null || !ref.isValid()) {
/* 189 */       throw new RuntimeException("Unable to process BuilderToolEntityAction packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 192 */     Store<EntityStore> store = ref.getStore();
/* 193 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 195 */     world.execute(() -> {
/*     */           UUIDComponent uuidComponent;
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent)) {
/*     */             return;
/*     */           }
/*     */           int entityId = packet.entityId;
/*     */           Ref<EntityStore> entityReference = world.getEntityStore().getRefFromNetworkId(entityId);
/*     */           if (entityReference == null) {
/*     */             playerComponent.sendMessage(Message.translation("server.general.entityNotFound").param("id", entityId));
/*     */             return;
/*     */           } 
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           switch (packet.action) {
/*     */             case HistoryUndo:
/*     */               uuidComponent = (UUIDComponent)store.getComponent(entityReference, UUIDComponent.getComponentType());
/*     */               if (uuidComponent != null)
/*     */                 CommandManager.get().handleCommand((CommandSender)playerComponent, "npc freeze --toggle --entity " + String.valueOf(uuidComponent.getUuid())); 
/*     */               break;
/*     */             case HistoryRedo:
/*     */               world.execute(());
/*     */               break;
/*     */             case SelectionCopy:
/*     */               world.execute(());
/*     */               break;
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolGeneralAction packet) {
/* 225 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 226 */     Ref<EntityStore> ref = playerRef.getReference();
/* 227 */     if (ref == null || !ref.isValid()) {
/* 228 */       throw new RuntimeException("Unable to process BuilderToolGeneralAction packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 231 */     Store<EntityStore> store = ref.getStore();
/* 232 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 234 */     world.execute(() -> {
/*     */           TransformComponent transformComponent;
/*     */           BuilderToolsPlugin.BuilderState builderState;
/*     */           Vector3d position;
/*     */           Vector3i intTriple;
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           switch (packet.action) {
/*     */             case HistoryUndo:
/*     */               if (!hasPermission(playerComponent, "hytale.editor.history"))
/*     */                 return; 
/*     */               BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */               break;
/*     */             case HistoryRedo:
/*     */               if (!hasPermission(playerComponent, "hytale.editor.history"))
/*     */                 return; 
/*     */               BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */               break;
/*     */             case SelectionCopy:
/*     */               if (!hasPermission(playerComponent, "hytale.editor.selection.clipboard"))
/*     */                 return; 
/*     */               CopyCommand.copySelection(ref, (ComponentAccessor)store);
/*     */               break;
/*     */             case SelectionPosition1:
/*     */             case SelectionPosition2:
/*     */               if (!hasPermission(playerComponent, "hytale.editor.selection.use"))
/*     */                 return; 
/*     */               transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*     */               builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/*     */               position = transformComponent.getPosition();
/*     */               intTriple = new Vector3i(MathUtil.floor(position.getX()), MathUtil.floor(position.getY()), MathUtil.floor(position.getZ()));
/*     */               BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */               break;
/*     */             case ActivateToolMode:
/*     */               if (!hasPermission(playerComponent))
/*     */                 return; 
/*     */               playerComponent.getInventory().setUsingToolsItem(true);
/*     */               break;
/*     */             case DeactivateToolMode:
/*     */               if (!hasPermission(playerComponent))
/*     */                 return; 
/*     */               playerComponent.getInventory().setUsingToolsItem(false);
/*     */               break;
/*     */           } 
/*     */         });
/*     */   } public void handle(@Nonnull BuilderToolSelectionUpdate packet) {
/* 281 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 282 */     Ref<EntityStore> ref = playerRef.getReference();
/* 283 */     if (ref == null || !ref.isValid()) {
/* 284 */       throw new RuntimeException("Unable to process BuilderToolSelectionUpdate packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 287 */     Store<EntityStore> store = ref.getStore();
/* 288 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 290 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.selection.use")) {
/*     */             return;
/*     */           }
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(BuilderToolSelectionToolAskForClipboard packet) {
/* 301 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 302 */     Ref<EntityStore> ref = playerRef.getReference();
/* 303 */     if (ref == null || !ref.isValid()) {
/* 304 */       throw new RuntimeException("Unable to process BuilderToolSelectionToolAskForClipboard packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 307 */     Store<EntityStore> store = ref.getStore();
/* 308 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 310 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.selection.clipboard")) {
/*     */             return;
/*     */           }
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           PrototypePlayerBuilderToolSettings prototypeSettings = ToolOperation.getOrCreatePrototypeSettings(playerRef.getUuid());
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
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
/*     */   public int toInt(float value) {
/* 349 */     return (int)Math.floor(value + 0.1D);
/*     */   }
/*     */   
/*     */   private void handle(@Nonnull BuilderToolSelectionTransform packet) {
/* 353 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 354 */     Ref<EntityStore> ref = playerRef.getReference();
/* 355 */     if (ref == null || !ref.isValid()) {
/* 356 */       throw new RuntimeException("Unable to process BuilderToolSelectionTransform packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 359 */     Store<EntityStore> store = ref.getStore();
/* 360 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 362 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.selection.clipboard")) {
/*     */             return;
/*     */           }
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           float[] tmx = new float[16];
/*     */           for (int i = 0; i < packet.transformationMatrix.length; i++) {
/*     */             tmx[i] = toInt(packet.transformationMatrix[i]);
/*     */           }
/*     */           Matrix4d transformationMatrix = (new Matrix4d()).assign(tmx[0], tmx[4], tmx[8], tmx[12], tmx[1], tmx[5], tmx[9], tmx[13], tmx[2], tmx[6], tmx[10], tmx[14], tmx[3], tmx[7], tmx[11], tmx[15]);
/*     */           Vector3i initialSelectionMin = new Vector3i(packet.initialSelectionMin.x, packet.initialSelectionMin.y, packet.initialSelectionMin.z);
/*     */           Vector3i initialSelectionMax = new Vector3i(packet.initialSelectionMax.x, packet.initialSelectionMax.y, packet.initialSelectionMax.z);
/*     */           Vector3f rotationOrigin = new Vector3f(packet.initialRotationOrigin.x, packet.initialRotationOrigin.y, packet.initialRotationOrigin.z);
/*     */           PrototypePlayerBuilderToolSettings prototypeSettings = ToolOperation.getOrCreatePrototypeSettings(playerRef.getUuid());
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
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
/*     */   public void handle(@Nonnull BuilderToolExtrudeAction packet) {
/* 462 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 463 */     Ref<EntityStore> ref = playerRef.getReference();
/* 464 */     if (ref == null || !ref.isValid()) {
/* 465 */       throw new RuntimeException("Unable to process BuilderToolExtrudeAction packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 468 */     Store<EntityStore> store = ref.getStore();
/* 469 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 471 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.selection.modify")) {
/*     */             return;
/*     */           }
/*     */           BuilderTool builderTool = BuilderTool.getActiveBuilderTool(playerComponent);
/*     */           if (builderTool == null || !builderTool.getId().equals("Extrude")) {
/*     */             return;
/*     */           }
/*     */           ItemStack activeItemStack = playerComponent.getInventory().getItemInHand();
/*     */           BuilderTool.ArgData args = builderTool.getItemArgData(activeItemStack);
/*     */           int extrudeDepth = ((Integer)args.tool().get("ExtrudeDepth")).intValue();
/*     */           int extrudeRadius = ((Integer)args.tool().get("ExtrudeRadius")).intValue();
/*     */           int blockId = ((BlockPattern)args.tool().get("ExtrudeMaterial")).firstBlock();
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull BuilderToolStackArea packet) {
/* 497 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 498 */     Ref<EntityStore> ref = playerRef.getReference();
/* 499 */     if (ref == null || !ref.isValid()) {
/* 500 */       throw new RuntimeException("Unable to process BuilderToolStackArea packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 503 */     Store<EntityStore> store = ref.getStore();
/* 504 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 506 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           if (!hasPermission(playerComponent, "hytale.editor.selection.clipboard")) {
/*     */             return;
/*     */           }
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i fromBlockPosition(@Nonnull BlockPosition position) {
/* 522 */     return new Vector3i(position.x, position.y, position.z);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolRotateClipboard packet) {
/* 526 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 527 */     Ref<EntityStore> ref = playerRef.getReference();
/* 528 */     if (ref == null || !ref.isValid()) {
/* 529 */       throw new RuntimeException("Unable to process BuilderToolPasteClipboard packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 532 */     Store<EntityStore> store = ref.getStore();
/* 533 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 535 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.selection.clipboard")) {
/*     */             return;
/*     */           }
/*     */           Axis axis = (packet.axis == Axis.X) ? Axis.X : ((packet.axis == Axis.Y) ? Axis.Y : Axis.Z);
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull BuilderToolPasteClipboard packet) {
/* 550 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 551 */     Ref<EntityStore> ref = playerRef.getReference();
/* 552 */     if (ref == null || !ref.isValid()) {
/* 553 */       throw new RuntimeException("Unable to process BuilderToolPasteClipboard packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 556 */     Store<EntityStore> store = ref.getStore();
/* 557 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 559 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.selection.clipboard")) {
/*     */             return;
/*     */           }
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolLineAction packet) {
/* 570 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 571 */     Ref<EntityStore> ref = playerRef.getReference();
/* 572 */     if (ref == null || !ref.isValid()) {
/* 573 */       throw new RuntimeException("Unable to process BuilderToolLineAction packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 576 */     Store<EntityStore> store = ref.getStore();
/* 577 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 579 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.brush.use")) {
/*     */             return;
/*     */           }
/*     */           BuilderTool builderTool = BuilderTool.getActiveBuilderTool(playerComponent);
/*     */           if (builderTool == null || !builderTool.getId().equals("Line")) {
/*     */             return;
/*     */           }
/*     */           BuilderTool.ArgData args = builderTool.getItemArgData(playerComponent.getInventory().getItemInHand());
/*     */           BrushData.Values brushData = args.brush();
/*     */           int lineWidth = ((Integer)args.tool().get("bLineWidth")).intValue();
/*     */           int lineHeight = ((Integer)args.tool().get("cLineHeight")).intValue();
/*     */           BrushShape lineShape = BrushShape.valueOf((String)args.tool().get("dLineShape"));
/*     */           BrushOrigin lineOrigin = BrushOrigin.valueOf((String)args.tool().get("eLineOrigin"));
/*     */           int lineWallThickness = ((Integer)args.tool().get("fLineWallThickness")).intValue();
/*     */           int lineSpacing = ((Integer)args.tool().get("gLineSpacing")).intValue();
/*     */           int lineDensity = ((Integer)args.tool().get("hLineDensity")).intValue();
/*     */           BlockPattern lineMaterial = (BlockPattern)args.tool().get("aLineMaterial");
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
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
/*     */   public void handle(@Nonnull BuilderToolOnUseInteraction packet) {
/* 612 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 613 */     Ref<EntityStore> ref = playerRef.getReference();
/* 614 */     if (ref == null || !ref.isValid()) {
/* 615 */       throw new RuntimeException("Unable to process BuilderToolOnUseInteraction packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 618 */     Store<EntityStore> store = ref.getStore();
/* 619 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 621 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent, "hytale.editor.brush.use"))
/*     */             return; 
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           BuilderToolsPlugin.addToQueue(playerComponent, playerRef, ());
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolSetEntityTransform packet) {
/* 631 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 632 */     Ref<EntityStore> ref = playerRef.getReference();
/* 633 */     if (ref == null || !ref.isValid()) {
/* 634 */       throw new RuntimeException("Unable to process BuilderToolSetEntityTransform packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 637 */     Store<EntityStore> store = ref.getStore();
/* 638 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 640 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           if (!hasPermission(playerComponent)) {
/*     */             return;
/*     */           }
/*     */           Ref<EntityStore> entityReference = world.getEntityStore().getRefFromNetworkId(packet.entityId);
/*     */           if (entityReference == null) {
/*     */             return;
/*     */           }
/*     */           TransformComponent transformComponent = (TransformComponent)store.getComponent(entityReference, TransformComponent.getComponentType());
/*     */           assert transformComponent != null;
/*     */           HeadRotation headRotation = (HeadRotation)store.getComponent(entityReference, HeadRotation.getComponentType());
/*     */           ModelTransform modelTransform = packet.modelTransform;
/*     */           if (modelTransform != null) {
/*     */             boolean hasPosition = (modelTransform.position != null);
/*     */             boolean hasLookOrientation = (modelTransform.lookOrientation != null);
/*     */             boolean hasBodyOrientation = (modelTransform.bodyOrientation != null);
/*     */             if (hasPosition) {
/*     */               transformComponent.getPosition().assign(modelTransform.position.x, modelTransform.position.y, modelTransform.position.z);
/*     */             }
/*     */             if (hasLookOrientation && headRotation != null) {
/*     */               headRotation.getRotation().assign(modelTransform.lookOrientation.pitch, modelTransform.lookOrientation.yaw, modelTransform.lookOrientation.roll);
/*     */             }
/*     */             if (hasBodyOrientation) {
/*     */               transformComponent.getRotation().assign(modelTransform.bodyOrientation.pitch, modelTransform.bodyOrientation.yaw, modelTransform.bodyOrientation.roll);
/*     */             }
/*     */             if (hasPosition || hasLookOrientation || hasBodyOrientation) {
/*     */               transformComponent.markChunkDirty((ComponentAccessor)store);
/*     */             }
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull PrefabUnselectPrefab packet) {
/* 679 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 680 */     Ref<EntityStore> ref = playerRef.getReference();
/* 681 */     if (ref == null || !ref.isValid()) {
/* 682 */       throw new RuntimeException("Unable to process PrefabUnselectPrefab packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 685 */     Store<EntityStore> store = ref.getStore();
/* 686 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 688 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent)) {
/*     */             return;
/*     */           }
/*     */           LOGGER.at(Level.INFO).log("%s: %s", this.packetHandler.getIdentifier(), packet);
/*     */           PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/*     */           PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerRef.getUuid());
/*     */           if (prefabEditSession == null) {
/*     */             playerComponent.sendMessage(Message.translation("server.commands.editprefab.notInEditSession"));
/*     */             return;
/*     */           } 
/*     */           if (prefabEditSession.clearSelectedPrefab(ref, (ComponentAccessor)store)) {
/*     */             playerComponent.sendMessage(Message.translation("server.commands.editprefab.unselected"));
/*     */           } else {
/*     */             playerComponent.sendMessage(Message.translation("server.commands.editprefab.noPrefabSelected"));
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull BuilderToolSetEntityScale packet) {
/* 711 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 712 */     Ref<EntityStore> ref = playerRef.getReference();
/* 713 */     if (ref == null || !ref.isValid()) {
/* 714 */       throw new RuntimeException("Unable to process BuilderToolSetEntityScale packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 717 */     Store<EntityStore> store = ref.getStore();
/* 718 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 720 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent)) {
/*     */             return;
/*     */           }
/*     */           Ref<EntityStore> entityReference = world.getEntityStore().getRefFromNetworkId(packet.entityId);
/*     */           if (entityReference == null) {
/*     */             return;
/*     */           }
/*     */           PropComponent propComponent = (PropComponent)store.getComponent(entityReference, PropComponent.getComponentType());
/*     */           if (propComponent == null)
/*     */             return; 
/*     */           EntityScaleComponent scaleComponent = (EntityScaleComponent)store.getComponent(entityReference, EntityScaleComponent.getComponentType());
/*     */           if (scaleComponent == null) {
/*     */             scaleComponent = new EntityScaleComponent(packet.scale);
/*     */             store.addComponent(entityReference, EntityScaleComponent.getComponentType(), (Component)scaleComponent);
/*     */           } else {
/*     */             scaleComponent.setScale(packet.scale);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolSetEntityPickupEnabled packet) {
/* 743 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 744 */     Ref<EntityStore> ref = playerRef.getReference();
/* 745 */     if (ref == null || !ref.isValid()) {
/* 746 */       throw new RuntimeException("Unable to process BuilderToolSetEntityPickupEnabled packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 749 */     Store<EntityStore> store = ref.getStore();
/* 750 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 752 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent)) {
/*     */             return;
/*     */           }
/*     */           Ref<EntityStore> entityReference = world.getEntityStore().getRefFromNetworkId(packet.entityId);
/*     */           if (entityReference == null) {
/*     */             return;
/*     */           }
/*     */           PropComponent propComponent = (PropComponent)store.getComponent(entityReference, PropComponent.getComponentType());
/*     */           if (propComponent == null) {
/*     */             return;
/*     */           }
/*     */           if (packet.enabled) {
/*     */             store.ensureComponent(entityReference, Interactable.getComponentType());
/*     */             if (store.getComponent(entityReference, PreventPickup.getComponentType()) != null) {
/*     */               store.removeComponent(entityReference, PreventPickup.getComponentType());
/*     */             }
/*     */             Interactions interactionsComponent = (Interactions)store.getComponent(entityReference, Interactions.getComponentType());
/*     */             if (interactionsComponent == null) {
/*     */               interactionsComponent = new Interactions();
/*     */               store.addComponent(entityReference, Interactions.getComponentType(), (Component)interactionsComponent);
/*     */             } 
/*     */             interactionsComponent.setInteractionId(InteractionType.Use, "*PickupItem");
/*     */             interactionsComponent.setInteractionHint("server.interactionHints.pickup");
/*     */           } else {
/*     */             if (store.getComponent(entityReference, Interactable.getComponentType()) != null)
/*     */               store.removeComponent(entityReference, Interactable.getComponentType()); 
/*     */             if (store.getComponent(entityReference, Interactions.getComponentType()) != null)
/*     */               store.removeComponent(entityReference, Interactions.getComponentType()); 
/*     */             store.ensureComponent(entityReference, PreventPickup.getComponentType());
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolSetEntityLight packet) {
/* 788 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 789 */     Ref<EntityStore> ref = playerRef.getReference();
/* 790 */     if (ref == null || !ref.isValid()) {
/* 791 */       throw new RuntimeException("Unable to process BuilderToolSetEntityLight packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 794 */     Store<EntityStore> store = ref.getStore();
/* 795 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 797 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent))
/*     */             return; 
/*     */           Ref<EntityStore> entityReference = world.getEntityStore().getRefFromNetworkId(packet.entityId);
/*     */           if (entityReference == null)
/*     */             return; 
/*     */           if (packet.light == null) {
/*     */             store.removeComponent(entityReference, DynamicLight.getComponentType());
/*     */             store.removeComponent(entityReference, PersistentDynamicLight.getComponentType());
/*     */           } else {
/*     */             ColorLight colorLight = new ColorLight(packet.light.radius, packet.light.red, packet.light.green, packet.light.blue);
/*     */             store.putComponent(entityReference, DynamicLight.getComponentType(), (Component)new DynamicLight(colorLight));
/*     */             store.putComponent(entityReference, PersistentDynamicLight.getComponentType(), (Component)new PersistentDynamicLight(colorLight));
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull BuilderToolSetNPCDebug packet) {
/* 816 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 817 */     Ref<EntityStore> ref = playerRef.getReference();
/* 818 */     if (ref == null || !ref.isValid()) {
/* 819 */       throw new RuntimeException("Unable to process BuilderToolSetNPCDebug packet. Player ref is invalid!");
/*     */     }
/*     */     
/* 822 */     Store<EntityStore> store = ref.getStore();
/* 823 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 825 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (!hasPermission(playerComponent))
/*     */             return; 
/*     */           Ref<EntityStore> entityReference = world.getEntityStore().getRefFromNetworkId(packet.entityId);
/*     */           if (entityReference == null)
/*     */             return; 
/*     */           UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(entityReference, UUIDComponent.getComponentType());
/*     */           if (uuidComponent == null)
/*     */             return; 
/*     */           UUID uuid = uuidComponent.getUuid();
/*     */           String command = packet.enabled ? ("npc debug set display --entity " + String.valueOf(uuid)) : ("npc debug clear --entity " + String.valueOf(uuid));
/*     */           CommandManager.get().handleCommand((CommandSender)playerComponent, command);
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\BuilderToolsPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */