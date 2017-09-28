import {Response} from "@angular/http";
import {Post} from "../post/post";
import {Comment} from "../post/comment";

export class RestResponse {

    private json: any;

    constructor (response: Response) {
        this.json = response.json();
    }

    public getComments(): Comment[] {
        return this.json._embedded.comments || null;
    }

    public getPosts(): Post[] {
        return this.json._embedded.posts || null;
    }

    public getPageSize(): number {
        return this.json.page.size;
    }

    public getTotalPages(): number {
        return this.json.page.totalPages;
    }

    public getTotalElements(): number {
        return this.json.page.totalElements;
    }

    public hasNextPage(): boolean {
        return this.getTotalPages() > (this.getCurrentPage() + 1);
    }

    public getNextPage(): number {
        return this.hasNextPage() ? ++ this.json.page.number : null;
    }

    public getCurrentPage(): number {
        return this.json.page.number;
    }

}
