import {Response} from "@angular/http";
import {Post} from "../post/post";
import {Comment} from "../comment/comment";

export class RestResponse {

    private json: any;

    constructor (response: Response) {
        this.json = response.json();
    }

    public getComments(): Comment[] {
        return this.json.content || null;
    }

    public getPosts(): Post[] {
        return this.json.content || null;
    }

    public getPageSize(): number {
        return this.json.size;
    }

    public getTotalPages(): number {
        return this.json.totalPages;
    }

    public getTotalElements(): number {
        return this.json.totalElements;
    }

    public hasNextPage(): boolean {
        return this.getTotalPages() > (this.getCurrentPage() + 1);
    }

    public getNextPage(): number {
        return this.hasNextPage() ? ++ this.json.number : null;
    }

    public getCurrentPage(): number {
        return this.json.number;
    }

}
