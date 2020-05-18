package net.runelite.client.plugins.xptrackersimple;

import net.runelite.api.Skill;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("SimpleXpTrackerConfig")
public interface SimpleXpTrackerConfig extends Config {

    @ConfigItem(
            keyName="chosenSkill",
            name="Skill:",
            description = "",
            position=1
    )
    default Skill chosenSkill() { return Skill.ATTACK;}

    @ConfigItem(
            keyName="targetLevel",
            name="Target Level:",
            description = "",
            position=2
    )
    default int targetLevel() { return 0;}

}
