package dev.katcodes.forgedentropy.events;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.db.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.*;

public class ChaosEventRegistry {
    private static int last_index = 0;
    private static final Random random = new Random();
    /**
     * Event registry
     */
    public static HashMap<String, Supplier<ChaosEvent>> EntropyEvents;

    /**
     * Registers events
     */
    public static void Register() {
        EntropyEvents = new HashMap<>();
        EntropyEvents.put("AddHeartEvent", AddHeartEvent::new);
        EntropyEvents.put("AdventureEvent", AdventureEvent::new);
        EntropyEvents.put("AngryBeeEvent", AngryBeeEvent::new);
        EntropyEvents.put("ArmorCurseEvent", ArmorCurseEvent::new);
        EntropyEvents.put("ArrowRainEvent",ArrowRainEvent::new);
        EntropyEvents.put("BeeEvent",BeeEvent::new);
        EntropyEvents.put("BlackAndWhiteEvent",BlackAndWhiteEvent::new);
        EntropyEvents.put("BlazeEvent",BlazeEvent::new);
        EntropyEvents.put("BlindnessEvent",BlindnessEvent::new);
        EntropyEvents.put("BlurEvent", BlurEvent::new);
        EntropyEvents.put("BulldozeEvent", BulldozeEvent::new);
        EntropyEvents.put("ChickenRainEvent",ChickenRainEvent::new);
        EntropyEvents.put("CinematicScreenEvent",CinematicScreenEvent::new);
        EntropyEvents.put("CloseRandomTPEvent",CloseRandomTPEvent::new);
        EntropyEvents.put("ConstantAttackingEvent",ConstantAttackingEvent::new);
        EntropyEvents.put("ConstantInteractingEvent", ConstantInteractingEvent::new);
        EntropyEvents.put("CreativeFlightEvent",CreativeFlightEvent::new);
        EntropyEvents.put("CreeperEvent", CreeperEvent::new);

        EntropyEvents.put("HideEventsEvent", HideEventsEvent::new);
        EntropyEvents.put("HyperChickenRainEvent",HyperChickenRainEvent::new);
    }


    /**
     * Get a random event that isn't in the event list already
     * @param events event list to iterate over
     * @return a new Chaos Event
     */
    public static ChaosEvent getRandomDifferentEvent(List<ChaosEvent> events) {
        ArrayList<String> eventKeys = new ArrayList<>(EntropyEvents.keySet());
        eventKeys.removeAll(Config.disabledEvents);
        Set<String> ignoreCurrentEvents = new HashSet<>();
        events.forEach(event -> ignoreCurrentEvents.add(event.getClass().getSimpleName()));
        if(eventKeys.size()>ignoreCurrentEvents.size())
            eventKeys.removeAll(ignoreCurrentEvents);
        Set<String> ignoreTypes = new HashSet<>();
        events.forEach(event -> {
            if(event.getTickCount()>0 && !event.hasEnded() && !event.type().equalsIgnoreCase("none"))
                ignoreTypes.add(event.type().toLowerCase());
        });
        Set<String> ignoreEventsByType = new HashSet<>();
        eventKeys.forEach(eventName -> {
            if(ignoreTypes.contains(EntropyEvents.get(eventName).get().type().toLowerCase())){
                ignoreEventsByType.add(eventName);
            }
        });
        if(eventKeys.size()>ignoreEventsByType.size())
            eventKeys.removeAll(ignoreEventsByType);
        if(FMLEnvironment.dist!= Dist.DEDICATED_SERVER)
        {
            eventKeys.remove("StutteringEvent");
        }
        return getRandomEvent(eventKeys);
    }

    /**
     * Gets a random event from the list of keys
     * @param eventKeys keys to choose from
     * @return new Chaos Event
     */
    private static ChaosEvent getRandomEvent(List<String> eventKeys) {
        if(eventKeys.isEmpty())
            return null;
        int index = random.nextInt(eventKeys.size());
        String newEventName = eventKeys.get(index);
        ChaosEvent event = EntropyEvents.get(newEventName).get();
        if(Config.accessibilityMode && event.isDisabledByAccessibilityMode()) {
            eventKeys.remove(index);
            return getRandomEvent(eventKeys);
        }
        return event;
    }

    /**
     * Get a chaos event by the name
     * @param eventName Event name
     * @return new Chaos event
     */
    public static ChaosEvent get(String eventName) {
        Supplier<ChaosEvent> newEvent = EntropyEvents.get(eventName);
        if(newEvent!=null)
            return newEvent.get();
        else
            return null;
    }

    /**
     * Gets the next event in order
     * @return Next Chaos Event
     */
    public static ChaosEvent getNextOrdered() {
        Supplier<ChaosEvent> newEvent = EntropyEvents.get(EntropyEvents.keySet().stream().sorted().toList().get(last_index));
        last_index = (last_index+1) % EntropyEvents.size();
        if(newEvent!=null)
            return newEvent.get();
        else
            return null;
    }

    /**
     * Gets the event ID from the event class
     * @param event Chaos event to check
     * @return ID of Chaos Event
     */
    public static String getEventId(ChaosEvent event) {
        String[] name=event.getClass().getName().split("\\.");
        return name[name.length-1];
    }

    /**
     * Gets the translation key for an event
     * @param event Event to check
     * @return translation key for the event
     */
    public static String getTranslationKey(ChaosEvent event) {
        return "forged_entropy.events." + getEventId(event);
    }

    /**
     * Gets translation key for event name
     * @param eventID event name to check
     * @return translation key for the event
     */
    public static String getTranslationKey(String eventID) {
        return "forged_entropy.events." + eventID;
    }

}
