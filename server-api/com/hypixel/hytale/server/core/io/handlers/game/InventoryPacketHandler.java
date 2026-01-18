/*     */ package com.hypixel.hytale.server.core.io.handlers.game;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.InventoryActionType;
/*     */ import com.hypixel.hytale.protocol.ItemSoundEvent;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.SmartMoveType;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.DropCreativeItem;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.DropItemStack;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.InventoryAction;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.MoveItemStack;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.SetActiveSlot;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.SetCreativeItem;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.SmartGiveCreativeItem;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.SmartMoveItemStack;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.SwitchHotbarBlockSet;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.BlockGroup;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.BlockSelectorToolData;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemArmor;
/*     */ import com.hypixel.hytale.server.core.asset.type.itemsound.config.ItemSoundSet;
/*     */ import com.hypixel.hytale.server.core.entity.ItemUtils;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ItemContainerWindow;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.DropItemEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.SwitchActiveSlotEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SortType;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.handlers.IPacketHandler;
/*     */ import com.hypixel.hytale.server.core.modules.item.ItemModule;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.NotificationUtil;
/*     */ import com.hypixel.hytale.server.core.util.TempAssetIdUtil;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class InventoryPacketHandler implements SubPacketHandler {
/*     */   public InventoryPacketHandler(IPacketHandler packetHandler) {
/*  56 */     this.packetHandler = packetHandler;
/*     */   }
/*     */   private final IPacketHandler packetHandler;
/*     */   
/*     */   public void registerHandlers() {
/*  61 */     this.packetHandler.registerHandler(171, p -> handle((SetCreativeItem)p));
/*  62 */     this.packetHandler.registerHandler(172, p -> handle((DropCreativeItem)p));
/*  63 */     this.packetHandler.registerHandler(173, p -> handle((SmartGiveCreativeItem)p));
/*  64 */     this.packetHandler.registerHandler(174, p -> handle((DropItemStack)p));
/*  65 */     this.packetHandler.registerHandler(175, p -> handle((MoveItemStack)p));
/*  66 */     this.packetHandler.registerHandler(176, p -> handle((SmartMoveItemStack)p));
/*  67 */     this.packetHandler.registerHandler(177, p -> handle((SetActiveSlot)p));
/*  68 */     this.packetHandler.registerHandler(178, p -> handle((SwitchHotbarBlockSet)p));
/*  69 */     this.packetHandler.registerHandler(179, p -> handle((InventoryAction)p));
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull SetCreativeItem packet) {
/*  73 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/*  74 */     Ref<EntityStore> ref = playerRef.getReference();
/*  75 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/*  79 */     Store<EntityStore> store = ref.getStore();
/*  80 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  82 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */           assert playerRefComponent != null;
/*     */           if (playerComponent.getGameMode() != GameMode.Creative) {
/*     */             NotificationUtil.sendNotification(playerRefComponent.getPacketHandler(), Message.translation("server.general.setCreativeItem.notInCreativeMode"));
/*     */             return;
/*     */           } 
/*     */           Inventory inventory = playerComponent.getInventory();
/*     */           int quantity = packet.item.quantity;
/*     */           if (quantity > 0) {
/*     */             ItemStack itemStack = ItemStack.fromPacket(packet.item);
/*     */             if (packet.slotId < 0) {
/*     */               ItemStackTransaction transaction = inventory.getCombinedHotbarFirst().addItemStack(itemStack);
/*     */               ItemStack remainder = transaction.getRemainder();
/*     */               if (remainder != null && !remainder.isEmpty()) {
/*     */                 ItemUtils.dropItem(ref, remainder, (ComponentAccessor)store);
/*     */               }
/*     */             } else {
/*     */               ItemContainer sectionById = inventory.getSectionById(packet.inventorySectionId);
/*     */               if (packet.override) {
/*     */                 sectionById.setItemStackForSlot((short)packet.slotId, itemStack);
/*     */               } else {
/*     */                 ItemStack existing = sectionById.getItemStack((short)packet.slotId);
/*     */                 if (existing != null && !existing.isEmpty() && existing.isStackableWith(itemStack)) {
/*     */                   sectionById.addItemStackToSlot((short)packet.slotId, itemStack);
/*     */                 } else {
/*     */                   sectionById.setItemStackForSlot((short)packet.slotId, itemStack);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } else if (packet.override) {
/*     */             inventory.getSectionById(packet.inventorySectionId).setItemStackForSlot((short)packet.slotId, null);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull DropCreativeItem packet) {
/* 126 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 127 */     Ref<EntityStore> ref = playerRef.getReference();
/* 128 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     Store<EntityStore> store = ref.getStore();
/* 133 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 135 */     ItemStack itemStack = ItemStack.fromPacket(packet.item);
/* 136 */     if (itemStack == null)
/* 137 */       return;  Item item = itemStack.getItem();
/* 138 */     if (item == Item.UNKNOWN)
/*     */       return; 
/* 140 */     itemStack = itemStack.withQuantity(Math.min(itemStack.getQuantity(), item.getMaxStack()));
/* 141 */     ItemStack fStack = itemStack;
/*     */     
/* 143 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */           assert playerRefComponent != null;
/*     */           if (playerComponent.getGameMode() != GameMode.Creative) {
/*     */             NotificationUtil.sendNotification(playerRefComponent.getPacketHandler(), Message.translation("server.general.setCreativeItem.notInCreativeMode"));
/*     */             return;
/*     */           } 
/*     */           ItemUtils.dropItem(ref, fStack, (ComponentAccessor)store);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(SwitchHotbarBlockSet packet) {
/* 160 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 161 */     Ref<EntityStore> ref = playerRef.getReference();
/* 162 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 166 */     Store<EntityStore> store = ref.getStore();
/* 167 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 169 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           Inventory inventory = playerComponent.getInventory();
/*     */           byte slot = inventory.getActiveHotbarSlot();
/*     */           if (slot == -1) {
/*     */             return;
/*     */           }
/*     */           ItemContainer hotbar = inventory.getHotbar();
/*     */           ItemStack stack = hotbar.getItemStack((short)slot);
/*     */           if (stack == null || stack.isEmpty()) {
/*     */             return;
/*     */           }
/*     */           BlockGroup set = BlockGroup.findItemGroup(stack.getItem());
/*     */           if (set == null) {
/*     */             return;
/*     */           }
/*     */           Item desiredItem = (Item)Item.getAssetMap().getAsset(packet.itemId);
/*     */           if (desiredItem == null) {
/*     */             return;
/*     */           }
/*     */           int currentIndex = set.getIndex(stack.getItem());
/*     */           if (currentIndex == -1) {
/*     */             return;
/*     */           }
/*     */           int desiredIndex = set.getIndex(desiredItem);
/*     */           if (desiredIndex == -1 || desiredIndex == currentIndex) {
/*     */             return;
/*     */           }
/*     */           ItemStack maxSelectorTool = null;
/*     */           short maxSlot = -1;
/*     */           CombinedItemContainer combinedInventory = inventory.getCombinedArmorHotbarUtilityStorage();
/*     */           short i;
/*     */           for (i = 0; i < combinedInventory.getCapacity(); i = (short)(i + 1)) {
/*     */             ItemStack potentialSelector = combinedInventory.getItemStack(i);
/*     */             if (!ItemStack.isEmpty(potentialSelector)) {
/*     */               Item item = potentialSelector.getItem();
/*     */               BlockSelectorToolData selectorTool = item.getBlockSelectorToolData();
/*     */               if (selectorTool != null) {
/*     */                 if (maxSelectorTool == null || maxSelectorTool.getDurability() < potentialSelector.getDurability()) {
/*     */                   maxSelectorTool = potentialSelector;
/*     */                   maxSlot = i;
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */           if (maxSelectorTool == null) {
/*     */             return;
/*     */           }
/*     */           BlockSelectorToolData toolData = maxSelectorTool.getItem().getBlockSelectorToolData();
/*     */           if (playerComponent.canDecreaseItemStackDurability(ref, (ComponentAccessor)store) && !maxSelectorTool.isUnbreakable()) {
/*     */             playerComponent.updateItemStackDurability(ref, maxSelectorTool, (ItemContainer)combinedInventory, maxSlot, -toolData.getDurabilityLossOnUse(), (ComponentAccessor)store);
/*     */           }
/*     */           ItemStack replacement = new ItemStack(set.get(desiredIndex), stack.getQuantity());
/*     */           hotbar.setItemStackForSlot((short)slot, replacement);
/*     */           ItemSoundSet soundSet = (ItemSoundSet)ItemSoundSet.getAssetMap().getAsset(desiredItem.getItemSoundSetIndex());
/*     */           if (soundSet == null) {
/*     */             return;
/*     */           }
/*     */           String dragSound = (String)soundSet.getSoundEventIds().get(ItemSoundEvent.Drop);
/*     */           if (dragSound == null) {
/*     */             return;
/*     */           }
/*     */           int dragSoundIndex = SoundEvent.getAssetMap().getIndex(dragSound);
/*     */           if (dragSoundIndex == 0) {
/*     */             return;
/*     */           }
/*     */           SoundUtil.playSoundEvent2d(ref, dragSoundIndex, SoundCategory.UI, (ComponentAccessor)store);
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
/*     */   public void handle(@Nonnull SmartGiveCreativeItem packet) {
/* 257 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 258 */     Ref<EntityStore> ref = playerRef.getReference();
/* 259 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 263 */     Store<EntityStore> store = ref.getStore();
/* 264 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 266 */     world.execute(() -> {
/*     */           Item item;
/*     */           ItemArmor itemArmor;
/*     */           int quantity;
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */           assert playerRefComponent != null;
/*     */           if (playerComponent.getGameMode() != GameMode.Creative) {
/*     */             NotificationUtil.sendNotification(playerRefComponent.getPacketHandler(), Message.translation("server.general.setCreativeItem.notInCreativeMode"));
/*     */             return;
/*     */           } 
/*     */           Inventory inventory = playerComponent.getInventory();
/*     */           ItemStack itemStack = ItemStack.fromPacket(packet.item);
/*     */           switch (packet.moveType) {
/*     */             case TakeAll:
/*     */               item = itemStack.getItem();
/*     */               itemArmor = item.getArmor();
/*     */               if (itemArmor != null) {
/*     */                 inventory.getArmor().setItemStackForSlot((short)itemArmor.getArmorSlot().ordinal(), itemStack);
/*     */                 return;
/*     */               } 
/*     */               quantity = itemStack.getQuantity();
/*     */               if (item.getUtility().isUsable()) {
/*     */                 ItemStackTransaction transaction = inventory.getUtility().addItemStack(itemStack);
/*     */                 ItemStack remainder = transaction.getRemainder();
/*     */                 if (ItemStack.isEmpty(remainder) || remainder.getQuantity() != quantity) {
/*     */                   List<ItemStackSlotTransaction> slotTransactions = transaction.getSlotTransactions();
/*     */                   for (ItemStackSlotTransaction slotTransaction : slotTransactions) {
/*     */                     if (!slotTransaction.succeeded()) {
/*     */                       continue;
/*     */                     }
/*     */                     inventory.setActiveUtilitySlot((byte)slotTransaction.getSlot());
/*     */                   } 
/*     */                 } 
/*     */                 return;
/*     */               } 
/*     */               break;
/*     */             case PutAll:
/*     */               inventory.getCombinedHotbarFirst().addItemStack(itemStack);
/*     */               break;
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull DropItemStack packet) {
/* 316 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 317 */     Ref<EntityStore> ref = playerRef.getReference();
/* 318 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 322 */     Store<EntityStore> store = ref.getStore();
/* 323 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 325 */     world.execute(() -> {
/*     */           DropItemEvent.PlayerRequest event = new DropItemEvent.PlayerRequest(packet.inventorySectionId, (short)packet.slotId);
/*     */           store.invoke(ref, (EcsEvent)event);
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           Inventory inventory = playerComponent.getInventory();
/*     */           if (!event.isCancelled()) {
/*     */             ItemStackSlotTransaction transaction = inventory.getSectionById(event.getInventorySectionId()).removeItemStackFromSlot(event.getSlotId(), packet.quantity);
/*     */             ItemStack item = transaction.getOutput();
/*     */             if (item == null || item.isEmpty()) {
/*     */               HytaleLogger.getLogger().at(Level.WARNING).log("%s attempted to drop an empty ItemStack!", playerRef.getUsername());
/*     */               return;
/*     */             } 
/*     */             String itemId = item.getItemId();
/*     */             if (!ItemModule.exists(itemId)) {
/*     */               HytaleLogger.getLogger().at(Level.WARNING).log("%s attempted to drop an unregistered ItemStack! %s", playerRef.getUsername(), itemId);
/*     */               return;
/*     */             } 
/*     */             ItemUtils.throwItem(ref, item, 6.0F, (ComponentAccessor)store);
/*     */             SoundUtil.playSoundEvent2d(ref, TempAssetIdUtil.getSoundEventIndex("SFX_Player_Drop_Item"), SoundCategory.UI, (ComponentAccessor)store);
/*     */           } else {
/*     */             playerComponent.sendInventory();
/*     */           } 
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
/*     */   public void handle(@Nonnull MoveItemStack packet) {
/* 359 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 360 */     Ref<EntityStore> ref = playerRef.getReference();
/* 361 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 365 */     Store<EntityStore> store = ref.getStore();
/* 366 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 367 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           Inventory inventory = playerComponent.getInventory();
/*     */           inventory.moveItem(packet.fromSectionId, packet.fromSlotId, packet.quantity, packet.toSectionId, packet.toSlotId);
/*     */           if (packet.toSectionId != packet.fromSectionId && packet.toSectionId == -5) {
/*     */             byte newSlot = (byte)packet.toSlotId;
/*     */             int inventorySectionId = packet.toSectionId;
/*     */             byte currentSlot = inventory.getActiveSlot(inventorySectionId);
/*     */             if (currentSlot == newSlot) {
/*     */               return;
/*     */             }
/*     */             SwitchActiveSlotEvent event = new SwitchActiveSlotEvent(inventorySectionId, currentSlot, newSlot, true);
/*     */             store.invoke(ref, (EcsEvent)event);
/*     */             if (event.isCancelled() || event.getNewSlot() == currentSlot) {
/*     */               return;
/*     */             }
/*     */             newSlot = event.getNewSlot();
/*     */             inventory.setActiveSlot(inventorySectionId, newSlot);
/*     */             playerRef.getPacketHandler().writeNoCache((Packet)new SetActiveSlot(inventorySectionId, newSlot));
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull SmartMoveItemStack packet) {
/* 398 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 399 */     Ref<EntityStore> ref = playerRef.getReference();
/* 400 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 404 */     Store<EntityStore> store = ref.getStore();
/* 405 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 407 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           Inventory inventory = playerComponent.getInventory();
/*     */           inventory.smartMoveItem(packet.fromSectionId, packet.fromSlotId, packet.quantity, packet.moveType);
/*     */         });
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull SetActiveSlot packet) {
/* 416 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 417 */     Ref<EntityStore> ref = playerRef.getReference();
/* 418 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 422 */     Store<EntityStore> store = ref.getStore();
/* 423 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 424 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           Inventory inventory = playerComponent.getInventory();
/*     */           PacketHandler packetHandler = playerRef.getPacketHandler();
/*     */           if (packet.inventorySectionId == -1) {
/*     */             packetHandler.disconnect("Attempted to change the hotbar slot without an interaction");
/*     */             return;
/*     */           } 
/*     */           if (packet.activeSlot < -1 || packet.activeSlot >= inventory.getSectionById(packet.inventorySectionId).getCapacity()) {
/*     */             packetHandler.disconnect("Attempted to set " + packet.inventorySectionId + " slot outside of range!");
/*     */             return;
/*     */           } 
/*     */           if (packet.activeSlot == inventory.getActiveSlot(packet.inventorySectionId)) {
/*     */             packetHandler.disconnect("Attempted to set hotbar slot to already selected hotbar slot!");
/*     */             return;
/*     */           } 
/*     */           byte previousSlot = inventory.getActiveSlot(packet.inventorySectionId);
/*     */           byte targetSlot = (byte)packet.activeSlot;
/*     */           SwitchActiveSlotEvent event = new SwitchActiveSlotEvent(packet.inventorySectionId, previousSlot, targetSlot, false);
/*     */           store.invoke(ref, (EcsEvent)event);
/*     */           if (event.isCancelled()) {
/*     */             targetSlot = previousSlot;
/*     */           } else if (targetSlot != event.getNewSlot()) {
/*     */             targetSlot = event.getNewSlot();
/*     */           } 
/*     */           if (targetSlot != packet.activeSlot) {
/*     */             packetHandler.writeNoCache((Packet)new SetActiveSlot(packet.inventorySectionId, targetSlot));
/*     */           }
/*     */           if (targetSlot != previousSlot) {
/*     */             inventory.setActiveSlot(packet.inventorySectionId, targetSlot);
/*     */           }
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
/*     */   public void handle(@Nonnull InventoryAction packet) {
/* 473 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 474 */     Ref<EntityStore> ref = playerRef.getReference();
/* 475 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 479 */     if (packet.inventorySectionId < 0 && packet.inventorySectionId != -9) {
/*     */       return;
/*     */     }
/* 482 */     Store<EntityStore> store = ref.getStore();
/* 483 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 485 */     world.execute(() -> {
/*     */           Window window;
/*     */           SortType sortType;
/*     */           Window window1;
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           Inventory inventory = playerComponent.getInventory();
/*     */           switch (packet.inventoryActionType) {
/*     */             case TakeAll:
/*     */               if (packet.inventorySectionId == -9) {
/*     */                 inventory.takeAll(packet.inventorySectionId);
/*     */                 return;
/*     */               } 
/*     */               window = playerComponent.getWindowManager().getWindow(packet.inventorySectionId);
/*     */               if (window instanceof ItemContainerWindow)
/*     */                 inventory.takeAll(packet.inventorySectionId); 
/*     */               break;
/*     */             case PutAll:
/*     */               if (packet.inventorySectionId == -9) {
/*     */                 inventory.putAll(packet.inventorySectionId);
/*     */                 return;
/*     */               } 
/*     */               window = playerComponent.getWindowManager().getWindow(packet.inventorySectionId);
/*     */               if (window instanceof ItemContainerWindow)
/*     */                 inventory.putAll(packet.inventorySectionId); 
/*     */               break;
/*     */             case QuickStack:
/*     */               if (packet.inventorySectionId == -9) {
/*     */                 inventory.quickStack(packet.inventorySectionId);
/*     */                 return;
/*     */               } 
/*     */               window = playerComponent.getWindowManager().getWindow(packet.inventorySectionId);
/*     */               if (window instanceof ItemContainerWindow)
/*     */                 inventory.quickStack(packet.inventorySectionId); 
/*     */               break;
/*     */             case Sort:
/*     */               sortType = SortType.VALUES[packet.actionData];
/*     */               if (packet.inventorySectionId == 0) {
/*     */                 inventory.sortStorage(sortType);
/*     */                 break;
/*     */               } 
/*     */               if (packet.inventorySectionId == -9 && inventory.getBackpack() != null) {
/*     */                 inventory.getBackpack().sortItems(sortType);
/*     */                 return;
/*     */               } 
/*     */               window1 = playerComponent.getWindowManager().getWindow(packet.inventorySectionId);
/*     */               if (window1 instanceof ItemContainerWindow) {
/*     */                 ItemContainerWindow itemContainerWindow = (ItemContainerWindow)window1;
/*     */                 itemContainerWindow.getItemContainer().sortItems(sortType);
/*     */               } 
/*     */               break;
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\game\InventoryPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */