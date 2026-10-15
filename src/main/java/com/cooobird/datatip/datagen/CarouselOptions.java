package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.content.CarouselContent;

import java.util.List;
import java.util.Objects;

/**
 * CarouselContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class CarouselOptions {
    private List<TipContent> frames;
    private Integer intervalSeconds;
    private CarouselContent.TransitionType transition;

    public CarouselOptions frames(List<TipContent> value) {
        frames = List.copyOf(value);
        return this;
    }

    public CarouselOptions intervalSeconds(int value) {
        intervalSeconds = value;
        return this;
    }

    public CarouselOptions transition(CarouselContent.TransitionType value) {
        transition = Objects.requireNonNull(value, "transition");
        return this;
    }

    public CarouselOptions transition(String value) {
        return transition(CarouselContent.TransitionType.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public CarouselContent apply(CarouselContent source) {
        Objects.requireNonNull(source, "source");
        return new CarouselContent(
            frames != null ? frames : source.getFrames(),
            intervalSeconds != null ? intervalSeconds : source.getIntervalSeconds(),
            transition != null ? transition : source.getTransition()
        );
    }
}
