/*    */ package com.hypixel.hytale.server.core.asset.type.particle.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
/*    */ import com.hypixel.hytale.server.core.asset.type.particle.pages.ParticleSpawnPage;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ParticleSpawnCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 33 */   private final RequiredArg<ParticleSystem> particleSystemArg = withRequiredArg("particle", "server.commands.particle.spawn.particle.desc", (ArgumentType)ArgTypes.PARTICLE_SYSTEM);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParticleSpawnCommand() {
/* 39 */     super("spawn", "server.commands.particle.spawn.desc");
/*    */ 
/*    */     
/* 42 */     addUsageVariant((AbstractCommand)new ParticleSpawnPageCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 47 */     ParticleSystem particleSystem = (ParticleSystem)this.particleSystemArg.get(context);
/*    */     
/* 49 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 50 */     assert transformComponent != null;
/*    */     
/* 52 */     Vector3d position = transformComponent.getPosition();
/* 53 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 54 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 55 */     playerSpatialResource.getSpatialStructure().collect(position, 75.0D, (List)results);
/*    */     
/* 57 */     ParticleUtil.spawnParticleEffect(particleSystem.getId(), position, transformComponent.getRotation(), (List)results, (ComponentAccessor)store);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class ParticleSpawnPageCommand
/*    */     extends AbstractTargetPlayerCommand
/*    */   {
/*    */     public ParticleSpawnPageCommand() {
/* 68 */       super("server.commands.particle.spawn.page.desc");
/*    */     }
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 73 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 74 */       assert playerComponent != null;
/* 75 */       playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new ParticleSpawnPage(playerRef));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\commands\ParticleSpawnCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */