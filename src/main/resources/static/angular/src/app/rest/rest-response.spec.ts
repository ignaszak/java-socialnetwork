import {RestResponse} from "./rest-response";
import {Response} from "@angular/http";
import {Comment} from "../comment/comment";

describe('RestResponse', () => {

    it('should receive pageable single comments', () => {
        let response: Response = jasmine.createSpyObj('Response', ['json']);
        (response.json as jasmine.Spy).and.callFake(() => {
            return {
                content: [
                    {text: 'someText', id: 1}
                ],
                size: 5,
                totalElements: 1,
                totalPages: 1,
                last: true,
                number: 0 // current page
            };
        });
        let restResponse: RestResponse<Comment> = new RestResponse<Comment>(response);
        expect(restResponse.getResponse()[0].text).toEqual('someText');
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
                content: [
                    {text: 'someText', id: 1}
                ],
                size: 1,
                totalElements: 2,
                totalPages: 2,
                last: false,
                number: 0 // current page
            };
        });
        let restResponse: RestResponse<Comment> = new RestResponse<Comment>(response);
        expect(restResponse.getResponse()[0].text).toEqual('someText');
        expect(restResponse.hasNextPage()).toEqual(true);
        expect(restResponse.getNextPage()).toEqual(1);
    });
});
