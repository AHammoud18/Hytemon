/*    */ package com.hypixel.hytale.server.core.command.commands.player.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.ItemUtils;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InventoryBackpackCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_INVENTORY_BACKPACK_SIZE = Message.translation("server.commands.inventory.backpack.size");
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_INVENTORY_BACKPACK_RESIZED = Message.translation("server.commands.inventory.backpack.resized");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final OptionalArg<Integer> sizeArg = withOptionalArg("size", "server.commands.inventorybackpack.size.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InventoryBackpackCommand() {
/* 40 */     super("backpack", "server.commands.inventorybackpack.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 45 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 46 */     assert playerComponent != null;
/*    */     
/* 48 */     Inventory inventory = playerComponent.getInventory();
/*    */     
/* 50 */     if (!this.sizeArg.provided(context)) {
/*    */       
/* 52 */       context.sendMessage(MESSAGE_COMMANDS_INVENTORY_BACKPACK_SIZE
/* 53 */           .param("capacity", inventory.getBackpack().getCapacity()));
/*    */     } else {
/*    */       
/* 56 */       short capacity = ((Integer)this.sizeArg.get(context)).shortValue();
/*    */       
/* 58 */       ObjectArrayList<ItemStack> remainder = new ObjectArrayList();
/* 59 */       inventory.resizeBackpack(capacity, (List)remainder);
/*    */       
/* 61 */       for (ObjectListIterator<ItemStack> objectListIterator = remainder.iterator(); objectListIterator.hasNext(); ) { ItemStack item = objectListIterator.next();
/* 62 */         ItemUtils.dropItem(ref, item, (ComponentAccessor)store); }
/*    */ 
/*    */       
/* 65 */       context.sendMessage(MESSAGE_COMMANDS_INVENTORY_BACKPACK_RESIZED
/* 66 */           .param("capacity", inventory.getBackpack().getCapacity())
/* 67 */           .param("dropped", remainder.size()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\inventory\InventoryBackpackCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */