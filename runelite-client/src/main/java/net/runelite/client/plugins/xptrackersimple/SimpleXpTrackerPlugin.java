package net.runelite.client.plugins.xptrackersimple;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.text.NumberFormat;
import java.util.Locale;

@Slf4j
@PluginDescriptor(
        name = "Simple XP Tracker",
        description = "A simple XP tracker that displays the remaining XP to the player's target level."
)
public class SimpleXpTrackerPlugin extends Plugin {

    private Skill chosenSkill;
    private int targetLevel;

    @Inject
    private Client client;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private SimpleXpTrackerOverlay xpTrackerOverlay;
    @Inject
    private SimpleXpTrackerConfig xpTrackerConfig;

    @Provides
    SimpleXpTrackerConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(SimpleXpTrackerConfig.class);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        targetLevel = xpTrackerConfig.targetLevel();
        chosenSkill = xpTrackerConfig.chosenSkill();
    }

    @Override
    public void startUp() {
        overlayManager.add(xpTrackerOverlay);
    }

    @Override
    public void shutDown() {
        overlayManager.remove(xpTrackerOverlay);
    }

    public String getCurrentSkillLevel() {
        if(chosenSkill != null) {
            return String.valueOf(client.getRealSkillLevel(chosenSkill));
        } else {
            return String.valueOf(0);
        }
    }

    public String getTargetSkillLevel() {
        if(chosenSkill != null) {
            return String.valueOf(targetLevel);
        } else {
            return String.valueOf(0);
        }
    }

    public String getTotalXpForSkill() {
        if(chosenSkill != null) {
            return formatNumberWithCommas(client.getSkillExperience(chosenSkill));
        } else {
            return String.valueOf(0);
        }
    }

    public String getXpToTargetLevel() {
        if(chosenSkill != null) {
            long currentXp = client.getSkillExperience(chosenSkill);
            long targetXp = 0;

            for(int level = 1; level < targetLevel; level++) {
                targetXp += Math.floor(level + 300 * Math.pow(2, level/ 7.));
            }

            if(targetXp <= currentXp) {
                return String.valueOf(0);
            } else {
                return formatNumberWithCommas((targetXp/4) - currentXp);
            }
        } else {
            return String.valueOf(0);
        }
    }

    private String formatNumberWithCommas(long number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

}
