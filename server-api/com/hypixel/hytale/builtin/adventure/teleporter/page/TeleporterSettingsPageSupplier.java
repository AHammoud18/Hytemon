/*    */ package com.hypixel.hytale.builtin.adventure.teleporter.page;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.teleporter.component.Teleporter;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.protocol.BlockPosition;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class TeleporterSettingsPageSupplier
/*    */   implements OpenCustomUIInteraction.CustomPageSupplier
/*    */ {
/*    */   public static final BuilderCodec<TeleporterSettingsPageSupplier> CODEC;
/*    */   
/*    */   static {
/* 49 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TeleporterSettingsPageSupplier.class, TeleporterSettingsPageSupplier::new).appendInherited(new KeyedCodec("Create", (Codec)Codec.BOOLEAN), (supplier, b) -> supplier.create = b.booleanValue(), supplier -> Boolean.valueOf(supplier.create), (supplier, parent) -> supplier.create = parent.create).add()).appendInherited(new KeyedCodec("Mode", TeleporterSettingsPage.Mode.CODEC), (supplier, o) -> supplier.mode = o, supplier -> supplier.mode, (supplier, parent) -> supplier.mode = parent.mode).add()).appendInherited(new KeyedCodec("ActiveState", (Codec)Codec.STRING), (supplier, o) -> supplier.activeState = o, supplier -> supplier.activeState, (supplier, parent) -> supplier.activeState = parent.activeState).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean create = true;
/*    */   
/* 55 */   private TeleporterSettingsPage.Mode mode = TeleporterSettingsPage.Mode.FULL;
/*    */   
/*    */   @Nullable
/*    */   private String activeState;
/*    */   
/*    */   @Nullable
/*    */   public CustomUIPage tryCreate(@Nonnull Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor, @Nonnull PlayerRef playerRef, @Nonnull InteractionContext context) {
/* 62 */     BlockPosition targetBlock = context.getTargetBlock();
/* 63 */     if (targetBlock == null) return null;
/*    */     
/* 65 */     Store<EntityStore> store = ref.getStore();
/* 66 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 68 */     ChunkStore chunkStore = world.getChunkStore();
/* 69 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/* 70 */     BlockComponentChunk blockComponentChunk = (chunkRef == null) ? null : (BlockComponentChunk)chunkStore.getStore().getComponent(chunkRef, BlockComponentChunk.getComponentType());
/* 71 */     if (blockComponentChunk == null) return null;
/*    */     
/* 73 */     int blockIndex = ChunkUtil.indexBlockInColumn(targetBlock.x, targetBlock.y, targetBlock.z);
/* 74 */     Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndex);
/* 75 */     if (blockRef == null || !blockRef.isValid()) {
/* 76 */       if (!this.create) return null;
/*    */       
/* 78 */       Holder<ChunkStore> holder = ChunkStore.REGISTRY.newHolder();
/* 79 */       holder.putComponent(BlockModule.BlockStateInfo.getComponentType(), (Component)new BlockModule.BlockStateInfo(blockIndex, chunkRef));
/* 80 */       holder.ensureComponent(Teleporter.getComponentType());
/* 81 */       blockRef = world.getChunkStore().getStore().addEntity(holder, AddReason.SPAWN);
/*    */     } 
/*    */     
/* 84 */     return (CustomUIPage)new TeleporterSettingsPage(playerRef, blockRef, this.mode, this.activeState);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\teleporter\page\TeleporterSettingsPageSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */