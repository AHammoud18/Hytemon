/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.HotbarManager;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HotbarSwitchCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 27 */   private final RequiredArg<Integer> hotbarSlotArg = (RequiredArg<Integer>)
/* 28 */     withRequiredArg("hotbarSlot", "server.commands.hotbar.hotbarSlot.desc", (ArgumentType)ArgTypes.INTEGER)
/* 29 */     .addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(9)));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 35 */   private final FlagArg saveInsteadOfLoadArg = withFlagArg("save", "server.commands.hotbar.save.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HotbarSwitchCommand() {
/* 41 */     super("hotbar", "server.commands.hotbar.desc");
/* 42 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 47 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 48 */     assert playerComponent != null;
/*    */     
/* 50 */     HotbarManager hotbarManager = playerComponent.getHotbarManager();
/* 51 */     if (((Boolean)this.saveInsteadOfLoadArg.get(context)).booleanValue()) {
/* 52 */       hotbarManager.saveHotbar(ref, ((Integer)this.hotbarSlotArg.get(context)).shortValue(), (ComponentAccessor)store);
/*    */     } else {
/* 54 */       hotbarManager.loadHotbar(ref, ((Integer)this.hotbarSlotArg.get(context)).shortValue(), (ComponentAccessor)store);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\HotbarSwitchCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */