import {MessageInterface} from "./message.interface";

export class EventHandler {

    private tasks: Object = {};

    public add(type: string, task: Object): EventHandler {
        if (this.tasks.hasOwnProperty(type)) {
            throw new TypeError("Task '" + type + "' already exists!");
        } else {
            this.tasks[type] = task;
        }
        return this;
    }

    public run(message: MessageInterface<any>) {
        if (this.tasks.hasOwnProperty(message.getType())) {
            this.tasks[message.getType()](message);
        } else {
            throw new TypeError("Task '" + message.getType() + "' does not exists!");
        }
    }
}
