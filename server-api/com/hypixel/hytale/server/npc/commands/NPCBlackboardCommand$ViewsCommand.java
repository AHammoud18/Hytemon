/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.blocktype.BlockTypeView;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViewsCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   public ViewsCommand() {
/* 249 */     super("views", "server.commands.npc.blackboard.views.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 255 */     StringBuilder sb = new StringBuilder("Blackboard views:\n");
/* 256 */     Message msg = Message.translation("server.commands.npc.blackboard.views.title");
/* 257 */     Blackboard blackboard = (Blackboard)store.getResource(Blackboard.getResourceType());
/*     */     
/* 259 */     int[] count = { 0 };
/* 260 */     blackboard.forEachView(BlockTypeView.class, entry -> count[0] = count[0] + 1);
/* 261 */     sb.append(" Total partial views: ").append(count[0]).append('\n').append(" Views:\n");
/* 262 */     msg.insert(Message.translation("server.commands.npc.blackboard.views.partialViews").param("count", count[0]));
/* 263 */     msg.insert("\n");
/*     */     
/* 265 */     blackboard.forEachView(BlockTypeView.class, entry -> {
/*     */           sb.append("  View (").append(BlockTypeView.xOfViewIndex(entry.getIndex())).append(", ").append(BlockTypeView.zOfViewIndex(entry.getIndex()));
/*     */ 
/*     */           
/*     */           sb.append(") Entities: ").append(entry.getEntities().size()).append(", BlockSets: ").append(entry.getAllBlockSets().cardinality()).append('\n');
/*     */           
/*     */           msg.insert(Message.translation("server.commands.npc.blackboard.views.view").param("x", BlockTypeView.xOfViewIndex(entry.getIndex())).param("z", BlockTypeView.zOfViewIndex(entry.getIndex())).param("size", entry.getEntities().size()).param("cardinal", entry.getAllBlockSets().cardinality()));
/*     */           
/*     */           msg.insert("\n");
/*     */         });
/*     */     
/* 276 */     context.sendMessage(msg);
/* 277 */     NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCBlackboardCommand$ViewsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */