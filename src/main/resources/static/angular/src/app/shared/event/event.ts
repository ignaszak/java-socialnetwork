import {Subject} from "rxjs/Subject";
import {EventHandler} from "./event-handler";
import {Message} from "./message";

export abstract class Event {

    public static eventHandler: Subject<any> = new Subject();
    private handler: EventHandler;

    constructor() {
        this.handler = new EventHandler();
    }

    protected addEvent(type: string, message: Object) {
        this.handler.add(type, message);
    }

    protected listenToEvents(): void {
        Event.eventHandler.subscribe(response => this.handler.run(response));
    }

    protected sendEvent<T>(type: string, message: T): void {
        Event.eventHandler.next(new Message<T>(type, message));
    }
}
