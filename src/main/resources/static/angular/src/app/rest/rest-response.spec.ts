import {RestResponse} from "./rest-response";
import {Response} from "@angular/http";

describe('RestResponse', () => {

    it('should receive pageable single comments', () => {
        let response: Response = jasmine.createSpyObj('Response', ['json']);
        (response.json as jasmine.Spy).and.callFake(() => {
            return {
                _embedded: {
                    comments: [
                        {text: 'someText', id: 1}
                    ]
                },
                page: {
                    size: 5,
                    totalElements: 1,
                    totalPages: 1,
                    number: 0 // current page
                }
            };
        });
        let restResponse: RestResponse = new RestResponse(response);
        expect(restResponse.getComments()[0].text).toEqual('someText');
        expect(restResponse.getPageSize()).toEqual(5);
        expect(restResponse.getTotalPages()).toEqual(1);
        expect(restResponse.getTotalElements()).toEqual(1);
        expect(restResponse.hasNextPage()).toEqual(false);
        expect(restResponse.getNextPage()).toEqual(null);
        expect(restResponse.getCurrentPage()).toEqual(0);
    });

    it('should receive pageable many posts', () => {
        let response: Response = jasmine.createSpyObj('Response', ['json']);
        (response.json as jasmine.Spy).and.callFake(() => {
            return {
                _embedded: {
                    posts: [
                        {text: 'someText', id: 1}
                    ]
                },
                page: {
                    size: 1,
                    totalElements: 2,
                    totalPages: 2,
                    number: 0 // current page
                }
            };
        });
        let restResponse: RestResponse = new RestResponse(response);
        expect(restResponse.getComments()).toEqual(null);
        expect(restResponse.getPosts()[0].text).toEqual('someText');
        expect(restResponse.hasNextPage()).toEqual(true);
        expect(restResponse.getNextPage()).toEqual(1);
    });
});
