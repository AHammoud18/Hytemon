/*    */ package com.hypixel.hytale.server.npc.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.systems.RoleChangeSystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCRoleCommand
/*    */   extends NPCWorldCommandBase
/*    */ {
/*    */   @Nonnull
/* 26 */   private final RequiredArg<BuilderInfo> roleArg = withRequiredArg("role", "server.commands.npc.role.role.desc", (ArgumentType)NPCCommand.NPC_ROLE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NPCRoleCommand() {
/* 32 */     super("role", "server.commands.npc.role.desc");
/* 33 */     addUsageVariant((AbstractCommand)new GetRoleCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 43 */     BuilderInfo roleInfo = (BuilderInfo)this.roleArg.get(context);
/* 44 */     if (npc.getRole().isRoleChangeRequested()) {
/* 45 */       context.sendMessage(Message.translation("server.commands.npc.role.unableToSetRole"));
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     RoleChangeSystem.requestRoleChange(ref, npc.getRole(), roleInfo.getIndex(), true, store);
/* 50 */     context.sendMessage(Message.translation("server.commands.npc.role.roleSet")
/* 51 */         .param("role", roleInfo.getKeyName()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class GetRoleCommand
/*    */     extends NPCWorldCommandBase
/*    */   {
/*    */     public GetRoleCommand() {
/* 62 */       super("server.commands.npc.role.get.desc");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 72 */       context.sendMessage(Message.translation("server.commands.npc.role.npcHasRole")
/* 73 */           .param("role", npc.getRoleName()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCRoleCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */