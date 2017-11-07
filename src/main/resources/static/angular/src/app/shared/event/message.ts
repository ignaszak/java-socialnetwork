import {MessageInterface} from "./message.interface";

export class Message<T> implements MessageInterface<T> {

    private type: string;
    private message: T;

    constructor (type: string, message: T) {
        this.type = type;
        this.message = message;
    }

    public getType(): string {
        return this.type;
    }

    public getMessage(): T {
        return this.message;
    }
}
