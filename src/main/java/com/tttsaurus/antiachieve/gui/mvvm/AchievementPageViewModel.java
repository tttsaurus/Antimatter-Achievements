package com.tttsaurus.antiachieve.gui.mvvm;

import com.tttsaurus.antiachieve.AntimatterAchievements;
import com.tttsaurus.antiachieve.gui.AchievementGuiConstant;
import com.tttsaurus.ingameinfo.common.core.mvvm.binding.Reactive;
import com.tttsaurus.ingameinfo.common.core.mvvm.binding.ReactiveObject;
import com.tttsaurus.ingameinfo.common.core.mvvm.binding.SlotAccessor;
import com.tttsaurus.ingameinfo.common.core.mvvm.viewmodel.ViewModel;

public class AchievementPageViewModel extends ViewModel<AchievementPageView>
{
    @Reactive(targetUid = "main", property = "width", initiativeSync = true)
    public ReactiveObject<Float> mainWindowWidth = new ReactiveObject<>(){};

    @Reactive(targetUid = "main", property = "height", initiativeSync = true)
    public ReactiveObject<Float> mainWindowHeight = new ReactiveObject<>(){};

    @Reactive(targetUid = "grid")
    public SlotAccessor grid = new SlotAccessor();

    @Override
    public void onStart()
    {
        mainWindowWidth.set(AchievementGuiConstant.GUI_WIDTH);
        mainWindowHeight.set(AchievementGuiConstant.GUI_HEIGHT);
        grid.initComposeBlock(AchievementGridCompose.class);
    }

    @Override
    public void onFixedUpdate(double v)
    {

    }

    @Override
    public void onGuiOpen()
    {
        AntimatterAchievements.LOGGER.info("Achievement Page Opened");
    }

    @Override
    public void onGuiClose()
    {
        AntimatterAchievements.LOGGER.info("Achievement Page Closed");
    }
}
