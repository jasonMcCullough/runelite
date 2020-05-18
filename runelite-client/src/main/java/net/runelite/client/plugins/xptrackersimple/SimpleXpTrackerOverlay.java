package net.runelite.client.plugins.xptrackersimple;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;

public class SimpleXpTrackerOverlay extends Overlay {

    private final SimpleXpTrackerPlugin plugin;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    public SimpleXpTrackerOverlay(SimpleXpTrackerPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
        setPosition(OverlayPosition.BOTTOM_LEFT);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        panelComponent.getChildren().clear();

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Skill Summary")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Total XP:")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left(plugin.getTotalXpForSkill())
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Current Level:")
                .right(String.valueOf(plugin.getCurrentSkillLevel()))
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Target Level:")
                .right(plugin.getTargetSkillLevel())
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("XP Remaining:")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left(plugin.getXpToTargetLevel())
                .build());

        return panelComponent.render(graphics);
    }

}
