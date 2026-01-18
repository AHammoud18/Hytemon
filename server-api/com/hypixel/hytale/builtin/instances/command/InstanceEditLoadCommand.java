/*    */ package com.hypixel.hytale.builtin.instances.command;
/*    */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InstanceEditLoadCommand extends AbstractAsyncCommand {
/* 18 */   private final RequiredArg<String> instanceNameArg = (RequiredArg<String>)withRequiredArg("instanceName", "server.commands.instances.edit.arg.name", (ArgumentType)ArgTypes.STRING)
/* 19 */     .addValidator((Validator)new InstanceValidator());
/*    */   
/*    */   public InstanceEditLoadCommand() {
/* 22 */     super("load", "server.commands.instances.edit.load.desc");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 27 */     if (AssetModule.get().getBaseAssetPack().isImmutable()) {
/* 28 */       context.sendMessage(Message.translation("server.commands.instances.edit.assetsImmutable"));
/* 29 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 32 */     String name = (String)this.instanceNameArg.get(context);
/*    */     
/* 34 */     context.sendMessage(Message.translation("server.commands.instance.beginLoading").param("name", name));
/*    */     
/* 36 */     InstancesPlugin.get(); return InstancesPlugin.loadInstanceAssetForEdit(name).thenAccept(world -> {
/*    */           context.sendMessage(Message.translation("server.commands.instance.doneLoading").param("world", world.getName()));
/*    */           if (context.isPlayer()) {
/*    */             Ref<EntityStore> ref = context.senderAsPlayerRef();
/*    */             if (ref == null || !ref.isValid())
/*    */               return; 
/*    */             Store<EntityStore> playerStore = ref.getStore();
/*    */             World playerWorld = ((EntityStore)playerStore.getExternalData()).getWorld();
/*    */             playerWorld.execute(());
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstanceEditLoadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */