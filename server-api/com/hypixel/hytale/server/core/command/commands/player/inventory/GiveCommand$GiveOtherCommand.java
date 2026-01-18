/*     */ package com.hypixel.hytale.server.core.command.commands.player.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GiveOtherCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/* 114 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */   @Nonnull
/* 116 */   private static final Message MESSAGE_COMMANDS_GIVE_GAVE = Message.translation("server.commands.give.gave");
/*     */   @Nonnull
/* 118 */   private static final Message MESSAGE_COMMANDS_GIVE_INSUFFICIENT_INV_SPACE = Message.translation("server.commands.give.insufficientInvSpace");
/*     */   @Nonnull
/* 120 */   private static final Message MESSAGE_COMMANDS_GIVE_INVALID_METADATA = Message.translation("server.commands.give.invalidMetadata");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 126 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 132 */   private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.give.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 138 */   private final DefaultArg<Integer> quantityArg = withDefaultArg("quantity", "server.commands.give.quantity.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(1), "1");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 144 */   private final OptionalArg<String> metadataArg = withOptionalArg("metadata", "server.commands.give.metadata.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GiveOtherCommand() {
/* 150 */     super("server.commands.give.other.desc");
/* 151 */     requirePermission(HytalePermissions.fromCommand("give.other"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/* 156 */     PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 157 */     Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */     
/* 159 */     if (ref == null || !ref.isValid()) {
/* 160 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */       
/*     */       return;
/*     */     } 
/* 164 */     Store<EntityStore> store = ref.getStore();
/* 165 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 167 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           
/*     */           if (playerComponent == null) {
/*     */             context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */           
/*     */           assert playerRefComponent != null;
/*     */           
/*     */           Item item = (Item)this.itemArg.get(context);
/*     */           
/*     */           Integer quantity = (Integer)this.quantityArg.get(context);
/*     */           
/*     */           BsonDocument metadata = null;
/*     */           if (this.metadataArg.provided(context)) {
/*     */             String metadataStr = (String)this.metadataArg.get(context);
/*     */             try {
/*     */               metadata = BsonDocument.parse(metadataStr);
/* 189 */             } catch (Exception e) {
/*     */               context.sendMessage(MESSAGE_COMMANDS_GIVE_INVALID_METADATA.param("error", e.getMessage()));
/*     */               return;
/*     */             } 
/*     */           } 
/*     */           ItemStackTransaction transaction = playerComponent.getInventory().getCombinedHotbarFirst().addItemStack(new ItemStack(item.getId(), quantity.intValue(), metadata));
/*     */           ItemStack remainder = transaction.getRemainder();
/*     */           Message itemNameMessage = Message.translation(item.getTranslationKey());
/*     */           if (remainder == null || remainder.isEmpty()) {
/*     */             context.sendMessage(MESSAGE_COMMANDS_GIVE_GAVE.param("targetUsername", targetPlayerRef.getUsername()).param("quantity", quantity.intValue()).param("item", itemNameMessage));
/*     */           } else {
/*     */             context.sendMessage(MESSAGE_COMMANDS_GIVE_INSUFFICIENT_INV_SPACE.param("quantity", quantity.intValue()).param("item", itemNameMessage));
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\inventory\GiveCommand$GiveOtherCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */