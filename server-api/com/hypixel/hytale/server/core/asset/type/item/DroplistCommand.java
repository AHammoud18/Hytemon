/*    */ package com.hypixel.hytale.server.core.asset.type.item;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.item.ItemModule;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ public class DroplistCommand extends CommandBase {
/*    */   @Nonnull
/* 20 */   private final RequiredArg<String> itemDroplistArg = withRequiredArg("droplist", "server.commands.droplist.set.droplist.desc", (ArgumentType)ArgTypes.STRING);
/*    */   
/* 22 */   private final OptionalArg<Integer> countArg = (OptionalArg<Integer>)withOptionalArg("count", "server.commands.droplist.set.count.desc", (ArgumentType)ArgTypes.INTEGER)
/* 23 */     .addValidator(Validators.greaterThan(Integer.valueOf(0)));
/*    */   
/*    */   public DroplistCommand() {
/* 26 */     super("droplist", "server.commands.droplist.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@NonNullDecl CommandContext context) {
/* 31 */     String droplistId = (String)this.itemDroplistArg.get(context);
/* 32 */     ItemDropList itemDropList = (ItemDropList)ItemDropList.getAssetMap().getAsset(droplistId);
/* 33 */     if (itemDropList == null) {
/* 34 */       context.sendMessage(Message.translation("server.commands.droplist.notFound").param("droplistId", droplistId));
/*    */       
/*    */       return;
/*    */     } 
/* 38 */     int count = this.countArg.provided(context) ? ((Integer)this.countArg.get(context)).intValue() : 1;
/* 39 */     LinkedHashMap<String, Integer> accumulatedDrops = new LinkedHashMap<>();
/*    */     
/* 41 */     for (int i = 0; i < count; i++) {
/* 42 */       List<ItemStack> randomItemsToDrop = ItemModule.get().getRandomItemDrops(droplistId);
/* 43 */       for (ItemStack itemStack : randomItemsToDrop) {
/* 44 */         accumulatedDrops.merge(itemStack.getItemId(), Integer.valueOf(itemStack.getQuantity()), Integer::sum);
/*    */       }
/*    */     } 
/*    */     
/* 48 */     if (accumulatedDrops.isEmpty()) {
/* 49 */       context.sendMessage(Message.translation("server.commands.droplist.empty").param("droplistId", droplistId));
/*    */       
/*    */       return;
/*    */     } 
/* 53 */     context.sendMessage(Message.translation("server.commands.droplist.result").param("droplistId", droplistId));
/* 54 */     for (Map.Entry<String, Integer> entry : accumulatedDrops.entrySet()) {
/*    */ 
/*    */       
/* 57 */       Message message = Message.translation("server.commands.droplist.result.item").param("itemName", entry.getKey()).param("itemQuantity", ((Integer)entry.getValue()).intValue());
/* 58 */       context.sendMessage(message);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\DroplistCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */