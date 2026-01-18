/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.RoleStats;
/*     */ import com.hypixel.hytale.server.npc.systems.PositionCacheSystems;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class NPCSensorStatsCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   public NPCSensorStatsCommand() {
/*  32 */     super("sensorstats", "server.commands.npc.sensorstats.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  42 */     NPCPlugin npcPlugin = NPCPlugin.get();
/*  43 */     List<String> roles = npcPlugin.getRoleTemplateNames(true);
/*  44 */     if (roles.isEmpty()) {
/*  45 */       context.sendMessage(Message.translation("server.commands.npc.sensorstats.noroles"));
/*     */       
/*     */       return;
/*     */     } 
/*  49 */     roles.sort(String::compareToIgnoreCase);
/*     */     
/*  51 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  52 */     assert transformComponent != null;
/*     */     
/*  54 */     Vector3d pos = new Vector3d(transformComponent.getPosition());
/*  55 */     String name = roles.getFirst();
/*  56 */     int roleIndex = NPCPlugin.get().getIndex(name);
/*  57 */     if (roleIndex < 0) {
/*  58 */       throw new IllegalStateException("No such valid role: " + name);
/*     */     }
/*     */     
/*  61 */     Pair<Ref<EntityStore>, NPCEntity> npcPair = npcPlugin.spawnEntity(store, roleIndex, pos, null, null, null);
/*  62 */     NPCEntity npcComponent = (NPCEntity)npcPair.second();
/*     */     
/*  64 */     StringBuilder out = new StringBuilder();
/*  65 */     RoleStats roleStats = new RoleStats();
/*     */     
/*  67 */     for (int i = 0; i < roles.size(); i++) {
/*  68 */       String roleName = roles.get(i);
/*     */       try {
/*  70 */         roleStats.clear();
/*     */         
/*  72 */         BuilderInfo builderInfo = NPCPlugin.get().prepareRoleBuilderInfo(NPCPlugin.get().getIndex(roleName));
/*     */         
/*  74 */         Builder<Role> roleBuilder = builderInfo.getBuilder();
/*  75 */         BuilderSupport builderSupport = new BuilderSupport(NPCPlugin.get().getBuilderManager(), npcComponent, EntityStore.REGISTRY.newHolder(), new ExecutionContext(), roleBuilder, roleStats);
/*     */         
/*  77 */         Role role = NPCPlugin.buildRole(roleBuilder, builderInfo, builderSupport, roleIndex);
/*  78 */         PositionCacheSystems.initialisePositionCache(role, builderSupport.getStateEvaluator(), 0.0D);
/*  79 */       } catch (Throwable t) {
/*  80 */         context.sendMessage(Message.translation("server.commands.npc.spawn.templateNotFound").param("template", roleName));
/*  81 */         npcPlugin.getLogger().at(Level.WARNING).log("Error spawning role " + roleName + ": " + t.getMessage());
/*     */       } 
/*     */ 
/*     */       
/*  85 */       if (!isRangesEmpty(roleStats, true)) {
/*  86 */         out.append('\n').append("PLY ");
/*  87 */         formatRanges(out, roleStats, "S=", true, RoleStats.RangeType.SORTED, 25);
/*  88 */         formatRanges(out, roleStats, "U=", true, RoleStats.RangeType.UNSORTED, 9);
/*  89 */         formatRanges(out, roleStats, "A=", true, RoleStats.RangeType.AVOIDANCE, 9);
/*  90 */         formatBuckets(out, roleStats, "B=", true, 20);
/*  91 */         out.append(roleName);
/*     */       } 
/*     */       
/*  94 */       if (!isRangesEmpty(roleStats, false)) {
/*  95 */         out.append('\n').append("ENT ");
/*  96 */         formatRanges(out, roleStats, "S=", false, RoleStats.RangeType.SORTED, 25);
/*  97 */         formatRanges(out, roleStats, "U=", false, RoleStats.RangeType.UNSORTED, 9);
/*  98 */         formatRanges(out, roleStats, "A=", false, RoleStats.RangeType.AVOIDANCE, 9);
/*  99 */         formatBuckets(out, roleStats, "B=", false, 20);
/* 100 */         out.append(roleName);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     npcPlugin.getLogger().at(Level.INFO).log(out.toString());
/* 105 */     npcComponent.remove();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isRangesEmpty(@Nonnull RoleStats roleStats, boolean isPlayer) {
/* 110 */     return (roleStats.getRanges(isPlayer, RoleStats.RangeType.SORTED) == null && roleStats
/* 111 */       .getRanges(isPlayer, RoleStats.RangeType.UNSORTED) == null && roleStats
/* 112 */       .getRanges(isPlayer, RoleStats.RangeType.AVOIDANCE) == null);
/*     */   }
/*     */   
/*     */   private static void formatBuckets(@Nonnull StringBuilder builder, @Nonnull RoleStats roleStats, @Nonnull String label, boolean isPlayer, int width) {
/* 116 */     builder.append(label);
/* 117 */     int length = builder.length();
/* 118 */     IntArrayList buckets = roleStats.getBuckets(isPlayer);
/* 119 */     for (int i = 0; i < buckets.size(); i++) {
/* 120 */       builder.append(buckets.getInt(i)).append(" ");
/*     */     }
/* 122 */     length = width + length - builder.length();
/* 123 */     if (length > 0) builder.append(" ".repeat(length)); 
/*     */   }
/*     */   
/*     */   private static void formatRanges(@Nonnull StringBuilder builder, @Nonnull RoleStats roleStats, @Nonnull String label, boolean isPlayer, @Nonnull RoleStats.RangeType rangeType, int width) {
/* 127 */     builder.append(label);
/* 128 */     int length = builder.length();
/*     */     
/* 130 */     int[] ranges = roleStats.getRangesSorted(isPlayer, rangeType);
/* 131 */     if (ranges != null && ranges.length != 0) {
/* 132 */       for (int range : ranges) {
/* 133 */         builder.append(range).append(" ");
/*     */       }
/*     */     } else {
/* 136 */       builder.append("- ");
/*     */     } 
/* 138 */     length = width + length - builder.length();
/* 139 */     if (length > 0) builder.append(" ".repeat(length)); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCSensorStatsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */