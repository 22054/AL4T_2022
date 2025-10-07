package model;

import manager.GameEngine;
import model.prize.Prize;

/**
 * Capability for objects that can react when Mario bumps them from below.
 * Keeps Map subtype-agnostic by letting collision code call this behavior
 * without knowing concrete classes.
 */
public interface IBumpable {
    /**
     * Called when Mario hits the bottom of this object with his head.
     * May return a Prize to be revealed/spawned.
     */
    Prize onHeadBump(GameEngine engine);

    /**
     * Whether this object should be considered solid for collision purposes
     * (top/bottom/side). For example, a breaking brick may become non-solid
     * during its breaking animation.
     */
    default boolean isSolidForCollision() { return true; }
}
