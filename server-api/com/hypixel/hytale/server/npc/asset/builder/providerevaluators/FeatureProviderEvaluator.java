package com.hypixel.hytale.server.npc.asset.builder.providerevaluators;

import com.hypixel.hytale.server.npc.asset.builder.Feature;
import java.util.EnumSet;

public interface FeatureProviderEvaluator extends ProviderEvaluator {
  boolean provides(EnumSet<Feature> paramEnumSet);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\providerevaluators\FeatureProviderEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */