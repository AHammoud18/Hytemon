/*     */ package com.hypixel.hytale.server.core.modules.item.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.ItemUtils;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionHolder;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SpawnItemCommand
/*     */   extends AbstractPlayerCommand {
/*     */   @Nonnull
/*  37 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  43 */   private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.spawnitem.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  49 */   private final DefaultArg<Integer> quantityArg = withDefaultArg("qty", "server.commands.spawnitem.quantity.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(1), "1");
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   private final OptionalArg<Integer> countArg = (OptionalArg<Integer>)
/*  55 */     withOptionalArg("count", "server.commands.spawnitem.count.desc", (ArgumentType)ArgTypes.INTEGER)
/*  56 */     .addAliases(new String[] { "n" });
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  61 */   private final DefaultArg<Float> forceArg = (DefaultArg<Float>)
/*  62 */     withDefaultArg("force", "server.commands.spawnitem.force.desc", (ArgumentType)ArgTypes.FLOAT, Float.valueOf(1.0F), "1.0")
/*  63 */     .addAliases(new String[] { "x" });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnItemCommand() {
/*  69 */     super("spawnitem", "server.commands.spawnitem.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  79 */     CommandUtil.requirePermission((PermissionHolder)context.sender(), HytalePermissions.fromCommand("spawnitem"));
/*     */     
/*  81 */     Item item = (Item)this.itemArg.get(context);
/*  82 */     String itemId = item.getId();
/*  83 */     int quantity = ((Integer)this.quantityArg.get(context)).intValue();
/*  84 */     float force = ((Float)this.forceArg.get(context)).floatValue();
/*     */     
/*  86 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  87 */     ModelComponent modelComponent = (ModelComponent)store.getComponent(ref, ModelComponent.getComponentType());
/*     */     
/*  89 */     if (transformComponent == null || modelComponent == null) {
/*  90 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */       
/*     */       return;
/*     */     } 
/*  94 */     Vector3d playerPosition = transformComponent.getPosition();
/*  95 */     Model playerModel = modelComponent.getModel();
/*     */     
/*  97 */     float throwSpeed = 6.0F * force;
/*     */     
/*  99 */     if (this.countArg.provided(context)) {
/* 100 */       int count = ((Integer)this.countArg.get(context)).intValue();
/*     */       
/* 102 */       Vector3d throwPosition = playerPosition.clone();
/*     */ 
/*     */       
/* 105 */       throwPosition.add(0.0D, playerModel.getEyeHeight(ref, (ComponentAccessor)store), 0.0D);
/*     */       
/* 107 */       ThreadLocalRandom random = ThreadLocalRandom.current();
/* 108 */       for (int i = 0; i < count; i++) {
/* 109 */         Holder<EntityStore> itemEntityHolder = ItemComponent.generateItemDrop((ComponentAccessor)store, new ItemStack(itemId, quantity), throwPosition, Vector3f.ZERO, 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 114 */             (float)random.nextGaussian() * throwSpeed, 0.5F, 
/*     */             
/* 116 */             (float)random.nextGaussian() * throwSpeed);
/*     */ 
/*     */         
/* 119 */         ItemComponent itemEntityComponent = (ItemComponent)itemEntityHolder.getComponent(ItemComponent.getComponentType());
/* 120 */         if (itemEntityComponent != null) {
/* 121 */           itemEntityComponent.setPickupDelay(1.5F);
/*     */         }
/*     */         
/* 124 */         store.addEntity(itemEntityHolder, AddReason.SPAWN);
/*     */       } 
/*     */       
/* 127 */       int totalQuantity = count * quantity;
/* 128 */       context.sendMessage(Message.translation("server.commands.spawnitem.spawnedMultiple")
/* 129 */           .param("count", count)
/* 130 */           .param("quantity", quantity)
/* 131 */           .param("total", totalQuantity)
/* 132 */           .param("item", itemId));
/*     */     } else {
/* 134 */       ItemUtils.throwItem(ref, new ItemStack(itemId, quantity), throwSpeed, (ComponentAccessor)store);
/* 135 */       context.sendMessage(Message.translation("server.commands.spawnitem.spawned")
/* 136 */           .param("quantity", quantity)
/* 137 */           .param("item", itemId));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\item\commands\SpawnItemCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */