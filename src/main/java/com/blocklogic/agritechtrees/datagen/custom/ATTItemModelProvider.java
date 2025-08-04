package com.blocklogic.agritechtrees.datagen.custom;

import com.blocklogic.agritechtrees.AgritechTrees;
import com.blocklogic.agritechtrees.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ATTItemModelProvider extends ItemModelProvider {
    public ATTItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AgritechTrees.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.HOPPING_UPGRADE.get());
    }
}
