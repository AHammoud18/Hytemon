/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.path.PathPlugin;
/*    */ import com.hypixel.hytale.builtin.path.WorldPathData;
/*    */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabPathListCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   public PrefabPathListCommand() {
/* 24 */     super("list", "server.commands.npcpath.list.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 29 */     WorldPathData worldPathDataResource = (WorldPathData)store.getResource(WorldPathData.getResourceType());
/* 30 */     List<IPrefabPath> paths = worldPathDataResource.getAllPrefabPaths();
/*    */ 
/*    */     
/* 33 */     StringBuilder sb = new StringBuilder("Active prefab paths:\n");
/* 34 */     Message msg = Message.translation("server.npc.npcpath.list.prefabPaths");
/* 35 */     for (IPrefabPath path : paths) {
/* 36 */       sb.append(' ').append(path.getWorldGenId()).append('.').append(path.getId());
/* 37 */       sb.append(" (").append(path.getName()).append(')');
/* 38 */       sb.append(" [ Length: ").append(path.length());
/* 39 */       sb.append(", Loaded nodes: ").append(path.loadedWaypointCount()).append(" ]\n");
/*    */       
/* 41 */       msg.insert(Message.translation("server.npc.npcpath.list.details")
/* 42 */           .param("worldGenId", path.getWorldGenId())
/* 43 */           .param("pathId", path.getId().toString())
/* 44 */           .param("pathName", path.getName())
/* 45 */           .param("length", path.length())
/* 46 */           .param("count", path.loadedWaypointCount()));
/*    */     } 
/*    */     
/* 49 */     PathPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/* 50 */     context.sendMessage(msg);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */