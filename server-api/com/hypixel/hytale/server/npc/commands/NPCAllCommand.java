/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.Frozen;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class NPCAllCommand extends AbstractPlayerCommand {
/*     */   @Nonnull
/*  31 */   private static final Message MESSAGE_COMMANDS_NPC_ALL_NO_ROLES_TO_SPAWN = Message.translation("server.commands.npc.all.noRolesToSpawn");
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  36 */   private final OptionalArg<Double> distanceArg = (OptionalArg<Double>)
/*  37 */     withOptionalArg("distance", "server.commands.npc.all.distance", (ArgumentType)ArgTypes.DOUBLE)
/*  38 */     .addValidator(Validators.greaterThan(Double.valueOf(0.0D)));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCAllCommand() {
/*  44 */     super("all", "server.commands.npc.all.desc", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  54 */     double distance = this.distanceArg.provided(context) ? ((Double)this.distanceArg.get(context)).doubleValue() : 4.0D;
/*  55 */     NPCPlugin npcModule = NPCPlugin.get();
/*  56 */     List<String> roles = npcModule.getRoleTemplateNames(true);
/*     */     
/*  58 */     if (roles.isEmpty()) {
/*  59 */       playerRef.sendMessage(MESSAGE_COMMANDS_NPC_ALL_NO_ROLES_TO_SPAWN);
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     roles.sort(String::compareToIgnoreCase);
/*     */     
/*  65 */     int columns = MathUtil.ceil(Math.sqrt(roles.size()));
/*  66 */     double squareSideLength = (columns - 1) * distance;
/*  67 */     double squareSideLengthHalf = squareSideLength / 2.0D;
/*     */     
/*  69 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  70 */     assert transformComponent != null;
/*     */     
/*  72 */     Vector3d position = transformComponent.getPosition();
/*  73 */     double px = position.getX() - squareSideLengthHalf;
/*  74 */     double pz = position.getZ() - squareSideLengthHalf;
/*     */     
/*  76 */     Vector3d pos = new Vector3d();
/*     */     
/*  78 */     for (int index = 0; index < roles.size(); index++) {
/*  79 */       String name = roles.get(index);
/*  80 */       if (name != null && !name.isEmpty())
/*     */         
/*     */         try {
/*  83 */           double x = px + distance * (index % columns);
/*  84 */           double z = pz + distance * (index / columns);
/*  85 */           double y = NPCPhysicsMath.heightOverGround(world, x, z);
/*  86 */           if (y >= 0.0D)
/*  87 */           { pos.assign(x, y, z);
/*     */             
/*  89 */             int roleIndex = npcModule.getIndex(name);
/*  90 */             if (roleIndex < 0) throw new IllegalStateException("No such valid role: " + name);
/*     */             
/*  92 */             Pair<Ref<EntityStore>, NPCEntity> npcPair = npcModule.spawnEntity(store, roleIndex, pos, null, null, null);
/*  93 */             Ref<EntityStore> npcRef = (Ref<EntityStore>)npcPair.first();
/*  94 */             assert npcRef != null;
/*     */ 
/*     */             
/*  97 */             store.putComponent(npcRef, Nameplate.getComponentType(), (Component)new Nameplate(name));
/*     */             
/*  99 */             store.ensureComponent(npcRef, Frozen.getComponentType()); } 
/* 100 */         } catch (Throwable t) {
/* 101 */           playerRef.sendMessage(Message.translation("server.commands.npc.all.failedToSpawn")
/* 102 */               .param("role", name));
/* 103 */           npcModule.getLogger().at(Level.WARNING).log("Error spawning NPC with role: %s", name, t);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCAllCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */