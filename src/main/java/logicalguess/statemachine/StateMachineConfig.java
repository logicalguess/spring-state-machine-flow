package logicalguess.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .stateEntry(States.S1, logic1())
                .stateEntry(States.S2, logic2())
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.SI).target(States.S1).event(Events.C1).action(validate1())
                .and()
                .withExternal()
                .source(States.SI).target(States.S1).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.C2).action(validate2())
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    @Bean
    public Action<States, Events> validate1() {
        return ctx -> {
            System.out.println("Validation1 for " + ctx.getTarget().getId());
            //throw new RuntimeException();
        };
    }


    @Bean
    public Action<States, Events> validate2() {
        return ctx -> {
            System.out.println("Validation2 for " + ctx.getTarget().getId());
            //throw new RuntimeException();
        };
    }

    @Bean
    public Action<States, Events> logic1() {
        return ctx -> {
            System.out.println(
                    "Logic1 for " + ctx.getTarget().getId());
            //throw new RuntimeException();

        };
    }

    @Bean
    public Action<States, Events> logic2() {
        return ctx -> {
            System.out.println(
                    "Logic2 for " + ctx.getTarget().getId());
            //throw new RuntimeException();

        };
    }
}