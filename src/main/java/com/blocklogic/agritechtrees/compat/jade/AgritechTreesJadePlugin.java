package com.blocklogic.agritechtrees.compat.jade;

import com.blocklogic.agritechtrees.block.custom.AgritechTreesPlanterBlock;
import com.blocklogic.agritechtrees.block.custom.AgritechTreesHoppingPlanterBlock;
import com.blocklogic.agritechtrees.block.entity.AgritechTreesPlanterBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class AgritechTreesJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(TreePlanterProvider.INSTANCE, AgritechTreesPlanterBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(TreePlanterProvider.INSTANCE, AgritechTreesPlanterBlock.class);
        registration.registerBlockComponent(TreePlanterProvider.INSTANCE, AgritechTreesHoppingPlanterBlock.class);
    }
}