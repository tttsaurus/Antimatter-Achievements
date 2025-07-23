package com.tttsaurus.antiachieve.gui.mvvm;

import com.tttsaurus.ingameinfo.common.core.mvvm.view.View;

public class AchievementPageView extends View
{
    @Override
    public String getIxmlFileName()
    {
        return "achievement_page";
    }

    @Override
    public String getDefaultIxml()
    {
        return """
                <Def focused = true>
                <SizedGroup width = 100 height = 100 backgroundStyle = "mc-vanilla">
                
                </Group>
                """;
    }
}
