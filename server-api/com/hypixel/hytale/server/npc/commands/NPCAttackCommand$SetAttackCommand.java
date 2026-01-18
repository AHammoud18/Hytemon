/*    */ package com.hypixel.hytale.server.npc.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.support.CombatSupport;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetAttackCommand
/*    */   extends NPCWorldCommandBase
/*    */ {
/*    */   @Nonnull
/* 41 */   private final OptionalArg<List<Interaction>> attackArg = withListOptionalArg("attack", "server.commands.npc.attack.sequence", (ArgumentType)ArgTypes.INTERACTION_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SetAttackCommand() {
/* 47 */     super("", "server.commands.npc.attack.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 58 */     if (!this.attackArg.provided(context)) {
/*    */       return;
/*    */     }
/*    */     
/* 62 */     List<Interaction> sequences = (List<Interaction>)this.attackArg.get(context);
/* 63 */     CombatSupport combatSupport = npc.getRole().getCombatSupport();
/* 64 */     combatSupport.clearAttackOverrides();
/* 65 */     for (Interaction sequence : sequences)
/* 66 */       combatSupport.addAttackOverride(sequence.getId()); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCAttackCommand$SetAttackCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */