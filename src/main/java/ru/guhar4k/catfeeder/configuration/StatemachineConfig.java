package ru.guhar4k.catfeeder.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import ru.guhar4k.catfeeder.model.status.Event;
import ru.guhar4k.catfeeder.model.status.State;

@Configuration
public class StatemachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {
}
