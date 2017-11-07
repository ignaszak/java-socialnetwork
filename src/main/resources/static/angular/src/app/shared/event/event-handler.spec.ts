import {EventHandler} from "./event-handler";
import {MessageInterface} from "./message.interface";

class Test extends Object {
    test: any;
}

describe('EventHandler', () => {

    it('should run existing task', () => {
        let message: MessageInterface<String> = jasmine.createSpyObj(
            'MessageInterface',
            ['getType', 'getMessage']
        );

        message.getType = jasmine.createSpy('getType()').and.callFake(() => 'TEST_TYPE');
        message.getMessage = jasmine.createSpy('getMessage()').and.callFake(() => 'TEST_MESSAGE');

        let handler: EventHandler = new EventHandler();
        handler.add('TEST_TYPE', (message: MessageInterface<String>) => {
            expect(message.getMessage()).toEqual('TEST_MESSAGE');
        });
        handler.run(message);
    });

    it('should thrown error if task does not exists', () => {
        let message: MessageInterface<String> = jasmine.createSpyObj(
            'MessageInterface',
            ['getType', 'getMessage']
        );
        message.getType = jasmine.createSpy('getType()').and.callFake(() => 'TEST_TYPE');
        let handler: EventHandler = new EventHandler();
        expect(() => handler.run(message)).toThrowError(/TEST_TYPE/);
    });

    it('should thrown error if task is duplicated', () => {
        let message: MessageInterface<String> = jasmine.createSpyObj(
            'MessageInterface',
            ['getType', 'getMessage']
        );
        let handler: EventHandler = new EventHandler();
        handler.add('TEST_TYPE', () => {});
        expect(() => handler.add('TEST_TYPE', () => {})).toThrowError(/TEST_TYPE/);
    });
});
