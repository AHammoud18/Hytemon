/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditingMetadata;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabEditUpdateBoxCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  30 */   private final FlagArg confirmAnchorDeletionArg = withFlagArg("confirm", "server.commands.editprefab.setbox.confirm.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabEditUpdateBoxCommand() {
/*  36 */     super("setBox", "server.commands.editprefab.setbox.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  41 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  42 */     assert playerComponent != null;
/*     */     
/*  44 */     UUID playerUUID = playerRef.getUuid();
/*     */     
/*  46 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/*  47 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerUUID);
/*     */     
/*  49 */     if (prefabEditSession == null) {
/*  50 */       context.sendMessage(Message.translation("server.commands.editprefab.notInEditSession"));
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     PrefabEditingMetadata selectedPrefab = prefabEditSession.getSelectedPrefab(playerUUID);
/*  55 */     if (selectedPrefab == null) {
/*  56 */       context.sendMessage(Message.translation("server.commands.editprefab.noPrefabSelected"));
/*     */       
/*     */       return;
/*     */     } 
/*  60 */     boolean didMoveAnchor = false;
/*     */     
/*  62 */     BlockSelection currSelection = BuilderToolsPlugin.getState(playerComponent, playerRef).getSelection();
/*  63 */     if (currSelection != null && !isLocationWithinSelection(selectedPrefab.getAnchorEntityPosition(), currSelection)) {
/*  64 */       if (!((Boolean)this.confirmAnchorDeletionArg.get(context)).booleanValue()) {
/*  65 */         context.sendMessage(Message.translation("server.commands.editprefab.setbox.anchorOutsideNewSelection"));
/*     */         return;
/*     */       } 
/*  68 */       didMoveAnchor = true;
/*  69 */       selectedPrefab.setAnchorPoint(currSelection.getSelectionMin(), world);
/*  70 */       selectedPrefab.sendAnchorHighlightingPacket(playerRef.getPacketHandler());
/*     */     } 
/*     */     
/*  73 */     boolean finalDidMoveAnchor = didMoveAnchor;
/*  74 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> {
/*     */           BlockSelection selection = s.getSelection();
/*     */           if (selection == null) {
/*     */             context.sendMessage(Message.translation("server.commands.editprefab.noSelection"));
/*     */             return;
/*     */           } 
/*     */           Vector3i selectionMin = selection.getSelectionMin();
/*     */           Vector3i selectionMax = selection.getSelectionMax();
/*     */           prefabEditSession.updatePrefabBounds(selectedPrefab.getUuid(), selectionMin, selectionMax);
/*     */           context.sendMessage(Message.translation("server.commands.editprefab.setbox.success"));
/*     */           if (finalDidMoveAnchor) {
/*     */             context.sendMessage(Message.translation("server.commands.editprefab.setbox.success.movedAnchor"));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocationWithinSelection(@Nonnull Vector3i location, @Nonnull BlockSelection selection) {
/*  98 */     Vector3i selectionMin = selection.getSelectionMin();
/*  99 */     Vector3i selectionMax = selection.getSelectionMax();
/* 100 */     return (location.x >= selectionMin.x && location.x <= selectionMax.x && location.y >= selectionMin.y && location.y <= selectionMax.y && location.z >= selectionMin.z && location.z <= selectionMax.z);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditUpdateBoxCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */