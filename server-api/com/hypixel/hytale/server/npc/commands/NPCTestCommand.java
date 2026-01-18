/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.collision.BoxBlockIntersectionEvaluator;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionConfig;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.util.PositionProbeAir;
/*     */ import com.hypixel.hytale.server.npc.util.PositionProbeWater;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class NPCTestCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public NPCTestCommand() {
/*  37 */     super("test", "server.commands.npc.test.desc");
/*  38 */     addSubCommand((AbstractCommand)new ProbeTestCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ProbeTestCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     public ProbeTestCommand() {
/*  49 */       super("probe", "server.commands.npc.test.probe.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  59 */       CollisionResult collisionResult = new CollisionResult();
/*  60 */       PositionProbeAir probeAir = new PositionProbeAir();
/*  61 */       PositionProbeWater probeWater = new PositionProbeWater();
/*     */       
/*  63 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  64 */       assert playerComponent != null;
/*     */       
/*  66 */       BoundingBox boundingBoxComponent = (BoundingBox)store.getComponent(ref, BoundingBox.getComponentType());
/*  67 */       assert boundingBoxComponent != null;
/*     */       
/*  69 */       Box playerCollider = boundingBoxComponent.getBoundingBox();
/*     */       
/*  71 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  72 */       assert transformComponent != null;
/*     */       
/*  74 */       ModelComponent modelComponent = (ModelComponent)store.getComponent(ref, ModelComponent.getComponentType());
/*  75 */       assert modelComponent != null;
/*     */       
/*  77 */       Vector3d position = transformComponent.getPosition();
/*  78 */       Model model = modelComponent.getModel();
/*     */       
/*  80 */       float eyeHeight = (model != null) ? model.getEyeHeight(ref, (ComponentAccessor)store) : 0.0F;
/*     */       
/*  82 */       boolean testAir = probeAir.probePosition(ref, playerCollider, position, collisionResult, (ComponentAccessor)store);
/*  83 */       boolean testWater = probeWater.probePosition(ref, playerCollider, position, collisionResult, eyeHeight, (ComponentAccessor)store);
/*  84 */       boolean validatePosition = (CollisionModule.get().validatePosition(world, playerCollider, position, 4, null, (_this, collisionCode, collision, collisionConfig) -> (collisionConfig.blockId != -1), collisionResult) != -1);
/*     */ 
/*     */       
/*  87 */       NPCPlugin npcPlugin = NPCPlugin.get();
/*     */       
/*  89 */       String text = "Pos Y [" + position.y + ", " + String.valueOf(playerCollider) + "] Height=" + world.getChunk(ChunkUtil.indexChunkFromBlock(position.x, position.z)).getHeight(MathUtil.floor(position.x), MathUtil.floor(position.z));
/*     */       
/*  91 */       context.sendMessage(Message.raw(text));
/*  92 */       npcPlugin.getLogger().at(Level.INFO).log(text);
/*     */       
/*  94 */       text = "Air " + testAir + " " + String.valueOf(probeAir);
/*     */       
/*  96 */       context.sendMessage(Message.raw(text));
/*  97 */       npcPlugin.getLogger().at(Level.INFO).log(text);
/*     */       
/*  99 */       text = "Water " + testWater + " " + String.valueOf(probeWater);
/*     */       
/* 101 */       context.sendMessage(Message.raw(text));
/* 102 */       npcPlugin.getLogger().at(Level.INFO).log(text);
/*     */       
/* 104 */       text = "ValidatePosition " + validatePosition;
/*     */       
/* 106 */       context.sendMessage(Message.raw(text));
/* 107 */       npcPlugin.getLogger().at(Level.INFO).log(text);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCTestCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */