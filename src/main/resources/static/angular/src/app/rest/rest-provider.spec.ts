import {RestProvider} from "./rest-provider";
import {RestProviderInterface} from "./rest-provider.interface";

describe('RestProvider', () => {

    let provider: RestProviderInterface;

    beforeEach(() => {
        provider = new RestProvider();
    });

    it('#getPath should by default return url string', () => {
        let URL = 'some/url';
        expect(provider.getPath(URL)).toEqual(URL);
    });

    it('#getPath should match params', () => {
        let path = provider.getPath('some/url/{param}/{anotherParam}', {
            'param': 'value',
            'anotherParam': 'anotherValue'
        });
        expect(path).toEqual('some/url/value/anotherValue');
    });
});
