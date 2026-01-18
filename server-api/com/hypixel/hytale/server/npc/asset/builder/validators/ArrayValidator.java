package com.hypixel.hytale.server.npc.asset.builder.validators;

import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectArrayHelper;

public abstract class ArrayValidator extends Validator {
  public abstract boolean test(BuilderObjectArrayHelper<?, ?> paramBuilderObjectArrayHelper);
  
  public abstract String errorMessage(String paramString, BuilderObjectArrayHelper<?, ?> paramBuilderObjectArrayHelper);
  
  public abstract String errorMessage(BuilderObjectArrayHelper<?, ?> paramBuilderObjectArrayHelper);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\ArrayValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */