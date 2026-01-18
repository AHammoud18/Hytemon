/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateJsonAsset;
/*     */ import com.hypixel.hytale.protocol.packets.assets.UpdateRecipes;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Connect;
/*     */ import com.hypixel.hytale.protocol.packets.machinima.RequestMachinimaActorModel;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateEnvironmentMusic;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public final class PacketRegistry {
/*  14 */   private static final Map<Integer, PacketInfo> BY_ID = new HashMap<>();
/*  15 */   private static final Map<Integer, PacketInfo> BY_ID_UNMODIFIABLE = Collections.unmodifiableMap(BY_ID);
/*  16 */   private static final Map<Class<? extends Packet>, Integer> BY_TYPE = new HashMap<>();
/*     */   
/*     */   static {
/*  19 */     register(0, "Connect", (Class)Connect.class, 82, 38161, false, Connect::validateStructure, Connect::deserialize);
/*  20 */     register(1, "Disconnect", (Class)Disconnect.class, 2, 16384007, false, Disconnect::validateStructure, Disconnect::deserialize);
/*  21 */     register(2, "Ping", (Class)Ping.class, 29, 29, false, Ping::validateStructure, Ping::deserialize);
/*  22 */     register(3, "Pong", (Class)Pong.class, 20, 20, false, Pong::validateStructure, Pong::deserialize);
/*  23 */     register(10, "Status", (Class)Status.class, 9, 2587, false, Status::validateStructure, Status::deserialize);
/*  24 */     register(11, "AuthGrant", (Class)AuthGrant.class, 1, 49171, false, AuthGrant::validateStructure, AuthGrant::deserialize);
/*  25 */     register(12, "AuthToken", (Class)AuthToken.class, 1, 49171, false, AuthToken::validateStructure, AuthToken::deserialize);
/*  26 */     register(13, "ServerAuthToken", (Class)ServerAuthToken.class, 1, 32851, false, ServerAuthToken::validateStructure, ServerAuthToken::deserialize);
/*  27 */     register(14, "ConnectAccept", (Class)ConnectAccept.class, 1, 70, false, ConnectAccept::validateStructure, ConnectAccept::deserialize);
/*  28 */     register(15, "PasswordResponse", (Class)PasswordResponse.class, 1, 70, false, PasswordResponse::validateStructure, PasswordResponse::deserialize);
/*  29 */     register(16, "PasswordAccepted", (Class)PasswordAccepted.class, 0, 0, false, PasswordAccepted::validateStructure, PasswordAccepted::deserialize);
/*  30 */     register(17, "PasswordRejected", (Class)PasswordRejected.class, 5, 74, false, PasswordRejected::validateStructure, PasswordRejected::deserialize);
/*  31 */     register(18, "ClientReferral", (Class)ClientReferral.class, 1, 5141, false, ClientReferral::validateStructure, ClientReferral::deserialize);
/*  32 */     register(20, "WorldSettings", (Class)WorldSettings.class, 5, 1677721600, true, WorldSettings::validateStructure, WorldSettings::deserialize);
/*  33 */     register(21, "WorldLoadProgress", (Class)WorldLoadProgress.class, 9, 16384014, false, WorldLoadProgress::validateStructure, WorldLoadProgress::deserialize);
/*  34 */     register(22, "WorldLoadFinished", (Class)WorldLoadFinished.class, 0, 0, false, WorldLoadFinished::validateStructure, WorldLoadFinished::deserialize);
/*  35 */     register(23, "RequestAssets", (Class)RequestAssets.class, 1, 1677721600, true, RequestAssets::validateStructure, RequestAssets::deserialize);
/*  36 */     register(24, "AssetInitialize", (Class)AssetInitialize.class, 4, 2121, false, AssetInitialize::validateStructure, AssetInitialize::deserialize);
/*  37 */     register(25, "AssetPart", (Class)AssetPart.class, 1, 4096006, true, AssetPart::validateStructure, AssetPart::deserialize);
/*  38 */     register(26, "AssetFinalize", (Class)AssetFinalize.class, 0, 0, false, AssetFinalize::validateStructure, AssetFinalize::deserialize);
/*  39 */     register(27, "RemoveAssets", (Class)RemoveAssets.class, 1, 1677721600, false, RemoveAssets::validateStructure, RemoveAssets::deserialize);
/*  40 */     register(28, "RequestCommonAssetsRebuild", (Class)RequestCommonAssetsRebuild.class, 0, 0, false, RequestCommonAssetsRebuild::validateStructure, RequestCommonAssetsRebuild::deserialize);
/*  41 */     register(29, "SetUpdateRate", (Class)SetUpdateRate.class, 4, 4, false, SetUpdateRate::validateStructure, SetUpdateRate::deserialize);
/*  42 */     register(30, "SetTimeDilation", (Class)SetTimeDilation.class, 4, 4, false, SetTimeDilation::validateStructure, SetTimeDilation::deserialize);
/*  43 */     register(31, "UpdateFeatures", (Class)UpdateFeatures.class, 1, 8192006, false, UpdateFeatures::validateStructure, UpdateFeatures::deserialize);
/*  44 */     register(32, "ViewRadius", (Class)ViewRadius.class, 4, 4, false, ViewRadius::validateStructure, ViewRadius::deserialize);
/*  45 */     register(33, "PlayerOptions", (Class)PlayerOptions.class, 1, 327680184, false, PlayerOptions::validateStructure, PlayerOptions::deserialize);
/*  46 */     register(34, "ServerTags", (Class)ServerTags.class, 1, 1677721600, false, ServerTags::validateStructure, ServerTags::deserialize);
/*  47 */     register(40, "UpdateBlockTypes", (Class)UpdateBlockTypes.class, 10, 1677721600, true, UpdateBlockTypes::validateStructure, UpdateBlockTypes::deserialize);
/*  48 */     register(41, "UpdateBlockHitboxes", (Class)UpdateBlockHitboxes.class, 6, 1677721600, true, UpdateBlockHitboxes::validateStructure, UpdateBlockHitboxes::deserialize);
/*  49 */     register(42, "UpdateBlockSoundSets", (Class)UpdateBlockSoundSets.class, 6, 1677721600, true, UpdateBlockSoundSets::validateStructure, UpdateBlockSoundSets::deserialize);
/*  50 */     register(43, "UpdateItemSoundSets", (Class)UpdateItemSoundSets.class, 6, 1677721600, true, UpdateItemSoundSets::validateStructure, UpdateItemSoundSets::deserialize);
/*  51 */     register(44, "UpdateBlockParticleSets", (Class)UpdateBlockParticleSets.class, 2, 1677721600, true, UpdateBlockParticleSets::validateStructure, UpdateBlockParticleSets::deserialize);
/*  52 */     register(45, "UpdateBlockBreakingDecals", (Class)UpdateBlockBreakingDecals.class, 2, 1677721600, true, UpdateBlockBreakingDecals::validateStructure, UpdateBlockBreakingDecals::deserialize);
/*  53 */     register(46, "UpdateBlockSets", (Class)UpdateBlockSets.class, 2, 1677721600, true, UpdateBlockSets::validateStructure, UpdateBlockSets::deserialize);
/*  54 */     register(47, "UpdateWeathers", (Class)UpdateWeathers.class, 6, 1677721600, true, UpdateWeathers::validateStructure, UpdateWeathers::deserialize);
/*  55 */     register(48, "UpdateTrails", (Class)UpdateTrails.class, 2, 1677721600, true, UpdateTrails::validateStructure, UpdateTrails::deserialize);
/*  56 */     register(49, "UpdateParticleSystems", (Class)UpdateParticleSystems.class, 2, 1677721600, true, UpdateParticleSystems::validateStructure, UpdateParticleSystems::deserialize);
/*  57 */     register(50, "UpdateParticleSpawners", (Class)UpdateParticleSpawners.class, 2, 1677721600, true, UpdateParticleSpawners::validateStructure, UpdateParticleSpawners::deserialize);
/*  58 */     register(51, "UpdateEntityEffects", (Class)UpdateEntityEffects.class, 6, 1677721600, true, UpdateEntityEffects::validateStructure, UpdateEntityEffects::deserialize);
/*  59 */     register(52, "UpdateItemPlayerAnimations", (Class)UpdateItemPlayerAnimations.class, 2, 1677721600, true, UpdateItemPlayerAnimations::validateStructure, UpdateItemPlayerAnimations::deserialize);
/*  60 */     register(53, "UpdateModelvfxs", (Class)UpdateModelvfxs.class, 6, 1677721600, true, UpdateModelvfxs::validateStructure, UpdateModelvfxs::deserialize);
/*  61 */     register(54, "UpdateItems", (Class)UpdateItems.class, 4, 1677721600, true, UpdateItems::validateStructure, UpdateItems::deserialize);
/*  62 */     register(55, "UpdateItemQualities", (Class)UpdateItemQualities.class, 6, 1677721600, true, UpdateItemQualities::validateStructure, UpdateItemQualities::deserialize);
/*  63 */     register(56, "UpdateItemCategories", (Class)UpdateItemCategories.class, 2, 1677721600, true, UpdateItemCategories::validateStructure, UpdateItemCategories::deserialize);
/*  64 */     register(57, "UpdateItemReticles", (Class)UpdateItemReticles.class, 6, 1677721600, true, UpdateItemReticles::validateStructure, UpdateItemReticles::deserialize);
/*  65 */     register(58, "UpdateFieldcraftCategories", (Class)UpdateFieldcraftCategories.class, 2, 1677721600, true, UpdateFieldcraftCategories::validateStructure, UpdateFieldcraftCategories::deserialize);
/*  66 */     register(59, "UpdateResourceTypes", (Class)UpdateResourceTypes.class, 2, 1677721600, true, UpdateResourceTypes::validateStructure, UpdateResourceTypes::deserialize);
/*  67 */     register(60, "UpdateRecipes", (Class)UpdateRecipes.class, 2, 1677721600, true, UpdateRecipes::validateStructure, UpdateRecipes::deserialize);
/*  68 */     register(61, "UpdateEnvironments", (Class)UpdateEnvironments.class, 7, 1677721600, true, UpdateEnvironments::validateStructure, UpdateEnvironments::deserialize);
/*  69 */     register(62, "UpdateAmbienceFX", (Class)UpdateAmbienceFX.class, 6, 1677721600, true, UpdateAmbienceFX::validateStructure, UpdateAmbienceFX::deserialize);
/*  70 */     register(63, "UpdateFluidFX", (Class)UpdateFluidFX.class, 6, 1677721600, true, UpdateFluidFX::validateStructure, UpdateFluidFX::deserialize);
/*  71 */     register(64, "UpdateTranslations", (Class)UpdateTranslations.class, 2, 1677721600, true, UpdateTranslations::validateStructure, UpdateTranslations::deserialize);
/*  72 */     register(65, "UpdateSoundEvents", (Class)UpdateSoundEvents.class, 6, 1677721600, true, UpdateSoundEvents::validateStructure, UpdateSoundEvents::deserialize);
/*  73 */     register(66, "UpdateInteractions", (Class)UpdateInteractions.class, 6, 1677721600, true, UpdateInteractions::validateStructure, UpdateInteractions::deserialize);
/*  74 */     register(67, "UpdateRootInteractions", (Class)UpdateRootInteractions.class, 6, 1677721600, true, UpdateRootInteractions::validateStructure, UpdateRootInteractions::deserialize);
/*  75 */     register(68, "UpdateUnarmedInteractions", (Class)UpdateUnarmedInteractions.class, 2, 20480007, true, UpdateUnarmedInteractions::validateStructure, UpdateUnarmedInteractions::deserialize);
/*  76 */     register(69, "TrackOrUpdateObjective", (Class)TrackOrUpdateObjective.class, 1, 1677721600, false, TrackOrUpdateObjective::validateStructure, TrackOrUpdateObjective::deserialize);
/*  77 */     register(70, "UntrackObjective", (Class)UntrackObjective.class, 16, 16, false, UntrackObjective::validateStructure, UntrackObjective::deserialize);
/*  78 */     register(71, "UpdateObjectiveTask", (Class)UpdateObjectiveTask.class, 21, 16384035, false, UpdateObjectiveTask::validateStructure, UpdateObjectiveTask::deserialize);
/*  79 */     register(72, "UpdateEntityStatTypes", (Class)UpdateEntityStatTypes.class, 6, 1677721600, true, UpdateEntityStatTypes::validateStructure, UpdateEntityStatTypes::deserialize);
/*  80 */     register(73, "UpdateEntityUIComponents", (Class)UpdateEntityUIComponents.class, 6, 1677721600, true, UpdateEntityUIComponents::validateStructure, UpdateEntityUIComponents::deserialize);
/*  81 */     register(74, "UpdateHitboxCollisionConfig", (Class)UpdateHitboxCollisionConfig.class, 6, 36864011, true, UpdateHitboxCollisionConfig::validateStructure, UpdateHitboxCollisionConfig::deserialize);
/*  82 */     register(75, "UpdateRepulsionConfig", (Class)UpdateRepulsionConfig.class, 6, 65536011, true, UpdateRepulsionConfig::validateStructure, UpdateRepulsionConfig::deserialize);
/*  83 */     register(76, "UpdateViewBobbing", (Class)UpdateViewBobbing.class, 2, 1677721600, true, UpdateViewBobbing::validateStructure, UpdateViewBobbing::deserialize);
/*  84 */     register(77, "UpdateCameraShake", (Class)UpdateCameraShake.class, 2, 1677721600, true, UpdateCameraShake::validateStructure, UpdateCameraShake::deserialize);
/*  85 */     register(78, "UpdateBlockGroups", (Class)UpdateBlockGroups.class, 2, 1677721600, true, UpdateBlockGroups::validateStructure, UpdateBlockGroups::deserialize);
/*  86 */     register(79, "UpdateSoundSets", (Class)UpdateSoundSets.class, 6, 1677721600, true, UpdateSoundSets::validateStructure, UpdateSoundSets::deserialize);
/*  87 */     register(80, "UpdateAudioCategories", (Class)UpdateAudioCategories.class, 6, 1677721600, true, UpdateAudioCategories::validateStructure, UpdateAudioCategories::deserialize);
/*  88 */     register(81, "UpdateReverbEffects", (Class)UpdateReverbEffects.class, 6, 1677721600, true, UpdateReverbEffects::validateStructure, UpdateReverbEffects::deserialize);
/*  89 */     register(82, "UpdateEqualizerEffects", (Class)UpdateEqualizerEffects.class, 6, 1677721600, true, UpdateEqualizerEffects::validateStructure, UpdateEqualizerEffects::deserialize);
/*  90 */     register(83, "UpdateFluids", (Class)UpdateFluids.class, 6, 1677721600, true, UpdateFluids::validateStructure, UpdateFluids::deserialize);
/*  91 */     register(84, "UpdateTagPatterns", (Class)UpdateTagPatterns.class, 6, 1677721600, true, UpdateTagPatterns::validateStructure, UpdateTagPatterns::deserialize);
/*  92 */     register(85, "UpdateProjectileConfigs", (Class)UpdateProjectileConfigs.class, 2, 1677721600, true, UpdateProjectileConfigs::validateStructure, UpdateProjectileConfigs::deserialize);
/*  93 */     register(100, "SetClientId", (Class)SetClientId.class, 4, 4, false, SetClientId::validateStructure, SetClientId::deserialize);
/*  94 */     register(101, "SetGameMode", (Class)SetGameMode.class, 1, 1, false, SetGameMode::validateStructure, SetGameMode::deserialize);
/*  95 */     register(102, "SetMovementStates", (Class)SetMovementStates.class, 2, 2, false, SetMovementStates::validateStructure, SetMovementStates::deserialize);
/*  96 */     register(103, "SetBlockPlacementOverride", (Class)SetBlockPlacementOverride.class, 1, 1, false, SetBlockPlacementOverride::validateStructure, SetBlockPlacementOverride::deserialize);
/*  97 */     register(104, "JoinWorld", (Class)JoinWorld.class, 18, 18, false, JoinWorld::validateStructure, JoinWorld::deserialize);
/*  98 */     register(105, "ClientReady", (Class)ClientReady.class, 2, 2, false, ClientReady::validateStructure, ClientReady::deserialize);
/*  99 */     register(106, "LoadHotbar", (Class)LoadHotbar.class, 1, 1, false, LoadHotbar::validateStructure, LoadHotbar::deserialize);
/* 100 */     register(107, "SaveHotbar", (Class)SaveHotbar.class, 1, 1, false, SaveHotbar::validateStructure, SaveHotbar::deserialize);
/* 101 */     register(108, "ClientMovement", (Class)ClientMovement.class, 153, 153, false, ClientMovement::validateStructure, ClientMovement::deserialize);
/* 102 */     register(109, "ClientTeleport", (Class)ClientTeleport.class, 52, 52, false, ClientTeleport::validateStructure, ClientTeleport::deserialize);
/* 103 */     register(110, "UpdateMovementSettings", (Class)UpdateMovementSettings.class, 252, 252, false, UpdateMovementSettings::validateStructure, UpdateMovementSettings::deserialize);
/* 104 */     register(111, "MouseInteraction", (Class)MouseInteraction.class, 44, 20480071, false, MouseInteraction::validateStructure, MouseInteraction::deserialize);
/* 105 */     register(112, "DamageInfo", (Class)DamageInfo.class, 29, 32768048, false, DamageInfo::validateStructure, DamageInfo::deserialize);
/* 106 */     register(113, "ReticleEvent", (Class)ReticleEvent.class, 4, 4, false, ReticleEvent::validateStructure, ReticleEvent::deserialize);
/* 107 */     register(114, "DisplayDebug", (Class)DisplayDebug.class, 19, 32768037, false, DisplayDebug::validateStructure, DisplayDebug::deserialize);
/* 108 */     register(115, "ClearDebugShapes", (Class)ClearDebugShapes.class, 0, 0, false, ClearDebugShapes::validateStructure, ClearDebugShapes::deserialize);
/* 109 */     register(116, "SyncPlayerPreferences", (Class)SyncPlayerPreferences.class, 8, 8, false, SyncPlayerPreferences::validateStructure, SyncPlayerPreferences::deserialize);
/* 110 */     register(117, "ClientPlaceBlock", (Class)ClientPlaceBlock.class, 20, 20, false, ClientPlaceBlock::validateStructure, ClientPlaceBlock::deserialize);
/* 111 */     register(118, "UpdateMemoriesFeatureStatus", (Class)UpdateMemoriesFeatureStatus.class, 1, 1, false, UpdateMemoriesFeatureStatus::validateStructure, UpdateMemoriesFeatureStatus::deserialize);
/* 112 */     register(119, "RemoveMapMarker", (Class)RemoveMapMarker.class, 1, 16384006, false, RemoveMapMarker::validateStructure, RemoveMapMarker::deserialize);
/* 113 */     register(131, "SetChunk", (Class)SetChunk.class, 13, 12288040, true, SetChunk::validateStructure, SetChunk::deserialize);
/* 114 */     register(132, "SetChunkHeightmap", (Class)SetChunkHeightmap.class, 9, 4096014, true, SetChunkHeightmap::validateStructure, SetChunkHeightmap::deserialize);
/* 115 */     register(133, "SetChunkTintmap", (Class)SetChunkTintmap.class, 9, 4096014, true, SetChunkTintmap::validateStructure, SetChunkTintmap::deserialize);
/* 116 */     register(134, "SetChunkEnvironments", (Class)SetChunkEnvironments.class, 9, 4096014, true, SetChunkEnvironments::validateStructure, SetChunkEnvironments::deserialize);
/* 117 */     register(135, "UnloadChunk", (Class)UnloadChunk.class, 8, 8, false, UnloadChunk::validateStructure, UnloadChunk::deserialize);
/* 118 */     register(136, "SetFluids", (Class)SetFluids.class, 13, 4096018, true, SetFluids::validateStructure, SetFluids::deserialize);
/* 119 */     register(140, "ServerSetBlock", (Class)ServerSetBlock.class, 19, 19, false, ServerSetBlock::validateStructure, ServerSetBlock::deserialize);
/* 120 */     register(141, "ServerSetBlocks", (Class)ServerSetBlocks.class, 12, 36864017, false, ServerSetBlocks::validateStructure, ServerSetBlocks::deserialize);
/* 121 */     register(142, "ServerSetFluid", (Class)ServerSetFluid.class, 17, 17, false, ServerSetFluid::validateStructure, ServerSetFluid::deserialize);
/* 122 */     register(143, "ServerSetFluids", (Class)ServerSetFluids.class, 12, 28672017, false, ServerSetFluids::validateStructure, ServerSetFluids::deserialize);
/* 123 */     register(144, "UpdateBlockDamage", (Class)UpdateBlockDamage.class, 21, 21, false, UpdateBlockDamage::validateStructure, UpdateBlockDamage::deserialize);
/* 124 */     register(145, "UpdateTimeSettings", (Class)UpdateTimeSettings.class, 10, 10, false, UpdateTimeSettings::validateStructure, UpdateTimeSettings::deserialize);
/* 125 */     register(146, "UpdateTime", (Class)UpdateTime.class, 13, 13, false, UpdateTime::validateStructure, UpdateTime::deserialize);
/* 126 */     register(147, "UpdateEditorTimeOverride", (Class)UpdateEditorTimeOverride.class, 14, 14, false, UpdateEditorTimeOverride::validateStructure, UpdateEditorTimeOverride::deserialize);
/* 127 */     register(148, "ClearEditorTimeOverride", (Class)ClearEditorTimeOverride.class, 0, 0, false, ClearEditorTimeOverride::validateStructure, ClearEditorTimeOverride::deserialize);
/* 128 */     register(149, "UpdateWeather", (Class)UpdateWeather.class, 8, 8, false, UpdateWeather::validateStructure, UpdateWeather::deserialize);
/* 129 */     register(150, "UpdateEditorWeatherOverride", (Class)UpdateEditorWeatherOverride.class, 4, 4, false, UpdateEditorWeatherOverride::validateStructure, UpdateEditorWeatherOverride::deserialize);
/* 130 */     register(151, "UpdateEnvironmentMusic", (Class)UpdateEnvironmentMusic.class, 4, 4, false, UpdateEnvironmentMusic::validateStructure, UpdateEnvironmentMusic::deserialize);
/* 131 */     register(152, "SpawnParticleSystem", (Class)SpawnParticleSystem.class, 44, 16384049, false, SpawnParticleSystem::validateStructure, SpawnParticleSystem::deserialize);
/* 132 */     register(153, "SpawnBlockParticleSystem", (Class)SpawnBlockParticleSystem.class, 30, 30, false, SpawnBlockParticleSystem::validateStructure, SpawnBlockParticleSystem::deserialize);
/* 133 */     register(154, "PlaySoundEvent2D", (Class)PlaySoundEvent2D.class, 13, 13, false, PlaySoundEvent2D::validateStructure, PlaySoundEvent2D::deserialize);
/* 134 */     register(155, "PlaySoundEvent3D", (Class)PlaySoundEvent3D.class, 38, 38, false, PlaySoundEvent3D::validateStructure, PlaySoundEvent3D::deserialize);
/* 135 */     register(156, "PlaySoundEventEntity", (Class)PlaySoundEventEntity.class, 16, 16, false, PlaySoundEventEntity::validateStructure, PlaySoundEventEntity::deserialize);
/* 136 */     register(157, "UpdateSleepState", (Class)UpdateSleepState.class, 36, 65536050, false, UpdateSleepState::validateStructure, UpdateSleepState::deserialize);
/* 137 */     register(158, "SetPaused", (Class)SetPaused.class, 1, 1, false, SetPaused::validateStructure, SetPaused::deserialize);
/* 138 */     register(159, "ServerSetPaused", (Class)ServerSetPaused.class, 1, 1, false, ServerSetPaused::validateStructure, ServerSetPaused::deserialize);
/* 139 */     register(160, "SetEntitySeed", (Class)SetEntitySeed.class, 4, 4, false, SetEntitySeed::validateStructure, SetEntitySeed::deserialize);
/* 140 */     register(161, "EntityUpdates", (Class)EntityUpdates.class, 1, 1677721600, true, EntityUpdates::validateStructure, EntityUpdates::deserialize);
/* 141 */     register(162, "PlayAnimation", (Class)PlayAnimation.class, 6, 32768024, false, PlayAnimation::validateStructure, PlayAnimation::deserialize);
/* 142 */     register(163, "ChangeVelocity", (Class)ChangeVelocity.class, 35, 35, false, ChangeVelocity::validateStructure, ChangeVelocity::deserialize);
/* 143 */     register(164, "ApplyKnockback", (Class)ApplyKnockback.class, 38, 38, false, ApplyKnockback::validateStructure, ApplyKnockback::deserialize);
/* 144 */     register(165, "SpawnModelParticles", (Class)SpawnModelParticles.class, 5, 1677721600, false, SpawnModelParticles::validateStructure, SpawnModelParticles::deserialize);
/* 145 */     register(166, "MountMovement", (Class)MountMovement.class, 59, 59, false, MountMovement::validateStructure, MountMovement::deserialize);
/* 146 */     register(170, "UpdatePlayerInventory", (Class)UpdatePlayerInventory.class, 2, 1677721600, true, UpdatePlayerInventory::validateStructure, UpdatePlayerInventory::deserialize);
/* 147 */     register(171, "SetCreativeItem", (Class)SetCreativeItem.class, 9, 16384019, false, SetCreativeItem::validateStructure, SetCreativeItem::deserialize);
/* 148 */     register(172, "DropCreativeItem", (Class)DropCreativeItem.class, 0, 16384010, false, DropCreativeItem::validateStructure, DropCreativeItem::deserialize);
/* 149 */     register(173, "SmartGiveCreativeItem", (Class)SmartGiveCreativeItem.class, 1, 16384011, false, SmartGiveCreativeItem::validateStructure, SmartGiveCreativeItem::deserialize);
/* 150 */     register(174, "DropItemStack", (Class)DropItemStack.class, 12, 12, false, DropItemStack::validateStructure, DropItemStack::deserialize);
/* 151 */     register(175, "MoveItemStack", (Class)MoveItemStack.class, 20, 20, false, MoveItemStack::validateStructure, MoveItemStack::deserialize);
/* 152 */     register(176, "SmartMoveItemStack", (Class)SmartMoveItemStack.class, 13, 13, false, SmartMoveItemStack::validateStructure, SmartMoveItemStack::deserialize);
/* 153 */     register(177, "SetActiveSlot", (Class)SetActiveSlot.class, 8, 8, false, SetActiveSlot::validateStructure, SetActiveSlot::deserialize);
/* 154 */     register(178, "SwitchHotbarBlockSet", (Class)SwitchHotbarBlockSet.class, 1, 16384006, false, SwitchHotbarBlockSet::validateStructure, SwitchHotbarBlockSet::deserialize);
/* 155 */     register(179, "InventoryAction", (Class)InventoryAction.class, 6, 6, false, InventoryAction::validateStructure, InventoryAction::deserialize);
/* 156 */     register(200, "OpenWindow", (Class)OpenWindow.class, 6, 1677721600, true, OpenWindow::validateStructure, OpenWindow::deserialize);
/* 157 */     register(201, "UpdateWindow", (Class)UpdateWindow.class, 5, 1677721600, true, UpdateWindow::validateStructure, UpdateWindow::deserialize);
/* 158 */     register(202, "CloseWindow", (Class)CloseWindow.class, 4, 4, false, CloseWindow::validateStructure, CloseWindow::deserialize);
/* 159 */     register(203, "SendWindowAction", (Class)SendWindowAction.class, 4, 32768027, false, SendWindowAction::validateStructure, SendWindowAction::deserialize);
/* 160 */     register(204, "ClientOpenWindow", (Class)ClientOpenWindow.class, 1, 1, false, ClientOpenWindow::validateStructure, ClientOpenWindow::deserialize);
/* 161 */     register(210, "ServerMessage", (Class)ServerMessage.class, 2, 1677721600, false, ServerMessage::validateStructure, ServerMessage::deserialize);
/* 162 */     register(211, "ChatMessage", (Class)ChatMessage.class, 1, 16384006, false, ChatMessage::validateStructure, ChatMessage::deserialize);
/* 163 */     register(212, "Notification", (Class)Notification.class, 2, 1677721600, false, Notification::validateStructure, Notification::deserialize);
/* 164 */     register(213, "KillFeedMessage", (Class)KillFeedMessage.class, 1, 1677721600, false, KillFeedMessage::validateStructure, KillFeedMessage::deserialize);
/* 165 */     register(214, "ShowEventTitle", (Class)ShowEventTitle.class, 14, 1677721600, false, ShowEventTitle::validateStructure, ShowEventTitle::deserialize);
/* 166 */     register(215, "HideEventTitle", (Class)HideEventTitle.class, 4, 4, false, HideEventTitle::validateStructure, HideEventTitle::deserialize);
/* 167 */     register(216, "SetPage", (Class)SetPage.class, 2, 2, false, SetPage::validateStructure, SetPage::deserialize);
/* 168 */     register(217, "CustomHud", (Class)CustomHud.class, 2, 1677721600, true, CustomHud::validateStructure, CustomHud::deserialize);
/* 169 */     register(218, "CustomPage", (Class)CustomPage.class, 4, 1677721600, true, CustomPage::validateStructure, CustomPage::deserialize);
/* 170 */     register(219, "CustomPageEvent", (Class)CustomPageEvent.class, 2, 16384007, false, CustomPageEvent::validateStructure, CustomPageEvent::deserialize);
/* 171 */     register(222, "EditorBlocksChange", (Class)EditorBlocksChange.class, 30, 139264048, true, EditorBlocksChange::validateStructure, EditorBlocksChange::deserialize);
/* 172 */     register(223, "ServerInfo", (Class)ServerInfo.class, 5, 32768023, false, ServerInfo::validateStructure, ServerInfo::deserialize);
/* 173 */     register(224, "AddToServerPlayerList", (Class)AddToServerPlayerList.class, 1, 1677721600, false, AddToServerPlayerList::validateStructure, AddToServerPlayerList::deserialize);
/* 174 */     register(225, "RemoveFromServerPlayerList", (Class)RemoveFromServerPlayerList.class, 1, 65536006, false, RemoveFromServerPlayerList::validateStructure, RemoveFromServerPlayerList::deserialize);
/* 175 */     register(226, "UpdateServerPlayerList", (Class)UpdateServerPlayerList.class, 1, 131072006, false, UpdateServerPlayerList::validateStructure, UpdateServerPlayerList::deserialize);
/* 176 */     register(227, "UpdateServerPlayerListPing", (Class)UpdateServerPlayerListPing.class, 1, 81920006, false, UpdateServerPlayerListPing::validateStructure, UpdateServerPlayerListPing::deserialize);
/* 177 */     register(228, "UpdateKnownRecipes", (Class)UpdateKnownRecipes.class, 1, 1677721600, false, UpdateKnownRecipes::validateStructure, UpdateKnownRecipes::deserialize);
/* 178 */     register(229, "UpdatePortal", (Class)UpdatePortal.class, 6, 16384020, false, UpdatePortal::validateStructure, UpdatePortal::deserialize);
/* 179 */     register(230, "UpdateVisibleHudComponents", (Class)UpdateVisibleHudComponents.class, 1, 4096006, false, UpdateVisibleHudComponents::validateStructure, UpdateVisibleHudComponents::deserialize);
/* 180 */     register(231, "ResetUserInterfaceState", (Class)ResetUserInterfaceState.class, 0, 0, false, ResetUserInterfaceState::validateStructure, ResetUserInterfaceState::deserialize);
/* 181 */     register(232, "UpdateLanguage", (Class)UpdateLanguage.class, 1, 16384006, false, UpdateLanguage::validateStructure, UpdateLanguage::deserialize);
/* 182 */     register(233, "WorldSavingStatus", (Class)WorldSavingStatus.class, 1, 1, false, WorldSavingStatus::validateStructure, WorldSavingStatus::deserialize);
/* 183 */     register(234, "OpenChatWithCommand", (Class)OpenChatWithCommand.class, 1, 16384006, false, OpenChatWithCommand::validateStructure, OpenChatWithCommand::deserialize);
/* 184 */     register(240, "UpdateWorldMapSettings", (Class)UpdateWorldMapSettings.class, 16, 1677721600, false, UpdateWorldMapSettings::validateStructure, UpdateWorldMapSettings::deserialize);
/* 185 */     register(241, "UpdateWorldMap", (Class)UpdateWorldMap.class, 1, 1677721600, true, UpdateWorldMap::validateStructure, UpdateWorldMap::deserialize);
/* 186 */     register(242, "ClearWorldMap", (Class)ClearWorldMap.class, 0, 0, false, ClearWorldMap::validateStructure, ClearWorldMap::deserialize);
/* 187 */     register(243, "UpdateWorldMapVisible", (Class)UpdateWorldMapVisible.class, 1, 1, false, UpdateWorldMapVisible::validateStructure, UpdateWorldMapVisible::deserialize);
/* 188 */     register(244, "TeleportToWorldMapMarker", (Class)TeleportToWorldMapMarker.class, 1, 16384006, false, TeleportToWorldMapMarker::validateStructure, TeleportToWorldMapMarker::deserialize);
/* 189 */     register(245, "TeleportToWorldMapPosition", (Class)TeleportToWorldMapPosition.class, 8, 8, false, TeleportToWorldMapPosition::validateStructure, TeleportToWorldMapPosition::deserialize);
/* 190 */     register(250, "RequestServerAccess", (Class)RequestServerAccess.class, 3, 3, false, RequestServerAccess::validateStructure, RequestServerAccess::deserialize);
/* 191 */     register(251, "UpdateServerAccess", (Class)UpdateServerAccess.class, 2, 1677721600, false, UpdateServerAccess::validateStructure, UpdateServerAccess::deserialize);
/* 192 */     register(252, "SetServerAccess", (Class)SetServerAccess.class, 2, 16384007, false, SetServerAccess::validateStructure, SetServerAccess::deserialize);
/* 193 */     register(260, "RequestMachinimaActorModel", (Class)RequestMachinimaActorModel.class, 1, 49152028, false, RequestMachinimaActorModel::validateStructure, RequestMachinimaActorModel::deserialize);
/* 194 */     register(261, "SetMachinimaActorModel", (Class)SetMachinimaActorModel.class, 1, 1677721600, false, SetMachinimaActorModel::validateStructure, SetMachinimaActorModel::deserialize);
/* 195 */     register(262, "UpdateMachinimaScene", (Class)UpdateMachinimaScene.class, 6, 36864033, true, UpdateMachinimaScene::validateStructure, UpdateMachinimaScene::deserialize);
/* 196 */     register(280, "SetServerCamera", (Class)SetServerCamera.class, 157, 157, false, SetServerCamera::validateStructure, SetServerCamera::deserialize);
/* 197 */     register(281, "CameraShakeEffect", (Class)CameraShakeEffect.class, 9, 9, false, CameraShakeEffect::validateStructure, CameraShakeEffect::deserialize);
/* 198 */     register(282, "RequestFlyCameraMode", (Class)RequestFlyCameraMode.class, 1, 1, false, RequestFlyCameraMode::validateStructure, RequestFlyCameraMode::deserialize);
/* 199 */     register(283, "SetFlyCameraMode", (Class)SetFlyCameraMode.class, 1, 1, false, SetFlyCameraMode::validateStructure, SetFlyCameraMode::deserialize);
/* 200 */     register(290, "SyncInteractionChains", (Class)SyncInteractionChains.class, 0, 1677721600, false, SyncInteractionChains::validateStructure, SyncInteractionChains::deserialize);
/* 201 */     register(291, "CancelInteractionChain", (Class)CancelInteractionChain.class, 5, 1038, false, CancelInteractionChain::validateStructure, CancelInteractionChain::deserialize);
/* 202 */     register(292, "PlayInteractionFor", (Class)PlayInteractionFor.class, 19, 16385065, false, PlayInteractionFor::validateStructure, PlayInteractionFor::deserialize);
/* 203 */     register(293, "MountNPC", (Class)MountNPC.class, 16, 16, false, MountNPC::validateStructure, MountNPC::deserialize);
/* 204 */     register(294, "DismountNPC", (Class)DismountNPC.class, 0, 0, false, DismountNPC::validateStructure, DismountNPC::deserialize);
/* 205 */     register(300, "FailureReply", (Class)FailureReply.class, 5, 1677721600, false, FailureReply::validateStructure, FailureReply::deserialize);
/* 206 */     register(301, "SuccessReply", (Class)SuccessReply.class, 5, 1677721600, false, SuccessReply::validateStructure, SuccessReply::deserialize);
/* 207 */     register(302, "AssetEditorInitialize", (Class)AssetEditorInitialize.class, 0, 0, false, AssetEditorInitialize::validateStructure, AssetEditorInitialize::deserialize);
/* 208 */     register(303, "AssetEditorAuthorization", (Class)AssetEditorAuthorization.class, 1, 1, false, AssetEditorAuthorization::validateStructure, AssetEditorAuthorization::deserialize);
/* 209 */     register(304, "AssetEditorCapabilities", (Class)AssetEditorCapabilities.class, 5, 5, false, AssetEditorCapabilities::validateStructure, AssetEditorCapabilities::deserialize);
/* 210 */     register(305, "AssetEditorSetupSchemas", (Class)AssetEditorSetupSchemas.class, 1, 1677721600, true, AssetEditorSetupSchemas::validateStructure, AssetEditorSetupSchemas::deserialize);
/* 211 */     register(306, "AssetEditorSetupAssetTypes", (Class)AssetEditorSetupAssetTypes.class, 1, 1677721600, false, AssetEditorSetupAssetTypes::validateStructure, AssetEditorSetupAssetTypes::deserialize);
/* 212 */     register(307, "AssetEditorCreateDirectory", (Class)AssetEditorCreateDirectory.class, 5, 32768024, false, AssetEditorCreateDirectory::validateStructure, AssetEditorCreateDirectory::deserialize);
/* 213 */     register(308, "AssetEditorDeleteDirectory", (Class)AssetEditorDeleteDirectory.class, 5, 32768024, false, AssetEditorDeleteDirectory::validateStructure, AssetEditorDeleteDirectory::deserialize);
/* 214 */     register(309, "AssetEditorRenameDirectory", (Class)AssetEditorRenameDirectory.class, 5, 65536051, false, AssetEditorRenameDirectory::validateStructure, AssetEditorRenameDirectory::deserialize);
/* 215 */     register(310, "AssetEditorFetchAsset", (Class)AssetEditorFetchAsset.class, 6, 32768025, false, AssetEditorFetchAsset::validateStructure, AssetEditorFetchAsset::deserialize);
/* 216 */     register(311, "AssetEditorFetchJsonAssetWithParents", (Class)AssetEditorFetchJsonAssetWithParents.class, 6, 32768025, false, AssetEditorFetchJsonAssetWithParents::validateStructure, AssetEditorFetchJsonAssetWithParents::deserialize);
/* 217 */     register(312, "AssetEditorFetchAssetReply", (Class)AssetEditorFetchAssetReply.class, 5, 4096010, false, AssetEditorFetchAssetReply::validateStructure, AssetEditorFetchAssetReply::deserialize);
/* 218 */     register(313, "AssetEditorFetchJsonAssetWithParentsReply", (Class)AssetEditorFetchJsonAssetWithParentsReply.class, 5, 1677721600, true, AssetEditorFetchJsonAssetWithParentsReply::validateStructure, AssetEditorFetchJsonAssetWithParentsReply::deserialize);
/* 219 */     register(314, "AssetEditorAssetPackSetup", (Class)AssetEditorAssetPackSetup.class, 1, 1677721600, false, AssetEditorAssetPackSetup::validateStructure, AssetEditorAssetPackSetup::deserialize);
/* 220 */     register(315, "AssetEditorUpdateAssetPack", (Class)AssetEditorUpdateAssetPack.class, 1, 1677721600, false, AssetEditorUpdateAssetPack::validateStructure, AssetEditorUpdateAssetPack::deserialize);
/* 221 */     register(316, "AssetEditorCreateAssetPack", (Class)AssetEditorCreateAssetPack.class, 5, 1677721600, false, AssetEditorCreateAssetPack::validateStructure, AssetEditorCreateAssetPack::deserialize);
/* 222 */     register(317, "AssetEditorDeleteAssetPack", (Class)AssetEditorDeleteAssetPack.class, 1, 16384006, false, AssetEditorDeleteAssetPack::validateStructure, AssetEditorDeleteAssetPack::deserialize);
/* 223 */     register(318, "AssetEditorEnableAssetPack", (Class)AssetEditorEnableAssetPack.class, 2, 16384007, false, AssetEditorEnableAssetPack::validateStructure, AssetEditorEnableAssetPack::deserialize);
/* 224 */     register(319, "AssetEditorAssetListSetup", (Class)AssetEditorAssetListSetup.class, 4, 1677721600, true, AssetEditorAssetListSetup::validateStructure, AssetEditorAssetListSetup::deserialize);
/* 225 */     register(320, "AssetEditorAssetListUpdate", (Class)AssetEditorAssetListUpdate.class, 1, 1677721600, true, AssetEditorAssetListUpdate::validateStructure, AssetEditorAssetListUpdate::deserialize);
/* 226 */     register(321, "AssetEditorRequestChildrenList", (Class)AssetEditorRequestChildrenList.class, 1, 32768020, false, AssetEditorRequestChildrenList::validateStructure, AssetEditorRequestChildrenList::deserialize);
/* 227 */     register(322, "AssetEditorRequestChildrenListReply", (Class)AssetEditorRequestChildrenListReply.class, 1, 1677721600, false, AssetEditorRequestChildrenListReply::validateStructure, AssetEditorRequestChildrenListReply::deserialize);
/* 228 */     register(323, "AssetEditorUpdateJsonAsset", (Class)AssetEditorUpdateJsonAsset.class, 9, 1677721600, true, AssetEditorUpdateJsonAsset::validateStructure, AssetEditorUpdateJsonAsset::deserialize);
/* 229 */     register(324, "AssetEditorUpdateAsset", (Class)AssetEditorUpdateAsset.class, 9, 53248050, false, AssetEditorUpdateAsset::validateStructure, AssetEditorUpdateAsset::deserialize);
/* 230 */     register(325, "AssetEditorJsonAssetUpdated", (Class)AssetEditorJsonAssetUpdated.class, 1, 1677721600, false, AssetEditorJsonAssetUpdated::validateStructure, AssetEditorJsonAssetUpdated::deserialize);
/* 231 */     register(326, "AssetEditorAssetUpdated", (Class)AssetEditorAssetUpdated.class, 1, 36864033, false, AssetEditorAssetUpdated::validateStructure, AssetEditorAssetUpdated::deserialize);
/* 232 */     register(327, "AssetEditorCreateAsset", (Class)AssetEditorCreateAsset.class, 10, 53248051, false, AssetEditorCreateAsset::validateStructure, AssetEditorCreateAsset::deserialize);
/* 233 */     register(328, "AssetEditorRenameAsset", (Class)AssetEditorRenameAsset.class, 5, 65536051, false, AssetEditorRenameAsset::validateStructure, AssetEditorRenameAsset::deserialize);
/* 234 */     register(329, "AssetEditorDeleteAsset", (Class)AssetEditorDeleteAsset.class, 5, 32768024, false, AssetEditorDeleteAsset::validateStructure, AssetEditorDeleteAsset::deserialize);
/* 235 */     register(330, "AssetEditorDiscardChanges", (Class)AssetEditorDiscardChanges.class, 1, 1677721600, false, AssetEditorDiscardChanges::validateStructure, AssetEditorDiscardChanges::deserialize);
/* 236 */     register(331, "AssetEditorFetchAutoCompleteData", (Class)AssetEditorFetchAutoCompleteData.class, 5, 32768023, false, AssetEditorFetchAutoCompleteData::validateStructure, AssetEditorFetchAutoCompleteData::deserialize);
/* 237 */     register(332, "AssetEditorFetchAutoCompleteDataReply", (Class)AssetEditorFetchAutoCompleteDataReply.class, 5, 1677721600, false, AssetEditorFetchAutoCompleteDataReply::validateStructure, AssetEditorFetchAutoCompleteDataReply::deserialize);
/* 238 */     register(333, "AssetEditorRequestDataset", (Class)AssetEditorRequestDataset.class, 1, 16384006, false, AssetEditorRequestDataset::validateStructure, AssetEditorRequestDataset::deserialize);
/* 239 */     register(334, "AssetEditorRequestDatasetReply", (Class)AssetEditorRequestDatasetReply.class, 1, 1677721600, false, AssetEditorRequestDatasetReply::validateStructure, AssetEditorRequestDatasetReply::deserialize);
/* 240 */     register(335, "AssetEditorActivateButton", (Class)AssetEditorActivateButton.class, 1, 16384006, false, AssetEditorActivateButton::validateStructure, AssetEditorActivateButton::deserialize);
/* 241 */     register(336, "AssetEditorSelectAsset", (Class)AssetEditorSelectAsset.class, 1, 32768020, false, AssetEditorSelectAsset::validateStructure, AssetEditorSelectAsset::deserialize);
/* 242 */     register(337, "AssetEditorPopupNotification", (Class)AssetEditorPopupNotification.class, 2, 1677721600, false, AssetEditorPopupNotification::validateStructure, AssetEditorPopupNotification::deserialize);
/* 243 */     register(338, "AssetEditorFetchLastModifiedAssets", (Class)AssetEditorFetchLastModifiedAssets.class, 0, 0, false, AssetEditorFetchLastModifiedAssets::validateStructure, AssetEditorFetchLastModifiedAssets::deserialize);
/* 244 */     register(339, "AssetEditorLastModifiedAssets", (Class)AssetEditorLastModifiedAssets.class, 1, 1677721600, false, AssetEditorLastModifiedAssets::validateStructure, AssetEditorLastModifiedAssets::deserialize);
/* 245 */     register(340, "AssetEditorModifiedAssetsCount", (Class)AssetEditorModifiedAssetsCount.class, 4, 4, false, AssetEditorModifiedAssetsCount::validateStructure, AssetEditorModifiedAssetsCount::deserialize);
/* 246 */     register(341, "AssetEditorSubscribeModifiedAssetsChanges", (Class)AssetEditorSubscribeModifiedAssetsChanges.class, 1, 1, false, AssetEditorSubscribeModifiedAssetsChanges::validateStructure, AssetEditorSubscribeModifiedAssetsChanges::deserialize);
/* 247 */     register(342, "AssetEditorExportAssets", (Class)AssetEditorExportAssets.class, 1, 1677721600, false, AssetEditorExportAssets::validateStructure, AssetEditorExportAssets::deserialize);
/* 248 */     register(343, "AssetEditorExportAssetInitialize", (Class)AssetEditorExportAssetInitialize.class, 6, 81920066, false, AssetEditorExportAssetInitialize::validateStructure, AssetEditorExportAssetInitialize::deserialize);
/* 249 */     register(344, "AssetEditorExportAssetPart", (Class)AssetEditorExportAssetPart.class, 1, 4096006, true, AssetEditorExportAssetPart::validateStructure, AssetEditorExportAssetPart::deserialize);
/* 250 */     register(345, "AssetEditorExportAssetFinalize", (Class)AssetEditorExportAssetFinalize.class, 0, 0, false, AssetEditorExportAssetFinalize::validateStructure, AssetEditorExportAssetFinalize::deserialize);
/* 251 */     register(346, "AssetEditorExportDeleteAssets", (Class)AssetEditorExportDeleteAssets.class, 1, 1677721600, false, AssetEditorExportDeleteAssets::validateStructure, AssetEditorExportDeleteAssets::deserialize);
/* 252 */     register(347, "AssetEditorExportComplete", (Class)AssetEditorExportComplete.class, 1, 1677721600, false, AssetEditorExportComplete::validateStructure, AssetEditorExportComplete::deserialize);
/* 253 */     register(348, "AssetEditorRebuildCaches", (Class)AssetEditorRebuildCaches.class, 5, 5, false, AssetEditorRebuildCaches::validateStructure, AssetEditorRebuildCaches::deserialize);
/* 254 */     register(349, "AssetEditorUndoChanges", (Class)AssetEditorUndoChanges.class, 5, 32768024, false, AssetEditorUndoChanges::validateStructure, AssetEditorUndoChanges::deserialize);
/* 255 */     register(350, "AssetEditorRedoChanges", (Class)AssetEditorRedoChanges.class, 5, 32768024, false, AssetEditorRedoChanges::validateStructure, AssetEditorRedoChanges::deserialize);
/* 256 */     register(351, "AssetEditorUndoRedoReply", (Class)AssetEditorUndoRedoReply.class, 5, 1677721600, false, AssetEditorUndoRedoReply::validateStructure, AssetEditorUndoRedoReply::deserialize);
/* 257 */     register(352, "AssetEditorSetGameTime", (Class)AssetEditorSetGameTime.class, 14, 14, false, AssetEditorSetGameTime::validateStructure, AssetEditorSetGameTime::deserialize);
/* 258 */     register(353, "AssetEditorUpdateSecondsPerGameDay", (Class)AssetEditorUpdateSecondsPerGameDay.class, 8, 8, false, AssetEditorUpdateSecondsPerGameDay::validateStructure, AssetEditorUpdateSecondsPerGameDay::deserialize);
/* 259 */     register(354, "AssetEditorUpdateWeatherPreviewLock", (Class)AssetEditorUpdateWeatherPreviewLock.class, 1, 1, false, AssetEditorUpdateWeatherPreviewLock::validateStructure, AssetEditorUpdateWeatherPreviewLock::deserialize);
/* 260 */     register(355, "AssetEditorUpdateModelPreview", (Class)AssetEditorUpdateModelPreview.class, 30, 1677721600, false, AssetEditorUpdateModelPreview::validateStructure, AssetEditorUpdateModelPreview::deserialize);
/* 261 */     register(360, "UpdateSunSettings", (Class)UpdateSunSettings.class, 8, 8, false, UpdateSunSettings::validateStructure, UpdateSunSettings::deserialize);
/* 262 */     register(361, "UpdatePostFxSettings", (Class)UpdatePostFxSettings.class, 20, 20, false, UpdatePostFxSettings::validateStructure, UpdatePostFxSettings::deserialize);
/* 263 */     register(400, "BuilderToolArgUpdate", (Class)BuilderToolArgUpdate.class, 14, 32768032, false, BuilderToolArgUpdate::validateStructure, BuilderToolArgUpdate::deserialize);
/* 264 */     register(401, "BuilderToolEntityAction", (Class)BuilderToolEntityAction.class, 5, 5, false, BuilderToolEntityAction::validateStructure, BuilderToolEntityAction::deserialize);
/* 265 */     register(402, "BuilderToolSetEntityTransform", (Class)BuilderToolSetEntityTransform.class, 54, 54, false, BuilderToolSetEntityTransform::validateStructure, BuilderToolSetEntityTransform::deserialize);
/* 266 */     register(403, "BuilderToolExtrudeAction", (Class)BuilderToolExtrudeAction.class, 24, 24, false, BuilderToolExtrudeAction::validateStructure, BuilderToolExtrudeAction::deserialize);
/* 267 */     register(404, "BuilderToolStackArea", (Class)BuilderToolStackArea.class, 41, 41, false, BuilderToolStackArea::validateStructure, BuilderToolStackArea::deserialize);
/* 268 */     register(405, "BuilderToolSelectionTransform", (Class)BuilderToolSelectionTransform.class, 52, 16384057, false, BuilderToolSelectionTransform::validateStructure, BuilderToolSelectionTransform::deserialize);
/* 269 */     register(406, "BuilderToolRotateClipboard", (Class)BuilderToolRotateClipboard.class, 5, 5, false, BuilderToolRotateClipboard::validateStructure, BuilderToolRotateClipboard::deserialize);
/* 270 */     register(407, "BuilderToolPasteClipboard", (Class)BuilderToolPasteClipboard.class, 12, 12, false, BuilderToolPasteClipboard::validateStructure, BuilderToolPasteClipboard::deserialize);
/* 271 */     register(408, "BuilderToolSetTransformationModeState", (Class)BuilderToolSetTransformationModeState.class, 1, 1, false, BuilderToolSetTransformationModeState::validateStructure, BuilderToolSetTransformationModeState::deserialize);
/* 272 */     register(409, "BuilderToolSelectionUpdate", (Class)BuilderToolSelectionUpdate.class, 24, 24, false, BuilderToolSelectionUpdate::validateStructure, BuilderToolSelectionUpdate::deserialize);
/* 273 */     register(410, "BuilderToolSelectionToolAskForClipboard", (Class)BuilderToolSelectionToolAskForClipboard.class, 0, 0, false, BuilderToolSelectionToolAskForClipboard::validateStructure, BuilderToolSelectionToolAskForClipboard::deserialize);
/* 274 */     register(411, "BuilderToolSelectionToolReplyWithClipboard", (Class)BuilderToolSelectionToolReplyWithClipboard.class, 1, 139264019, true, BuilderToolSelectionToolReplyWithClipboard::validateStructure, BuilderToolSelectionToolReplyWithClipboard::deserialize);
/* 275 */     register(412, "BuilderToolGeneralAction", (Class)BuilderToolGeneralAction.class, 1, 1, false, BuilderToolGeneralAction::validateStructure, BuilderToolGeneralAction::deserialize);
/* 276 */     register(413, "BuilderToolOnUseInteraction", (Class)BuilderToolOnUseInteraction.class, 57, 57, false, BuilderToolOnUseInteraction::validateStructure, BuilderToolOnUseInteraction::deserialize);
/* 277 */     register(414, "BuilderToolLineAction", (Class)BuilderToolLineAction.class, 24, 24, false, BuilderToolLineAction::validateStructure, BuilderToolLineAction::deserialize);
/* 278 */     register(415, "BuilderToolShowAnchor", (Class)BuilderToolShowAnchor.class, 12, 12, false, BuilderToolShowAnchor::validateStructure, BuilderToolShowAnchor::deserialize);
/* 279 */     register(416, "BuilderToolHideAnchors", (Class)BuilderToolHideAnchors.class, 0, 0, false, BuilderToolHideAnchors::validateStructure, BuilderToolHideAnchors::deserialize);
/* 280 */     register(417, "PrefabUnselectPrefab", (Class)PrefabUnselectPrefab.class, 0, 0, false, PrefabUnselectPrefab::validateStructure, PrefabUnselectPrefab::deserialize);
/* 281 */     register(418, "BuilderToolsSetSoundSet", (Class)BuilderToolsSetSoundSet.class, 4, 4, false, BuilderToolsSetSoundSet::validateStructure, BuilderToolsSetSoundSet::deserialize);
/* 282 */     register(419, "BuilderToolLaserPointer", (Class)BuilderToolLaserPointer.class, 36, 36, false, BuilderToolLaserPointer::validateStructure, BuilderToolLaserPointer::deserialize);
/* 283 */     register(420, "BuilderToolSetEntityScale", (Class)BuilderToolSetEntityScale.class, 8, 8, false, BuilderToolSetEntityScale::validateStructure, BuilderToolSetEntityScale::deserialize);
/* 284 */     register(421, "BuilderToolSetEntityPickupEnabled", (Class)BuilderToolSetEntityPickupEnabled.class, 5, 5, false, BuilderToolSetEntityPickupEnabled::validateStructure, BuilderToolSetEntityPickupEnabled::deserialize);
/* 285 */     register(422, "BuilderToolSetEntityLight", (Class)BuilderToolSetEntityLight.class, 9, 9, false, BuilderToolSetEntityLight::validateStructure, BuilderToolSetEntityLight::deserialize);
/* 286 */     register(423, "BuilderToolSetNPCDebug", (Class)BuilderToolSetNPCDebug.class, 5, 5, false, BuilderToolSetNPCDebug::validateStructure, BuilderToolSetNPCDebug::deserialize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void register(int id, String name, Class<? extends Packet> type, int fixedBlockSize, int maxSize, boolean compressed, BiFunction<ByteBuf, Integer, ValidationResult> validate, BiFunction<ByteBuf, Integer, Packet> deserialize) {
/* 292 */     PacketInfo existing = BY_ID.get(Integer.valueOf(id));
/* 293 */     if (existing != null) {
/* 294 */       throw new IllegalStateException("Duplicate packet ID " + id + ": '" + name + "' conflicts with '" + existing.name() + "'");
/*     */     }
/* 296 */     PacketInfo info = new PacketInfo(id, name, type, fixedBlockSize, maxSize, compressed, validate, deserialize);
/* 297 */     BY_ID.put(Integer.valueOf(id), info);
/* 298 */     BY_TYPE.put(type, Integer.valueOf(id));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static PacketInfo getById(int id) {
/* 303 */     return BY_ID.get(Integer.valueOf(id));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Integer getId(Class<? extends Packet> type) {
/* 308 */     return BY_TYPE.get(type);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Map<Integer, PacketInfo> all() {
/* 313 */     return BY_ID_UNMODIFIABLE; } public static final class PacketInfo extends Record { private final int id; @Nonnull private final String name; @Nonnull
/*     */     private final Class<? extends Packet> type; private final int fixedBlockSize; private final int maxSize; private final boolean compressed; @Nonnull
/*     */     private final BiFunction<ByteBuf, Integer, ValidationResult> validate; @Nonnull
/* 316 */     private final BiFunction<ByteBuf, Integer, Packet> deserialize; public PacketInfo(int id, @Nonnull String name, @Nonnull Class<? extends Packet> type, int fixedBlockSize, int maxSize, boolean compressed, @Nonnull BiFunction<ByteBuf, Integer, ValidationResult> validate, @Nonnull BiFunction<ByteBuf, Integer, Packet> deserialize) { this.id = id; this.name = name; this.type = type; this.fixedBlockSize = fixedBlockSize; this.maxSize = maxSize; this.compressed = compressed; this.validate = validate; this.deserialize = deserialize; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/protocol/PacketRegistry$PacketInfo;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #316	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/protocol/PacketRegistry$PacketInfo; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/protocol/PacketRegistry$PacketInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #316	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/protocol/PacketRegistry$PacketInfo; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/protocol/PacketRegistry$PacketInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #316	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/protocol/PacketRegistry$PacketInfo;
/* 316 */       //   0	8	1	o	Ljava/lang/Object; } public int id() { return this.id; } @Nonnull public String name() { return this.name; } @Nonnull public Class<? extends Packet> type() { return this.type; } public int fixedBlockSize() { return this.fixedBlockSize; } public int maxSize() { return this.maxSize; } public boolean compressed() { return this.compressed; } @Nonnull public BiFunction<ByteBuf, Integer, ValidationResult> validate() { return this.validate; } @Nonnull public BiFunction<ByteBuf, Integer, Packet> deserialize() { return this.deserialize; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\PacketRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */