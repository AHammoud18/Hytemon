/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.commands.world.entity.snapshot.EntitySnapshotSubCommand;
/*    */ import com.hypixel.hytale.server.core.command.commands.world.entity.stats.EntityStatsSubCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public EntityCommand() {
/* 16 */     super("entity", "server.commands.entity.desc");
/* 17 */     addAliases(new String[] { "entities" });
/* 18 */     addSubCommand((AbstractCommand)new EntityCloneCommand());
/* 19 */     addSubCommand((AbstractCommand)new EntityRemoveCommand());
/* 20 */     addSubCommand((AbstractCommand)new EntityDumpCommand());
/* 21 */     addSubCommand((AbstractCommand)new EntityCleanCommand());
/* 22 */     addSubCommand((AbstractCommand)new EntityLodCommand());
/* 23 */     addSubCommand((AbstractCommand)new EntityTrackerCommand());
/* 24 */     addSubCommand((AbstractCommand)new EntityResendCommand());
/* 25 */     addSubCommand((AbstractCommand)new EntityNameplateCommand());
/* 26 */     addSubCommand((AbstractCommand)new EntityStatsSubCommand());
/* 27 */     addSubCommand((AbstractCommand)new EntitySnapshotSubCommand());
/* 28 */     addSubCommand((AbstractCommand)new EntityEffectCommand());
/* 29 */     addSubCommand((AbstractCommand)new EntityMakeInteractableCommand());
/* 30 */     addSubCommand((AbstractCommand)new EntityIntangibleCommand());
/* 31 */     addSubCommand((AbstractCommand)new EntityInvulnerableCommand());
/* 32 */     addSubCommand((AbstractCommand)new EntityHideFromAdventurePlayersCommand());
/* 33 */     addSubCommand((AbstractCommand)new EntityCountCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */