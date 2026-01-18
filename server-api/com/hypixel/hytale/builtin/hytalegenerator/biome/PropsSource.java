package com.hypixel.hytale.builtin.hytalegenerator.biome;

import com.hypixel.hytale.builtin.hytalegenerator.PropField;
import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
import java.util.List;

public interface PropsSource {
  List<PropField> getPropFields();
  
  List<Assignments> getAllPropDistributions();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\biome\PropsSource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */