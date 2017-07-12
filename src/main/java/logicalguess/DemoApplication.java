package logicalguess;

import logicalguess.statemachine.Events;
import logicalguess.statemachine.States;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner
{
    @Autowired
    private StateMachineFactory<States, Events> factory;

    public static void main(String[] args)
    {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        StateMachine<States, Events> stateMachine = factory.getStateMachine();

        //stateMachine.sendEvent(Events.E1);
        //stateMachine.sendEvent(Events.E2);

        stateMachine.sendEvent(Events.C1);
        stateMachine.sendEvent(Events.C2);

        System.out.println("Current state: " + stateMachine.getState().getId());
    }
}
