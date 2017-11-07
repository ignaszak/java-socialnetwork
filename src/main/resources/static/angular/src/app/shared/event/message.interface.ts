export interface MessageInterface<T> {
    getType(): string;
    getMessage(): T;
}
