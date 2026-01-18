/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockGetStateCommand extends SimpleBlockCommand {
/*    */   public BlockGetStateCommand() {
/* 12 */     super("getstate", "server.commands.block.getstate.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeWithBlock(@Nonnull CommandContext context, @Nonnull WorldChunk chunk, int x, int y, int z) {
/* 17 */     CommandSender sender = context.sender();
/*    */     
/* 19 */     Ref<ChunkStore> ref = chunk.getBlockComponentEntity(x, y, z);
/* 20 */     if (ref == null)
/* 21 */       return;  StringBuilder stateString = new StringBuilder();
/* 22 */     Archetype<ChunkStore> archetype = ref.getStore().getArchetype(ref);
/* 23 */     for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/* 24 */       ComponentType<ChunkStore, ? extends Component<ChunkStore>> c = archetype.get(i);
/* 25 */       if (c != null)
/* 26 */         stateString.append(c.getTypeClass().getSimpleName())
/* 27 */           .append(" = ")
/* 28 */           .append(ref.getStore().getComponent(ref, c))
/* 29 */           .append('\n'); 
/*    */     } 
/* 31 */     sender.sendMessage(Message.translation("server.commands.block.getstate.info").param("x", x).param("y", y).param("z", z).param("state", stateString.toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockGetStateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */