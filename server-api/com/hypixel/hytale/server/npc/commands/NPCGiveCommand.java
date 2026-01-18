/*    */ package com.hypixel.hytale.server.npc.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.RoleUtils;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCGiveCommand
/*    */   extends NPCWorldCommandBase
/*    */ {
/*    */   @Nonnull
/* 25 */   private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.npc.give.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NPCGiveCommand() {
/* 31 */     super("give", "server.commands.npc.give.desc");
/* 32 */     addSubCommand((AbstractCommand)new GiveNothingCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 42 */     Item item = (Item)this.itemArg.get(context);
/* 43 */     String itemName = item.getId();
/* 44 */     if (item.getArmor() != null) {
/* 45 */       RoleUtils.setArmor(npc, itemName);
/*    */     } else {
/* 47 */       RoleUtils.setItemInHand(npc, itemName);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class GiveNothingCommand
/*    */     extends NPCWorldCommandBase
/*    */   {
/*    */     public GiveNothingCommand() {
/* 59 */       super("nothing", "server.commands.npc.give.nothing.desc");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 69 */       RoleUtils.setItemInHand(npc, null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCGiveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */