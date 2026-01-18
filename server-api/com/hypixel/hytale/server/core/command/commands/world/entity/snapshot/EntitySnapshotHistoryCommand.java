/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity.snapshot;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.entity.EntitySnapshot;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.SnapshotBuffer;
/*    */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntitySnapshotHistoryCommand extends AbstractWorldCommand {
/*    */   public EntitySnapshotHistoryCommand() {
/* 25 */     super("history", "server.commands.entity.snapshot.history.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 30 */     ComponentType<EntityStore, SnapshotBuffer> snapshotBufferComponentType = SnapshotBuffer.getComponentType();
/*    */     
/* 32 */     store.forEachChunk((Query)snapshotBufferComponentType, (chunk, cmdBuffer) -> {
/*    */           for (int idx = 0; idx < chunk.size(); idx++) {
/*    */             SnapshotBuffer snapshotBufferComponent = (SnapshotBuffer)chunk.getComponent(idx, snapshotBufferComponentType);
/*    */             assert snapshotBufferComponent != null;
/*    */             if (!snapshotBufferComponent.isInitialized())
/*    */               return; 
/*    */             for (int i = snapshotBufferComponent.getOldestTickIndex(); i <= snapshotBufferComponent.getCurrentTickIndex(); i++) {
/*    */               EntitySnapshot snapshot = snapshotBufferComponent.getSnapshot(i);
/*    */               assert snapshot != null;
/*    */               Vector3d pos = snapshot.getPosition();
/*    */               SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)cmdBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
/*    */               ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/*    */               playerSpatialResource.getSpatialStructure().collect(pos, 75.0D, (List)results);
/*    */               ParticleUtil.spawnParticleEffect("Example_Simple", pos.x, pos.y, pos.z, (List)results, (ComponentAccessor)cmdBuffer);
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\snapshot\EntitySnapshotHistoryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */