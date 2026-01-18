/*    */ package com.hypixel.hytale.server.npc.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCAppearanceCommand
/*    */   extends NPCWorldCommandBase
/*    */ {
/*    */   @Nonnull
/* 25 */   private final RequiredArg<ModelAsset> modelArg = withRequiredArg("model", "server.commands.npc.appearance.model.desc", (ArgumentType)ArgTypes.MODEL_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NPCAppearanceCommand() {
/* 31 */     super("appearance", "server.commands.npc.appearance.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 41 */     ModelAsset model = (ModelAsset)this.modelArg.get(context);
/* 42 */     npc.setAppearance(ref, model, (ComponentAccessor)store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCAppearanceCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */